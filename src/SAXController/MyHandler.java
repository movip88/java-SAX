package SAXController;

import excepciones.ExcepcionArbolIncompleto;
import java.util.ArrayList;
import java.util.Arrays;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.HashMap;
import modelo.Arbre;

/**
 * Esta clase extiende de DefaultHandel y sirve para leer los datos de un
 * fichero XML
 *
 * @author polmonleonvives
 */
public class MyHandler extends DefaultHandler {

    private StringBuilder estruxturtaXML;
    private int numeroArboles = 0;
    private int nivelIdentacion = 0;
    private HashMap<String, String> dataArbol;
    private String llave;
    private ArrayList<Arbre> arbresComplets;
    private HashMap<String, ArrayList<String>> filtros;
    private ArrayList<String> camposFiltrables;

    /**
     * Este metodo se ejecuta cada vez que hay texto dentro de un nodo, se coge
     * el texto y si es más grande que 0 se añade al map de data de arbol con la
     * llave del nodo activo y el valor es el texto que hay dentro
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (this.nivelIdentacion == 3) {
            StringBuilder datos = new StringBuilder();
            for (int i = start; i < start + length; i++) {
                datos.append(ch[i]);
            }
            if (datos.toString().trim().length() > 0) {
                this.dataArbol.put(this.llave, datos.toString());
                if (this.filtros.containsKey(this.llave)) {
                    if (!this.filtros.get(this.llave).contains(datos.toString())) {
                        this.filtros.get(this.llave).add(datos.toString());
                    }
                }
            }
        }
    }

    /**
     * Este metodo se llama cada vez que se acaba un nodo controla el nivel de
     * identacion, va generando la estructura del XML y si el nivle de
     * identacion es 1 intenta añadir el arbol a la lista si no esta completa se
     * genera una execpcion y no se guarada ya que no esta completa
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.nivelIdentacion--;
        if (!this.estruxturtaXML.toString().contains("</" + qName)) {
            for (int i = 0; i < this.nivelIdentacion; i++) {
                this.estruxturtaXML.append("\t");
            }
            this.estruxturtaXML.append("</");
            this.estruxturtaXML.append(qName);
            this.estruxturtaXML.append(">");
            this.estruxturtaXML.append("\n");
        }
        if (this.nivelIdentacion == 1) {
            try {
                this.arbresComplets.add(new Arbre(dataArbol));
            } catch (ExcepcionArbolIncompleto ex) {
                //Logger.getLogger(MyHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Este es el metodo que se ejecuta cada vez que se incia un nodo, va
     * generaqndo la estructura del XML, controla el nivel de identación y si el
     * nivel de identacion es 1 cuenta los arboles que lleva y inicializa un
     * HasMap vacio, si el nivel de identacion es 3 guarda el nombre del nodo y
     * si no esta en el map de filtros lo añade
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!this.estruxturtaXML.toString().contains(qName)) {
            for (int i = 0; i < this.nivelIdentacion; i++) {
                this.estruxturtaXML.append("\t");
            }
            this.estruxturtaXML.append("<");
            this.estruxturtaXML.append(qName);
            this.estruxturtaXML.append(">");
            this.estruxturtaXML.append("\n");
        }
        if (this.nivelIdentacion == 1) {
            this.numeroArboles++;
            this.dataArbol = new HashMap<>();
        }
        this.nivelIdentacion++;
        if (this.nivelIdentacion == 3) {
            this.llave = qName;
            if (this.camposFiltrables.contains(qName)) {
                if (!this.filtros.containsKey(qName)) {
                    this.filtros.put(qName, new ArrayList());
                }
            }
        }
    }

    /**
     * Este metodo inicializa el StringBuilder que guarda la estructura del XML,
     * el ArrayList que guarda los arboles completos, el HashMap de filtros y la
     * lista de campos filtrables
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        this.estruxturtaXML = new StringBuilder();
        this.arbresComplets = new ArrayList();
        this.filtros = new HashMap();
        String[] strings = {"tipusElement", "alcada", "categoriaArbrat", "tipAigua", "tipReg", "tipSuperf", "tipSuport", "cobertaEscocell", "midaEscocell", "voraEscocell"};
        this.camposFiltrables = new ArrayList<>(Arrays.asList(strings));
    }

    /**
     * Este metodo devuelve un String con la estructura del XML
     *
     * @return String
     */
    public String getEstructuraXML() {
        return this.estruxturtaXML.toString();
    }

    /**
     * Este metodo devuelve un int con el numero total de Arboles que hay
     *
     * @return int
     */
    public int getNumeroArboles() {
        return this.numeroArboles;
    }

    /**
     * Este metodo devuelve una lista con todos los arboles completos que ha
     * encontroado
     *
     * @return ArrayList
     */
    public ArrayList<Arbre> getArbresComplets() {
        return this.arbresComplets;
    }

    /**
     * Este metodo devuelve un diccionario con todos los filtros posibles y sus
     * valores
     *
     * @return HashMap
     */
    public HashMap<String, ArrayList<String>> getFiltros() {
        return filtros;
    }
}
