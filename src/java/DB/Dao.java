/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;
import java.net.URISyntaxException;
import java.sql.*;
 
public class Dao {
    public Connection conexion;
    //Conectar a la Base de datos
    public void conectar() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.conexion=c.getConnection();
    }
    //Desconectar a la Base de datos
    public void desconectar() throws SQLException, ClassNotFoundException{
        conexion.close();
    }
    
    //Metodo para consultar si un email y contraseñan pertenecen a una cuenta registrada
    public boolean isAcountExists(String usuario, String password) throws SQLException{
        String sql = "SELECT * FROM Usuario WHERE usuario='"+usuario+"' AND contraseña='"+password+"'";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    
    //Metodo para consultar si el email recibido ya esta registrado
    public boolean isEmailRegistered(String usuario) throws SQLException{
        String sql = "SELECT * FROM Usuario WHERE usuario='"+usuario+"'";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
 
        return rs.next();
    }  
}
