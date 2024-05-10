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
import lk.ijse.model.tm.AddClientTm;
import lk.ijse.repository.AddClientRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class addNewClientController {

    @FXML
    private TableView<AddClientTm> tblClient;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCnumber;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane rootaddNewClient;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCId;

    @FXML
    private TextField txtCemail;

    @FXML
    private TextField txtCname;

    @FXML
    private TextField txtCnumber;
    private List<Client> clientList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.clientList = getAllClients();
        setCellValueFactory();
        loadClientTable();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCnumber.setCellValueFactory(new PropertyValueFactory<>("cnumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadClientTable() {
        ObservableList<AddClientTm> tmList = FXCollections.observableArrayList();

        for (Client client : clientList) {
            AddClientTm addClientTm = new AddClientTm(
                    client.getId(),
                    client.getName(),
                    client.getAddress(),
                    client.getCnumber(),
                    client.getEmail()
            );

            tmList.add(addClientTm);
        }
        tblClient.setItems(tmList);
        AddClientTm selectedItem = (AddClientTm) tblClient.getSelectionModel().getSelectedItem();

    }

    private List<Client> getAllClients() {
        List<Client> clientList = null;
        try {
            clientList = AddClientRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientList;
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootaddNewClient.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENTS");
    }

    @FXML
    void btnClearOnACtion(ActionEvent event) {
        clear();
    }
    public void clear(){
        txtCId.clear();
        txtCname.clear();
        txtAddress.clear();
        txtCnumber.clear();
        txtCemail.clear();
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
        String id = txtCId.getText();
        String name = txtCname.getText();
        String address = txtAddress.getText();
        String number = txtCnumber.getText();
        String email = txtCemail.getText();

        Client client = new Client(id,name,address,number,email);
        try {
            boolean isSaved = AddClientRepo.save(client);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                clear();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

}
