package sample.DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.authentication.MysqlClearPasswordPlugin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Complements.MySQL;
import sample.Modelos.Company;
import sample.Modelos.PlanHS;
import sample.Modelos.TablaHomeService;
import sample.Modelos.TypeHomeService;

public class HomeServiceDAO {

    Connection conn;
    TypeHomeServiceDAO typeHomeServiceDAO=new TypeHomeServiceDAO(MySQL.getConnection());
    CompanyDAO companyDAO=new CompanyDAO(MySQL.getConnection());
    PlanHSDAO planHSDAO=new PlanHSDAO(MySQL.getConnection());

    int i;

    private static ObservableList<sample.Modelos.HomeService> data = FXCollections.observableArrayList();

    public HomeServiceDAO(Connection conn) { this.conn = conn; }

    public static void addTransaction(sample.Modelos.HomeService customer)
    {
        data.add(customer);
    }

    public List<sample.Modelos.HomeService> findAll() {
        List<sample.Modelos.HomeService> HomeService = new ArrayList<sample.Modelos.HomeService>();
        try {
            String query = "SELECT * FROM transaction";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.Modelos.HomeService p = null;
            while(rs.next()) {
                p = new sample.Modelos.HomeService(
                        rs.getInt("id_HomeService"),
                        typeHomeServiceDAO.fetch(rs.getInt("id_typeHS")),
                        companyDAO.fetch(rs.getInt("id_company")),
                        planHSDAO.fetch(rs.getInt("id_planHS"))
                );
                HomeService.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return HomeService;
    }


    public ObservableList<sample.Modelos.HomeService> fetchAll() {
        ObservableList<sample.Modelos.HomeService> HomeService = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM transaction";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.Modelos.HomeService p = null;
            while(rs.next()) {
                p = new sample.Modelos.HomeService(
                        rs.getInt("id_HomeService"),
                        typeHomeServiceDAO.fetch(rs.getInt("id_typeHS")),
                        companyDAO.fetch(rs.getInt("id_company")),
                        planHSDAO.fetch(rs.getInt("id_planHS"))
                );
                HomeService.add(p);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return HomeService;
    }

    public sample.Modelos.HomeService fetch(int trans_id) {
        ResultSet rs = null;
        sample.Modelos.HomeService e = null;
        try {
            String query = "SELECT * FROM homeservice where id_HomeService = " + trans_id;
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()){
                e = new sample.Modelos.HomeService(
                        rs.getInt("id_HomeService"),
                        typeHomeServiceDAO.fetch(rs.getInt("id_typeHS")),
                        companyDAO.fetch(rs.getInt("id_company")),
                        planHSDAO.fetch(rs.getInt("id_planHS"))
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return e;
    }

    public ObservableList<TablaHomeService> fetch2(int id_type) {
        ObservableList<TablaHomeService> HomeService = FXCollections.observableArrayList();
        try {
            String query = "select c.name " +
                    "from homeservice h inner join company c on h.id_company = c.id_company " +
                    "where h.id_TypeHS="+id_type+
                    " group by c.name";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            sample.Modelos.TablaHomeService p = null;
            while (rs.next()) {
                p = new sample.Modelos.TablaHomeService(
                        rs.getString("name")
                );
                HomeService.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return HomeService;
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

    public Boolean insert(sample.Modelos.HomeService customer) {
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

    public Boolean update(sample.Modelos.HomeService customer) {
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
