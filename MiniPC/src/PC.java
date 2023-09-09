
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

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
    private int[] parametros = new int[3];

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
    
    /*
    Incrementa en 1 el valor del AC
    */
    public void ejecutarINC() {
        int valorAC = registros.get("AC").getValor();
        int nuevoValor = valorAC + 1;
        registros.get("AC").setValor(nuevoValor);
    }
    /*
    INC AX
    Incrementa en 1 el valor ubicadoen el registro 
    */
    public void ejecutarINCRegistro(String registro) {
        int valorReg = registros.get(registro).getValor();
        int nuevoValor =valorReg + 1;
        registros.get(registro).setValor(nuevoValor);
    }
    /*
    Decrementa en 1 el valor del AC
    */
    public void ejecutarDEC() {
        int valorAC = registros.get("AC").getValor();
        int nuevoValor = valorAC - 1;
        registros.get("AC").setValor(nuevoValor);
    }
    
    /*
    DEC AX
    Decrementa en 1 el valor ubicado en el registro 
    */
    public void ejecutarDECRegistro(String registro) {
        int valorReg = registros.get(registro).getValor();
        int nuevoValor =valorReg - 1;
        registros.get(registro).setValor(nuevoValor); 
    }
    
    /*
    SWAP AX, BX 
    Intercambian los valores entre los registros 
    */
    public void ejecutarSwap(String registro1,String registro2) {
        int valorReg1 = registros.get(registro1).getValor();
        int valorReg2 = registros.get(registro2).getValor();
        registros.get(registro1).setValor(valorReg2); 
        registros.get(registro2).setValor(valorReg1); 
    }
    
    /*
    INT 20H
    Finaliza el programa 
    */
    public void ejecutarINT20H() {
        /*Aqui podríamos finalizar el programa en cargarArchivo.java*/
    }
    
    /*
    INT 10H
    Imprime en pantalla el valor del DX 
    */
    public int ejecutarINT10H() {
        int valorReg = registros.get("DX").getValor();
        return valorReg ; 
    }
    
    
    /*
    Entrada del teclado (solo numérico 0-255), 
    el valor se guarda en el DX, finaliza con un ENTER
    */  
    public void ejecutarINT09H() {
             
    }
    
    /*
    JMP [+/-Desplazamiento]
    Salta a la instrucción, según su desplazamiento 
    */
    public int ejecutarJMP(int posActual, int cantSaltos) {
        int saltos= posActual + cantSaltos;
        return saltos;
    }
    
    /*
    CMP Reg1, Reg2 
    Compara el contenido de Reg1con respecto a Reg2. 
    Tanto Reg1o Reg2 son registros 
    */
    public boolean ejecutarCMP(String registro1,String registro2) {
        int valorReg1 = registros.get(registro1).getValor();
        int valorReg2 = registros.get(registro2).getValor();
        return valorReg1==valorReg2;
    }
    /*
    JE (si es igual)
    Salta a la instrucción si es igual según su desplazamiento. 
    Tomar en cuenta el desbordamiento. 
    */
    public boolean ejecutarJE(String registro,int valor) {
        int valorReg = registros.get(registro).getValor();
        return valorReg==valor; 
    }
    /*
    JNE (si no es igual)
    Salta a la instrucción si no es igual según su desplazamiento. 
    Tomar en cuenta el desbordamiento. 
    */
    public boolean ejecutarJNE(String registro,int valor) {
        int valorReg = registros.get(registro).getValor();
        return valorReg != valor;
    }
    /*
    PARAM v1, v2, .. vN 

    Forma de representar los parámetros de entrada. Los 
    valores v1, v2 .. vn serán numéricos y se guardará en pila. 
    Máximo 3 parámetros de entrada 
    */
    public void ejecutarPARAM(List<Integer> valores) {
        for (int i = 0; i < 3; i++) {
            if (i < valores.size()) {
                this.parametros[i] = valores.get(i);
            }/*preguntar a Hunt el uso de PARAM*/
        }
    }
    
    /*
    PUSH AX
    
    Guarda en la pila el valor del registro AX 
    */
    public void ejecutarPUSH(String registro) {
       /*tanto en push y pop podemos usar las mismas funcionalidades de java
        pero creo que aun no tenemos una pila, nose si la inicializamos aquí 
        o en cargarArchivo.java*/ 
    }
    
    /*
    POP AX
    
    Sacar valores de la pila, será usado para los parámetros de
    entrada, y lo almacena en un registro 
    */
    public void ejecutarPOP(String registro) {
        /*tanto en push y pop podemos usar las mismas funcionalidades de java
        pero creo que aun no tenemos una pila, nose si la inicializamos aquí 
        o en cargarArchivo.java*/ 
    }
    
    
} 
