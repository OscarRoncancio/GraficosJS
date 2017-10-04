/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Modelo.Horario;
import Modelo.Nota;
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
public class Horarios implements IBaseDatos<Horario> {

    private PreparedStatement preparedStmt;
    private Connection connection;
    private String query;

    public Horarios() throws URISyntaxException {
        DbConnection c = new DbConnection();
        this.connection = c.getConnection();
    }

    @Override
    public List<Horario> findAll() {
        ArrayList<Horario> mat = new ArrayList();
        this.query = "select * from Horario ";
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("id");
                String materia = rs.getString("materia");
                int dia = rs.getInt("dia");
                int hinicio = rs.getInt("hinicio");
                int hfin = rs.getInt("hfin");
                Horario e = new Horario(id2, materia, dia, hinicio, hfin);
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
    public boolean insert(Horario a) {
        boolean r = false;
        try {
            // the mysql insert statement
            query = " insert into Horario (id, materia, dia, hinicio, hfin)"
                    + " values (?, ?, ?, ?, ?);";
            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, a.getId());
            preparedStmt.setString(2, a.getMateria());
            preparedStmt.setInt(3, a.getDia());
            preparedStmt.setInt(4, a.getHinicio());
            preparedStmt.setInt(5, a.getHfin());
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
    public boolean update(Horario a) {
        boolean r = false;
        if (buscar(a.getId()) != null) {
            try {
                //Update
                // create the java mysql update preparedstatement
                query = "update Nota set materia=?, dia=?,hinicio=?, hfin=? where id = ?";
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, a.getMateria().trim());
                preparedStmt.setInt(2, a.getDia());
                preparedStmt.setInt(3, a.getHinicio());
                preparedStmt.setInt(4, a.getHfin());
                preparedStmt.setInt(5, a.getId());
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
    public boolean delete(Horario t) {
        boolean hecho = false;
        try {
            //borramos el curso
            this.query = "delete from Horario where id = " + t.getId();
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

    public Horario buscar(int id) {
        Horario e = null;
        this.query = "select * from Horario where id = " + id;
        try {
            // create the java statement
            Statement st = this.connection.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(this.query);
            // iterate through the java resultset
            while (rs.next()) {
                int id2 = rs.getInt("id");
                String materia = rs.getString("materia");
                int dia = rs.getInt("dia");
                int hinicio = rs.getInt("hinicio");
                int hfin = rs.getInt("hfin");
                e = new Horario(id2, materia, dia, hinicio, hfin);
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
