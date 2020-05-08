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

public class core_jst {
    static int unit_input = 7;
    static int unit_output = 1;
    static int unit_hidden = 10; 
    static int jml_training = 25;
    static int jml_testing = 10;
    static int epoch = 120;
    
    static double data_training[][] = new double[jml_training][11];
    static double data_testing[][] = new double[jml_testing][11];
    static double data_asli[][] = new double[jml_testing][11];
    
    static double w1[][] = new double[unit_input][unit_hidden];
    static double w2[] = new double [unit_hidden];
    static double b1[] = new double [unit_hidden];
    static double b2 = Math.random();
    
    static double sig_hidden[] = new double [unit_hidden];
    static double w1_hidden[][] = new double [unit_input][unit_hidden];
    static double sum_hidden[] = new double [unit_hidden];
  
    static double sig_output[] = new double [jml_training];
    static double w2_output[] = new double[unit_hidden];
    static double sum_output[] = new double[jml_training];
    
    static double learning_rate = 0.6;
    static double mse[] = new double[epoch];
    static double output[]= new double[jml_testing];
    
    static double in_hidden[] = new double[unit_hidden];
    static double in_output;
    
    public static void training(){
        // proses training ann dengan backpropagation
        try{       
            Statement sta = (Statement)koneksiDB.koneksi().createStatement();
            ResultSet rse = sta.executeQuery("SELECT * from tb_training order by inflasi;");
            
            for(int i = 0; i < jml_training; i++){
                if(rse.next()){        
                    data_training[i][0] = rse.getDouble("likuiditas");
                    data_training[i][1] = rse.getDouble("inflasi");
                    data_training[i][2] = rse.getDouble("pertumbuhan_eko");
                    data_training[i][3] = rse.getDouble("car");
                    data_training[i][4] = rse.getDouble("ldr");
                    data_training[i][5] = rse.getDouble("us_rate");
                    data_training[i][6] = rse.getDouble("bi_rate");
                    data_training[i][7] = 1;
                    data_training[i][8] = rse.getDouble("output");
                }
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //inisialisasi bobot awal input-hidden
        for(int k = 0; k < unit_hidden; k++){
            for(int l = 0; l < unit_input; l++){
                w1[l][k] = Math.random();
            }
        }
        
        //inisialisasi bobot awal hidden-output
        for(int m = 0; m < unit_hidden; m++){
            w2[m] = Math.random();
        }
        
        //inisialisasi bobot awal bias hidden layer 
        for(int n = 0; n < unit_hidden; n++){
            b1[n] = Math.random();
        }
        
        double error = 0;
        double delta;
        double miu[] = new double[unit_hidden];
        double dw2[] = new double[unit_hidden];
        double dw1[][] = new double[unit_input][unit_hidden];
        double db1[] = new double[unit_hidden];
        double db2;
        
        
        for(int z = 0; z < epoch; z++){
            
            // forward computing
            for(int a = 0; a < jml_training; a++){
                sum_output[a] = 0;
                for(int b = 0; b < unit_hidden; b++){
                    sum_hidden[b] = 0;

                    for(int c = 0; c < unit_input; c++){
                        w1_hidden[c][b] = data_training[a][c]*w1[c][b];
                        sum_hidden[b] = sum_hidden[b] + w1_hidden[c][b];

                    }
                    
                    in_hidden[b] = sum_hidden[b] + b1[b];
                    sig_hidden[b] = 1/(1+Math.exp(-in_hidden[b]));
                    w2_output[b] = sig_hidden[b]*w2[b];
                    sum_output[a] = sum_output[a] + w2_output[b];
                }
                in_output = sum_output[a]+b2;
                sig_output[a] = 1/(1+Math.exp(-in_output));
                error = data_training[a][8]-sig_output[a];
                delta = error * (1/(1+Math.exp(-in_output))) * (1-(1/(1+Math.exp(-in_output))));

                //backpro
                for(int e=0; e<unit_hidden; e++){                    
                    dw2[e] = learning_rate * delta * sig_hidden[e];
                    w2[e] = w2[e] + dw2[e];
                    db2 = learning_rate * delta;
                    b2 = b2 + db2;
                    miu[e] = delta * w2[e] * ((1/(1+Math.exp(-in_hidden[e]))) * (1-(1/(1+Math.exp(-in_hidden[e])))));

                    for (int d = 0; d < unit_input; d++){
                        dw1[d][e] = learning_rate * miu[e] * data_training[a][d];
                        w1[d][e] = w1[d][e] + dw1[d][e];
                    }
                    
                    db1[e] = learning_rate*miu[e];
                    b1[e] = b1[e] + db1[e];
                }                
            }
                mse[z] = Math.pow(error, 2);
        }
        
        //testing
        
        try{       
            Statement sta = (Statement)koneksiDB.koneksi().createStatement();
            ResultSet rse = sta.executeQuery("SELECT * from tb_testing;");
            
            for(int i = 0; i < jml_testing; i++){        
                if(rse.next()){           
                    data_testing[i][0] = rse.getDouble("likuiditas");
                    data_testing[i][1] = rse.getDouble("inflasi");
                    data_testing[i][2] = rse.getDouble("pertumbuhan_eko");
                    data_testing[i][3] = rse.getDouble("car");
                    data_testing[i][4] = rse.getDouble("ldr");
                    data_testing[i][5] = rse.getDouble("us_rate");
                    data_testing[i][6] = rse.getDouble("bi_rate");
                    data_testing[i][7] = 1;
                    data_testing[i][8] = rse.getDouble("output");
                }
            }      
        
            for(int a = 0; a < jml_testing; a++){
                sum_output[a] = 0;
                for(int b = 0; b < unit_hidden; b++){
                    sum_hidden[b] = 0;

                    for(int c = 0; c < unit_input; c++){
                        w1_hidden[c][b] = data_testing[a][c]*w1[c][b];
                        sum_hidden[b] = sum_hidden[b] + w1_hidden[c][b];

                    }
                    
                    in_hidden[b] = sum_hidden[b] + b1[b];
                    sig_hidden[b] = 1/(1+Math.exp(-in_hidden[b]));
                    w2_output[b] = sig_hidden[b]*w2[b];
                    sum_output[a] = sum_output[a] + w2_output[b];
                }
                in_output = sum_output[a]+b2;
                sig_output[a] = 1/(1+Math.exp(-in_output));
                
                if(sig_output[a]>=0 & sig_output[a]<0.1){
                    output[a] = 0;
                }
                else if(sig_output[a]>=0.1 && sig_output[a]<=0.5){
                    output[a] = 0.5;
                }
                else{
                    output[a] = 1;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
         /*try{       
            Statement sta = (Statement)koneksiDB.koneksi().createStatement();
            ResultSet rse = sta.executeQuery("SELECT * from tb_testing;");
            
            for(int j = 0; j < jml_testing; j++){        
                if(rse.next()){           
                    data_asli[j][0] = rse.getDouble("test_ri");
                    data_asli[j][1] = rse.getDouble("test_na");
                    data_asli[j][2] = rse.getDouble("test_mg");
                    data_asli[j][3] = rse.getDouble("test_al");
                    data_asli[j][4] = rse.getDouble("test_si");
                    data_asli[j][5] = rse.getDouble("test_k");
                    data_asli[j][6] = rse.getDouble("test_ca");
                    data_asli[j][7] = rse.getDouble("test_ba");
                    data_asli[j][8] = rse.getDouble("test_fe");
                    data_asli[j][9] = 1;
                    data_asli[j][10] = rse.getDouble("test_class");
                }
            }            
        }catch(Exception e){
            e.printStackTrace();
        }*/   
        
    }
}
