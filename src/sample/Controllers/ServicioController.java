package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Complements.MySQL;
import sample.Complements.Ticket;
import sample.DAO.*;
import sample.Modelos.*;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServicioController implements Initializable {
    Controller controller=new Controller();
    User user=null;
    String direccion, company_name, referencia;
    int cantPagar=0, pago, id=0, id2=0,id3=0,idbus=0, idc=0,idbusTK=0, idr=0 ;
    String telefono="",nombre;
    byte bi[] = null;

    @FXML ComboBox<String> combobox, combobox2,combobox3;
    @FXML Button btnRegresar, btnAceptar,acceptarbus;
    @FXML TextField TF4, TF1, TF2, TF3, TF5,origenbus,destinobus, TFNumeAutorizacion;
    @FXML Label lblPago;

    @FXML TableView tabla;
    @FXML TableColumn clmCantidad,clmOtros;
    @FXML ImageView lblImagen;
    @FXML Image img;
    @FXML StackPane stPane;

    CompanyDAO companyDAO=new CompanyDAO(MySQL.getConnection());
    CityDAO cityDAO = new CityDAO(MySQL.getConnection());
    PhoneplanDAO phoneplanDAO=new PhoneplanDAO(MySQL.getConnection());
    BusDAO busDAO = new BusDAO(MySQL.getConnection());
    TypeHomeServiceDAO typeHomeServiceDAO=new TypeHomeServiceDAO(MySQL.getConnection());
    HomeServiceDAO homeServiceDAO=new HomeServiceDAO(MySQL.getConnection());
    PlanHSDAO planHSDAO=new PlanHSDAO(MySQL.getConnection());
    PaymentHSDAO paymentHSDAO=new PaymentHSDAO(MySQL.getConnection());
    RechargeDAO rechargeDAO=new RechargeDAO(MySQL.getConnection());
    busticketDAO bustickDAO =new busticketDAO(MySQL.getConnection());
    GiftcartcreditDAO giftcartcreditDAO =new GiftcartcreditDAO(MySQL.getConnection());
    GiftcartDAO giftcartDAO = new GiftcartDAO(MySQL.getConnection());

    ObservableList<TablaHomeService> tablaHomeService=null;
    ObservableList<Phoneplan> p=null;
    ObservableList<Bus> b = null;
    ObservableList<Busticket> bt = null;
    ObservableList<Giftcartcredit> c = null;
    List<Company> company=new ArrayList<>();
    List<City> city =new ArrayList<>();
    List<TypeHomeService> typeHomeServices=new ArrayList<>();
    quantity_telephone e=null;
    Company company_select=null;
    Busticket busTK;
    Giftcart GC;
    Recharge recharge=null;
    PaymentHS paymentHS=null;

    public static final String ticket = "results/Tickets/ticket.pdf";
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TF1.setVisible(false);
        TF2.setVisible(false);
        TF3.setVisible(false);
        TF4.setVisible(false);
        TF5.setVisible(false);
        combobox2.setVisible(false);
        combobox3.setVisible(false);
        acceptarbus.setVisible(false);
        clmOtros.setVisible(false);
        switch (direccion){
            case "Hogar":
                TF1.setText("Comision");
                TF2.setText("Pago");
                TF3.setText("Nuemero de referncia");
                TF4.setText("Telefono");
                TF5.setText("Confirmar telefono");
                clmCantidad.setText("Compañia");
                clmCantidad.setPrefWidth(595);
                company=companyDAO.findAll(2);
                typeHomeServices=typeHomeServiceDAO.findAll();
                combobox.setPromptText("Sevicios del Hogar");
                for (int i = 0; i < typeHomeServices.size(); i++) {
                    combobox.getItems().add(typeHomeServices.get(i).getType());
                }

                TF2.setEditable(false);
                TF1.setVisible(true);
                TF2.setVisible(true);
                TF3.setVisible(true);
                TF4.setVisible(true);
                TF5.setVisible(true);
                break;
            case "Recargas":
                TF1.setText("Comision");
                TF4.setText("Telefono");
                TF5.setText("Confirmar Telefono");
                clmCantidad.setText("Cantidad");
                clmCantidad.setPrefWidth(595);
                company=companyDAO.findAll(1);
                combobox.setPromptText("Compañia");
                for (int i = 0; i < company.size(); i++) {
                    combobox.getItems().add(company.get(i).getName());
                }
                TF1.setVisible(true);
                TF4.setVisible(true);
                TF5.setVisible(true);
                break;

            case "Pagos":
                clmCantidad.setVisible(true);
                clmCantidad.setText("CANTIDAD");
                combobox.setPromptText("Pagos en linea");
                company=companyDAO.fetchAll(4);
                for (int i = 0; i < company.size(); i++) {
                    combobox.getItems().add(company.get(i).getName());

                }
                btnAceptar.setText("PAGAR");
                break;
            case "Autobus":
                company=companyDAO.fetchAll(3);
                for (int i = 0; i < company.size(); i++) {
                    combobox.getItems().add(company.get(i).getName());
                }
                city   =cityDAO.fetchAll();
                for (int i = 0; i < city.size(); i++) {
                    combobox2.getItems().add(city.get(i).getCity());
                    combobox3.getItems().add(city.get(i).getCity());
                }
                combobox.setPromptText("Empresa");
                combobox2.setPromptText("origen");
                combobox3.setPromptText("Destino");
                clmCantidad.setText("SALIDA");
                clmOtros.setText("COSTO");
                clmOtros.setVisible(true);
                TF5.setText("Nombre del Cliente");
                TF5.setVisible(true);
                combobox2.setVisible(true);
                combobox3.setVisible(true);
                acceptarbus.setVisible(true);
                btnAceptar.setText("PAGAR");
        }

        acceptarbus.setOnAction(event ->{
            for (int i = 0; i < company.size(); i++) {
                if (company.get(i).getName().equals(combobox.getSelectionModel().getSelectedItem())){
                    id=company.get(i).getId_company();
                    break;
                }
            }
            for (int i = 0; i < city.size(); i++) {
                if (city.get(i).getCity().equals(combobox2.getSelectionModel().getSelectedItem())){
                    id2=city.get(i).getId_city();
                    break;
                }
            }
            for (int i = 0; i < city.size(); i++) {
                if (city.get(i).getCity().equals(combobox3.getSelectionModel().getSelectedItem())){
                    id3=city.get(i).getId_city();
                    break;
                }
            }


            clmCantidad.setCellValueFactory(new PropertyValueFactory<Bus, String>("outdate"));
            clmOtros.setCellValueFactory(new PropertyValueFactory<Bus, String>("amount"));

            tabla.setItems(busDAO.fetch2(id,id2,id3));
            b=busDAO.fetch2(id,id2,id3);

        } );

        btnRegresar.setOnAction(event -> {
            controller.mostMenu(event, user);
        });

        TF1.setEditable(false);
        TF3.setOnMouseClicked(eventM);
        TF2.setOnMouseClicked(eventM);
        TF4.setOnMouseClicked(eventM);
        TF5.setOnMouseClicked(eventM);
        TFNumeAutorizacion.setOnMouseClicked(eventM);

        combobox.setOnAction(event1 -> {
            switch (direccion){
                case "Hogar":
                    int id_type=0;
                    for (int i = 0; i < typeHomeServices.size(); i++) {
                        if (typeHomeServices.get(i).getType().equals(combobox.getSelectionModel().getSelectedItem())){
                            id_type=typeHomeServices.get(i).getId_TypeHS();
                            break;
                        }
                    }
                    clmOtros.setVisible(false);
                    clmCantidad.setPrefWidth(600);
                    clmCantidad.setCellValueFactory(new PropertyValueFactory<TablaHomeService, String>("name"));
                    tabla.setItems(homeServiceDAO.fetch2(id_type));
                    tablaHomeService=homeServiceDAO.fetch2(id_type);
                    break;
                case "Recargas":
                    clmOtros.setVisible(false);
                    clmCantidad.setPrefWidth(600);
                    id=0;
                    for (int i = 0; i < company.size(); i++) {
                        if (company.get(i).getName().equals(combobox.getSelectionModel().getSelectedItem())){
                            id=company.get(i).getId_company();
                            break;
                        }
                    }
                    clmCantidad.setCellValueFactory(new PropertyValueFactory<Phoneplan, String>("quantity"));
                    tabla.setItems(phoneplanDAO.fetchPlanCompany(id));
                    p=phoneplanDAO.fetchPlanCompany(id);
                    company_select=companyDAO.fetch(id);
                    if(company_select.getIs()==null){
                        lblImagen.setVisible(false);
                    }else {
                        try {
                            bi= company_select.getIs().getBytes(1, (int) company_select.getIs().length());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        Image image=new Image(new ByteArrayInputStream(bi));
                        lblImagen.setImage(image);
                        lblImagen.setFitWidth(460);
                        lblImagen.setFitHeight(320);
                        lblImagen.setPreserveRatio(true);
                    }
                    TF1.setText(company_select.getId_commission().getPercentage());
                    break;

                case "Pagos":
                    for (int i = 0; i < company.size(); i++) {
                        if (company.get(i).getName().equals(combobox.getSelectionModel().getSelectedItem())){
                            id=company.get(i).getId_company();
                            break;
                        }
                    }
                    clmCantidad.setCellValueFactory(new PropertyValueFactory<Giftcartcredit, String>("credit"));
                    tabla.setItems(giftcartcreditDAO.fetchGcredit(id));
                    c=giftcartcreditDAO.fetchGcredit(id);
            }
        });

        tabla.setOnMouseClicked(data);
        btnAceptar.setOnAction(Aceptar);
        TF3.setOnKeyPressed(info);

    }

    EventHandler<KeyEvent> info=new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode().getName().equals("Enter")){
                if (direccion.equals("Hogar")) {
                    referencia = TF3.getText();
                    company_name = tablaHomeService.get(tabla.getSelectionModel().getSelectedIndex()).getName();
                    e = planHSDAO.getQuantityAndPhone(company_name, referencia);
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    if (e != null) {
                        if (e.getPay_amount() != 0) {
                            lblPago.setText("Cuenta de usuario pagada");
                            lblPago.setVisible(true);
                        } else {
                            cantPagar = e.getQuantity();
                            telefono = e.getTelephone();
                            a.setTitle("Confirmar");
                            a.setContentText("Datos registrados");
                            a.show();
                            TF2.setEditable(true);
                        }
                    } else {
                        a.setTitle("Error");
                        a.setContentText("Numero de referencia o empresa invalido");
                        a.show();
                    }
                }
            }
        }
    };

    EventHandler<ActionEvent> Aceptar=new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (TFNumeAutorizacion.getText().equals("1603")){
                    lblPago.setVisible(false);
                    switch (direccion) {
                        case "Hogar":
                            if(TF4.getText().equals(TF5.getText())){
                                pago=Integer.parseInt(TF2.getText());
                                if (pago>=cantPagar && telefono.equals(TF4.getText())) {
                                    if (TF4.getText().equals(e.getTelephone())){
                                        pago-=cantPagar;
                                        if (paymentHSDAO.update(cantPagar, pago, referencia))
                                            lblPago.setVisible(true);
                                    }else{
                                        lblPago.setText("Numero de telefono invalido");
                                        lblPago.setVisible(true);
                                    }
                                }
                                else{
                                    lblPago.setText("Pago insuficiente");
                                    lblPago.setVisible(true);
                                }
                            }else{
                                lblPago.setText("Los telefonos no coinciden");
                                lblPago.setVisible(true);
                            }
                            paymentHS=paymentHSDAO.fetch(TF3.getText());
                            ticketimp(event, paymentHS);
                            break;
                        case "Recargas":
                            if (TF4.getText().equals(TF5.getText())){
                                idr=rechargeDAO.count()+1;
                                telefono=TF4.getText();
                                cantPagar=p.get(tabla.getSelectionModel().getSelectedIndex()).getQuantity();
                                company_name=combobox.getSelectionModel().getSelectedItem();
                                id=phoneplanDAO.getId_phoneplan(cantPagar,company_name);
                                if (rechargeDAO.insert(idr, telefono, id)){
                                    lblPago.setText("Recarga registrada");
                                    lblPago.setVisible(true);
                                }else{
                                    lblPago.setText("Recarga invalida");
                                    lblPago.setVisible(true);
                                }
                            }else{
                                lblPago.setText("Los numero no coinciden");
                                lblPago.setVisible(true);
                            }
                            recharge=rechargeDAO.fetch(idr);
                            ticketimp(event, recharge);
                            break;
                        case "Autobus":
                            idbus =b.get(tabla.getSelectionModel().getSelectedIndex()).getId_bus();
                            nombre = TF5.getText();
                            bustickDAO.insert(nombre,idbus);
                            idbusTK = bustickDAO.Count();
                            busTK= bustickDAO.fetch(idbusTK);
                            lblPago.setText("Boleto registrado");
                            lblPago.setVisible(true);
                            ticketimp(event, busTK);

                            break;

                        case "Pagos":
                            idc = c.get(tabla.getSelectionModel().getSelectedIndex()).getId_giftcartcredit();
                            giftcartDAO.insert(idc);
                            idc = giftcartDAO.Count();
                            GC = giftcartDAO.fetch(idc);
                            lblPago.setText("Pago registrado");
                            lblPago.setVisible(true);
                            ticketimp(event,GC);

                    }
                }else{
                    lblPago.setText("Numero de autorizacion invalido");
                    lblPago.setVisible(true);
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }
    };

    private void ticketimp(ActionEvent event, Object t) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("To print");
            a.setContentText("¿Desea imprimir ticket?");
            Optional<ButtonType> responde = a.showAndWait();
            if (responde.get().equals(ButtonType.OK)) {
                try {
                    File file = new File(ticket);
                    file.getParentFile().mkdirs();
                    switch (direccion){
                        case "Hogar":
                            new Ticket().createHomeService(ticket, paymentHS);
                            Desktop.getDesktop().open(file);
                            break;
                        case "Recargas":
                            new Ticket().createRecharge(ticket, recharge);
                            Desktop.getDesktop().open(file);
                            break;
                        case "Autobus":
                            new Ticket().createBUSTK(ticket,busTK);
                            Desktop.getDesktop().open(file);
                            break;
                        case "Pagos":
                            new Ticket().GiftCartTK(ticket, GC);
                            Desktop.getDesktop().open(file);
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Main.primaryStage.show();
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).hide();
            }else {
                Main.primaryStage.show();
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).hide();
            }
        }




    EventHandler<MouseEvent> eventM=new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            TextField text=(TextField) event.getSource();
                text.setText("");
        }
    };

    EventHandler<MouseEvent> data=new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (direccion.equals("Hogar")){
                try {
                    for (int i = 0; i < company.size(); i++) {
                        if (company.get(i).getName().equals(tablaHomeService.get(tabla.getSelectionModel().getSelectedIndex()).getName())){
                            id=company.get(i).getId_company();
                            break;
                        }
                    }
                    company_select=companyDAO.fetch(id);
                    if(company_select.getIs()==null){
                        lblImagen.setVisible(false);
                    }else {
                        bi= company_select.getIs().getBytes(1, (int) company_select.getIs().length());
                        Image image=new Image(new ByteArrayInputStream(bi));
                        lblImagen.setImage(image);
                        lblImagen.setFitWidth(460);
                        lblImagen.setFitHeight(320);
                        lblImagen.setPreserveRatio(true);
                    }
                    TF1.setText(company_select.getId_commission().getPercentage());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    };

    public void setdestino(String direccion){
        this.direccion=direccion;
    }
    public void setUser(User user){
        this.user=user;
    }
}
