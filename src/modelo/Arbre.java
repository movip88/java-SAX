package modelo;

import java.util.HashMap;
import excepciones.ExcepcionArbolIncompleto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Este es la clase encargada de controlar la declaración de arboles
 *
 * @author polmonleonvives
 */
public class Arbre {

    private String codi;
    private double posicioX_ETRS89;
    private double posicioY_ETRS89;
    private double latitud_WGS84;
    private double longitud_WGS84;
    private String tipusElement;
    private String espaiVerd;
    private String adreca;
    private String alcada;
    private int catEspecieId;
    private String nomCientific;
    private String nomEsp;
    private String nomCat;
    private String categoriaArbrat;
    private String ampladaVorera;
    private String plantacioDT;
    private String tipAigua;
    private String tipReg;
    private String tipSuperf;
    private String tipSuport;
    private String cobertaEscocell;
    private String midaEscocell;
    private String voraEscocell;

    /**
     * Este es el constructor al cual se le pasa un HashMap con los valores si
     * encuentra que falatan datos en el HashMap lanza un excepcion de arbol
     * incompleto y no lo crea
     *
     * @param data HashMap
     * @throws ExcepcionArbolIncompleto
     */
    public Arbre(HashMap<String, String> data) throws ExcepcionArbolIncompleto {
        String[] strings = {"codi", "posicioX_ETRS89", "posicioY_ETRS89", "latitud_WGS84", "longitud_WGS84", "tipusElement", "espaiVerd", "adreca", "alcada", "catEspecieId", "nomCientific", "nomEsp", "nomCat", "categoriaArbrat", "ampladaVorera", "plantacioDT", "tipAigua", "tipReg", "tipSuperf", "tipSuport", "cobertaEscocell", "midaEscocell", "voraEscocell"};
        List<String> infoArbol = new ArrayList<>(Arrays.asList(strings));

        for (String s : infoArbol) {
            if (!data.containsKey(s)) {
                throw new ExcepcionArbolIncompleto();
            }
        }

        this.codi = data.get("codi");
        this.posicioX_ETRS89 = data.get("posicioX_ETRS89").equals("NO HAY DATOS REGISTRADOS") ? 0 : Double.parseDouble(data.get("posicioX_ETRS89"));
        this.posicioY_ETRS89 = data.get("posicioY_ETRS89").equals("NO HAY DATOS REGISTRADOS") ? 0 : Double.parseDouble(data.get("posicioY_ETRS89"));
        this.latitud_WGS84 = data.get("latitud_WGS84").equals("NO HAY DATOS REGISTRADOS") ? 0 : Double.parseDouble(data.get("latitud_WGS84"));
        this.longitud_WGS84 = data.get("longitud_WGS84").equals("NO HAY DATOS REGISTRADOS") ? 0 : Double.parseDouble(data.get("longitud_WGS84"));
        this.tipusElement = data.get("tipusElement");
        this.espaiVerd = data.get("espaiVerd");
        this.adreca = data.get("adreca");
        this.alcada = data.get("alcada");
        this.catEspecieId = data.get("catEspecieId").equals("NO HAY DATOS REGISTRADOS") ? 0 : Integer.parseInt(data.get("catEspecieId"));
        this.nomCientific = data.get("nomCientific");
        this.nomEsp = data.get("nomEsp");
        this.nomCat = data.get("nomCat");
        this.categoriaArbrat = data.get("categoriaArbrat");
        this.ampladaVorera = data.get("ampladaVorera");
        this.plantacioDT = data.get("plantacioDT");
        this.tipAigua = data.get("tipAigua");
        this.tipReg = data.get("tipReg");
        this.tipSuperf = data.get("tipSuperf");
        this.tipSuport = data.get("tipSuport");
        this.cobertaEscocell = data.get("cobertaEscocell");
        this.midaEscocell = data.get("midaEscocell");
        this.voraEscocell = data.get("voraEscocell");
    }

    /**
     * Este metodo devueve un String con toda la información de una arbre
     * @return String
     */
    @Override
    public String toString() {
        return "Arbre{" + "codi=" + codi + ", posicioX_ETRS89=" + posicioX_ETRS89 + ", posicioY_ETRS89=" + posicioY_ETRS89 + ", latitud_WGS84=" + latitud_WGS84 + ", longitud_WGS84=" + longitud_WGS84 + ", tipusElement=" + tipusElement + ", espaiVerd=" + espaiVerd + ", adreca=" + adreca + ", alcada=" + alcada + ", catEspecieId=" + catEspecieId + ", nomCientific=" + nomCientific + ", nomEsp=" + nomEsp + ", nomCat=" + nomCat + ", categoriaArbrat=" + categoriaArbrat + ", ampladaVorera=" + ampladaVorera + ", plantacioDT=" + plantacioDT + ", tipAigua=" + tipAigua + ", tipReg=" + tipReg + ", tipSuperf=" + tipSuperf + ", tipSuport=" + tipSuport + ", cobertaEscocell=" + cobertaEscocell + ", midaEscocell=" + midaEscocell + ", voraEscocell=" + voraEscocell + '}';
    }
}
