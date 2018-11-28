package sample.DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommissionDAO {

    Connection conn;

    private static ObservableList<sample.Modelos.Commission> data = FXCollections.observableArrayList();

    public CommissionDAO(Connection conn) { this.conn = conn; }

    public static void addTransaction(sample.Modelos.Commission customer)
    {
        data.add(customer);
    }

    public List<sample.Modelos.Commission> findAll() {
        List<sample.Modelos.Commission> Commission = new ArrayList<sample.Modelos.Commission>();
        try {
            String query = "SELECT * FROM transaction";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.Modelos.Commission p = null;
            while(rs.next()) {
                p = new sample.Modelos.Commission(
                        rs.getInt("id_commission"),
                        rs.getString("percentage")
                );
                Commission.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return Commission;
    }


    public ObservableList<sample.Modelos.Commission> fetchAll() {
        ObservableList<sample.Modelos.Commission> Commission = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM transaction";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.Modelos.Commission p = null;
            while(rs.next()) {
                p = new sample.Modelos.Commission(
                        rs.getInt("id_commission"),
                        rs.getString("percentage")
                );
                Commission.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return Commission;
    }

    public sample.Modelos.Commission fetch(int id_commission) {
        ResultSet rs = null;
        sample.Modelos.Commission e = null;
        try {
            String query = "SELECT * FROM transaction where id = " + id_commission;
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            e = new sample.Modelos.Commission(
                    rs.getInt("id_commission"),
                    rs.getString("percentage")
            );
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

    public Boolean insert(sample.Modelos.Commission customer) {
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

    public Boolean update(sample.Modelos.Commission customer) {
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
