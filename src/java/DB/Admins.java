/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Modelo.Admin;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author willy
 */
public class Admins implements IBaseDatos<Admin> {

    private PreparedStatement preparedStmt;
    private Connection connection;
    private String query;

    public Admins() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }

    @Override
    public boolean insert(Admin t) {
        boolean r = false;
//        try {
//            // the mysql insert statement
//            query = " insert into Cursos (id,nombre,profesor)"
//                    + " values (?, ?, ?);";
//            // create the mysql insert preparedstatement
//            preparedStmt = connection.prepareStatement(query);
//            preparedStmt.setInt(1, a.getId());
//            preparedStmt.setString(2, a.getNombre().trim());
//            preparedStmt.setInt(3, a.getProfesor());
//            // execute the preparedstatement
//            preparedStmt.execute();
//            System.out.println("You made it, the insertion is ok!");
//            r = true;
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            System.out.println("Failed to make insertion!");
//            e.printStackTrace();
//        }
        return r;
    }

    @Override
    public boolean update(Admin t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Admin t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    public Estudiante buscar(int id) {
//        Estudiante e = null;
//        this.query = "select * from Estudiante where id = " + id;
//        try {
//            // create the java statement
//            Statement st = this.connection.createStatement();
//            // execute the query, and get a java resultset
//            ResultSet rs = st.executeQuery(this.query);
//            // iterate through the java resultset
//            while (rs.next()) {
//                int id2 = rs.getInt("id");
//                String nom = rs.getString("nombre");
//                String apellido = rs.getString("apellido");
//                 int curso = rs.getInt("curso");
//                e = new Estudiante(id2, nom, apellido,curso);
//            }
//            st.close();
//        } catch (SQLException ex) {
//            // TODO Auto-generated catch block
//            System.out.println("Failed to make update!");
//            ex.printStackTrace();
//        }
//        return e;
//    }
//    public ArrayList<Curso> GetAdmins() {
//        ArrayList<Curso> cur2 = new ArrayList<>();
//        this.query = "select * from Cursos ";
//        try {
//            // create the java statement
//            Statement st = this.connection.createStatement();
//            // execute the query, and get a java resultset
//            ResultSet rs = st.executeQuery(this.query);
//            // iterate through the java resultset
//            while (rs.next()) {
//                int id2 = rs.getInt("id");
//                String nom = rs.getString("nombre");
//                int profesor = rs.getInt("profesor");
//                Curso e = new Curso(id2, nom, profesor);
//                cur2.add(e);
//            }
//            st.close();
//            
//        } catch (SQLException ex) {
//            // TODO Auto-generated catch block
//            System.out.println("Failed to make update!");
//            ex.printStackTrace();
//        }
//        return cur2;
//    }
    
     public Admin buscarNombre(String nom1) {
        Admin e = null;
        boolean r = false;
        this.query = "select * from Admin where nombre = \"" + nom1+"\"";
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
                String usu = rs.getString("usuario");
                e = new Admin(id2, nom, apellido, null);
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

    @Override
    public List findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
