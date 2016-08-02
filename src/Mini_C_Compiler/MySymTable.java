/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Mini_C_Compiler;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author David
 */
public class MySymTable {
   
    public ArrayList<SymTableRow> symtable = new ArrayList<SymTableRow>();
    public int for_count = 0;
    public int if_count = 0;
    public int while_count = 0;
    public int option_count = 0;
    public int select_count = 0;
    public int direccion = 0;
    
    // private node myAst;
    public boolean uniqueness = true;
    public boolean validTable = true;
  
     
     public MySymTable()
     {
         //this.symtable = null;
     }

    public void setScopes(int prof, node myAst)
    {
       
      // if(!myAst.children.isEmpty()){   
         
         for (int i = 0; i < myAst.children.size() ; i++)
         {
             
             myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
            
                  //System.out.println( myAst.children.get(i).sym); 
    
             //System.out.println(myAst.children.get(i).nodeScope);  
              
//             if (myAst.children.get(i).sym == "ritual") 
//             {
//                //myAst.children.get(i).param_scope = "Vodoo";
//                 //myAst.nodeScope = "Voodoo";
//                 myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
//                //System.out.println(myAst.children.get(i).nodeScope);        
//             }
             
             if (myAst.children.get(i).sym == "function") 
             {
                 
                 //myAst.children.get(i).param_scope = myAst.children.get(1).sym;
                 myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
                 
                 //System.out.println(myAst.children.get(i).nodeScope);
             }
             if (myAst.children.get(i).sym == "bloque") 
             {
                 if (myAst.sym == "function") 
                 {
                      myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.children.get(1).sym;
                     // System.out.println(myAst.children.get(i).nodeScope);
                     
                 }
                 else
                 {
                      myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
                      //System.out.println(myAst.children.get(i).nodeScope);
                 }
                
             }
             if (myAst.children.get(i).sym == "functionParams") 
             {
                 if (myAst.sym == "function") 
                 {
                    // myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.children.get(1).sym;
                      myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.children.get(1).sym;
                     //System.out.println( myAst.children.get(i).nodeScope);
                 }
                 else
                 {
                     myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
                     //System.out.println( myAst.children.get(i).nodeScope);
                 }
                
             }
             if (myAst.children.get(i).sym == "for") 
             {
                 myAst.children.get(i).sym = "for_" + for_count;
                 for_count++;
                 myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
                // System.out.println(myAst.children.get(i).nodeScope);        
             }
             if (myAst.children.get(i).sym == "if") 
             {
                 myAst.children.get(i).sym = "if_" + if_count;
                 if_count++;
                 myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
                 //System.out.println(myAst.children.get(i).nodeScope);        
             }
             if (myAst.children.get(i).sym == "while") 
             {
                 myAst.children.get(i).sym = "while_" + while_count;
                 while_count++;
                 myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
                 //System.out.println(myAst.children.get(i).nodeScope);        
             }
//             if (myAst.children.get(i).sym == "opción") 
//             {
//                 myAst.children.get(i).sym = "opcion_" + option_count;
//                 option_count++;
//                 myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
//                 //System.out.println(myAst.children.get(i).nodeScope);        
//             }
//             if (myAst.children.get(i).sym == "select") 
//             {
//                 myAst.children.get(i).sym = "select_" + select_count;
//                 select_count++;
//                 myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
//                // System.out.println(myAst.children.get(i).nodeScope);        
//             }
             if (myAst.children.get(i).sym == "forParams") 
             {
                myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
                 //System.out.println(myAst.children.get(i).sym);
             }
             if (myAst.children.get(i).sym == "param") 
             {
                myAst.children.get(i).nodeScope = myAst.nodeScope + "." + myAst.sym;
                //System.out.println(myAst.children.get(i).sym);
             }
             if (myAst.children.get(i).sym == "declaracion") 
             {
                 if ((myAst.children.get(i).nodeScope.contains("for"))||(myAst.children.get(i).nodeScope.contains("while"))||(myAst.children.get(i).nodeScope.contains("if"))||(myAst.children.get(i).nodeScope.contains("select"))||(myAst.children.get(i).nodeScope.contains("opción")))
                 {
                     validTable = false;
                     System.out.println("Error sintáctico en la línea "+myAst.children.get(i).children.get(1).lin + " declaración de variable inválida"); 
                     
                 }
                 else
                 {
                     if (myAst.reversed == false)
                     {
                         //System.out.println(myAst.children.get(i).children.get(1).sym);
                         Collections.reverse(myAst.children);
                        // System.out.println(myAst.children.get(i).children.get(1).sym);
                     }
                     
                     myAst.reversed = true;
                     myAst.children.get(i).nodeScope = myAst.nodeScope ;
                     //myAst.children.get(i).nodeScope.replace(".declaraciones", "");
                    // System.out.println(myAst.children.get(i).nodeScope);   
                 }
                      
                 
                   
             }
             myAst.children.get(i).nodeScope = myAst.children.get(i).nodeScope.replace(".bloque", "");
                
             if (!myAst.children.get(i).children.isEmpty()) 
             {
                            //child.writeTree(prof+=1);

                     setScopes(prof+=1, myAst.children.get(i));
                     prof-=1;
             }
            
         }
         
      // }//endif 
    }
    
    public void verifyFunctionsParams()
    {
        for (int i = 0; i < symtable.size(); i++) 
        {
            for (int j = 0; j < symtable.size(); j++)
            {
                
                if ((symtable.get(i).id.equals(symtable.get(j).id))&&(i != j)&&(symtable.get(i).ambito.equals(symtable.get(j).ambito))) 
                {
                    validTable = false;
                    uniqueness = false;
                    if ((symtable.get(j).param) == false) 
                    {
                        System.out.println("Error semántico: " +
                                                 " Variable " + symtable.get(i).id + " ya ha sido declarada.");
                    }
                  
                }
            }    
        }
    }
    
    public void generateSymTable(int prof, node myAst)
    {
        //System.out.println(prof);
        //System.out.println(myAst.children.size());
      
        for (int i = myAst.children.size()-1; i >= 0; i--) {
        //for(node child: myAst.children){
                uniqueness = true;
    		SymTableRow row = new SymTableRow();
                 
               //if para que no haga las comparaciones de unicidad en donde no sea una declaración pero si hayan identificadores de variables.
               if ((myAst.sym.equals("=="))||(myAst.sym.contains("<"))||(myAst.sym.contains(">"))||(myAst.sym.contains("+"))||(myAst.sym.contains("-"))||(myAst.sym.contains("*"))||(myAst.sym.contains("/"))||(myAst.sym.contains("="))) 
               {}
               else{
               //if(!myAst.children.isEmpty()){
                if ((myAst.children.get(i).type != null)||(myAst.children.get(i).returnType != null)||(myAst.children.get(i).sym == "param")||(myAst.children.get(i).sym == "forParams")) 
                  {
                     
                       //System.out.println("aqui: " + myAst.children.get(i).sym);
                      
                    if (myAst.children.get(i).returnType != null && myAst.children.get(i).sym == "function") 
                    {
                        if (symtable.size() > 0) 
                        {
                                 for (int j = 0; j < symtable.size(); j++) 
                                 {
                                     //Comprueba que la función no haya sido declarada anteriormente.
                                     if ((myAst.children.get(i).children.get(1).sym.equals(symtable.get(j).id))&&(symtable.get(j).isFuncion() == true))
                                     {
                                         //System.out.println("aqui: " + myAst.children.get(i).children.get(1).sym);
                                         validTable = false;
                                         System.out.println("Error semántico en la línea: " + myAst.children.get(i).children.get(1).lin +
                                                 " Función " + myAst.children.get(i).children.get(1).sym + " ya ha sido declarada.");
                                     }
                                     else
                                     {
                                         //System.out.println("aqui: " + myAst.children.get(i).children.get(1).sym);
                                         //Tipo funcion
                                         row.setId(myAst.children.get(i).children.get(1).sym);
                                         row.setFuncion(true);
                                         ArrayList<String> exp_tipo = new ArrayList<String>();
                                         exp_tipo.add(myAst.children.get(i).returnType);
                                         if (myAst.children.get(i).params != null) 
                                         {
                                            for (int k = 0; k < myAst.children.get(i).params.children.size(); k++) 
                                            {
                                               exp_tipo.add(myAst.children.get(i).params.children.get(k).children.get(0).sym);
                                            }
                                         }

                                            //AQUI


                                          row.setTipo_funcion(exp_tipo);
                                     }
                                 }
                        }
                        else
                        {
                             row.setId(myAst.children.get(i).children.get(1).sym);
                                         row.setFuncion(true);
                                         ArrayList<String> exp_tipo = new ArrayList<String>();
                                         exp_tipo.add(myAst.children.get(i).returnType);
                                         if (myAst.children.get(i).params != null) 
                                         {
                                            for (int k = 0; k < myAst.children.get(i).params.children.size(); k++) 
                                            {
                                               exp_tipo.add(myAst.children.get(i).params.children.get(k).children.get(0).sym);
                                            }
                                         }

                                            //AQUI


                                          row.setTipo_funcion(exp_tipo);
                        }
                        
                        

                     }
                    else 
                    { 
                            if (myAst.children.get(i).sym.equals("nada")) 
                            {
                                //row.setAmbito(myAst.param_scope);
                                row.setAmbito(myAst.children.get(i).nodeScope);
                                row.setId(myAst.children.get(i).children.get(1).sym);
                                row.setTipo(myAst.children.get(i).children.get(0).sym);
                                row.setParam(true);
                            }
                            else
                            {
                                 if (myAst.children.get(i).sym.equals("param")) 
                                    {
                                        //row.setAmbito(myAst.param_scope);
                                        row.setAmbito(myAst.children.get(i).nodeScope);
                                        row.setId(myAst.children.get(i).children.get(1).sym);
                                        row.setTipo(myAst.children.get(i).children.get(0).sym);
                                        row.setParam(true);
                                    }
                                if (myAst.children.get(i).sym.equals("forParams")) 
                                {
                                    row.setAmbito(myAst.children.get(i).nodeScope);
                                    row.setId(myAst.children.get(i).children.get(1).sym);
                                    row.setTipo(myAst.children.get(i).children.get(0).sym);
                                    //row.setParam(true);
                                }
                                else
                                {
                                    
                                    //para declaraciones.
                                    
                                    //Comprobación de unicidad.
                                        for (int j = 0; j < symtable.size(); j++) 
                                        {
//                                            if ((symtable.get(j).isParam() == true)&&((myAst.children.get(i).sym.equals("declaracion")))) 
//                                                {
//                                                    System.out.println("id: " + symtable.get(j).id);
//                                                    System.out.println("myAST id: " + myAst.children.get(i).children.get(1).sym);
//                                                    System.out.println("ambito: " +symtable.get(j).ambito);
//                                                    System.out.println("myAST ambito: " + myAst.children.get(i).children.get(1).nodeScope);
//                                                }
                                            if (myAst.children.get(i).sym.equals("declaracion")) 
                                            {
                                                //System.out.println("fresa");
                                                    myAst.children.get(i).children.get(1).nodeScope = myAst.children.get(i).children.get(1).nodeScope.replace(".declaracion", "");

                                            }
                                            //System.out.println(myAst.children.get(i).sym);
                                            //Unicidad de variables.
                                            if(((myAst.children.get(i).sym.equals("declaracion")))&&(symtable.get(j).id.equals(myAst.children.get(i).children.get(1).sym))&&(symtable.get(j).ambito.equals(myAst.children.get(i).nodeScope))&&(symtable.get(j).isFuncion() != true))
                                            {   
                                                System.out.println("");
                                                
                                                validTable = false;
                                                System.out.println("Error semántico en la línea: " + myAst.children.get(i).children.get(1).lin + " Columna: " 
                                                        + myAst.children.get(i).children.get(1).col + ", Variable " + myAst.children.get(i).children.get(1).sym +
                                                        " ya ha sido declarada.");
                                                uniqueness = false;
                                            }
                                            //Unicidad de funciones.
                                            if(((myAst.children.get(i).sym == "function"))&&(symtable.get(j).id.equals(myAst.children.get(i).children.get(1).sym))&&(symtable.get(j).ambito.equals(myAst.children.get(i).nodeScope))&&(symtable.get(j).isFuncion() == true))
                                            {   
                                                validTable = false;
                                                System.out.println("Error semántico en la línea: " + myAst.children.get(i).children.get(1).lin + " Columna: " 
                                                        + myAst.children.get(i).children.get(1).col + ", Función " + myAst.children.get(i).children.get(1).sym +
                                                        " ya ha sido declarada.");
                                                uniqueness = false;
                                            }
//                                            else if ((symtable.get(j).id.equals(myAst.children.get(i).children.get(1).sym))&&(myAst.children.get(i).nodeScope.contains(symtable.get(j).ambito))&&(symtable.get(j).isFuncion() != true))
//                                            {
//                                                validTable = false;
//                                                System.out.println("Error semántico en la línea: " + myAst.children.get(i).children.get(1).lin + " Columna: " 
//                                                        + myAst.children.get(i).children.get(1).col + ", Variable " + myAst.children.get(i).children.get(1).sym +
//                                                        " ya ha sido declarada.");
//                                                uniqueness = false;
//                                            }
                                            
                                            //System.out.println(myAst.children.get(i).sym + ": " + myAst.children.get(i).type);
                                            //System.out.println(myAst.children.get(i).children.get(2).sym + ": " + myAst.children.get(i).children.get(2).type);
                                            
     
                                        }
                                        
                                        if (myAst.children.get(i).sym.equals("declaracion")) 
                                        {
                                            if (myAst.children.get(i).children.get(0).sym.equals(myAst.children.get(i).children.get(myAst.children.get(i).children.size()-1).type) != true) 
                                            {
                                                System.out.println("Error de tipo en la línea: " + myAst.children.get(i).lin  + ", declaración inválida: " + myAst.children.get(i).children.get(myAst.children.get(i).children.size()-1).sym +
                                                                       " de tipo: " + myAst.children.get(i).children.get(myAst.children.get(i).children.size()-1).type + " no se puede convertir a " + myAst.children.get(i).children.get(0).sym);
                                                myAst.children.get(i).children.get(0).sym = "error_tipo";
                                                myAst.children.get(i).children.get(0).type = "error_tipo";
                                                myAst.children.get(i).type = "error_tipo";
                                                
                                            } 
                                        }
                                        
                                        if ((myAst.children.get(i).sym == "declaracion")&&(uniqueness == true))
                                        {
                                             row.setAmbito(myAst.children.get(i).nodeScope);
                                             row.setTipo(myAst.children.get(i).type);
                                             row.setId(myAst.children.get(i).children.get(1).sym);
                                            if (!myAst.children.get(i).nodeScope.contains("function")) 
                                            {
                                                row.setDir(direccion);
                                                direccion = direccion + myAst.children.get(i).children.get(0).tamaño;

                                            }
                                        }
                                        
                                    
                                }
                                
                            }
                                
                    }

               // row.setParam(true);

                 //row.setTipo(myAst.children.get(i).returnType);
                    //System.out.println("|_"+child.sym);
                 
                    if((row.ambito != "global"))
                    {
                        if (row.ambito.contains(".functionParams")) 
                        {
                            row.ambito = row.ambito.replace(".functionParams", "");
                        }
                         symtable.add(row);
                    }
                      if (row.isFuncion()) 
                      {
                          row.ambito = "null.program";
                          symtable.add(row);
                      }
                   

                   
                }
             //  }   //endif
        }
                 if (!myAst.children.get(i).children.isEmpty()) {
                            //child.writeTree(prof+=1);

                         generateSymTable(prof+=1, myAst.children.get(i));
                         prof-=1;
                    }
           //row.setAmbito(null);
           // row.setDir(null);
            //row.setFuncion(true);
                
             
    	}    
    }
    
    public boolean validMain()
    {
        for (int i = symtable.size()-1; i >= 0; i--) 
        {
            if ((symtable.get(i).id.equals("main"))&&(symtable.get(i).tipo_funcion.get(0).equals("int")))
            {
                return true;
            }
        }
        return false;
    }
    
    public void printTable()
    {
       // System.out.println("");
        for (int i = 0; i < symtable.size(); i++) {
            
            symtable.get(i).printRow();
            System.out.println("\n");
            
        }
    }
}
