
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
    private  String estado;//nuevo, preparado, ejecución, en espera, finalizado
    private int PC;
    private int cpuActual;
    private Date tiempoInicio;
    private Date tiempoEmpleado;
    private String estadoES;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPC() {
        return PC;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public int getCpuActual() {
        return cpuActual;
    }

    public void setCpuActual(int cpuActual) {
        this.cpuActual = cpuActual;
    }

    public Date getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(Date tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public Date getTiempoEmpleado() {
        return tiempoEmpleado;
    }

    public void setTiempoEmpleado(Date tiempoEmpleado) {
        this.tiempoEmpleado = tiempoEmpleado;
    }

    public String getEstadoES() {
        return estadoES;
    }

    public void setEstadoES(String estadoES) {
        this.estadoES = estadoES;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getAlcance() {
        return alcance;
    }

    public void setAlcance(int alcance) {
        this.alcance = alcance;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public ArrayList<Integer> getPila() {
        return pila;
    }

    public void setPila(ArrayList<Integer> pila) {
        this.pila = pila;
    }
    
}
