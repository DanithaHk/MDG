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
import lk.ijse.model.Employee;
import lk.ijse.model.Product;
import lk.ijse.model.tm.EmployeeTm;
import lk.ijse.model.tm.ProductTm;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.ProductRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductpageController {


        @FXML
        private TableColumn<?, ?> colCostPerOne;

        @FXML
        private TableColumn<?, ?> colDescription;

        @FXML
        private TableColumn<?, ?> colId;

        @FXML
        private AnchorPane rootProductForm;

        @FXML
        private TableView<ProductTm> tableProduct;

        @FXML
        private TextField txtPCostPerOne;

        @FXML
        private TextField txtPDescription;

        @FXML
        private TextField txtPid;
        private List<Product> productList = new ArrayList<>();

        public void initialize() throws SQLException {
             this.productList = getAllProducts();
            setCellValueFactory();
            loadProductTable();
    }

    private List<Product> getAllProducts() {
        List<Product> productList = null;
        try {
            productList = ProductRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    private void loadProductTable() {
        ObservableList<ProductTm> tmList = FXCollections.observableArrayList();

        for (Product product : productList) {
            ProductTm productTm = new ProductTm(
                    product.getId(),
                    product.getDesc(),
                    product.getCostPerUnit()

            );

            tmList.add(productTm);
        }
        tableProduct.setItems(tmList);
        ProductTm selectedItem = (ProductTm) tableProduct.getSelectionModel().getSelectedItem();


    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCostPerOne.setCellValueFactory(new PropertyValueFactory<>("costPerOne"));

    }

    @FXML
        void btnBackOnAction(ActionEvent event) throws IOException {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));

            Scene scene = new Scene(rootNode);
            Stage stage = (Stage) this.rootProductForm.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("MDG GARMENT");
        }

        public void btnAddProductOnAction(ActionEvent event) {
            String id = txtPid.getText();
            String description = txtPDescription.getText();
            double costPerOne = Double.parseDouble(txtPCostPerOne.getText());

            Product product = new Product(id,description,costPerOne);
            boolean isSaved = false;
            try {
                isSaved = ProductRepo.save(product);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                    initialize();
                    clear();

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }



        @FXML
        void btnDeleteOnAction(ActionEvent event) {
            String id = txtPid.getText();

            boolean isDeleted = false;
            try {
                isDeleted = ProductRepo.delete(id);
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
        void btnSeachOnAction(ActionEvent event) {
            String id = txtPid.getText();
            Product product = null;
            try {
                product = ProductRepo.search(id);
                if (product != null) {
                    txtPDescription.setText(product.getDesc());
                    txtPCostPerOne.setText(String.valueOf(product.getCostPerUnit()));

                }

        } catch (SQLException e) {
                throw new RuntimeException(e);
            }
}
            @FXML
        void btnUpdateOnAction(ActionEvent event) {
                String id = txtPid.getText();
                String desc = txtPDescription.getText();
                double costPerOne  = Double.parseDouble(txtPCostPerOne.getText());

                Product product = new Product(id,desc,costPerOne);

                boolean isUpdate = false;
                try {
                    isUpdate = ProductRepo.update(product);
                    if(isUpdate) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                        initialize();
                        clear();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

        }

        public  void clear(){
            txtPid.clear();
            txtPDescription.clear();
            txtPCostPerOne.clear();
        }
}
