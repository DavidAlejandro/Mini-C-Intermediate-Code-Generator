 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Mini_C_Compiler;

import java.util.ArrayList;

/**
 *
 * @author David
 */
public class SymTableRow {
    
    public String id;
    public String tipo;
    public ArrayList<String> tipo_funcion = new ArrayList<>();
    public String ambito;
    public boolean param;
    public boolean funcion;
    public int dir;
    public String temp = "n/a";

    public SymTableRow(String id, String tipo, ArrayList<String> tipo_funcion, String ambito, boolean param, boolean funcion, int dir) {
        this.id = id;
        this.tipo = tipo;
        this.tipo_funcion = tipo_funcion;
        this.ambito = ambito;
        this.param = param;
        this.funcion = funcion;
        this.dir = dir;
    }

    
    public SymTableRow()
    {
        this.id = "";
        this.tipo = "";
        this.ambito = "global";
        this.param = false;
        this.funcion = false;
        this.dir = 0;
    }

    public ArrayList<String> getTipo_funcion() {
        return tipo_funcion;
    }

    public void setTipo_funcion(ArrayList<String> tipo_funcion) {
        this.tipo_funcion = tipo_funcion;
    }
    
   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public boolean isParam() {
        return param;
    }

    public void setParam(boolean param) {
        this.param = param;
    }

    public boolean isFuncion() {
        return funcion;
    }

    public void setFuncion(boolean funcion) {
        this.funcion = funcion;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }
    
    public void printRow()
    {
        String newScope = getAmbito().replace("null.", "");
        if (isFuncion() == true) 
        {
           /* System.out.println("Id: " + getId() + " " + "Tipo: " + getTipo_funcion().toString() + " "
            + "Ambito: " + getAmbito() + " " + "Param: " + isParam() + " " + "Funcion: " + isFuncion() + " "
            + "Temporal: " + temp + " " + "Direccion: " + getDir() + " ");*/
            System.out.println("Id: " + getId() + " " + "Tipo: " + getTipo_funcion().toString() + " "
            + "Ambito: " + newScope + " " + "Param: " + isParam() + " " + "Funcion: " + isFuncion() + " "
            + "Temporal: " + temp + " ");
        }
        else
        {
            /*System.out.println("Id: " + getId() + " " + "Tipo: " + getTipo() + " "
            + "Ambito: " + getAmbito() + " " + "Param: " + isParam() + " " + "Funcion: " + isFuncion() + " "
            + "Temporal: " + temp + " " + "Direccion: " + getDir() + " ");*/
             System.out.println("Id: " + getId() + " " + "Tipo: " + getTipo() + " "
            + "Ambito: " + newScope + " " + "Param: " + isParam() + " " + "Funcion: " + isFuncion() + " "
            + "Temporal: " + temp + " ");
        }
        
    }
    
    
    
}
