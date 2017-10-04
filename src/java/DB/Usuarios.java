/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Modelo.Usuario;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author willy
 */
public class Usuarios implements IBaseDatos<Usuario>{

    private PreparedStatement preparedStmt;
    private Connection connection;
    private String query;

    public Usuarios() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }
    
    @Override
    public List<Usuario> findAll() {
        return null;
    }

    @Override
    public boolean insert(Usuario a) {
         boolean r = false;
        try {
            // the mysql insert statement
            query = " insert into Usuario (usuario,contrasena,tipoUsuario)"
                    + " values (?, ?, ?);";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, a.getUsuario().trim());
            preparedStmt.setString(2, a.getContrasena().trim());
            preparedStmt.setString(3, a.getTipoUsuario());
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
    public boolean update(Usuario a) {
        boolean r = false;
        if (buscar(a.getUsuario(),a.getContrasena()) != null) {
            try {
                //Update
                // create the java mysql update preparedstatement
                query = "update Usuario set contrasena=?, tipoUsuario=? where usuario = ?";
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1,a.getContrasena().trim());
                preparedStmt.setString(2,a.getTipoUsuario().trim());
                preparedStmt.setString(3,a.getUsuario().trim());
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
    public boolean delete(Usuario t) {
        boolean hecho = false;
        try {
            //borramos el curso
            this.query = "delete from Usuario where usuario = \""+t.getUsuario().trim()+"\"";
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
    
    public Usuario buscar(String usu,String cont) {
        Usuario e=null;
        this.query = "select * from Usuario where usuario = \""+usu.trim()+"\"";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                String usuario = rs.getString("usuario");
                String contrasena = rs.getString("contrasena");
                String tipoUsuario = rs.getString("tipoUsuario");
                e = new Usuario(usuario, contrasena, tipoUsuario);
                break;
            }
            if(!cont.equals(e.getContrasena())){
                e=null;
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
