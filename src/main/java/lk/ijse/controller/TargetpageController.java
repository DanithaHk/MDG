package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Client;
import lk.ijse.model.Target;
import lk.ijse.model.TargetDetail;
import lk.ijse.model.tm.EmployeeTm;
import lk.ijse.model.tm.TargetDetailTm;
import lk.ijse.repository.ClientRepo;
import lk.ijse.repository.TargetDetailRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TargetpageController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEid;

    @FXML
    private TableColumn<?, ?> colTragetCoverOrNot;

    @FXML
    private TableColumn<?, ?> colTragetName;

    @FXML
    private TableColumn<?, ?> colTrargetId;

    @FXML
    private AnchorPane rootTarget;

    @FXML
    private TableView<TargetDetailTm> tableTarget;

    @FXML
    private TextField txtTCoverOrNot;

    @FXML
    private TextField txtTEid;

    @FXML
    private TextField txtTdate;

    @FXML
    private TextField txtTid;

    @FXML
    private TextField txtTname;
    private List<TargetDetail> targetDetailList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.targetDetailList = getAllTragetDetail();
        setCellValueFactory();
        loadTargetDetailTable();
    }

    private void loadTargetDetailTable() {
        ObservableList<TargetDetailTm> tmList = FXCollections.observableArrayList();

        for (TargetDetail targetDetail : targetDetailList) {
            TargetDetailTm targetDetailTm = new TargetDetailTm(
                    targetDetail.getId(),
                    targetDetail.getName(),
                    targetDetail.getDate(),
                    targetDetail.getTCoverOrNot(),
                    targetDetail.getEid()

            );

            tmList.add(targetDetailTm);
        }
        tableTarget.setItems(tmList);
        TargetDetailTm selectedItem = (TargetDetailTm) tableTarget.getSelectionModel().getSelectedItem();

    }

    private void setCellValueFactory() {
        colTrargetId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTragetName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTragetCoverOrNot.setCellValueFactory(new PropertyValueFactory<>("tCoverOrNot"));
        colEid.setCellValueFactory(new PropertyValueFactory<>("eid"));
    }

    private List<TargetDetail> getAllTragetDetail() {
        List<TargetDetail> targetDetail = null;
        try {
            targetDetail = TargetDetailRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return targetDetail;
    }

    @FXML
    void btnSaveTargetOnAction(ActionEvent event) {
        String tid = txtTid.getText();
        String name = txtTname.getText();
        String date = txtTdate.getText();
        String tCoverOrNot = txtTCoverOrNot.getText();
        String eid = txtTEid.getText();

        TargetDetail targetDetail = new TargetDetail(tid,name,date,tCoverOrNot,eid);
        boolean isSaved = false;
        try {
            isSaved = TargetDetailRepo.save(targetDetail);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Target saved!").show();
                initialize();
                clear();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteTargetOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchTargetOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateTargetOnAction(ActionEvent event) {

    }
    @FXML
    void txtTidOnAction(ActionEvent event) {
        String id = txtTid.getText();
        try {
            Target target = TargetDetailRepo.searchById(id);
            if (target != null) {
                txtTname.setText(target.getName());
                txtTdate.setText(target.getDate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void clear(){
        txtTid.clear();
        txtTname.clear();
        txtTdate.clear();
        txtTCoverOrNot.clear();
        txtTEid.clear();
    }

}
