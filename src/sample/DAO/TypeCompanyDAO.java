package sample.DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TypeCompanyDAO {

    Connection conn;

    private static ObservableList<sample.Modelos.TypeCompany> data = FXCollections.observableArrayList();

    public TypeCompanyDAO(Connection conn) { this.conn = conn; }

    public static void addTransaction(sample.Modelos.TypeCompany customer)
    {
        data.add(customer);
    }

    public List<sample.Modelos.TypeCompany> findAll() {
        List<sample.Modelos.TypeCompany> TypeCompany = new ArrayList<sample.Modelos.TypeCompany>();
        try {
            String query = "SELECT * FROM typecompany";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.Modelos.TypeCompany p = null;
            while(rs.next()) {
                p = new sample.Modelos.TypeCompany(
                        rs.getInt("id_typecompany"),
                        rs.getString("typecompany")
                );
                TypeCompany.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return TypeCompany;
    }


    public ObservableList<sample.Modelos.TypeCompany> fetchAll() {
        ObservableList<sample.Modelos.TypeCompany> TypeCompany = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM typecompany";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.Modelos.TypeCompany p = null;
            while(rs.next()) {
                p = new sample.Modelos.TypeCompany(
                        rs.getInt("id_typecompany"),
                        rs.getString("typecompany")
                );
                TypeCompany.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return TypeCompany;
    }

    public sample.Modelos.TypeCompany fetch(int trans_id) {
        ResultSet rs = null;
        sample.Modelos.TypeCompany e = null;
        try {
            String query = "SELECT * FROM typecompany where id_typecompany = " + trans_id;
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if (rs.first()){
                e = new sample.Modelos.TypeCompany(
                        rs.getInt("id_typecompany"),
                        rs.getString("typecompany")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return e;
    }
/*
    public Boolean delete(int trans_id) {
        try {
            String query = "delete from transaction where id = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, trans_id);
            st.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean insert(sample.Modelos.TypeCompany customer) {
        try {
            String query = "insert into customer "
                    + " (category, description, date_created, amount, type)"
                    + " values (?, ?, ?, ?, ?)";
            PreparedStatement st =  conn.prepareStatement(query);
            st.setString(1, customer.getCategory());
            st.setString(2, customer.getDescription());
            st.setDate(  3, customer.getDate_created());
            st.setDouble(4, customer.getAmount());
            st.setString(5, String.valueOf(customer.getType()));
            st.execute();
            //data.add(customer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Boolean update(sample.Modelos.TypeCompany customer) {
        try {
            String query = "update customer "
                    + " set category = ?, description = ?, date_created = ?, amount = ?, type = ?"
                    + " where id=?";
            System.out.println(query + "updating....");
            PreparedStatement st =  conn.prepareStatement(query);

            st.setString(1, customer.getCategory());
            st.setString(2, customer.getDescription());
            st.setDate(  3, customer.getDate_created());
            st.setDouble(4, customer.getAmount());
            st.setString(5, String.valueOf(customer.getType()));
            st.setInt(6, customer.getId());
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }
*/
}
