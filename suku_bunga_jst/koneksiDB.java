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

import java.sql.*;
import com.mysql.jdbc.Connection;

public class koneksiDB {
    private static Connection mysqlkonek;
 
    public static Connection koneksi() throws SQLException{
       if(mysqlkonek==null){
           try {

               DriverManager.registerDriver(new com.mysql.jdbc.Driver());
               mysqlkonek = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/suku_bunga_jst","root","");

           } catch (Exception e) {
           System.out.print("gagal koneksi!");
           }
       }

       return mysqlkonek;

    }
}
