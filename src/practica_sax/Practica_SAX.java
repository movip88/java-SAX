package practica_sax;

import SAXController.MyHandler;
import SAXController.MyHandlerFilter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import modelo.Arbre;
import org.xml.sax.SAXException;

/**
 *
 * @author polmonleonvives
 */
public class Practica_SAX {

    static Utilidades u = new Utilidades();
    static MyHandler myHandler = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            myHandler = new MyHandler();
            parser.parse(new File("datos.xml"), myHandler);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Practica_SAX.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (myHandler != null) {
            int op = menu();
            while (op != 5) {
                switch (op) {
                    case 1:
                        mostrarEstructuraXML();
                        break;
                    case 2:
                        verNumeroDeArboles();
                        break;
                    case 3:
                        mostrarArbolesConTodoElContenido();
                        break;
                    case 4:
                        busquedaParametizable();
                        break;
                }
                op = menu();
            }
        } else {
            System.out.println("Lo sentimos mucho no se han podido cargar los datos");
        }

    }

    private static void mostrarEstructuraXML() {
        System.out.println("Esta es la estructura del XML:");
        System.out.println(myHandler.getEstructuraXML());
    }

    private static void verNumeroDeArboles() {
        System.out.println("Este es el numero de arboles que hay registrados:");
        System.out.println(myHandler.getNumeroArboles());
    }

    private static void mostrarArbolesConTodoElContenido() {
        System.out.println("Hay "+myHandler.getArbresComplets().size()+" arboles con todo el contenido");
        System.out.println("Estos son los arboles con todo el contenido:");
        for (Arbre a : myHandler.getArbresComplets()) {
            System.out.println(a);
        }
    }

    /**
     * Este metodo te enseña todoas lo opciones que tienes para filtrar, y con
     * las opcionesz que escojas crea un nuevo MyHandlerFilter que busca los
     * arboles con esas caracteristicas y te los va mostrando de 10 en 10
     */
    private static void busquedaParametizable() {
        int count = 0;
        for (String s : myHandler.getFiltros().keySet()) {
            count++;
            System.out.println(count + ": " + s);
        }
        int filtro = u.pedirNumeroEntero("Elige uno de estos filtros", "tiene que ser un numero entero") - 1;
        if (filtro >= 0 && filtro < myHandler.getFiltros().keySet().size()) {
            count = 0;
            for (String s : myHandler.getFiltros().get(myHandler.getFiltros().keySet().toArray()[filtro])) {
                count++;
                System.out.println(count + ": " + s);
            }
            int filtroFiltrado = u.pedirNumeroEntero("Elige uno de estos filtros", "tiene que ser un numero entero") - 1;
            if (filtroFiltrado >= 0 && filtroFiltrado < myHandler.getFiltros().get(myHandler.getFiltros().keySet().toArray()[filtro]).size()) {
                MyHandlerFilter myHndlerFilter = null;
                try {
                    SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
                    myHndlerFilter = new MyHandlerFilter((String) myHandler.getFiltros().keySet().toArray()[filtro], myHandler.getFiltros().get(myHandler.getFiltros().keySet().toArray()[filtro]).get(filtroFiltrado));
                    parser.parse(new File("datos.xml"), myHndlerFilter);
                } catch (ParserConfigurationException | SAXException | IOException ex) {
                    Logger.getLogger(Practica_SAX.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Hay "+myHndlerFilter.getResultado().size()+" arboles con las carecteristicas especificadas");
                count = 0;
                for (Arbre a : myHndlerFilter.getResultado()) {
                    System.out.println(a);
                    count++;
                    if (count == 10) {
                        if (!u.asegurarAccion("Deseas segir viendo más registros de arboles con estas carecteristicas?")) {
                            break;
                        }
                        count = 0;
                    }
                }

            } else {
                System.out.println("No se encuentra la opcion introducida...");
            }
        } else {
            System.out.println("No se encuentra la opcion introducida...");
        }
    }

    private static int menu() {
        System.out.println("=======================================");
        System.out.println("    1-Mostrar la estructura del XML");
        System.out.println("    2-Ver el numero total de arboles registrados");
        System.out.println("    3-Ver los arboles que tienen todo el contenido");
        System.out.println("    4-Busqueda parametizable");
        System.out.println("    5-Salir");
        int num = u.pedirNumeroEntero("        Introduce tu opcion:", "Introduce un numero entero");
        System.out.println("=======================================");
        return num;
    }
}
