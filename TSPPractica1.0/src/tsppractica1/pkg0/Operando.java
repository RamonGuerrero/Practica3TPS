/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tsppractica1.pkg0;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 *class Operando nos sirve para obtener un modo de direccionamiento segun sea el operando
 * @author RamonGuerrero
 */
public class Operando {
    List<String>  Registros= Arrays.asList("X","Y","SP","PC");
    List<String>  Acumuladores= Arrays.asList("A","B","D");
    String modoDireccionamiento=null;
    String estado="";
    /**
     * Validarlo este metodo valida si es valido el operando
     * @param ModDir String son los modos de direccionamiento que puede tener el codop
     * @param operando String es el operando 
     * @param porCal byte son los bytes por calcular
     * @return boolean si el operando es valido
     */
    public boolean Validarlo(String ModDir,String operando,byte porCal){
        boolean banderaOperando=false;
        String modoAnalizar=Analizador(operando,ModDir);
        System.out.println("Modo Analizar: "+modoAnalizar);
        switch(modoAnalizar){
            case "SIN":
                    banderaOperando=true;
                    modoDireccionamiento="";
                    System.out.println("No contiene modo de direccionamiento");
                    break;
            case "INH":
                    banderaOperando=Ineherente(operando,porCal);
                    modoDireccionamiento=modoAnalizar;
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando+" PorCal: "+porCal);
                    break;
            case "REL8":
                    banderaOperando= relativo8(operando);
                    modoDireccionamiento=modoAnalizar;
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);
                    break;
            case "REL9":
                    banderaOperando=relativo9(operando);
                    modoDireccionamiento=modoAnalizar;
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);
                    break;
            case "REL16":
                    banderaOperando=relativo16(operando);
                    modoDireccionamiento=modoAnalizar;
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);
                    break;
            case "IMM8":
                    banderaOperando=Imediato8(operando,porCal);
                    modoDireccionamiento=modoAnalizar;
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando+"Por: "+porCal);
                    break;
            case "IMM16":
                    banderaOperando=Imediato16(operando,porCal);
                    modoDireccionamiento=modoAnalizar;
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando+"Por: "+porCal);
                    break;
            case "EXT":
                    banderaOperando=Extendido(operando);
                    modoDireccionamiento=modoAnalizar;
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);
                    break;
            case "IDXA":
                    banderaOperando=IndizadoAcumulador(operando);
                    modoDireccionamiento="IDX";
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);
                    break;
            case "IDXP":
                    banderaOperando=IndizadoPrePost(operando);
                    modoDireccionamiento="IDX";
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);
                    break;
            case "IDX":
                    banderaOperando= Indizado5(operando);
                    if(banderaOperando){
                          modoDireccionamiento="IDX";
                          System.out.println("IDX5 EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);
                    }
                    else{
                        System.out.println("No es IDX5");
                        estado="";
                        banderaOperando= Indizado9(operando);
                        if(banderaOperando){
                          modoDireccionamiento="IDX1";
                          System.out.println("IDX9 EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);
                        }
                        else{
                            System.out.println("No es IDX1");
                            estado="";
                            banderaOperando= Indizado16(operando);
                            if(banderaOperando){
                                modoDireccionamiento="IDX2";
                                System.out.println("IDX2 EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);
                            }
                            else{
                                estado+="b1";
                                System.out.println("Sintaxis invalida no es Ningun IDX");
                                modoDireccionamiento="IDX";
                                banderaOperando=false;
                            } 
                        }
                    }
                    break; 
            case "[D,IDX]":
                    banderaOperando=indizadoDindirecto(operando);
                    modoDireccionamiento=modoAnalizar;
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);break;
            case "[IDX2]":
                    banderaOperando=Indirecto16(operando);
                    modoDireccionamiento=modoAnalizar;
                    System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);break;
            case "DIR":
                    banderaOperando=Directo(operando);
                    if(banderaOperando && ModDir.contains("DIR")){
                        modoDireccionamiento="DIR";
                        System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);break;    
                    }
                    estado="";
                    banderaOperando=Extendido(operando);
                    if(banderaOperando){
                        modoDireccionamiento="EXT";
                        System.out.println(" EstadoErrores:"+estado+"  ModoDir: "+modoDireccionamiento+"    Oper: "+operando);break;    
                    }
                    else{
                        estado+="t1";
                    }
                    break;
            default:
                System.out.println("No se encontro el modo de direccionamiento");
                estado+="t1";
                banderaOperando=false;
                modoDireccionamiento="";
                break;
            }
        System.out.println("LLEgo a terminar el switch");
        return banderaOperando;
    }
    /**
     * 
     * @return String el estado si existiera error
     */
    public String RetornaEstado(){
        return estado;
    }
    /**
     * 
     * @return String el modo de direccionamiento mas adecuado
     */
    public String RetornaModDir(){
        return modoDireccionamiento;
    }
    /**
     * Analizador nos ayuda a analizar primero el operand para saber cual modo de direccionamineto
     * pudiera ser
     * @param operando String es el operando a analizar
     * @param modoDir   String conjunto de modods de direccionamientos que pudiera tener
     * @return String modo de direccionamiento mas adecuado segun el operando
     */
    public String Analizador(String operando,String modoDir){
        System.out.println("modo direccionamiento(operando antesAnalizar ): "+modoDir);
        if(null==modoDir)
            modoDir="";
        if(!modoDir.isEmpty()){
            System.out.println("Tiene modo de direccionamiento");
            StringTokenizer token= new StringTokenizer(modoDir,"|");
            String aux;
            if (token.countTokens()==1){
                aux=token.nextToken();
                System.out.println("Token unico:"+aux);
                return  aux;
            }
            else if(token.countTokens()==0){
                System.out.println("Token no tiene:");
                return "SIN";
            }
            else if(token.countTokens()>1){
                System.out.println("Token mas de uno opernado:"+operando);
                String auxToken;
                while(token.hasMoreTokens()){
                    
                    auxToken=token.nextToken();
                    System.out.println("Entro al switch token: "+auxToken);
                    if(operando.startsWith("#")==true){
                        System.out.append("Entro a imediato");
                        if(auxToken.contains("IMM8"))
                            return "IMM8";
                        else if (auxToken.contains("IMM16"))
                            return "IMM16";
                        else
                            return "SIN";
                        }
                    else if(operando.matches("^[a-zA-Z]{1}[\\w]*") && auxToken.contains("EXT"))
                        return auxToken;
                    else if(operando.matches("^[a-zA-Z],[a-z,A-Z]") && auxToken.contains("IDX"))
                        return "IDXA";
                    else if(operando.matches("^.*,[\\+\\-]{1}.+") || operando.matches("^.*,.+[\\+\\-]{1}$")&&auxToken.contains("IDX"))
                        return "IDXP";
                    else if(operando.matches("^[^\\[].*,.*[^\\]]$") || operando.matches("^,.*[^\\]]$") && auxToken.contains("IDX"))
                        return "IDX";
                    else if(operando.matches("^\\[(D|d),.*\\]?") && auxToken.contains("[D,IDX]"))
                        return "[D,IDX]";
                    else if(operando.startsWith("[") || operando.endsWith("]") && auxToken.contains("[IDX2]"))
                        return "[IDX2]";
                    else if(operando.matches("^[\\-\\$\\@\\%]?[0-9a-fA-F]+") && (auxToken.contains("DIR") || auxToken.contains("EXT")))
                        return "DIR";
                }
            }
        }
        else{
            return "SIN";
        }
        return  "SIN";
    } 
    /**
     * Conversor convierte un numero a otra base
     * @param operando a convertir
     * @return String valor segun sea el operando
     */
    public String Conversor(String operando){
        int valor = 0;
        if(operando.startsWith("%")){
            System.out.println("Conversor  binario");
            operando=operando.substring(1);
            if(operando.matches("[01]+"))
                if(operando.startsWith("1"))
                    valor=Complemento(operando,2);
                else
                    valor=Integer.parseInt(operando,2);
            else {
                estado+="b1";//Error sintaxis en operando binario
                return "!";
            }
        }
        else if(operando.startsWith("@")){ 
            System.out.println("Conversor  octal");
            operando=operando.substring(1);
                if (operando.matches("[0-7]+"))
                    if(operando.startsWith("7"))
                        valor=Complemento(operando,8);
                    else
                        valor=Integer.parseInt(operando,8);
                else {
                    estado+="b1";//Error sintaxis de operando octal
                    return "!";   
                }
        }
        else if(operando.startsWith("$")) {
            System.out.println("Conversor  hexa");
            operando=operando.substring(1);
                if(operando.matches("[0-9a-fA-F]+"))
                    if(operando.startsWith("F") || operando.startsWith("f"))
                        valor=Complemento(operando,16);
                     else   
                        valor=Integer.parseInt(operando.substring(0),16);
                else{
                    estado+="b1";//Error en sintaxis en operando Hexadecimal
                    return "!";
                }
        }
        else if(operando.matches("^[a-zA-Z]{1}.+")){
            System.out.println("Conversor  etiqueta");
            if(operando.matches("^[a-zA-Z]{1}[\\w]{0,7}"))
                return "+"+operando;//operando es una etiqueta valida
            else{
                estado+="b1";//Error Operando es Etiqueta invalida
                return "/"+operando;        
            }
        }
        
        else if(operando.matches("^[\\-]?[0-9]+")){
            System.out.println("Conversor  decimal");
            return operando;
        }
        else{
            System.out.println("Conversor else");
            return "!";
        }
        System.out.println("Valor(Conversor): "+Integer.toString(valor));
        return Integer.toString(valor);
    }
    /**
     * Complemento detecta de que base se desea convertir a decimal
     * @param numero a convertir
     * @param base la base en la que esta el numero
     * @return int numero convertido  a decimal
     */
    public int Complemento(String numero,int base){
        int respuesta=0;
        if(base==2)
            respuesta=Complemento_a_2(numero);
        else if(base==8)
        {
            int base10=Integer.parseInt(numero,base);
            String base2=Integer.toBinaryString(base10);
            respuesta=Complemento_a_2(base2);
        }
        else if(base==16)
        {
            int base16=Integer.parseInt(numero, base);
            String base2=Integer.toBinaryString(base16);
            respuesta=Complemento_a_2(base2);
        }
      return respuesta;
    }
    /**
     * hace el complemento a dos
     * @param numero binario a hacer complemento
     * @return int valor del complemento a dos en decimal
     */
    public int Complemento_a_2(String numero)
    {
        int respuesta=0;
        char[] numeroChar = numero.toCharArray();
        for(int i=0;i<numero.length();i++){ 
           if(numeroChar[i] =='0')
                numeroChar[i] = '1';   
            else
                numeroChar[i]= '0';
        }
                    numero = String.valueOf(numeroChar);
                    respuesta=(Integer.parseInt(numero,2)+1)*(-1);
        return respuesta;
   }
    /**
     * Valida el modo de direccionamiento INH
     * @param operando 
     * @param porCal bytes por calcular
     * @return boolean true si es valido 
     */
    public boolean Ineherente(String operando,byte porCal){
        if (porCal==0 && operando.equalsIgnoreCase("NULL")){
            return true;
        }
        else if(porCal>0 && operando.equalsIgnoreCase("NULL")){
            estado+="r3";//Error el modo INH  debe llevar operando
        }
        else{
            estado+="r2";//Error el modo INH no debe llevar operando 
        }
        return false;
    }
    /**
     * Valida el modo de direccionamiento IMM8
     * @param operando
     * @param porCalcular bytes por calcular
     * @return boolean true si es valido el operando en modo de direccionamiento 
     */
    public boolean Imediato8(String operando,byte porCalcular){
        String valor;
        int valorEntero;
        if(operando.startsWith("#") && porCalcular>0){
            valor=Conversor(operando.substring(1));
            if(!valor.startsWith("+") && !valor.startsWith("/") && !valor.startsWith("!")){
                valorEntero= Integer.parseInt(valor);
                if(valorEntero>= -256 && valorEntero <= 255){
                    return true;
                }
                else{
                    estado+="i1";//Error operando fuera de rango de 8bits
                }
            }
            else if(valor.startsWith("+") || valor.startsWith("/")){
                estado+="i2";//Error el modo de direccionamiento no soporta Etiquetas
            }
        }
        else if(porCalcular==0){
            if(operando.equalsIgnoreCase("NULL"))
                return true;
            else
                estado+="r2";//Error el codop no soporta operando
        }
        return false;
    }
    /**
     * Valida el modo de direccionamiento IMM16
     * @param operando
     * @param porCalcular bytes por calcular
     * @return boolean true si es valido el operando al modo de direccionamiento
     */
    public boolean Imediato16(String operando,byte porCalcular){
        String valor;
        int valorEntero;
        if(operando.startsWith("#") && porCalcular>0){
            valor=Conversor(operando.substring(1));
            if(!valor.startsWith("+") && !valor.startsWith("/")&& !valor.startsWith("!")){
                valorEntero= Integer.parseInt(valor);
                if(valorEntero>= -32768 && valorEntero <= 65535){
                    return true;
                }
                else{
                    estado+="i1";//Error operando fuera de rango de 16bits
                }
            }
            else if(valor.startsWith("+") || valor.startsWith("/")){
                estado+="i2";//Error el modo de direccionamiento no soporta Etiquetas
            }          
        }
        else if(porCalcular==0){
            if(operando.equalsIgnoreCase("NULL")){
                return true;
            }
            else
                estado+="r2";//Error el codop no soporta operando
        }
        return false;
    }
    /**
     * Valida el modo de direccionamiento DIR
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean Directo(String operando){
        String valor=Conversor(operando);
        int valorEntero;
        if(!valor.startsWith("+") && !valor.startsWith("/")&& !valor.startsWith("!")){
            valorEntero=Integer.parseInt(valor);
            if(valorEntero>=0 && valorEntero<=255)
                return true;
            else
                estado+="i1";//Error operando fuera de rango
        }
        return false;
    }
    /**
     * Valida el modo de direccionamiento EXT
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean Extendido(String operando){
        String valor=Conversor(operando);
        int valorEntero;
        if(!valor.startsWith("+") && !valor.startsWith("/")&& !valor.startsWith("!")){
            valorEntero=Integer.parseInt(valor);
            if(valorEntero>=-32768 && valorEntero<=65535)
                return true;
            else
                estado+="i1";//Error operando fuera de rango
        }
        else if(valor.startsWith("+"))
            return true;
        else if(valor.startsWith("/"))
            estado+="E2";//Error Etiqueta invalida
        return false;
    }
    /**
     * Valida el modo de direccionamiento IDX
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean Indizado5(String operando){
        String numero="",registro="";
        boolean bandera=false;
        int valorEntero;
        int contComas=0;
        for(int i=0;i<operando.length();i++)
            if(operando.charAt(i)==',')
                contComas++;
        for(int i=0;i<operando.length();i++){
            if(operando.charAt(i)== ','){
                if(i>0){
                numero=operando.substring(0,i);
                registro=operando.substring(i+1);
                bandera=true;
                break;
                }
                else{
                 registro=operando.substring(i+1);
                 bandera=true;
                 break;
                }
            }
        }
        System.out.println("Numero 5: "+numero);
        System.out.println("Registro: "+registro);
        if(bandera && contComas<=1){
            if (numero.length()> 0){
            String valor=Conversor(numero);
            if(!valor.startsWith("+") && !valor.startsWith("/")&& !valor.startsWith("!")){
                valorEntero=Integer.parseInt(valor);
                if(registro.length()>0)
                    if(valorEntero>=-16 && valorEntero<=15)
                        if(Registros.contains(registro.toUpperCase()))
                            return true;
                        else
                            estado+="x1";//Error no existe el registro  
                    else
                        estado+="i1";//Error operando fuera de rango
                else 
                    estado+="x6";//Error no existe registro a validar
            }
            }
            else{
                if(registro.length()>0){
                    if(Registros.contains(registro.toUpperCase()))
                        return true;
                    else 
                        estado+="x1";//Error no se encontro el registro
                }
            }
        }
        else
            estado+="b1";
        return false;
    }
    /**
     * Valida el modo de direccionamiento IDX1
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean Indizado9(String operando){
        String numero=null,registro="";
        boolean bandera=false;
        int valorEntero;
        int contComas=0;
        for(int i=0;i<operando.length();i++)
            if(operando.charAt(i)==',')
                contComas++;
        for(int i=0;i<operando.length();i++){
            if(operando.charAt(i)== ','){
                if(i>0){
                numero=operando.substring(0,i);
                registro=operando.substring(i+1);
                bandera=true;
                break;
                }
                else{
                 registro=operando.substring(i+1);
                 bandera=true;
                 break;
                }
            }
        }
        System.out.println("Numero 9: "+numero);
        System.out.println("Registro: "+registro);
        if(bandera && contComas<=1){
           if (numero.length()> 0){
            String valor=Conversor(numero);
            if(!valor.startsWith("+") && !valor.startsWith("/")&& !valor.startsWith("!")){
                valorEntero=Integer.parseInt(valor);
                if(registro.length()>0)
                    if(valorEntero>=-256 && valorEntero<=255)
                        if(Registros.contains(registro.toUpperCase()))
                            return true;
                        else
                            estado+="x1";//Error no existe el registro  
                    else
                        estado+="i1";//Error operando fuera de rango
                else
                    estado+="x6";//Error no existe registros a validar
            }
            }
            else
                estado+="x1";//Error el modo de direccionamiento no acepta campo vacio antes de la ,
        }
        else
            estado+="b1";
        return false;
    }
    /**
     * Valida el modo de direccionamiento IDX2
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean Indizado16(String operando){
        String numero="",registro="";
        boolean bandera=false;
        int valorEntero;
        int contComas=0;
        for(int i=0;i<operando.length();i++)
            if(operando.charAt(i)==',')
                contComas++;
        for(int i=0;i<operando.length();i++){
            if(operando.charAt(i)== ','){
                if(i>0){
                numero=operando.substring(0,i);
                registro=operando.substring(i+1);
                bandera=true;
                break;
                }
                else{
                    registro=operando.substring(i+1);
                    bandera=true;
                    break;
                }
                    
            }
        }
        System.out.println("Numero 16: "+numero);
        System.out.println("Registro: "+registro);
        if(bandera && contComas<=1){
           if (numero.length()> 0){
            String valor=Conversor(numero);
            if(!valor.startsWith("+") && !valor.startsWith("/")&& !valor.startsWith("!")){
                valorEntero=Integer.parseInt(valor);
                if(registro.length()>0)
                    if(valorEntero>=0 && valorEntero<=65535)
                        if(Registros.contains(registro.toUpperCase()))
                            return true;
                        else
                            estado+="x1";//Error no existe el registro  
                    else
                        estado+="i1";//Error operando fuera de rango
                else
                    estado+="x6";//Error contine ningun registro a validar
            }
            }
            else
                estado+="x1";//Error el modo de direccionamiento no acepta campo vacio antes de la ,
        }
        else{
            estado+="b1";
        }
        return false;
    }
    /**
     * Valida el modo de direccionamiento [IDX2]
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean Indirecto16(String operando){
        String numero="",registro="";
        boolean bandera=false;
        int valorEntero;
        int contComas=0;
        for(int i=0;i<operando.length();i++)
            if(operando.charAt(i)==',')
                contComas++;
        if (operando.startsWith("[") && operando.endsWith("]") && contComas<=1){
            for(int i=0;i<operando.length();i++){
                if(operando.charAt(i)== ','){
                    if(i>0){
                        numero=operando.substring(1,i);
                        registro=operando.substring(i+1,operando.length()-1);
                        bandera=true;
                        break;
                    }
                    else{
                        registro=operando.substring(i+1,operando.length()-1);
                        bandera=true;
                        break;
                    }
                }
            }
            System.out.println("Numero [idx2]: "+numero);
            System.out.println("Registro: "+registro);
        }
        else{
            estado+="b1";
        }
        
        if(bandera){
           if (numero.length()> 0){
            String valor=Conversor(numero);
            if(!valor.startsWith("+") && !valor.startsWith("-") && !valor.startsWith("!")){
                valorEntero=Integer.parseInt(valor);
                if(registro.length()>0)
                    if(valorEntero>=0 && valorEntero<=65535)
                        if(Registros.contains(registro.toUpperCase()))
                            return true;
                        else
                            estado+="x1";//Error no existe el registro  
                    else
                        estado+="i1";//Error operando fuera de rango
                else 
                    estado+="x6";//Error no existe el registro
             }
            }
            else
                estado+="Y5";//Error el modo de direccionamiento no acepta campo vacio antes de la ,
        }
        return false;
    }
    /**
     * Valida el modo de direccionamiento IDX
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean IndizadoPrePost(String operando){
        String numero="",registro="";
        boolean bandera=false;
        int valorEntero;
        int contComas=0;
        for(int i=0;i<operando.length();i++)
            if(operando.charAt(i)==',')
                contComas++;
        for(int i=0;i<operando.length();i++){
            if(operando.charAt(i)== ','){
                if(i>0){
                numero=operando.substring(0,i);
                registro=operando.substring(i+1);
                bandera=true;
                break;
                }
                else{
                 registro=operando.substring(i+1);
                 bandera=true;
                 break;
                }
            }
       }
        System.out.println("Numero PRE POST: "+numero);
        System.out.println("Registro: "+registro);
        if(bandera && contComas<=1){
           if (numero.length()> 0){
            String valor=Conversor(numero);
            if(!valor.startsWith("+") && !valor.startsWith("/")&& !valor.startsWith("!")){
                valorEntero=Integer.parseInt(valor);
                registro=registro.toUpperCase();
                if(registro.length()>0 && (registro.matches("^[\\-\\+]{1}[XY]{1}") || registro.matches("^[\\-\\+]{1}SP")|| registro.matches("^[\\-\\+]{1}PC")||registro.matches("^[XY]{1}[\\+\\-]{1}")||registro.matches("^SP[\\-\\+]{1}$")||registro.matches("^PC[\\-\\+]{1}$")))
                    if(valorEntero>=1 && valorEntero<=8)
                        return true;
                     else
                        estado+="i1";//Error operando fuera de rango  
                else
                    estado+="x1";//Error registro no valido
            }
            }
            else
                estado+="Y5";//Error el modo de direccionamiento no acepta campo vacio antes de la ,
        }
        else
            estado+="b1";
        return false;
    }
    /**
     * Valida el modo de direccionamiento IDX
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean IndizadoAcumulador(String operando){ 
        String acumulador="",registro="";
        boolean bandera=false;
        int valorEntero;
        int contComas=0;
        for(int i=0;i<operando.length();i++)
            if(operando.charAt(i)==',')
                contComas++;
        for(int i=0;i<operando.length();i++){
            if(operando.charAt(i)== ','){
                if(i>0){
                acumulador=operando.substring(0,i);
                registro=operando.substring(i+1);
                bandera=true;
                break;
                }
                else{
                 registro=operando.substring(i+1);
                 bandera=true;
                 break;
                }
            }
       }
        System.out.println("acumulador: "+acumulador);
        System.out.println("Registro: "+registro);
        if(bandera && contComas<=1){
           if (acumulador.length()> 0 ){
            if(Acumuladores.contains(acumulador.toUpperCase())){
                if(registro.length()>0)
                    if(Registros.contains(registro.toUpperCase()))
                        return true;
                     else
                        estado+="x1";//Error registro no valido  
                else
                    estado+="Y5";//Error registro vacio
            }
            else
                estado+="Z3";//Error acumulador no es valido
        
            }
            else
                estado+="Y5";//Error el modo de direccionamiento no acepta campo vacio antes de la ,
        }
        else
            estado+="b1";
        return false;
    }
    /**
     * Valida el modo de direccionamiento [D,IDX]
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean indizadoDindirecto(String operando){
        String acumuladorD="",registro="";
        boolean bandera=false;
        int contComas=0;
        for(int i=0;i<operando.length();i++)
            if(operando.charAt(i)==',')
                contComas++;
        if (operando.startsWith("[") && operando.endsWith("]") && contComas<=1){
            for(int i=0;i<operando.length();i++){
                if(operando.charAt(i)== ','){
                    if(i>0){
                        acumuladorD=operando.substring(0,i);
                        registro=operando.substring(i+1);
                        bandera=true;
                        break;
                    } 
                    else{
                        registro=operando.substring(i+1);
                        bandera=true;
                        break;
                    }
                }
            }
            System.out.println("AcumuladorD: "+acumuladorD);
            System.out.println("Registro: "+registro);
        }
        else
            estado+="b1";
        
        if(bandera){
           if(!acumuladorD.isEmpty()){
            if (acumuladorD.equalsIgnoreCase("D"))
               if(registro.length()>0)
                    if(Registros.contains(registro.toUpperCase()))
                        return true;
                    else
                        estado+="x1";//Error no existe el registro  
                else
                    estado+="x6";//Error no existe registro a validar
             else 
                estado+="z3";//Error el acumulador no es el indicado
            }
           else{
               estado+="Y5";
           }
        }
        return false;
    }
    /**
     * Valida el modo de direccionamiento REL8
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean relativo8(String operando){
        String valor=Conversor(operando);
        int valorEntero;
        if(!valor.startsWith("+") && !valor.startsWith("/")&& !valor.startsWith("!")){
            valorEntero=Integer.parseInt(valor);
            if(valorEntero>=-128 && valorEntero<=127)
                return true;
            else
                estado+="i1";//Error operando fuera de rango
        }
        else if(valor.startsWith("+"))
            return true;
        else if(valor.startsWith("/"))
            estado+="E2";//Error Etiqueta invalida
        else
            estado+="t1";//Error operando invalido
        return false;
    }
    /**
     * Valida el modo de direccionamiento REL9
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean relativo9(String operando){
        String valor=Conversor(operando);
        int valorEntero;
        if(!valor.startsWith("+") && !valor.startsWith("/")&& !valor.startsWith("!")){
            valorEntero=Integer.parseInt(valor);
            if(valorEntero>=-256 && valorEntero<=255)
                return true;
            else
                estado+="i1";//Error operando fuera de rango
        }
        else if(valor.startsWith("+"))
            return true;
        else if(valor.startsWith("/"))
            estado+="E2";//Error Etiqueta invalida  
        else
            estado+="t1";//Error operando invalido
        return false;
    }
    /**
     * Valida el modo de direccionamiento REL16
     * @param operando 
     * @return boolean true si es valido el operando
     */
    public boolean relativo16(String operando){
        String valor=Conversor(operando);
        int valorEntero;
        if(!valor.startsWith("+") && !valor.startsWith("/")&& !valor.startsWith("!")){
            valorEntero=Integer.parseInt(valor);
            if(valorEntero>=-32768 && valorEntero<=65535)
                return true;
            else
                estado+="i1";//Error operando fuera de rango
        }
        else if(valor.startsWith("+"))
            return true;
        else if(valor.startsWith("/"))
            estado+="E2";//Error Etiqueta invalida
        else
            estado+="t1";//Error operando invalido
        
        return false;
    }
}
