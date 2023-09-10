
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jeanp
 */
public class BCP {
    private  String estado;
    private int PC;
    private int cpuActual;
    private Date tiempoInicio;
    private Date tiempoEmpleado;
    private int base;
    private int alcance; //cantidad de lineas de codigo
    private String prioridad;
    private ArrayList<Integer> pila = new ArrayList<>(5);
    static Dictionary<String, Registro> registros;
    static{
        registros= new Hashtable<>();
        registros.put("AX", new Registro("AX")); //0001
        registros.put("BX", new Registro("BX"));//0010
        registros.put("CX", new Registro("CX"));//0011
        registros.put("DX", new Registro("DX"));//0100
        registros.put("AC", new Registro("AC"));
        registros.put("PC", new Registro("PC"));
        registros.put("IR", new Registro("IR"));
        
    }
    
    public static Dictionary<String, Registro> getRegistros() {
        return registros;
    }

    public static void setRegistros(Dictionary<String, Registro> registros) {
        BCP.registros = registros;
    }
    
}
