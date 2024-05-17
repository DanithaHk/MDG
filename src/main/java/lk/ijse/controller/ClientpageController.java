package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.model.Client;
import lk.ijse.model.Employee;
import lk.ijse.model.Product;
import lk.ijse.model.tm.ClientTm;
import lk.ijse.repository.ClientRepo;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.ProductRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientpageController {

    @FXML
    private TableView<ClientTm> tblClient;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNumber;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private AnchorPane rootClientpage;

    @FXML
    private TextField txtCadress;

    @FXML
    private TextField txtCconatctnumber;

    @FXML
    private TextField txtCemail;

    @FXML
    private TextField txtCname;

    @FXML
    private TextField txtclientId;
    private List<Client> clientList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.clientList = getAllClient();
        setCellValueFactory();
        loadClientTable();
    }

    private void loadClientTable() {
        ObservableList<ClientTm> clientTms = FXCollections.observableArrayList();

        for (Client client : clientList) {
            ClientTm clientTm = new ClientTm(
                    client.getId(),
                    client.getName(),
                    client.getAddress(),
                    client.getCnumber(),
                    client.getEmail()
            );

            clientTms.add(clientTm);
        }
        tblClient.setItems(clientTms);
        ClientTm selectedItem = (ClientTm) tblClient.getSelectionModel().getSelectedItem();

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("cnumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private List<Client> getAllClient() {
        List<Client> clientList = null;
        try {
            clientList = ClientRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientList;
    }

    @FXML
    void btnAddClientOnAction(ActionEvent event) throws IOException {
        boolean isValidate = validateCustomer();
        if (isValidate) {


            String id = txtclientId.getText();
            String name = txtCname.getText();
            String address = txtCadress.getText();
            String number = txtCconatctnumber.getText();
            String email = txtCemail.getText();

            Client client = new Client(id, name, address, number, email);
            try {
                boolean isSaved = ClientRepo.save(client);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                    initialize();
                    clear();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private boolean validateCustomer() {
        int num = 0;
        String id = txtclientId.getText();

        boolean isCustomerIdValidated = Pattern.matches("[C][0-9]{3,}", id);
        if (!isCustomerIdValidated) {
            //new Alert(Alert.AlertType.ERROR, "INVALID Id").show();
            num = 1;
        }

        String name = txtCname.getText();
        boolean isCustomerNameValidated = Pattern.matches("[A-Za-z]{3,}", name);
        if (!isCustomerNameValidated) {
           // new Alert(Alert.AlertType.ERROR, "INVALID Name").show();
            num = 1;
        }


        String number = txtCconatctnumber.getText();
        boolean isCustomerTelValidated = Pattern.matches("[0-9]{10}", number);
        if (!isCustomerTelValidated) {
            //new Alert(Alert.AlertType.ERROR, "INVALID Tel").show();
            num = 1;

        }

        String email = txtCemail.getText();
        boolean isCustomerEmailValidated = Pattern.matches("[a-z].*(com|lk)", email);
        if (!isCustomerEmailValidated) {
            //new Alert(Alert.AlertType.ERROR, "INVALID Email").show();
            num = 1;
            vibrateTextField(txtCemail);
        }
        String address = txtCadress.getText();
        boolean isCustomerAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", address);
        if (!isCustomerAddressValidated) {
            //new Alert(Alert.AlertType.ERROR, "INVALID Address").show();
            num = 1;
            vibrateTextField(txtCadress);
        }
        if (num == 1) {
            num = 0;
            return false;
        } else {
            num = 0;
            return true;
        }
    }

    private void vibrateTextField(TextField textField) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0), new KeyValue(textField.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(50), new KeyValue(textField.translateXProperty(), -6)),
                new KeyFrame(Duration.millis(100), new KeyValue(textField.translateXProperty(), 6)),
                new KeyFrame(Duration.millis(150), new KeyValue(textField.translateXProperty(), -6)),
                new KeyFrame(Duration.millis(200), new KeyValue(textField.translateXProperty(), 6)),
                new KeyFrame(Duration.millis(250), new KeyValue(textField.translateXProperty(), -6)),
                new KeyFrame(Duration.millis(300), new KeyValue(textField.translateXProperty(), 6)),
                new KeyFrame(Duration.millis(350), new KeyValue(textField.translateXProperty(), -6)),
                new KeyFrame(Duration.millis(400), new KeyValue(textField.translateXProperty(), 0))

        );

        textField.setStyle("-fx-border-color: red;");
        timeline.play();

        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.seconds(3), new KeyValue(textField.styleProperty(), "-fx-border-color: #bde0fe;"))
        );

        timeline1.play();
    }

    @FXML
    void btnDeleteClientOnAction(ActionEvent event) {
        String id = txtclientId.getText();

        boolean isDeleted = false;
        try {
            isDeleted = ClientRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                initialize();
                clear();
            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnSearchClientOnAction(ActionEvent event) {
        String id = txtclientId.getText();

        Client client = null;

        try {
            client = ClientRepo.search(id);
            if (client != null) {
                txtCname.setText(client.getName());
                txtCadress.setText(client.getAddress());
                txtCconatctnumber.setText(client.getCnumber());
                txtCemail.setText(client.getEmail());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear(){
        txtclientId.clear();
        txtCname.clear();
        txtCadress.clear();
        txtCconatctnumber.clear();
        txtCemail.clear();
    }

    @FXML
    void btnUpdateClientOnAction(ActionEvent event) {
        String id = txtclientId.getText();
        String name = txtCname.getText();
        String address = txtCadress.getText();
        String contactNumber = txtCconatctnumber.getText();
        String email = txtCemail.getText();


        Client client = new Client(id,name,address,contactNumber,email);

        boolean isUpdate = false;
        try {
            isUpdate = ClientRepo.update(client);
            if(isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                initialize();
                clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    public void txtAddClientIdOnAction(ActionEvent event) {
    }
}
