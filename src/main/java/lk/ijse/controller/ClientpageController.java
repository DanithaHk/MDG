package lk.ijse.controller;

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
        String id = txtclientId.getText();
        String name = txtCname.getText();
        String address = txtCadress.getText();
        String number = txtCconatctnumber.getText();
        String email = txtCemail.getText();

        Client client = new Client(id,name,address,number,email);
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

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootClientpage.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENT");
    }

    @FXML
    void btnDeleteClientOnAction(ActionEvent event)  {
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
