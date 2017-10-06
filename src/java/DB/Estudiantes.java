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
import java.util.List;

/**
 *
 * @author willy
 */
public class Estudiantes implements IBaseDatos<Estudiante>{

    private PreparedStatement preparedStmt;
    private Connection connection;
    private String query;
  
    public Estudiantes() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }

    @Override
    public List<Estudiante> findAll() {
        ArrayList<Estudiante> est = new ArrayList();
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
                String correoacu = rs.getString("correoAcudiente");
                String nombreacu = rs.getString("nombreAcudiente");
                int curso = rs.getInt("curso");
                Estudiante e = new Estudiante(id2, nom,apellido, correoacu, nombreacu, curso);
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

    @Override
    public boolean insert(Estudiante a) {
        boolean r = false;
        try {
            // the mysql insert statement
            query = " insert into Estudiante (id,nombre,apellido,correoAcudiente,nombreAcudiente,curso)"
                    + " values (?, ?, ?, ?,?,?);";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, a.getId());
            preparedStmt.setString(2, a.getNombre().trim());
            preparedStmt.setString(3, a.getApellido().trim());            
            preparedStmt.setString(3, a.getCorreoAcudiente().trim());
            preparedStmt.setString(3, a.getNombreAcudiente().trim());
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

    @Override
    public boolean update(Estudiante a) {
        boolean r = false;
        if (buscar(a.getId()) != null) {
            try {
                //Update
                // create the java mysql update preparedstatement
                query = "update Estudiante set nombre = ?, apellido=?, curso=?, correoAcudiente=?, nombreAcudiente=? where id = ?";
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1,a.getNombre().trim());
                preparedStmt.setString(2,a.getApellido().trim());
                preparedStmt.setString(3,a.getCorreoAcudiente().trim());
                preparedStmt.setString(4,a.getNombreAcudiente().trim());
                preparedStmt.setInt(5,a.getCurso());
                preparedStmt.setInt(6, a.getId());
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
    public boolean delete(Estudiante t) {
        boolean hecho = false;
        try {
            //borramos el curso
            this.query = "delete from Estudiante where id = " + t.getId();
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
                String correoacu = rs.getString("correoAcudiente");
                String nombreacu = rs.getString("nombreAcudiente");
                int curso = rs.getInt("curso");
                e = new Estudiante(id2, nom,apellido, correoacu, nombreacu, curso);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        return e;
    }
    
    public ArrayList<Estudiante> GetEstudiantesCurso(int c) {
        ArrayList<Estudiante> est = new ArrayList();
        this.query = "select * from Estudiante where curso = " + c;
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
                String correoacu = rs.getString("correoAcudiente");
                String nombreacu = rs.getString("nombreAcudiente");
                int curso = rs.getInt("curso");
                Estudiante e = new Estudiante(id2, nom,apellido, correoacu, nombreacu, curso);
                est.add(e);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        for (int i = 0; i < est.size(); i++) {
            Estudiante min = est.get(i);
            for (int j = 0; j < est.size(); j++) {
                if (est.get(j).getId() < min.getId()) {
                    est.set(i, est.get(j));
                    est.set(j, min);
                    min = est.get(i);
                }
            }
        }
        return est;
    }

    public ArrayList<Estudiante> GetEstudiantesSinCurso() {
        ArrayList<Estudiante> est = new ArrayList();
        this.query = "select * from Estudiante where curso = null";
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
                String correoacu = rs.getString("correoAcudiente");
                String nombreacu = rs.getString("nombreAcudiente");
                int curso = rs.getInt("curso");
                Estudiante e = new Estudiante(id2, nom,apellido, correoacu, nombreacu, curso);
                est.add(e);
            }
            st.close();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            System.out.println("Failed to make update!");
            ex.printStackTrace();
        }
        for (int i = 0; i < est.size(); i++) {
            Estudiante min = est.get(i);
            for (int j = 0; j < est.size(); j++) {
                if (est.get(j).getId() < min.getId()) {
                    est.set(i, est.get(j));
                    est.set(j, min);
                    min = est.get(i);
                }
            }
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
