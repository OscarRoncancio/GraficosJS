
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Modelo.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Usuarios u = null;
        try {
            u = new Usuarios();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.query = "select * from Profesor ";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                String id2 = rs.getString("cedula");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                Usuario usuario = u.buscar2(rs.getString("usuario"));
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
            preparedStmt.setString(1, a.getCedula().trim());
            preparedStmt.setString(2, a.getNombre().trim());
            preparedStmt.setString(3, a.getApellido().trim());
            preparedStmt.setString(4, a.getUsuario().getUsuario().trim());
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
                query = "update Profesor set nombre = ?, apellido=?, usuario=? where cedula = \"?\"";
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, a.getNombre().trim());
                preparedStmt.setString(2, a.getApellido().trim());
                preparedStmt.setString(3, a.getUsuario().getUsuario().trim());
                preparedStmt.setString(4, a.getCedula().trim());
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

    public Profesor buscar(String id) {
        Profesor e = null;
        Usuarios u = null;
        try {
            u = new Usuarios();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.query = "select * from Profesor where cedula = " + id;
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                String id2 = rs.getString("cedula");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                Usuario usu = u.buscar2(rs.getString("usuario"));
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
    
    public Profesor buscaremail(String email) {
        Profesor e = null;
        Usuarios u = null;
        try {
            u = new Usuarios();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.query = "select * from Profesor where usuario = " + email;
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                String id2 = rs.getString("cedula");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                Usuario usu = u.buscar2(rs.getString("usuario"));
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
    
     public Profesor buscarNombre(String nom1) {
        Profesor e = null;
        Usuarios u = null;
        try {
            u = new Usuarios();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Profesores.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.query = "select * from Profesor where nombre = \"" + nom1.trim()+"\"";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                String id2 = rs.getString("cedula");
                String nom = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                Usuario usu = u.buscar2(rs.getString("usuario"));
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
