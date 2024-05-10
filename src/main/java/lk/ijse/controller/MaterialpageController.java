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
import lk.ijse.model.tm.MaterialTm;
import lk.ijse.repository.MaterialRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialpageController {

    @FXML
    private TableColumn<?, ?> colCostPErUnit;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<MaterialTm> materialtable;

    @FXML
    private AnchorPane rootMaterial;

    @FXML
    private TextField txtMCostPerUnit;

    @FXML
    private TextField txtMdescription;

    @FXML
    private TextField txtMid;

    @FXML
    private TextField txtMqty;

    private List<Material> materialList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.materialList = getAllMaterial();
        setCellValueFactory();
        loadMaterialTable();
    }

    private void loadMaterialTable() {
        ObservableList<MaterialTm> tmList = FXCollections.observableArrayList();

        for (Material material : materialList) {
            MaterialTm materialTm = new MaterialTm(
                    material.getId(),
                    material.getDescription(),
                    material.getQty(),
                    material.getCostPerOne()

            );

            tmList.add(materialTm);
        }
        materialtable.setItems(tmList);
        MaterialTm selectedItem = (MaterialTm) materialtable.getSelectionModel().getSelectedItem();

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colCostPErUnit.setCellValueFactory(new PropertyValueFactory<>("costPerOne"));

    }

    @FXML
    void btnAddMaterialtOnAction(ActionEvent event) throws IOException {
        String id = txtMid.getText();
        String desc = txtMdescription.getText();
        int qty = Integer.parseInt(txtMqty.getText());
        Double costPUnit = Double.valueOf(txtMCostPerUnit.getText());

        Material material = new Material(id, desc, qty, costPUnit);

        boolean isSaved = false;

        try {
            isSaved = MaterialRepo.save(material);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Material saved!").show();
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
        Stage stage = (Stage) this.rootMaterial.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENT");

    }

    @FXML
    void btnDeleteMaterialOnAction(ActionEvent event) {
        String id = txtMid.getText();

        boolean isDeleted = false;
        try {
            isDeleted = MaterialRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                initialize();
                clear();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void clear(){
        txtMid.clear();
        txtMdescription.clear();
        txtMqty.clear();
        txtMCostPerUnit.clear();
    }

    @FXML
    void btnSearchMaterialOnAction(ActionEvent event) {
        String id = txtMid.getText();
        Material material = null;
        try {
            material = MaterialRepo.search(id);
            if (material != null) {
                txtMdescription.setText(material.getDescription());
                txtMqty.setText(String.valueOf(material.getQty()));
                txtMCostPerUnit.setText(String.valueOf(material.getCostPerOne()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateMaterialOnAction(ActionEvent event) {
        String id = txtMid.getText();
        String name = txtMdescription.getText();
        int qty = Integer.parseInt(txtMqty.getText());
        double costPerUnit = Double.parseDouble(txtMCostPerUnit.getText());


        Material material = new Material(id,name,qty,costPerUnit);

        boolean isUpdate = false;
        try {
            isUpdate = MaterialRepo.update(material);
            if(isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                initialize();
                clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtAddMaterialIdOnAction(ActionEvent event) {

    }
    private List<Material> getAllMaterial() {
        List<Material> materialList = null;
        try {
            materialList = MaterialRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return materialList;
    }
}