package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderpageController {

    @FXML
    private TableView<?> orderTable;

    @FXML
    private AnchorPane rootOrderFrom;

    @FXML
    private TextField txtOclientId;

    @FXML
    private TextField txtOclientId1;

    @FXML
    private TextField txtOclientId11;

    @FXML
    private TextField txtOid;

    @FXML
    private TextField txtOid1;

    @FXML
    private TextField txtOid11;

    @FXML
    void btnAddOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/addNewOrder.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootOrderFrom.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add New Target form");
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootOrderFrom.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("dashbord Form");
    }

    @FXML
    void btnDeleteOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOrderOnAction(ActionEvent event) {

    }

    @FXML
    void txtAddOrderIdOnAction(ActionEvent event) {

    }

    public void btnUpdateOrderOnAction(ActionEvent event) {
    }
}
