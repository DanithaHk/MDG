package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.model.Transport;
import lk.ijse.model.tm.MaterialTm;
import lk.ijse.model.tm.TransportTm;
import lk.ijse.repository.MaterialRepo;
import lk.ijse.repository.TargetRepo;
import lk.ijse.repository.TransportRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportController {



        @FXML
        private TableView<TransportTm> TableTransport;

        @FXML
        private JFXButton btnBack;

        @FXML
        private TableColumn<?, ?> colArea;

        @FXML
        private TableColumn<?, ?> colDate;

        @FXML
        private TableColumn<?, ?> colId;

        @FXML
        private TextField txtTArea;

        @FXML
        private TextField txtTDate;

        @FXML
        private TextField txtTid;

        @FXML
        private AnchorPane rootTransport;
        private List<Transport> transportList = new ArrayList<>();

        public void initialize() throws SQLException {
                this.transportList = getallTransport();
                setCellValueFactory();
                loadTransportTable();
        }

        private void loadTransportTable() {
                ObservableList<TransportTm> tmList = FXCollections.observableArrayList();

                for (Transport transport : transportList) {
                        TransportTm transportTm = new TransportTm(
                                transport.getId(),
                                transport.getDate(),
                                transport.getArea()
                        );
                        tmList.add(transportTm);
                }

                TableTransport.setItems(tmList);
                TransportTm selectedItem = (TransportTm) TableTransport.getSelectionModel().getSelectedItem();


        }

        private void setCellValueFactory() {
                colId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                colArea.setCellValueFactory(new PropertyValueFactory<>("area"));

        }


        private List<Transport> getallTransport() {
                List<Transport> transportList = null;
                try {
                        transportList = TransportRepo.getAll();
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
                return transportList;
        }

        @FXML
        void btnAddTransportOnAction(ActionEvent event) {
                String id = txtTid.getText();
                String date = txtTDate.getText();
                String area = txtTArea.getText();

                Transport transport = new Transport(id,date,area);
                boolean isSaved = false;
                try {
                        isSaved = TransportRepo.save(transport);
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
        void btnBackOnAction(ActionEvent event) throws IOException {
                AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));


                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) this.rootTransport.getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("MDG GARMENT");

        }

        @FXML
        void btnDeleteTrasnsportOnAction(ActionEvent event) {
                String id = txtTid.getText();

                boolean isDeleted = false;
                try {
                        isDeleted = TransportRepo.delete(id);
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
        void btnSearchTransportOnAction(ActionEvent event) {
                String id = txtTid.getText();
                Transport transport = null;
                try {
                        transport = TransportRepo.search(id);
                        if (transport != null) {
                                txtTDate.setText(transport.getDate());
                                txtTArea.setText(transport.getArea());
                        }

                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }

        @FXML
        void btnUpdateTransportOnAction(ActionEvent event) {
                String id = txtTid.getText();
                String date = txtTDate.getText();
                String area = txtTArea.getText();


                Transport transport = new Transport(id,date,area);

                boolean isUpdate = false;
                try {
                        isUpdate = TransportRepo.update(transport);
                        if(isUpdate) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                                initialize();
                                clear();
                        }
                } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

        }
        public void clear(){
                txtTid.clear();
                txtTDate.clear();
                txtTArea.clear();
        }

}


