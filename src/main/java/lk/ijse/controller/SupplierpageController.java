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
import lk.ijse.model.Material;
import lk.ijse.model.Supplier;
import lk.ijse.model.tm.MaterialTm;
import lk.ijse.model.tm.SupplierTm;
import lk.ijse.repository.MaterialRepo;
import lk.ijse.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierpageController {

    @FXML
    private TableView<SupplierTm> SuplierTable;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNumber;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colUsername;


    @FXML
    private AnchorPane rootSupplier;

    @FXML
    private TextField txtSUsername;

    @FXML
    private TextField txtSaddress;

    @FXML
    private TextField txtScontactNumber;

    @FXML
    private TextField txtSid;

    @FXML
    private TextField txtsname;

    private List<Supplier>  supplierList= new ArrayList<>();

   public void initialize() {
        this.supplierList = getAllSuppliers();
        setCellValueFactory();
        loadSupplierTable();
    }

    private List<Supplier> getAllSuppliers() {
        List<Supplier> supplierList = null;
        try {
            supplierList = SupplierRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return supplierList;
    }

    private void loadSupplierTable() {
        ObservableList<SupplierTm> tmList = FXCollections.observableArrayList();

        for (Supplier supplier : supplierList) {
            SupplierTm supplierTm = new SupplierTm(
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getAddress(),
                    supplier.getContactNumber(),
                    supplier.getUsername()

            );

            tmList.add(supplierTm);
        }
        SuplierTable.setItems(tmList);
        SupplierTm selectedItem = (SupplierTm) SuplierTable.getSelectionModel().getSelectedItem();

    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootSupplier.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENTS");
    }

    @FXML
    void btnADeleteSuplierOnAction(ActionEvent event) {
        String id = txtSid.getText();

        boolean isDeleted = false;
        try {
            isDeleted = SupplierRepo.delete(id);
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
    void btnAddSuplierOnAction(ActionEvent event) {
        String id = txtSid.getText();
        String name = txtsname.getText();
        String address = txtSaddress.getText();
        String contactNumber = txtScontactNumber.getText();
        String username = txtSUsername.getText();

        Supplier supplier = new Supplier(id, name, address, contactNumber,username);

        boolean isSaved = false;

        try {
            isSaved = SupplierRepo.save(supplier);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved!").show();
                initialize();
                clear();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnSearchSiplierOnAction(ActionEvent event) {
        String id = txtSid.getText();
        Supplier supplier = null;
        try {
            supplier = SupplierRepo.search(id);
            if (supplier != null) {
                txtsname.setText(supplier.getName());
                txtSaddress.setText(supplier.getAddress());
                txtScontactNumber.setText(supplier.getContactNumber());
                txtSUsername.setText(supplier.getUsername());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateSuplierOnAction(ActionEvent event) {
        String id = txtSid.getText();
        String name = txtsname.getText();
        String address = txtSaddress.getText();
        String contactNumber = txtScontactNumber.getText();
        String username = txtSUsername.getText();


        Supplier supplier = new Supplier(id,name,address,contactNumber,username);

        boolean isUpdate = false;
        try {
            isUpdate = SupplierRepo.update(supplier);
            if(isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                initialize();
                clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private void clear() {
        txtSid.clear();
        txtsname.clear();
        txtSaddress.clear();
        txtScontactNumber.clear();
        txtSUsername.clear();
    }


}
