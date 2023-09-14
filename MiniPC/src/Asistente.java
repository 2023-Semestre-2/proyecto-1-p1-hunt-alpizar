
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jeanp
 */
public class Asistente {
    
    static final String[] REGISTROSVALIDOS = {"AX", "BX", "CX", "DX"};
    static final String[] OPERADORESVALIDO = {"LOAD", "STORE", "MOV", "ADD", "SUB", "INC", "DEC",
        "SWAP", "INT", "JMP", "CMP", "JE", "JNE", "PARAM", "PUSH", "POP"
    };
    static final String[] INTERRUPCIONESVALIDAS = {"20H", "10H", "09H"};
    
    /*static Dictionary<String, String> binarios;
    static{
        binarios= new Hashtable<>();
        binarios.put("LOAD", "0001");
        binarios.put("STORE", "0010");
        binarios.put("MOV","0011");
        binarios.put("SUB","0100");
        binarios.put("ADD","0101");
        binarios.put("AX","0001");
        binarios.put("BX","0010");
        binarios.put("CX","0011");
        binarios.put("DX","0100");
    }*/
    private static int espacioUtilizadoDisco = 0;
    
    public static ArrayList<ArrayList<String[]>> validarArchivos(File[] listaArchivos) throws IOException{
        if(listaArchivos.length > cargarArchivo.getMiPC().getEspacioDisco() * (0.2)){ //se compara la cantidad de archivos con el 20% del tamaño del disco (reservado)
            cargarArchivo.mostrarError(0, "Espacio reservado en disco es insuficiente" );
        }
        
        ArrayList<ArrayList<String[]>> archivosValidados = new ArrayList<ArrayList<String[]>>();
        int contArchivos = 0;
        for(File i : listaArchivos){
            ArrayList<String[]> archivoTransformado =  validarArchivo(i.getAbsolutePath());
            
            if(archivoTransformado == null){
                
                if (contArchivos >= 0 && contArchivos < listaArchivos.length) {
                    File[] temp = new File[listaArchivos.length - 1];
                    System.arraycopy(listaArchivos, 0, temp, 0, contArchivos);
                    System.arraycopy(listaArchivos, contArchivos + 1, temp, contArchivos, listaArchivos.length - contArchivos - 1);
                    listaArchivos = temp;
                    cargarArchivo.setArchivos(listaArchivos);
                    System.out.println("Archivo elimindao");
                } 
             
            }else{
                archivosValidados.add(archivoTransformado);
                contArchivos++;
            }
            
        }
        System.out.println(archivosValidados.size());
        return archivosValidados;
    }
    
    
    /*
        valida que el archvo con la ruta señalada cumpla con las caracteristicas
        del lenguaje asm
    */
    public static ArrayList<String[]> validarArchivo(String nombre) throws IOException{
        System.out.println("Validando el archivo: " + nombre);
        ArrayList<String[]> lista = convertirArchivoLista(nombre);
        System.out.println(Arrays.deepToString(lista.toArray()));
        int noReservadoDisco = (int) (cargarArchivo.getMiPC().getEspacioDisco() * (0.8));//espacio disponible para programas
        if(lista!= null && validarLista(lista)){
            int tamanioLista =  Arrays.asList(lista.toArray()).size();
            if( espacioUtilizadoDisco  + tamanioLista > noReservadoDisco){
                cargarArchivo.mostrarError(0,"No existe sufieciente espacio en memora para almacenar " + nombre);
                return null;
            }
        }else{
            cargarArchivo.mostrarError(0,"El archivo: " + nombre + " no es valido");
            return null;
        }
        return lista;
    }
    
    /*
        ValidarLista
        valida que todos los elementos de la lista de instrucciones
        pertenescan a la gramatica asm
    */
    public static boolean validarLista(ArrayList<String[]> lista){
        int tamanioLista = Arrays.asList(lista).size();
        if(tamanioLista>cargarArchivo.getMiPC().getEspacioDisco() * (0.8) || tamanioLista<1) return false;
        for(String [] str : lista){
            boolean pos1Valida = (Arrays.asList(OPERADORESVALIDO).contains(str[0]));
            boolean pos2Valida = true;
            boolean pos3Valida = true;
            System.out.println(str[0]);
            
            if("PARAM".equals(str[0])  ){
                if(str.length <=4 && str.length >=2 ){
                    System.out.println(str.length);
                    boolean parametrosValido = true;
                    for(int i = 1; i < str.length; i++){
                        parametrosValido = esEntero(str[i]);
                        if(!parametrosValido) break;

                        System.out.println(esEntero(str[i]));
                        System.out.println("\n");
                    }
                    pos3Valida = parametrosValido;
                }else pos3Valida = false;
              
            }else{

                if(str.length == 1){
                    if("INC".equals(str[0]) ||str[0] == "DEC") pos1Valida = true;
                    else pos1Valida = false;
                }
                else if(str.length == 2){
                    if(str[0] == "INT"){
                        pos2Valida = (Arrays.asList(INTERRUPCIONESVALIDAS).contains(str[1]));
                    }
                    else if("JE".equals(str[0]) || "JNE".equals(str[0])|| "JMP".equals(str[0])){
                        pos2Valida = esEntero(str[1]);
                    }else{
                        pos2Valida = (Arrays.asList(REGISTROSVALIDOS).contains(str[1]));
                    }

                }
                
                if(str.length == 3){
                    pos2Valida = (Arrays.asList(REGISTROSVALIDOS).contains(str[1]));
                    if("CMP".equals(str[0]) || "SWAP".equals(str[0])  ){
                        pos3Valida = (Arrays.asList(REGISTROSVALIDOS).contains(str[2]));
                    }else{
                        if("MOV".equals(str[0])){
                            System.out.println("estoyu en mov: " + str[2]);
                            pos3Valida = (esEntero(str[2])) || (Arrays.asList(REGISTROSVALIDOS).contains(str[2])) ; 
                        }
                       //if(Integer.parseInt(str[2])>127) return false;
                    }


                }
            }
            if(!(pos1Valida && pos2Valida && pos3Valida )){
                return false;
            }
            
        }
        return true;
    }
    
    /*
        esEntero
        Valida si un string contiene un numero
    */
    public static boolean esEntero(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        Integer.parseInt(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
}
    
    //lee el contenido del archivo y lo transforma a una lista de  de listas
    //con la siguiente estructura [[operador, registro, valor(opt)],...,...]
    public static ArrayList<String[]> convertirArchivoLista(String nombre) throws FileNotFoundException, IOException{
        List<String> lineasArchivo = new ArrayList<String>();  
        BufferedReader bf = new BufferedReader(new FileReader(nombre));
        String linea = bf.readLine();      
        while (linea != null) {
            if(!linea.equals("")){
                lineasArchivo.add(linea);//ignora lineas en blanco   
            }
            linea = bf.readLine();  
        }
        bf.close();       
        String[] array = lineasArchivo.toArray(new String[0]);
        ArrayList<String[]> lista = new ArrayList<String[]>();
        
        for (String str : array) {
            String[] instruccion = divideString(str);//agrega dicha lista a la lista de instrucciones
            if(instruccion == null) return null;
            String instruccionStr = Arrays.toString(instruccion);
            lista.add(instruccion);
        }
        if(lista.isEmpty()){
            return null;//la lista no puede ser vacia
        }
        return lista;
    
    }
    
    //divide cada una de las lineas en listas
    //por espacio y coma
    //retorna nulo sino 
    public static String[] divideString(String input) {
        String[] dividido = null;
        String[] lista = input.split("[,\\s]+",-1);
        System.out.println("Entrada: "+Arrays.asList(lista));
        dividido = lista;
        int indice = 0;
        for(String str : dividido){
            dividido[indice] = str.toUpperCase();
            indice++;
        }
       
        /*if (lista.length == 2 ) {
            System.out.println(lista[1] == null);
            dividido = new String[3];
            String valor = lista[1].trim();
            String operadorYRegistro = lista[0].trim();    
            //String[] res = divideStringAux(operadorYRegistro);//dividir primer elemento
            //if(res != null){
                dividido[0] = lista[0].toUpperCase();
                dividido[1] = lista[1].toUpperCase();
                dividido[2] = lista[2]; // Value
            
        }
        else if(lista.length == 1){
            dividido = new String[2];
            String operadorYRegistro = lista[0].trim();          
            String[] res = divideStringAux(operadorYRegistro);//dividir primer elemento
            if(res != null){
                dividido[0] = res[0].toUpperCase();
                dividido[1] = res[1].toUpperCase();
            }else{
                dividido = null;
            }
        }
        else{
            dividido = null;
            
        }*/
        System.out.println("salida: "+Arrays.asList(dividido));
        return dividido;
    }
    
    //divide el primer elemento que se obtuvo
    //este elemento contiene ambos el operador y el registro
    //se divide en el espacio
    public static String[] divideStringAux(String operadorYRegistro){
        String[] res = new String[2];
        String[] operadorYRegistroLista = operadorYRegistro.split("\\s+");
        if (operadorYRegistroLista.length == 2) {
            res[0] = operadorYRegistroLista[0]; // Operador
            res[1] = operadorYRegistroLista[1]; // registro
            return res;
        }
        return null;
    }
    
    
    /*
        escogerPosicion
        escoge una posicion aleatoria en memoria para ubicar las entradas
        -las primeras diez posiciones están reservadas
        -la posicion debe tener como minimo n posiciones libres despues del indice
        siendo n el largo del la lista de instrucciones
    */
    public static int escogerPosicion(int largo){
        Random rand = new Random();
        int posicion = 0;
        if(largo == 90) posicion = 10;
        else{
            posicion = rand.nextInt(10, 100-largo);
        }
        return posicion;
    }
    
    /*
        TransformarBinario
        transforma la lista de instrucciones de ensamblador a binario
    */
   /* public static ArrayList<String[]> transformarBinario(ArrayList<String[]> instruccionesASM){
        ArrayList<String[]> listaBinario = new ArrayList<>();
        for(String[] str : instruccionesASM){
            String[] temp = new String[3];
            temp[0] = binarios.get(str[0].toUpperCase());//verifica que el registro pertenezca a la gramatica
            temp[1] = binarios.get(str[1].toUpperCase());
            if(str.length == 3){
                int numero = Integer.parseInt(str[2]);
                String numBin = numeroABinario(numero);
                temp[2] = numBin;
            }else{
                temp[2] = "00000000";
            }
            listaBinario.add(temp);
        }
        return listaBinario;
    }*/
    
    /*
        numeroABinario
        transforma de decimal a binario
    */
    public static String numeroABinario(int numero){
        String binario = Integer.toBinaryString(Math.abs(numero));
        for(int i = 7-binario.length(); i>0; i--){
            binario = "0" + binario;
        }
        if(numero<0){
            binario = "1" + binario;
        }else{
            binario = "0" + binario;
        }
          System.out.println(binario); 
        return binario;
    }
    
    /*
        getDecimal
        transforma de binario a decimal
    */
    public static int getDecimal(String valorBin) {
        int res = Integer.parseInt(valorBin.substring(1), 2);
        if (valorBin.charAt(0) == '1') {
            return res * -1;
        }
        return res;
    }
}
