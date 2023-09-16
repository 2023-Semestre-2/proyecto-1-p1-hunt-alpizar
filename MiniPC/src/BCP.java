
import java.util.ArrayList;
import java.util.Arrays;
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
 Estados; nuevo, preparado, ejecución, en espera, finalizado
Contador del programa (ubicación del programa cargado en memoria)
Registros AC, AX, BX, CX, DX
Información de la pila: definir tamaño de 5, y tomar en cuenta error de desbordamiento
Información contable; el cpu donde se está ejecutando, tiempo de inicio, tiempo empleado.
Información del estado de E/S; lista de archivos abiertos
Enlace al siguiente BPC
dirección inicio (Base)
Tamaño del proceso (Alcance)
Prioridad 

 */

//cada bcp usa 12 lineas
public class BCP {
    private String identificador;
    private  String estado;//nuevo, preparado, ejecución, en espera, finalizado
    private int PC;//ubicación del programa cargado en memoria
    private ArrayList<Integer> pila = new ArrayList<>(Arrays.asList(null, null, null, null, null));
    private int cpuActual;
    private Date tiempoInicio;
    private Date tiempoEmpleado;
    private String estadoInterrupcion;
    private int siguienteBPC;//primera linea del siguiente BPC
    private int base;//linea inicio
    private int alcance; //cantidad de lineas de codigo
    private String prioridad;
    
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

    public BCP(String identificador) {
        this.identificador = identificador;
        this.estado = "nuevo";
        this.estadoInterrupcion = "En espera";
        this.prioridad = "Normal";
        this.cpuActual = 1;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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

    public ArrayList<Integer> getPila() {
        return pila;
    }

    public void setPila(ArrayList<Integer> pila) {
        this.pila = pila;
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

    public String getEstadoInterrupcion() {
        return estadoInterrupcion;
    }

    public void setEstadoInterrupcion(String estadoInterrupcion) {
        this.estadoInterrupcion = estadoInterrupcion;
    }

    public int getSiguienteBPC() {
        return siguienteBPC;
    }

    public void setSiguienteBPC(int siguienteBPC) {
        this.siguienteBPC = siguienteBPC;
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

    public static Dictionary<String, Registro> getRegistros() {
        return registros;
    }

    public static void setRegistros(Dictionary<String, Registro> registros) {
        BCP.registros = registros;
    }
    
   
    
}
