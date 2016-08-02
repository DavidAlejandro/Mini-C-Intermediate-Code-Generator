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
public class CuadrupleGenerator {
    
    //public node myAst;
    public MySymTable mySymTable;
    public TypeCheck typeCheck;
    public sintactico sin ;
    public ArrayList<Quadruple> quadruples = new ArrayList<Quadruple>();
    public ArrayList incompletes = new ArrayList();//para los goto incompletos en ifs y fors.
    public ArrayList repeatCuad = new ArrayList();//para los goto de retorno incompletos de los fors y while
    
    public int tempCounter = 0;

    public CuadrupleGenerator(MySymTable mySymTable, TypeCheck typeCheck, sintactico sin) {
        //this.myAst = myAst;
        this.mySymTable = mySymTable;
        this.typeCheck = typeCheck;
        this.sin = sin;
    }
    public void generateCuadruplesIfValid(int prof, node myAst)
    {
//        System.out.println("mySymTable.validTable: " + mySymTable.validTable);
//        System.out.println("typeCheck.validType: " + typeCheck.validType);
//        System.out.println("sin.validSintax: " + sin.validSintax);
        if ((mySymTable.validTable == true)&&(typeCheck.validType == true)&&(sin.validSintax == true)) 
        {
            System.out.println("");
            //System.out.println("Quadruples: ");
            generateCuadruples(prof, myAst);
        }
    }
    
    
    public void generateCuadruples(int prof, node myAst)
    {
        //System.out.println("Generating....");
            
            for (int i = 0; i < myAst.children.size(); i++) 
            {
                //Para generar las variables inicializadas.
                if ((myAst.children.get(i).sym.equals("declaraciones"))&&(myAst.children.get(i).nodeScope.equals("null.program")))
                {
                    //System.out.println("Declaraciones");
                    //recorre las declaraciones al principio del programa.
                    for (int j = myAst.children.get(i).children.size()-1; j >= 0 ; j--) 
                    {
                        
                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(j).children.get(3).sym,"","t"+tempCounter ));
                        
                        //Asignar variables a temporales en la tabla de símbolos
                        for (int k = 0; k < mySymTable.symtable.size(); k++) 
                        {
                            myAst.children.get(i).children.get(j).children.get(1).nodeScope = myAst.children.get(i).children.get(j).children.get(1).nodeScope.replace(".declaracion", "");
                            if ((mySymTable.symtable.get(k).id.equals(myAst.children.get(i).children.get(j).children.get(1).sym))&&(!mySymTable.symtable.get(k).isFuncion())&&(mySymTable.symtable.get(k).ambito.equals(myAst.children.get(i).children.get(j).children.get(1).nodeScope)))
                            {
                                mySymTable.symtable.get(k).temp = "t"+tempCounter;
                            }
                        }
                        tempCounter++;
                    }
                    
                }   
                
                
                //Para generar las declaraciones de funciones.
                if (myAst.children.get(i).sym.equals("functions")) 
                {
                    //recorre las declaraciones de funciones.
                    for (int j = 0; j < myAst.children.get(i).children.size(); j++) 
                    {
                      
                        quadruples.add(new Quadruple("ETIQ", myAst.children.get(i).children.get(j).children.get(1).sym,"",""));
                        //genera quadruples para bloques dentro de una declaración de una función.
                        generateInitialForFunctions(0, myAst.children.get(i).children.get(j));//Genera los primeros temporales al inicializar las variables.
                        changeTempsForFunctions(0, myAst.children.get(i).children.get(j));//Agrega a cada nodo variable del AST el temporal que le corresponde en la tabla de símbolos.
                        generateBlocksForFunctions(0, myAst.children.get(i).children.get(j));//Genera los cadruplos de la función.
                        
                        
                    }
                }
                
//                if ((myAst.children.get(i).sym.equals("ritual"))&&(myAst.children.get(i).nodeScope.equals("null.startvoodoo.ritual")))
//                {
//                    //System.out.println(myAst.children.get(i).children.get(0).sym);
//                    
//                    //recorre las declaraciones de funciones.
//                    for (int j = 0; j < myAst.children.get(i).children.size(); j++) 
//                    {
//                        if (myAst.children.get(i).children.get(j).sym.equals("ritual")) 
//                        {
//                      /*       quadruples.add(new Quadruple("ETIQ", myAst.children.get(i).children.get(j).sym,"",""));
//                        //genera quadruples para bloques dentro de una declaración de una función.
//                        generateInitialForRitual(0, myAst.children.get(i).children.get(j));//Genera los primeros temporales al inicializar las variables.
//                        changeTempsForRitual(0, myAst.children.get(i).children.get(j));//Agrega a cada nodo variable del AST el temporal que le corresponde en la tabla de símbolos.
//                        generateBlocksForRitual(0, myAst.children.get(i).children.get(j));//Genera los cadruplos de la función.
//                           */ 
//                        }
//                       
//                        
//                        
//                    }
//                }
  
                
 
                
                
                if (!myAst.children.get(i).children.isEmpty()) 
                {
                    generateCuadruples(prof+=1, myAst.children.get(i));
                    prof-=1;
                }
            }
       
    }
    public void generateInitialForFunctions(int prof, node myAst)
    {
        for (int i = 0; i < myAst.children.size(); i++) 
        {
            //Para generar las variables inicializadas dentro de una función.
                if ((myAst.children.get(i).sym.equals("declaraciones"))&&(myAst.children.get(i).nodeScope.contains("null.program.functions")))
                {
                    //recorre las declaraciones al principio de la función.
                    //System.out.println("AQUI -> " +myAst.children.get(i).children.size());
                    //for (int j = 0; j < myAst.children.get(i).children.size(); j++) 
                    for (int j = myAst.children.get(i).children.size()-1; j >= 0; j--)
                    {
                       
                        //System.out.println(myAst.children.get(i).children.get(j).children.get(1).sym);
                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(j).children.get(3).sym,"","t"+tempCounter ));
                        
                        //Asignar variables a temporales en la tabla de símbolos
                        for (int k = 0; k < mySymTable.symtable.size(); k++) 
                        {
                            //System.out.println("AQUI -> " + mySymTable.symtable.get(k).isFuncion());
                            
                            myAst.children.get(i).children.get(j).children.get(1).nodeScope = myAst.children.get(i).children.get(j).children.get(1).nodeScope.replace(".declaracion", "");
//                            myAst.children.get(i).children.get(j).children.get(1).nodeScope = myAst.children.get(i).children.get(j).children.get(1).nodeScope.replace(".functionParams", "");
//                            myAst.children.get(i).children.get(j).children.get(1).nodeScope = myAst.children.get(i).children.get(j).children.get(1).nodeScope.replace(".param", "");
//                            System.out.println("mySymTable.symtable.get(k).ambito: " + mySymTable.symtable.get(k).ambito);
//                            System.out.println("myAst.children.get(i).children.get(j).children.get(1).nodeScope: " + myAst.children.get(i).children.get(j).children.get(1).nodeScope);
                            if ((mySymTable.symtable.get(k).id.equals(myAst.children.get(i).children.get(j).children.get(1).sym))&&(!mySymTable.symtable.get(k).isFuncion())&&(mySymTable.symtable.get(k).ambito.equals(myAst.children.get(i).children.get(j).children.get(1).nodeScope))) 
                            {
                                //System.out.println("nodeScope: " + myAst.children.get(i).children.get(j).children.get(1).nodeScope);
                           // System.out.println("symtable: "+mySymTable.symtable.get(k).ambito);
                                mySymTable.symtable.get(k).temp = "t"+tempCounter;
                            }
                        }
                        tempCounter++;
                    }
                    
                 }
                
            //Para generar asignaciones
               /* if ((myAst.children.get(i).sym.equals("="))&&(myAst.children.get(i).nodeScope.contains("null.startvoodoo.spells")))
                {
                    //asignaciones simples. a = b, a = 5
                    if (myAst.children.get(i).children.size() == 2) 
                    {
                        //Para saber si es variable o constante el dato del lado derecho.
                        if (myAst.children.get(i).children.get(1).variable == true) //a = b
                        {
                            
                        }
                        else //a = 5
                        {
                            
                        }
                       
                        //cuadruples.add(new Cuadruple("=", ));
                    }
                }*/
            
            if (!myAst.children.get(i).children.isEmpty()) 
                {
                    generateInitialForFunctions(prof+=1, myAst.children.get(i));
                    prof-=1;
                }
        }
    }
    
    public void generateInitialForMain(int prof, node myAst)
    {
        for (int i = 0; i < myAst.children.size(); i++) 
        {
            //Para generar las variables inicializadas dentro de una función.
                if ((myAst.children.get(i).sym.equals("declaraciones"))&&(myAst.children.get(i).nodeScope.contains("null.startvoodoo.ritual")))
                {
                    //recorre las declaraciones al principio de la función.
                    //System.out.println("AQUI -> " +myAst.children.get(i).children.size());
                    for (int j = 0; j < myAst.children.get(i).children.size(); j++) 
                    {
                       
                        //System.out.println(myAst.children.get(i).children.get(j).children.get(1).sym);
                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(j).children.get(2).sym,"","t"+tempCounter ));
                        
                        //Asignar variables a temporales en la tabla de símbolos
                        for (int k = 0; k < mySymTable.symtable.size(); k++) 
                        {
                            //System.out.println("AQUI -> " + mySymTable.symtable.get(k).isFuncion());
                            if ((mySymTable.symtable.get(k).id.equals(myAst.children.get(i).children.get(j).children.get(1).sym))&&(!mySymTable.symtable.get(k).isFuncion())) 
                            {
                                mySymTable.symtable.get(k).temp = "t"+tempCounter;
                            }
                        }
                        tempCounter++;
                    }
                    
                 }
                
           
            if (!myAst.children.get(i).children.isEmpty()) 
                {
                    generateInitialForMain(prof+=1, myAst.children.get(i));
                    prof-=1;
                }
        }
    }
    
    public void changeTempsForFunctions(int prof, node myAst)
    {
        for (int i = 0; i < myAst.children.size(); i++) 
        {
            //Para generar las variables inicializadas dentro de una función.
                if ((myAst.sym.equals("declaracion")))
                {}
                else
                {
                   
                    if (myAst.children.get(i).variable == true) 
                    {
                        for (int j = 0; j < mySymTable.symtable.size(); j++) 
                        {
                            if ((mySymTable.symtable.get(j).id.equals(myAst.children.get(i).sym)) && (mySymTable.symtable.get(j).param != true) && (mySymTable.symtable.get(j).temp != "n/a"))
                            {
                               // System.out.println("original: " + myAst.children.get(i).sym);
                                myAst.children.get(i).cuadTmp = mySymTable.symtable.get(j).temp;
                               // System.out.println("temp: " + myAst.children.get(i).cuadTmp);
                            }
                        }
                    }
                }
              
            if (!myAst.children.get(i).children.isEmpty()) 
                {
                    changeTempsForFunctions(prof+=1, myAst.children.get(i));
                    prof-=1;
                }
        }
    }
    
    public void changeTempsForMain(int prof, node myAst)
    {
        for (int i = 0; i < myAst.children.size(); i++) 
        {
            //Para generar las variables inicializadas dentro de una función.
                if ((myAst.sym.equals("declaracion")))
                {}
                else
                {
                   
                    if (myAst.children.get(i).variable == true) 
                    {
                        for (int j = 0; j < mySymTable.symtable.size(); j++) 
                        {
                            if ((mySymTable.symtable.get(j).id.equals(myAst.children.get(i).sym)) && (mySymTable.symtable.get(j).param != true) && (mySymTable.symtable.get(j).temp != "n/a"))
                            {
                               // System.out.println("original: " + myAst.children.get(i).sym);
                                myAst.children.get(i).cuadTmp = mySymTable.symtable.get(j).temp;
                               // System.out.println("temp: " + myAst.children.get(i).cuadTmp);
                            }
                        }
                    }
                }
              
            if (!myAst.children.get(i).children.isEmpty()) 
                {
                    changeTempsForMain(prof+=1, myAst.children.get(i));
                    prof-=1;
                }
        }
    }
    
    //Para generar los bloques dentro de las funciones.
    public void generateBlocksForFunctions(int prof, node myAst)
    {
        //for (int i = 0; i < myAst.children.size(); i++) 
//        if ((myAst.sym.contains("if_"))||(myAst.sym.contains("for_")) )
//        {
//           //Collections.reverse(myAst.children);
//        }
        for (int i = 0; i < myAst.children.size(); i++) 
        {
            
            if (myAst.children.get(i).sym.equals("functionCall")) 
            {
                for (int j = 0; j < myAst.children.get(i).children.get(1).children.size(); j++) 
                {
                    quadruples.add(new Quadruple("PARAM",myAst.children.get(i).children.get(1).children.get(j).children.get(0).sym, "",""));
                }
                quadruples.add(new Quadruple("CALL",myAst.children.get(i).children.get(0).sym, "",""));
            }
         
            if (myAst.children.get(i).sym.equals("printf")) 
            {
                boolean stringVal = false;
                for (int x = 0; x < mySymTable.symtable.size(); x++) 
                {
                    //System.out.println(myAst.children.get(i).children.get(1).nodeScope);
                    if ((mySymTable.symtable.get(x).id.equals(myAst.children.get(i).children.get(1).sym))&&(myAst.children.get(i).children.get(1).nodeScope.contains(mySymTable.symtable.get(x).ambito)) )
                    {
                        quadruples.add(new Quadruple("PRINTF",myAst.children.get(i).children.get(0).sym, mySymTable.symtable.get(x).temp,""));
                    }
                    else
                    {
                          stringVal = true;

                    }
                }
                if (stringVal == true) 
                {
                    quadruples.add(new Quadruple("PRINTF",myAst.children.get(i).children.get(0).sym, myAst.children.get(i).children.get(1).sym,""));
                }
            }
            
            if (myAst.children.get(i).sym.equals("scanf")) 
            {
                 //System.out.println(myAst.children.get(i).children.get(1).nodeScope);
                for (int x = 0; x < mySymTable.symtable.size(); x++) 
                {
                    if((mySymTable.symtable.get(x).id.equals(myAst.children.get(i).children.get(1).sym))&&(myAst.children.get(i).children.get(1).nodeScope.contains(mySymTable.symtable.get(x).ambito)) )
                    {
                        quadruples.add(new Quadruple("SCANF",myAst.children.get(i).children.get(0).sym, mySymTable.symtable.get(x).temp,""));
                    }
                }
                
            }
            //Para generar asignaciones
                if ((myAst.children.get(i).sym.equals("="))&&(myAst.children.get(i).nodeScope.contains("null.program.functions")))
                {
                   // System.out.println("asignacion");
                   // System.out.println(myAst.children.get(i).children.size());
                    //asignaciones simples. a = b, a = 5
                    if (myAst.children.get(i).children.size() == 2)
                    {
                        if ((!myAst.children.get(i).children.get(1).sym.equals("+"))&&(!myAst.children.get(i).children.get(1).sym.equals("-"))&&(!myAst.children.get(i).children.get(1).sym.equals("*"))&&(!myAst.children.get(i).children.get(1).sym.equals("/")))
                        {
                            
                            //Para saber si es variable o constante el dato del lado derecho.
                            if (myAst.children.get(i).children.get(1).variable == true) //a = b
                            {
                                //para validar los parametros
                                if ((myAst.children.get(i).children.get(1).cuadTmp == null) && (myAst.children.get(i).children.get(0).cuadTmp == null))
                                {
                                    if (myAst.children.get(i).children.get(1).cuadTmp == null) 
                                    {
                                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).sym, "", myAst.children.get(i).children.get(0).sym));
                                    }
                                    else
                                    {
                                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).sym, "", myAst.children.get(i).children.get(0).sym));
                                    }
                                }
                                else if ((myAst.children.get(i).children.get(1).cuadTmp == null) || (myAst.children.get(i).children.get(0).cuadTmp == null))
                                {
                                    if (myAst.children.get(i).children.get(1).cuadTmp == null) 
                                    {
                                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).sym, "", myAst.children.get(i).children.get(0).cuadTmp));
                                    }
                                    else
                                    {
                                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).cuadTmp, "", myAst.children.get(i).children.get(0).sym));
                                    }
                                }

                                else
                                {
                                    quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).cuadTmp, "", myAst.children.get(i).children.get(0).cuadTmp));

                                }


                            }
                            else //a = 5
                            {
                                if (myAst.children.get(i).children.get(0).cuadTmp == null) //para validar los parametros
                                {
                                    quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).sym, "", myAst.children.get(i).children.get(0).sym));
                                }
                                else
                                {
                                    quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).sym, "", myAst.children.get(i).children.get(0).cuadTmp));
                                }

                            }
                        }
                        else
                        {
                            //Para generar quadruples de expresiones aritméticas.
                             generateArithmetics(0, myAst.children.get(i));
                        }
                    }
                    

                   
                }
                
                boolean validIf = true;
                String ifArg1 = "";
                String ifArg2 = "";
                //Para generar bloques IF.
                if ((myAst.children.get(i).sym.contains("if_"))&&(myAst.children.get(i).nodeScope.contains("null.program.functions")))
                {
                    //System.out.println(myAst.children.get(i).sym);
                    if (myAst.children.get(i).children.get(0).children.size() == 2) 
                    {
                       
                        for (int x = 0; x < mySymTable.symtable.size(); x++) 
                        { //System.out.println(mySymTable.symtable.get(x).id);
                           // System.out.println(myAst.children.get(i).children.get(0).children.get(0).sym);
                            if ((mySymTable.symtable.get(x).id.equals(myAst.children.get(i).children.get(0).children.get(0).sym)) )
                            {
                                ifArg1 = mySymTable.symtable.get(x).temp;
                            }
                            else
                            {
                                ifArg1 = myAst.children.get(i).children.get(0).children.get(0).sym;
                            }
                            if ((mySymTable.symtable.get(x).id.equals(myAst.children.get(i).children.get(0).children.get(1).sym)) )
                            {
                                ifArg2 = mySymTable.symtable.get(x).temp;
                            }
                             else
                            {
                                ifArg2 = myAst.children.get(i).children.get(0).children.get(1).sym;
                            }
                        }
                        quadruples.add(new Quadruple("IF" + myAst.children.get(i).children.get(0).sym, ifArg1, ifArg2, "" + (quadruples.size() + 2)));
                        quadruples.add(new Quadruple("GO_TO", "" + "pendiente", "", ""));
                        incompletes.add(quadruples.size() - 1);
                    }
                    else
                    {
                        validIf = false;
                    }
                   
                }
                if ((validIf == true)&&(myAst.children.get(i).sym.equals("else"))&&(myAst.children.get(i).nodeScope.contains("null.program.functions"))&&(incompletes.size()>0) )
                {
                  
                    quadruples.add(new Quadruple("GO_TO", "" + incompletes.get(incompletes.size() - 1), "", ""));
                    quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1 = quadruples.size() + "";
                    incompletes.remove(incompletes.size() - 1);
                    incompletes.add(quadruples.size() - 1);
                    //System.out.println(quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1);
                    
                }
                if ((validIf == true)&&(myAst.children.get(i).sym.contains("endif"))&&(myAst.children.get(i).nodeScope.contains("null.program.functions")) )
                {
                   
                    quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1 = quadruples.size() + "";
                    incompletes.remove(incompletes.size() - 1);
                    //System.out.println(quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1);
                    
                }
            
           //Para generar bloques FOR.
                if ((myAst.children.get(i).sym.contains("for_"))&&(myAst.children.get(i).nodeScope.contains("null.program.functions")))
                {
                    for (int j = 0; j < myAst.children.get(i).children.get(0).children.size(); j++) 
                    {
                        if ((myAst.children.get(i).children.get(0).children.get(j).sym.contains("<"))||(myAst.children.get(i).children.get(0).children.get(j).sym.contains(">")) )
                        {
                            quadruples.add(new Quadruple("FOR"+ myAst.children.get(i).children.get(0).children.get(j).sym, myAst.children.get(i).children.get(0).children.get(j-1).sym , myAst.children.get(i).children.get(0).children.get(j+1).sym, ""+(quadruples.size() + 2)));
                            repeatCuad.add(quadruples.size() - 1);//para saber adonde regresar.
                            quadruples.add(new Quadruple("GO_TO", "" + "pendiente", "", ""));
                            incompletes.add(quadruples.size() - 1);//para saber donde ir cuando sea falso.
                            
                        }
                    }
                   
                }
                
                if ((myAst.children.get(i).sym.contains("endfor"))&&(myAst.children.get(i).nodeScope.contains("null.program.functions")) )
                {
                    //System.out.println(myAst.children.get(myAst.children.size()-1).sym);
                    for (int j = 0; j < myAst.children.get(myAst.children.size()-1).children.size(); j++) 
                    {
                        //System.out.println(myAst.children.get(0).children.get(j).sym);
                        if ((myAst.children.get(myAst.children.size()-1).children.get(j).sym.equals("--"))||(myAst.children.get(myAst.children.size()-1).children.get(j).sym.equals("++")))
                        {
                            if (quadruples.get(quadruples.size() - 1).arg1.equals(myAst.children.get(myAst.children.size()-1).children.get(4).sym)) 
                            {
                                
                            }
                            else
                            {
                                if (myAst.children.get(myAst.children.size()-1).children.get(j).sym.equals("--")) 
                                {
                                    quadruples.add(new Quadruple("-", myAst.children.get(myAst.children.size()-1).children.get(4).sym , "1", "t" + tempCounter));
                                 quadruples.add(new Quadruple("=", "t"+ tempCounter , "", myAst.children.get(myAst.children.size()-1).children.get(4).sym));
                                }
                                else
                                {
                                    quadruples.add(new Quadruple("+", myAst.children.get(myAst.children.size()-1).children.get(4).sym , "1", "t" + tempCounter));
                                 quadruples.add(new Quadruple("=", "t"+ tempCounter , "", myAst.children.get(myAst.children.size()-1).children.get(4).sym));
                                }
                                 
                            }
                       }
                    }
                    
                    quadruples.add(new Quadruple("GO_TO", "" + repeatCuad.get(repeatCuad.size()-1), "", ""));
                    repeatCuad.remove(incompletes.size() - 1);
                    quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1 = quadruples.size() + "";
                    incompletes.remove(incompletes.size() - 1);
                    //System.out.println(quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1);
                    
                }
            
                
            //Para generar bloques WHILE.
                if ((myAst.children.get(i).sym.contains("while_"))&&(myAst.children.get(i).nodeScope.contains("null.program.functions")))
                {
                   
                        if ((myAst.children.get(i).children.get(0).sym.contains("<"))||(myAst.children.get(i).children.get(0).sym.contains(">"))||(myAst.children.get(i).children.get(0).sym.equals("==")) )
                        {
                            quadruples.add(new Quadruple("WHILE"+ myAst.children.get(i).children.get(0).sym, myAst.children.get(i).children.get(0).children.get(0).sym , myAst.children.get(i).children.get(0).children.get(1).sym, ""+(quadruples.size() + 2)));
                            repeatCuad.add(quadruples.size() - 1);//para saber adonde regresar.
                            quadruples.add(new Quadruple("GO_TO", "" + "pendiente", "", ""));
                            incompletes.add(quadruples.size() - 1);//para saber donde ir cuando sea falso.
                            
                        }
                    
                   
                }
                
                if ((myAst.children.get(i).sym.contains("endwhile"))&&(myAst.children.get(i).nodeScope.contains("null.program.functions")) )
                {
                   
                    quadruples.add(new Quadruple("GO_TO", "" + repeatCuad.get(repeatCuad.size()-1), "", ""));
                    repeatCuad.remove(incompletes.size() - 1);
                    quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1 = quadruples.size() + "";
                    incompletes.remove(incompletes.size() - 1);
                    //System.out.println(quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1);
                    
                }
                
            if (!myAst.children.get(i).children.isEmpty()) 
                {
                    generateBlocksForFunctions(prof+=1, myAst.children.get(i));
                    prof-=1;
                }
        }
    }
    
    //Para generar los bloques dentro de los spells.
    public void generateArithmetics(int prof, node myAst)
    {
        //System.out.println("arith: " +myAst.sym);
        //for (int i = 0; i < myAst.children.size(); i++) 
        for (int i = myAst.children.size()-1; i >= 0; i--) 
        {
            if ((myAst.children.get(i).sym.equals("*")) || (myAst.children.get(i).sym.equals("/"))||(myAst.children.get(i).sym.equals("+"))||(myAst.children.get(i).sym.equals("-")))
            {
                //System.out.println("entre al primero");
                if (myAst.sym.equals("=")) 
                {
                    //System.out.println("entre al segundo");
                    Quadruple quad = new Quadruple(myAst.children.get(i).sym, myAst.children.get(i).children.get(0).sym, myAst.children.get(i).children.get(1).sym, "t" + tempCounter);
                    quad.firstArith = true;
                    quadruples.add(quad);
                }
                else
                {
                    quadruples.add(new Quadruple(myAst.children.get(i).sym, myAst.children.get(i).children.get(0).sym, myAst.children.get(i).children.get(1).sym, "t" + tempCounter));
                }
                
                tempCounter++;
            }
            else if ((myAst.children.get(i).sym.equals("+")) || (myAst.children.get(i).sym.equals("-")))
            {
                //System.out.println(myAst.children.get(i).sym);
                if (myAst.sym.equals("=")) 
                {
                    Quadruple quad = new Quadruple(myAst.children.get(i).sym, myAst.children.get(i).children.get(0).sym, myAst.children.get(i).children.get(1).sym, "t" + tempCounter);
                    quad.firstArith = true;
                    quadruples.add(quad);
                }
                else
                {
                     quadruples.add(new Quadruple(myAst.children.get(i).sym, myAst.children.get(i).children.get(0).sym, myAst.children.get(i).children.get(1).sym, "t" + tempCounter));
                }
                tempCounter++;
            }
            
            if (!myAst.children.get(i).children.isEmpty()) 
                {
                    generateArithmetics(prof+=1, myAst.children.get(i));
                    prof-=1;
                }
        }
    }
    
    public void completeArithmetics()
    {
        int firstQuad = -1;
        int lastQuad = -1;
        boolean firstQuadFound = false;
        for (int i = 0; i < quadruples.size(); i++) 
        {           
            if ((i == (quadruples.size() - 1)) && (firstQuadFound == true))
            {
                lastQuad = i;
                //System.out.println("ultimo lastQuad: " + lastQuad + " sym: " + quadruples.get(i).operador);
            }
            if ((quadruples.get(i).firstArith == true)&&((quadruples.get(i).operador.equals("+"))||(quadruples.get(i).operador.equals("-"))||(quadruples.get(i).operador.equals("*"))||(quadruples.get(i).operador.equals("/"))))
            {
                firstQuad = i;
                firstQuadFound = true;
                    
                    //System.out.println("Arithmetic found!!");
               // System.out.println("firstQuad: " + firstQuad + " sym: " + quadruples.get(i).operador);
            }
            if ((i+1) < quadruples.size()) 
            {//AYER, separar el if
                if ((firstQuadFound == true)&&(((!quadruples.get(i + 1).operador.equals("+"))&&(!quadruples.get(i + 1).operador.equals("-"))&&(!quadruples.get(i + 1).operador.equals("*"))&&(!quadruples.get(i + 1).operador.equals("/")))||(quadruples.get(i+1).firstArith == true)))
                {
                    lastQuad = i;
                    firstQuadFound = false;
                   // System.out.println("lastQuad: " + lastQuad + " sym: " + quadruples.get(i).operador);
                }
               
            } 
            if ((firstQuad > -1)&&(lastQuad > -1))
            {
                //termina los cuadruplos para expresiones aritmeticas.
                ArrayList<Quadruple> tmpquads = new ArrayList<Quadruple>();
                int cuadCounter = firstQuad;
                //System.out.println("first: " + firstQuad);
                  //  System.out.println("last: " + lastQuad);
                for (int j = firstQuad; j <= lastQuad; j++) 
                {
                    Quadruple tmpquad = new Quadruple(quadruples.get(j).operador, quadruples.get(j).arg1,quadruples.get(j).arg2,quadruples.get(j).res);
                    tmpquads.add(tmpquad);
                   // System.out.println(quadruples.get(j).operador);
                }
                //se invierte el orden de los operandos y operador (arg1, arg2, operador).
                Collections.reverse(tmpquads);
                String tmpRes = "";
                //System.out.println("quads size: " + tmpquads.size());
                for (int k = 0; k < tmpquads.size(); k++) 
                {
                    Quadruple tmpquad = new Quadruple(tmpquads.get(k).operador, tmpquads.get(k).arg1,tmpquads.get(k).arg2,quadruples.get(cuadCounter).res);
                    quadruples.set(cuadCounter, tmpquad);
                    cuadCounter++;
                }
                //reemplaza los operadores a temporales.
                for (int l = firstQuad; l <= lastQuad; l++) 
                {
                    if (((quadruples.get(l).arg1.equals("+"))||(quadruples.get(l).arg1.equals("-"))||(quadruples.get(l).arg1.equals("*"))||(quadruples.get(l).arg1.equals("/")))||((quadruples.get(l).arg2.equals("+"))||(quadruples.get(l).arg2.equals("-"))||(quadruples.get(l).arg2.equals("*"))||(quadruples.get(l).arg2.equals("/"))))
                    {
                        for (int m = firstQuad; m <= lastQuad; m++) 
                        {
                            if ((quadruples.get(l).arg1.equals(quadruples.get(m).operador))&&(quadruples.get(m).usedArg1 != true))  
                            {
                                quadruples.get(m).usedArg1 = true;
                                quadruples.get(l).arg1 = quadruples.get(m).res;
                            }
                            if ((quadruples.get(l).arg2.equals(quadruples.get(m).operador))&&(quadruples.get(m).usedArg2 != true))  
                            {
                                quadruples.get(m).usedArg2 = true;
                                quadruples.get(l).arg2 = quadruples.get(m).res;
                            }
                        }
                    } 
//                    if (((quadruples.get(l).arg1.equals("+"))||(quadruples.get(l).arg1.equals("-"))||(quadruples.get(l).arg1.equals("*"))||(quadruples.get(l).arg1.equals("/")))||((quadruples.get(l).arg2.equals("+"))||(quadruples.get(l).arg2.equals("-"))||(quadruples.get(l).arg2.equals("*"))||(quadruples.get(l).arg2.equals("/"))))
//                    {
//                        
//                    }
                    
                }
                
                //Reemplaza identificadores por temporales.
                for (int n = firstQuad; n < lastQuad; n++) 
                {
                    if (!isNumeric(quadruples.get(n).arg1)) 
                    {
                        for (int o = 0; o < mySymTable.symtable.size(); o++) 
                        {
                            if (mySymTable.symtable.get(o).id.equals(quadruples.get(n).arg1)) 
                            {
                                quadruples.get(n).arg1 = mySymTable.symtable.get(o).temp;
                            }
                        }
                    }
                    if (!isNumeric(quadruples.get(n).arg2)) 
                    {
                        for (int o = 0; o < mySymTable.symtable.size(); o++) 
                        {
                            if (mySymTable.symtable.get(o).id.equals(quadruples.get(n).arg2)) 
                            {
                                quadruples.get(n).arg2 = mySymTable.symtable.get(o).temp;
                            }
                        }
                    }
                    
                }
                firstQuad = -1;
                lastQuad = -1;
                firstQuadFound = false;
            }
            
        }
        
        
    }
    
    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
    
    public void generateBlocksForMain(int prof, node myAst)
    {
        //for (int i = 0; i < myAst.children.size(); i++) 
        if ((myAst.sym.contains("if_"))||(myAst.sym.contains("for_")) )
        {
           Collections.reverse(myAst.children);
        }
        for (int i = myAst.children.size()-1; i >= 0; i--) 
        {
           
            //Para generar asignaciones
                if ((myAst.children.get(i).sym.equals("="))&&(myAst.children.get(i).nodeScope.contains("null.startvoodoo.ritual")))
                {
                    //asignaciones simples. a = b, a = 5
                    if (myAst.children.get(i).children.size() == 2)
                    {
                        if ((!myAst.children.get(i).children.get(1).sym.equals("+"))&&(!myAst.children.get(i).children.get(1).sym.equals("-"))&&(!myAst.children.get(i).children.get(1).sym.equals("*"))&&(!myAst.children.get(i).children.get(1).sym.equals("/")))
                        {
                            
                            //Para saber si es variable o constante el dato del lado derecho.
                            if (myAst.children.get(i).children.get(1).variable == true) //a = b
                            {
                                //para validar los parametros
                                if ((myAst.children.get(i).children.get(1).cuadTmp == null) && (myAst.children.get(i).children.get(0).cuadTmp == null))
                                {
                                    if (myAst.children.get(i).children.get(1).cuadTmp == null) 
                                    {
                                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).sym, "", myAst.children.get(i).children.get(0).sym));
                                    }
                                    else
                                    {
                                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).sym, "", myAst.children.get(i).children.get(0).sym));
                                    }
                                }
                                else if ((myAst.children.get(i).children.get(1).cuadTmp == null) || (myAst.children.get(i).children.get(0).cuadTmp == null))
                                {
                                    if (myAst.children.get(i).children.get(1).cuadTmp == null) 
                                    {
                                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).sym, "", myAst.children.get(i).children.get(0).cuadTmp));
                                    }
                                    else
                                    {
                                        quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).cuadTmp, "", myAst.children.get(i).children.get(0).sym));
                                    }
                                }

                                else
                                {
                                    quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).cuadTmp, "", myAst.children.get(i).children.get(0).cuadTmp));

                                }


                            }
                            else //a = 5
                            {
                                if (myAst.children.get(i).children.get(0).cuadTmp == null) //para validar los parametros
                                {
                                    quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).sym, "", myAst.children.get(i).children.get(0).sym));
                                }
                                else
                                {
                                    quadruples.add(new Quadruple("=", myAst.children.get(i).children.get(1).sym, "", myAst.children.get(i).children.get(0).cuadTmp));
                                }

                            }
                        }
                        else
                        {
                            //Para generar quadruples de expresiones aritméticas.
                             
                             generateArithmetics(0, myAst.children.get(i));
                             completeArithmetics();
                        }
                    }
                    

                   
                }
                
                
                //Para generar bloques IF.
                if ((myAst.children.get(i).sym.contains("if_"))&&(myAst.children.get(i).nodeScope.contains("null.startvoodoo.ritual")))
                {
                    //System.out.println(myAst.children.get(i).sym);
                    quadruples.add(new Quadruple("IF" + myAst.children.get(i).children.get(0).sym, myAst.children.get(i).children.get(0).children.get(0).sym, myAst.children.get(i).children.get(0).children.get(1).sym, "" + (quadruples.size() + 2)));
                    quadruples.add(new Quadruple("GO_TO", "" + "pendiente", "", ""));
                    incompletes.add(quadruples.size() - 1);
                }
                if ((myAst.children.get(i).sym.contains("else"))&&(myAst.children.get(i).nodeScope.contains("null.startvoodoo.ritual")) )
                {
                    quadruples.add(new Quadruple("GO_TO", "" + incompletes.get(incompletes.size() - 1), "", ""));
                    quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1 = quadruples.size() + "";
                    incompletes.remove(incompletes.size() - 1);
                    incompletes.add(quadruples.size() - 1);
                    //System.out.println(quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1);
                    
                }
                if ((myAst.children.get(i).sym.contains("endif"))&&(myAst.children.get(i).nodeScope.contains("null.startvoodoo.ritual")) )
                {
                    quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1 = quadruples.size() + "";
                    incompletes.remove(incompletes.size() - 1);
                    //System.out.println(quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1);
                    
                }
            
           //Para generar bloques FOR.
                if ((myAst.children.get(i).sym.contains("for_"))&&(myAst.children.get(i).nodeScope.contains("null.startvoodoo.ritual")))
                {
                    for (int j = 0; j < myAst.children.get(i).children.get(0).children.size(); j++) 
                    {
                        if ((myAst.children.get(i).children.get(0).children.get(j).sym.contains("<"))||(myAst.children.get(i).children.get(0).children.get(j).sym.contains(">")) )
                        {
                            quadruples.add(new Quadruple("FOR"+ myAst.children.get(i).children.get(0).children.get(j).sym, myAst.children.get(i).children.get(0).children.get(j-1).sym , myAst.children.get(i).children.get(0).children.get(j+1).sym, ""+(quadruples.size() + 2)));
                            repeatCuad.add(quadruples.size() - 1);//para saber adonde regresar.
                            quadruples.add(new Quadruple("GO_TO", "" + "pendiente", "", ""));
                            incompletes.add(quadruples.size() - 1);//para saber donde ir cuando sea falso.
                            
                        }
                    }
                   
                }
                
                if ((myAst.children.get(i).sym.contains("endfor"))&&(myAst.children.get(i).nodeScope.contains("null.startvoodoo.ritual")) )
                {
                    //System.out.println(myAst.children.get(myAst.children.size()-1).sym);
                    for (int j = 0; j < myAst.children.get(myAst.children.size()-1).children.size(); j++) 
                    {
                        //System.out.println(myAst.children.get(0).children.get(j).sym);
                        if ((myAst.children.get(myAst.children.size()-1).children.get(j).sym.equals("-"))||(myAst.children.get(myAst.children.size()-1).children.get(j).sym.equals("+")))
                        {
                            if (quadruples.get(quadruples.size() - 1).arg1.equals(myAst.children.get(myAst.children.size()-1).children.get(4).sym)) 
                            {
                                
                            }
                            else
                            {
                                 quadruples.add(new Quadruple(myAst.children.get(myAst.children.size()-1).children.get(j).sym, myAst.children.get(myAst.children.size()-1).children.get(4).sym , "1", "t" + tempCounter));
                                 quadruples.add(new Quadruple(myAst.children.get(myAst.children.size()-1).children.get(j).sym, "t"+ tempCounter , "", myAst.children.get(myAst.children.size()-1).children.get(4).sym));
                            }
                       }
                    }
                    
                    quadruples.add(new Quadruple("GO_TO", "" + repeatCuad.get(repeatCuad.size()-1), "", ""));
                    repeatCuad.remove(incompletes.size() - 1);
                    quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1 = quadruples.size() + "";
                    incompletes.remove(incompletes.size() - 1);
                    //System.out.println(quadruples.get((int)incompletes.get(incompletes.size() - 1)).arg1);
                    
                }
            
            if (!myAst.children.get(i).children.isEmpty()) 
                {
                    generateBlocksForMain(prof+=1, myAst.children.get(i));
                    prof-=1;
                }
        }
    }
    
    public void printCuadruples()
    {
        for (int i = 0; i < quadruples.size(); i++) 
        {
            System.out.print(i + " ");
            quadruples.get(i).printCuadruple();
        }
    }
    
    
    
}
