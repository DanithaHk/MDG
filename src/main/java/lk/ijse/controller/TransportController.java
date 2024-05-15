package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.PlaceTransport;
import lk.ijse.model.Transport;
import lk.ijse.model.TransportDetail;
import lk.ijse.model.tm.TransportCartTm;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.PlaceTransportRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransportController {

        @FXML
        private TableView<TransportCartTm> TableCartTransport;

        @FXML
        private ComboBox<String> cmbEmployeId;

        @FXML
        private TableColumn<?, ?> colArea;

        @FXML
        private TableColumn<?, ?> colDate;

        @FXML
        private TableColumn<?, ?> colEmployeeId;

        @FXML
        private TableColumn<?, ?> colId;

        @FXML
        private AnchorPane rootTransport;

        @FXML
        private TextField txtTArea;

        @FXML
        private TextField txtTDate;

        @FXML
        private TextField txtTid;
        private ObservableList<TransportCartTm> TcartList = FXCollections.observableArrayList();
        public void initialize() throws SQLException {
                setCellValueFactory();
                setDate();
                getEmployeeIds();
        }

        private void setCellValueFactory() {
                colId.setCellValueFactory(new PropertyValueFactory<>("tId"));
                colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
                colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("eId"));
        }

        private void setDate() {
                LocalDate now = LocalDate.now();
                txtTDate.setText(String.valueOf(now));
        }

        private void getEmployeeIds() {

                ObservableList<String> obList = FXCollections.observableArrayList();

                try {
                        List<String> idList = EmployeeRepo.getIds();

                        for (String id : idList) {
                                obList.add(id);
                        }
                        cmbEmployeId.setItems(obList);

                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }

        @FXML
        void btnAddToCartOnAction(ActionEvent event) {
                String tId = txtTid.getText();
                String date = txtTDate.getText();
                String area = txtTArea.getText();
                String eid = cmbEmployeId.getValue();

                TransportCartTm transportCartTm = new TransportCartTm(tId,date,area,eid);
                TcartList.add(transportCartTm);
                TableCartTransport.setItems(TcartList);
        }

        @FXML
        void btnDeleteTrasnsportOnAction(ActionEvent event) {

        }

        @FXML
        void btnSaveTransportOnAction(ActionEvent event) {
                String tId = txtTid.getText();
                String date = txtTDate.getText();
                String area = txtTArea.getText();
                String eid = cmbEmployeId.getValue();

                var transport = new Transport(tId,date,area);

                List<TransportDetail> tdList = new ArrayList<>();
                for (int i = 0; i < TableCartTransport.getItems().size(); i++) {
                        TransportCartTm tm = TcartList.get(i);

                        TransportDetail td = new TransportDetail(
                                tm.getTId(),
                                tm.getDate(),
                                tm.getEId()
                        );
                        tdList.add(td);
                }
                PlaceTransport pt = new PlaceTransport(transport,tdList);
                try {
                        boolean iSaved = PlaceTransportRepo.placeTransport(pt);
                        if(iSaved) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Transport Saved!").show();
                        } else {
                                new Alert(Alert.AlertType.WARNING, "Transport Not Saved!").show();
                        }
                } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }

        }

        @FXML
        void btnSearchTransportOnAction(ActionEvent event) {

        }

        @FXML
        void btnUpdateTransportOnAction(ActionEvent event) {

        }

        @FXML
        void cmbEmployeIdOnAction(ActionEvent event) {

        }

}
