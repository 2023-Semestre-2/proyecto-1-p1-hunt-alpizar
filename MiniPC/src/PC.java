
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jeanp
 */
public class PC {
    private static ArrayList<BCP> bcps = new ArrayList<BCP>();
    private ArrayList<String[]> instruccionesASM;
    private int[] parametros = new int[5];
    private int espacioMemoria;
    private ArrayList<Object> memoria;
    private int espacioDisco;
    private ArrayList<Object> disco;

    public PC() {
        inicializarAlmacenamiento();
    }

    private void inicializarAlmacenamiento(){
        System.out.println("inicializarAlmacenamiento");
        try {
           List<String> lineasArchivo = new ArrayList<String>();  
           BufferedReader bf = new BufferedReader(new FileReader("src/config.txt"));
           String linea = bf.readLine();      
           while (linea != null) {
               if(!linea.equals("")){
                   lineasArchivo.add(linea);//ignora lineas en blanco   
               }
               linea = bf.readLine();  
           }
           bf.close();       
           String[] array = lineasArchivo.toArray(new String[0]);

      
           String[] infoMemoria = array[0].split(":");
           espacioMemoria = Integer.parseInt(infoMemoria[1]);
           if(espacioMemoria < 256){
               cargarArchivo.mostrarError(1, "El tamaño de la memoria no puede ser menor a 256");
               return;
           }
           
           String[] infoDisco = array[1].split(":");
           espacioDisco = Integer.parseInt(infoDisco[1]);
           if(espacioDisco < 512){
               cargarArchivo.mostrarError(1,"El tamaño del disco  no puede ser menor a 512");
               return;
           }
           System.out.println(espacioMemoria);
           System.out.println(espacioDisco);
           memoria = new ArrayList<Object>(espacioMemoria);
           disco = new ArrayList<Object>(espacioDisco);
           
            DefaultTableModel modeloMemoria =  (DefaultTableModel)cargarArchivo.tablaMemoria.getModel();
            modeloMemoria.setRowCount(espacioMemoria);
            cargarArchivo.tablaMemoria.setModel(modeloMemoria);
            
            DefaultTableModel modeloDisco =  (DefaultTableModel)cargarArchivo.tablaDisco.getModel();
            modeloDisco.setRowCount(espacioDisco);
            cargarArchivo.tablaDisco.setModel(modeloDisco);
            
        } catch (Exception e) {
            
           System.out.println("no se ha encontrado el archivo");
        }
        
        
        //ArrayList<String[]> lista = new ArrayList<String[]>();
        //String[] dividido = null;
        //String[] lista = input.split(",");
        
    }

    public ArrayList<String[]> getInstruccionesASM() {
        return instruccionesASM;
    }

    public void setInstruccionesASM(ArrayList<String[]> instruccionesASM) {
        this.instruccionesASM = instruccionesASM;
    }

    public static Dictionary<String, Registro> getRegistros() {
        return  bcps.get(0).getRegistros();
    }

    public static void setRegistros(Dictionary<String, Registro> registros) {
        bcps.get(0).setRegistros(registros);
    }

    
    
    

    /*
        ejecutarLoad
        carga el valor del registro indicado al registro AC
    */
    public void ejecutarLoad(String registro) {
        bcps.get(0).getRegistros().get("AC")
                .setValor(
                        bcps.get(0).getRegistros().get(registro).getValor());
    }

    /*
        ejecutarStore
        guarda el valor del registro AC en el registro indicado
    */
    public void ejecutarStore(String registro) {
        int valorAC = bcps.get(0).getRegistros().get("AC").getValor();
        bcps.get(0).getRegistros().get(registro).setValor(valorAC);
    }
    
    /*
        ejecutarMov
        carga el valor del registro x al registro destino
    */
    public void ejecutarMovRegistro(String registroDestino, String registroX) {
        int valor = bcps.get(0).getRegistros().get(registroX).getValor();
        bcps.get(0).getRegistros().get(registroDestino).setValor(valor);
    }
    
    
    /*
        ejecutarMov
        carga el valor  al registro indicado
    */
    public void ejecutarMov(String registro, String valorBin) {
        int valor = Asistente.getDecimal(valorBin);
        bcps.get(0).getRegistros().get(registro).setValor(valor);
    }
    
    /*
        ejecutarAdd
        suma el valor del registro indicado al registro ac
    */
    public void ejecutarAdd(String registro) {
        int valorAC = bcps.get(0).getRegistros().get("AC").getValor();
        int valorReg = bcps.get(0).getRegistros().get(registro).getValor();
        int nuevoValor = valorAC + valorReg;
        bcps.get(0).getRegistros().get("AC").setValor(nuevoValor);
    }

    /*
        ejecutarSub
        resta el valor del registro indicado al registro ac
    */
    public void ejecutarSub(String registro) {
        int valorAC = bcps.get(0).getRegistros().get("AC").getValor();
        int valorReg = bcps.get(0).getRegistros().get(registro).getValor();
        int nuevoValor = valorAC - valorReg;
        bcps.get(0).getRegistros().get("AC").setValor(nuevoValor);
    }
    
    
    
    /*
    Incrementa en 1 el valor del AC
    */
    public void ejecutarINC() {
        int valorAC = bcps.get(0).getRegistros().get("AC").getValor();
        int nuevoValor = valorAC + 1;
        bcps.get(0).getRegistros().get("AC").setValor(nuevoValor);
    }
    /*
    INC AX
    Incrementa en 1 el valor ubicadoen el registro 
    */
    public void ejecutarINCRegistro(String registro) {
        int valorReg = bcps.get(0).getRegistros().get(registro).getValor();
        int nuevoValor =valorReg + 1;
        bcps.get(0).getRegistros().get(registro).setValor(nuevoValor);
    }
    /*
    Decrementa en 1 el valor del AC
    */
    public void ejecutarDEC() {
        int valorAC = bcps.get(0).getRegistros().get("AC").getValor();
        int nuevoValor = valorAC - 1;
        bcps.get(0).getRegistros().get("AC").setValor(nuevoValor);
    }
    
    /*
    DEC AX
    Decrementa en 1 el valor ubicado en el registro 
    */
    public void ejecutarDECRegistro(String registro) {
        int valorReg = bcps.get(0).getRegistros().get(registro).getValor();
        int nuevoValor =valorReg - 1;
        bcps.get(0).getRegistros().get(registro).setValor(nuevoValor); 
    }
    
    /*
    SWAP AX, BX 
    Intercambian los valores entre los registros 
    */
    public void ejecutarSwap(String registro1,String registro2) {
        int valorReg1 = bcps.get(0).getRegistros().get(registro1).getValor();
        int valorReg2 = bcps.get(0).getRegistros().get(registro2).getValor();
        bcps.get(0).getRegistros().get(registro1).setValor(valorReg2); 
        bcps.get(0).getRegistros().get(registro2).setValor(valorReg1); 
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
        int valorReg = bcps.get(0).getRegistros().get("DX").getValor();
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
        int valorReg1 = bcps.get(0).getRegistros().get(registro1).getValor();
        int valorReg2 = bcps.get(0).getRegistros().get(registro2).getValor();
        return valorReg1==valorReg2;
    }
    /*
    JE (si es igual)
    Salta a la instrucción si es igual según su desplazamiento. 
    Tomar en cuenta el desbordamiento. 
    */
    public boolean ejecutarJE(String registro,int valor) {
        int valorReg = bcps.get(0).getRegistros().get(registro).getValor();
        return valorReg==valor; 
    }
    /*
    JNE (si no es igual)
    Salta a la instrucción si no es igual según su desplazamiento. 
    Tomar en cuenta el desbordamiento. 
    */
    public boolean ejecutarJNE(String registro,int valor) {
        int valorReg = bcps.get(0).getRegistros().get(registro).getValor();
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

    public ArrayList<Object> getMemoria() {
        return memoria;
    }

    public void setMemoria(ArrayList<Object> memoria) {
        this.memoria = memoria;
    }

    public ArrayList<Object> getDisco() {
        return disco;
    }

    public void setDisco(ArrayList<Object> disco) {
        this.disco = disco;
    }

    public int getEspacioMemoria() {
        return espacioMemoria;
    }

    public void setEspacioMemoria(int espacioMemoria) {
        this.espacioMemoria = espacioMemoria;
    }

    public int getEspacioDisco() {
        return espacioDisco;
    }

    public void setEspacioDisco(int espacioDisco) {
        this.espacioDisco = espacioDisco;
    }

    public static ArrayList<BCP> getBcps() {
        return bcps;
    }

    public static void setBcps(ArrayList<BCP> bcps) {
        PC.bcps = bcps;
    }
    
    public static void addBCP(BCP nuevo){
        PC.bcps.add(nuevo);
    }
    
    public static BCP getBCPat(int indice){
        return PC.bcps.get(indice);
    }
    
    public static void setBCPat(int indice, BCP nuevo){
        PC.bcps.set(indice, nuevo);
    }
    
    
    
    
    
    
} 
