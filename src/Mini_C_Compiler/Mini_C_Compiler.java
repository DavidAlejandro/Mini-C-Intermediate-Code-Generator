/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mini_C_Compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.html.HTMLEditorKit.Parser;
/**
 *
 * @author david
 */
public class Mini_C_Compiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
       System.out.println("-------------------------------");
       
      sintactico sin = new sintactico(new gramatilex(new FileInputStream("code_test.c")));
        sin.parse();
        node nodo = sin.padre;
        //nodo.removeFirst();
        MySymTable mst = new MySymTable();
//         
        mst.setScopes(0, nodo);
        mst.generateSymTable(0, nodo);
        mst.verifyFunctionsParams();
        
        if (mst.validMain() == true) 
        {
            TypeCheck tc = new TypeCheck(mst);
            tc.startCheck(0, nodo);
            tc.typeCheck(0, nodo);

            CuadrupleGenerator cg = new CuadrupleGenerator(mst, tc, sin);

            //mst.printTable();  
            System.out.println("AST\n");
            nodo.display(0);
            cg.generateCuadruplesIfValid(0, nodo);
            cg.completeArithmetics();
            System.out.println("Tabla de Simbolos\n");
            mst.printTable();
            System.out.println("Código Intermedio (Cuadruplas)\n");
            cg.printCuadruples();
            
        }
        else
        {
            System.out.println("Error: Programa sin función int main.");
        }
        
    
        
     //Para generar el lexer.
       
// String path = "/Users/David/NetBeansProjects/Mini_C_Compiler/src/Mini_C_Compiler/scanner.jflex";
// generarLexer(path);
        
     //Para generar el parser.
        
//   String path = "/Users/David/NetBeansProjects/Mini_C_Compiler/src/Mini_C_Compiler/parser.cup";
//         String opciones[] = new String[7];
//        
//        opciones[0] = "-destdir";
//        opciones[1] = "/Users/David/NetBeansProjects/Mini_C_Compiler/src/Mini_C_Compiler";
//        opciones[2] = "-parser";
//        opciones[3] = "sintactico";
//        opciones[4] = "-symbols";
//        opciones[5] = "simbolo";
//        opciones[6] = "/Users/David/NetBeansProjects/Mini_C_Compiler/src/Mini_C_Compiler/parser.cup";
//        generarParser(opciones);
        
    }
    
    public static void generarLexer(String path){
        File file=new File(path);
        JFlex.Main.generate(file);
    }
    
    public static void generarParser(String[] path)
    {
         
        try {
            //File file = new File(path);
            // jflex.Main.generate(file);
            java_cup.Main.main(path);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
