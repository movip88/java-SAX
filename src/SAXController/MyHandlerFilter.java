/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAXController;

import excepciones.ExcepcionArbolIncompleto;
import java.util.ArrayList;
import java.util.HashMap;
import modelo.Arbre;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Esta clase extiende de DefaultHandel y sirve para leer los datos de un
 * fichero XML
 *
 * @author polmonleonvives
 */
public class MyHandlerFilter extends DefaultHandler {

    private String filtro;
    private String datoFiltrar;
    private int nivelIdentacion;
    private ArrayList<Arbre> resultado;
    private HashMap<String, String> dataArbol;
    private String llave;
    private boolean filtrado;

    /**
     * Este es el contructor se utiliza para asignar los valores que se quieren
     * filtrar que campo y que valor
     *
     * @param filtro String
     * @param datoFiltrar String
     */
    public MyHandlerFilter(String filtro, String datoFiltrar) {
        this.filtro = filtro;
        this.datoFiltrar = datoFiltrar;
    }

    /**
     * Este metodo lo que hace es coger todos los caracteres de dentro de un
     * nodo y los junta para crear un string y comprueva si los valores
     * coinciden con el filtro si estan correctos cambia el estado de la
     * variable de control a true y si el string resultante es más largo que 0
     * cambia el valor de la llave
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
            if (this.llave.equals(this.filtro) && this.datoFiltrar.equals(datos.toString())) {
                this.filtrado = true;
            }
            if (datos.toString().trim().length() > 0) {
                this.dataArbol.replace(this.llave, datos.toString());
            }
        }
    }

    /**
     * ESte es el metodo que se llama cada vez que se acaba un nodo, lo que hace
     * es que si el nivel de identacion es 1 y filtrado es true añade el arbol a
     * la lista de resultados ya que se ha filtrado correctamente
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.nivelIdentacion--;
        if (this.nivelIdentacion == 1 && this.filtrado) {
            try {
                this.resultado.add(new Arbre(dataArbol));
            } catch (ExcepcionArbolIncompleto ex) {
                //Logger.getLogger(MyHandlerFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Este metodo se llama cada vez que se inicia un nodo, cuando el nivel de
     * identación es uno que quiere decir que empieza un arbol se inicializa el
     * hasmap sin valores y se pone la variable de control a false, a parte el
     * metodo cuando el nivel de identacion es 3 que quiere decir que esta en un
     * nodo de informacion de arbol añade la clave a al hash map sin valor
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (this.nivelIdentacion == 1) {
            this.dataArbol = new HashMap<>();
            this.filtrado = false;
        }
        this.nivelIdentacion++;
        if (this.nivelIdentacion == 3) {
            this.llave = qName;
            this.dataArbol.put(qName, "NO HAY DATOS REGISTRADOS");
        }
    }

    /**
     * Cuando empieza el documento se llama a este metodo y inicializa la lista
     * de arboles
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        this.resultado = new ArrayList();
    }

    /**
     * Este metodo devuelve un ArrayList con los arboles encontrados con esas
     * determinadas caracteristicas
     *
     * @return
     */
    public ArrayList<Arbre> getResultado() {
        return resultado;
    }
}
