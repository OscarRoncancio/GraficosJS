/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.*;
 
public class DaoUser extends Dao {
    
    
    public String getNameByEmail(String email) throws SQLException{
        String sql = "SELECT * FROM Empleado WHERE email='"+email+"'";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            return rs.getString("nombre");
        }
        
        return null;
    }
}