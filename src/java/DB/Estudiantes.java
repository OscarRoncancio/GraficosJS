/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Modelo.Estudiante;
import java.net.URISyntaxException;
import java.sql.*;

/**
 *
 * @author willy
 */
public class Estudiantes {
     PreparedStatement preparedStmt;
    Connection connection;
    String query;

    public Estudiantes() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }

    public boolean agregar(Estudiante a) {
        boolean r = false;
        try {
            // the mysql insert statement
            query = " insert into Estudiante (id,nombre,apellido)"
                    + " values (?, ?, ?);";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1,a.getId());
            preparedStmt.setString(2, a.getNombre().trim());
            preparedStmt.setString(3, a.getApellido().trim());
            // execute the preparedstatement
            preparedStmt.execute();
            System.out.println("You made it, the insertion is ok!");
            r = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make insertion!");
            e.printStackTrace();
        }
        return r;
    }
    
    public Estudiante buscar(int id) {
        Estudiante e = null;
        boolean r = false;
         this.query = "select * from Estudiante where id = " + id;
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("id");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                e = new Estudiante(id2, nom, apellido);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        return e;
    }
    
    public void disconect() throws SQLException {
        this.connection.close();
    }

    public PreparedStatement getPreparedStmt() {
        return preparedStmt;
    }

    public Connection getConnection() {
        return connection;
    }

}
