
import java.util.ArrayList;
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
public class PC {
    static Dictionary<String, Registro> registros;
    static{
        registros= new Hashtable<>();
        registros.put("0001", new Registro("AX"));
        registros.put("0010", new Registro("BX"));
        registros.put("0011", new Registro("CX"));
        registros.put("0100", new Registro("DX"));
        registros.put("AC", new Registro("AC"));
        registros.put("PC", new Registro("PC"));
        registros.put("IR", new Registro("IR"));
    }
    private ArrayList<String[]> instruccionesASM;
    private ArrayList<String[]> instruccionesbin;

    public PC() {
    }

    

    public ArrayList<String[]> getInstruccionesASM() {
        return instruccionesASM;
    }

    public void setInstruccionesASM(ArrayList<String[]> instruccionesASM) {
        this.instruccionesASM = instruccionesASM;
    }

    public ArrayList<String[]> getInstruccionesbin() {
        return instruccionesbin;
    }

    public void setInstruccionesbin(ArrayList<String[]> instruccionesbin) {
        this.instruccionesbin = instruccionesbin;
    }

    public static Dictionary<String, Registro> getRegistros() {
        return registros;
    }

    public static void setRegistros(Dictionary<String, Registro> registros) {
        PC.registros = registros;
    }
    
    /*
        ejecutarMov
        carga el valor  al registro indicado
    */
    public void ejecutarMov(String registro, String valorBin) {
        int valor = Asistente.getDecimal(valorBin);
        registros.get(registro).setValor(valor);
    }

    /*
        ejecutarLoad
        carga el valor del registro indicado al registro AC
    */
    public void ejecutarLoad(String registro) {
        registros.get("AC").setValor(registros.get(registro).getValor());
    }

    /*
        ejecutarAdd
        suma el valor del registro indicado al registro ac
    */
    public void ejecutarAdd(String registro) {
        int valorAC = registros.get("AC").getValor();
        int valorReg = registros.get(registro).getValor();
        int nuevoValor = valorAC + valorReg;
        registros.get("AC").setValor(nuevoValor);
    }

    /*
        ejecutarSub
        resta el valor del registro indicado al registro ac
    */
    public void ejecutarSub(String registro) {
        int valorAC = registros.get("AC").getValor();
        int valorReg = registros.get(registro).getValor();
        int nuevoValor = valorAC - valorReg;
        registros.get("AC").setValor(nuevoValor);
    }
    
    /*
        ejecutarStore
        guarda el valor del registro AC en el registro indicado
    */
    public void ejecutarStore(String registro) {
        int valorAC = registros.get("AC").getValor();
        registros.get(registro).setValor(valorAC);
    }
    
    
}
