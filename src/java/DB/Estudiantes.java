/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Modelo.Estudiante;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author willy
 */
public class Estudiantes {

    private PreparedStatement preparedStmt;
    private Connection connection;
    private String query;

    public Estudiantes() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }

    public boolean agregar(Estudiante a) {
        boolean r = false;
        try {
            // the mysql insert statement
            query = " insert into Estudiante (id,nombre,apellido,curso)"
                    + " values (?, ?, ?, ?);";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, a.getId());
            preparedStmt.setString(2, a.getNombre().trim());
            preparedStmt.setString(3, a.getApellido().trim());
            preparedStmt.setInt(4, a.getCurso());
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
                 int curso = rs.getInt("curso");
                e = new Estudiante(id2, nom, apellido,curso);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        return e;
    }
 
    public ArrayList<Estudiante> GetEstudiantes(){
        ArrayList<Estudiante> est= new ArrayList<>();
        this.query = "select * from Estudiante ";
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
                 int curso = rs.getInt("curso");
                Estudiante e = new Estudiante(id2, nom, apellido,curso);
                est.add(e);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        return est;
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
