/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mini_C_Compiler;

import java.util.ArrayList;

/**
 *
 * @author Nicolás
 */
public class node {
    public ArrayList<node> children;
    public String nodeScope;
   // public ArrayList<node> symtable;
    public String sym;
    //variables:
    public String type;
    //retorno
    public String returnType = null;
    //parametros de funciones:
    public node params;
    
    public int lin;
    
    public int col;
    
    public int tamaño;
    
    public boolean variable;
    public String cuadTmp;
    
    public boolean reversed = false;

    public node(String sym) {
        this.sym = sym;
        this.children = new ArrayList<>();
    }
    public node(String sym, int lin, int col) {
        this.sym = sym;
        this.children = new ArrayList<>();
        this.lin = lin;
        this.col = col;
    }
    public node(String sym, int lin, int col, int tamaño) {
        this.sym = sym;
        this.children = new ArrayList<>();
        this.lin = lin;
        this.col = col;
        this.tamaño = tamaño;
    }
    public node(String sym, int lin, int col, String type) {
        this.sym = sym;
        this.children = new ArrayList<>();
        this.lin = lin;
        this.col = col;
        this.type = type;
    }

    public node(ArrayList<node> children, String sym){
    	this.children = children;
    	this.sym = sym;
    }
    public void display(int prof){
    	System.out.println(sym);    	
    	writeTree(prof);
    }
    
    public void removeFirst(){
        this.children.remove(0);
    }

    public void writeTree(int prof){
        
    	for(node child: this.children){
    		for (int i = 0;i<=prof ;i++ ) {
    			System.out.print(" ");    			
    		}
    		System.out.println("|_"+child.sym);
    		if (!child.children.isEmpty()) {
    			child.writeTree(prof+=1);
    			prof-=1;
    		}
    	}    	
    }
}
