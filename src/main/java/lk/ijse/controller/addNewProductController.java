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
import lk.ijse.model.Product;
import lk.ijse.model.tm.AddProductTm;
import lk.ijse.model.tm.ProductTm;
import lk.ijse.repository.AddProductRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class addNewProductController {

    @FXML
    private AnchorPane rootaddNewProduct;
    @FXML
    private TableView<AddProductTm> productTable;

    @FXML
    private TableColumn<?, ?> colPcostPerUit;

    @FXML
    private TableColumn<?, ?> colPdesc;

    @FXML
    private TableColumn<?, ?> colPid;


    @FXML
    private TextField txtCostPerUnit;

    @FXML
    private TextField txtPdescription;



    @FXML
    private TextField txtPid;
    private List<Product> productList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.productList = getAllProduct();
        setCellValueFactory();
        loadProductTable();
    }

    private List<Product> getAllProduct() {
        List<Product> productList= null;
        try {
            productList = AddProductRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }
    private void setCellValueFactory() {
        colPid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPdesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPcostPerUit.setCellValueFactory(new PropertyValueFactory<>("costPerUnit"));

    }

    private void loadProductTable() {
        ObservableList<AddProductTm> productTmsList = FXCollections.observableArrayList();

        for (Product product : productList) {
            AddProductTm productTm = new AddProductTm();
                    product.getId();
                    product.getDesc();
                    product.getCostPerUnit();

            productTmsList.add(productTm);
        }
        productTable.setItems(productTmsList);
        AddProductTm selectedItem = (AddProductTm) productTable.getSelectionModel().getSelectedItem();

    }

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        String id = txtPid.getText();
        String description = txtPdescription.getText();
        Double costPerUnit = Double.valueOf(txtCostPerUnit.getText());

        Product product = new Product(id,description,costPerUnit);
        boolean isSaved = false;
        try {
            isSaved = AddProductRepo.save(product);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "product saved!").show();
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
        Stage stage = (Stage) this.rootaddNewProduct.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENT");
    }


    public void txtAddProductIdOnAction(ActionEvent event) {

    }

    public void btnClearOnAction(ActionEvent event) {
        clear();
    }
    public void clear () {
        txtPid.clear();
        txtPdescription.clear();
        txtCostPerUnit.clear();
    }
}
