/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Mini_C_Compiler;

/**
 *
 * @author David
 */
public class Quadruple {
    public String operador;
    public String arg1;
    public String arg2;
    public String res;
    public boolean firstArith;
    public boolean usedArg1;
    public boolean usedArg2;

    public Quadruple(String operador, String arg1, String arg2, String res) {
        this.operador = operador;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.res = res;
        this.firstArith = false;
        this.usedArg1 = false;
        this.usedArg2 = false;
    }
    
    public void printCuadruple()
    {
        System.out.println("Operador: " + operador + " arg1: " + arg1 + " arg2:" + arg2 + " res:" + res);
    }
    
}
