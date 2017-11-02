/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Oscar
 */
public class ConeccionGrafico {
    
    static String bd = "bd";
    static String login = "root";
    static String pass = "root";
    static String url = "jdbc:mysql://localhost:3306/db";
    
    
    Connection con = null;
    
    public ConeccionGrafico(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, login,pass);
            
            
        }catch(Exception ex){
            
        }
    }
    
    public Connection getConexion(){
        return con;
    }
    
    public void desconectar(){
        con=null;
    }
    
}
