/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tsppractica1.pkg0;

import java.io.*;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author RamonGuerrero
 */
public class Arbol {
    String porCal;
    String cal;
    String modo;
    
    /**
     * clase Nodo de un arbol binario
     * donde contiene cada nodo un lado izquierdo-derecho 
     * en este arbol tambien contiene un nodo hijo llamado center
     * el nodo center es para los codigos de operacin que contengan mas de un modos de direccionamientos 
     */
        class Nodo
      {
        String codop;
        String modoDir;
        String codMaquina;
        byte calculados;
        byte porCalcular;
        byte totalByte;
        Nodo izq, der, center;
      }
      Nodo raiz;//nodo raiz se declara

      /**
       * se crea el contructor con un valor de defecto para nuestra raiz
       */
      public Arbol() {
          raiz=null;
      }
      /**
       * Insertar es el metodo donde insertamos un nuevo nodo al arbol
       * @param codop String es el valor de codigo de operaicon a insertar
       * @param modoDir String el modo de direccionamiento
       * @param codMaquina String el codigo maquina del codop
       * @param calculado byte son los bytes calculados 
       * @param porCalcular byte son los bytes por calcular
       * @param total byte es el total de bytes que debe contener el codop
       */
      public void insertar (String codop,String modoDir,String codMaquina,byte calculado,byte porCalcular,byte total)
      {
          Nodo nuevo=new Nodo();
          nuevo.codop = codop;
          nuevo.modoDir=modoDir;
          nuevo.codMaquina=codMaquina;
          nuevo.calculados=calculado;
          nuevo.porCalcular=porCalcular;
          nuevo.totalByte=total;
          nuevo.izq = null;
          nuevo.der = null;
          nuevo.center=null;
          
          if (raiz == null)
              raiz = nuevo;
          else
          {
              Nodo anterior = null, auxiliar;
              auxiliar = raiz;
              int comparaCualMayor;
              while (auxiliar != null)
              {
                  anterior = auxiliar;
                  comparaCualMayor=codop.compareTo(auxiliar.codop); //metodo para comparar una cadena con otra
                  if (comparaCualMayor == 0) //si las cadenas son iguales retorna un 0
                      auxiliar = auxiliar.center;
                  else if(comparaCualMayor < 0) //si la cadena del codop es menor a la del nodo retorna un numero menor a 0
                      auxiliar = auxiliar.izq;
                  else // si la cadena del codop es mayor a la del nodo retorna un numero mayor que cero
                      auxiliar = auxiliar.der;
              }
              comparaCualMayor=codop.compareTo(anterior.codop);
              if (comparaCualMayor == 0)
                  anterior.center = nuevo;
              else if(comparaCualMayor<0)
                  anterior.izq = nuevo;
              else
                  anterior.der=nuevo;
          }
      }

      /**
       * Buscar es el metodo donde busca el codop en el arbol
       * @param codop String es el codop a buscar en el arbol
       * @return String si retorna  null indica que no se encontro el codop
       *                            "SN moddireccionamiento" indica se encontro pero no debe llevar operando
       *                            "SS mododireccionamiento" indica se encontro el codop y debe llevar operando
       */
      public String Buscar(String codop){
          Nodo aux=raiz;
          String  banderaEncontro = null,auxCadena="",calBytes="",porBytes="";
          byte contieneOperandos=127;
          int comparador;
          while(aux!=null){
              comparador=codop.compareTo(aux.codop);
              if (codop.equalsIgnoreCase(aux.codop)){
                  contieneOperandos=aux.porCalcular;
                while(aux!=null){ //en este ciclo obtenemos los modos de direccionamiento
                    auxCadena+="|"+aux.modoDir;
                    calBytes+="|"+aux.calculados;
                    porBytes+="|"+aux.porCalcular;
                    aux=aux.center;
                    }
              }
               else if(comparador < 0)
                aux = aux.izq;
               else
                aux = aux.der;
          }
          if(contieneOperandos==0){
              banderaEncontro="SN "+auxCadena;
              porCal=porBytes;
              cal=calBytes;
              modo=auxCadena;
          }
          else if(contieneOperandos>0 && contieneOperandos<127){
              banderaEncontro="SS "+auxCadena;
              porCal=porBytes;
              cal=calBytes;
              modo=auxCadena;
          }
          return banderaEncontro;
      }
   public String retornaBytesCalculados(){
       return cal;
   }   
   public String retornaBytesPorCalcular(){
       return porCal;
   }
   public String retornaModoDireccionamiento(){
       return modo;
   }
}



