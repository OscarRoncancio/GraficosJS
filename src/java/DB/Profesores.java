
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Modelo.Estudiante;
import Modelo.Materia;
import Modelo.Profesor;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author willy
 */
public class Profesores implements IBaseDatos<Profesor> {

    private PreparedStatement preparedStmt;
    private Connection connection;
    private String query;

    public Profesores() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }

    @Override
    public List<Profesor> findAll() {
         ArrayList<Profesor> mat = new ArrayList();
        this.query = "select * from Profesor ";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("cedula");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String usuario = rs.getString("usuario");
                Profesor e = new Profesor(id2, nom, apellido, usuario);
                mat.add(e);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        return mat;
    }

    @Override
    public boolean insert(Profesor a) {
        boolean r = false;
        try {
            // the mysql insert statement
            query = " insert into Profesor (cedula,nombre,apellido,usuario)"
                    + " values (?, ?, ?,?);";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, a.getCedula());
            preparedStmt.setString(2, a.getNombre().trim());
            preparedStmt.setString(3, a.getApellido().trim());
            preparedStmt.setString(4, a.getUsuario().trim());
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

    @Override
    public boolean update(Profesor a) {
        boolean r = false;
        if (buscar(a.getCedula()) != null) {
            try {
                //Update
                // create the java mysql update preparedstatement
                query = "update Profesor set nombre = ?, apellido=?, usuario=? where cedula = ?";
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1,a.getNombre().trim());
                preparedStmt.setString(2,a.getApellido().trim());
                preparedStmt.setString(3,a.getUsuario().trim());
                preparedStmt.setInt(4, a.getCedula());
                // execute the java preparedstatement
                preparedStmt.executeUpdate();
                r = true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("Failed to make update!");
                e.printStackTrace();
            }
        }
        return r;
    }

    @Override
    public boolean delete(Profesor t) {
       boolean hecho = false;
        try {
            //borramos el curso
            this.query = "delete from Profesor where cedula = " + t.getCedula();
            this.preparedStmt = this.connection.prepareStatement(this.query);
            this.preparedStmt.execute();
            hecho = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            e.printStackTrace();
        }
        return hecho;
    }

    public Profesor buscar(int id) {
        Profesor e = null;
        boolean r = false;
        this.query = "select * from Profesor where cedula = " + id;
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("cedula");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String usu = rs.getString("usuario");
                e = new Profesor(id2, nom, apellido, usu);
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
