/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suku_bunga_jst;

/**
 *
 * @author AM
 */

import java.sql.ResultSet;
import java.sql.Statement;
import static javax.swing.text.html.HTML.Tag.SELECT;

public class Suku_bunga_jst {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        core_jst.training();
        
        System.out.println("=========== MSE ============");
        for(int i=0;i<120;i++){
            System.out.println(core_jst.mse[i]);
        }
        
        System.out.println("");
        System.out.println("=========== Testing ===========");
        for(int j=0;j<10;j++){
            System.out.println(core_jst.output[j] + " ||| " + core_jst.data_testing[j][8]);
        }
        
        /*System.out.println("");
        System.out.println("=========== Bobot input 1 ===========");
        for(int k=0;k<10;k++){
            System.out.println(backpro_core.w1[0][k]);
        }*/
    
    
        LineDemo demo = new LineDemo("Demo Grafik Garis");
        demo.pack();
       // RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
  
}
