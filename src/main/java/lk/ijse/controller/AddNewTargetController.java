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
import lk.ijse.model.Target;
import lk.ijse.model.tm.TargetTm;
import lk.ijse.repository.TargetRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddNewTargetController {

    @FXML
    private TableView<TargetTm> TragetTable;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane rootaddNewTarget;

    @FXML
    private TextField txtTdate;

    @FXML
    private TextField txtTid;

    @FXML
    private TextField txtTname;

    private List<Target> targetList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.targetList = getAllTargets();
        setCellValueFactory();
        loadClientTable();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadClientTable() {
        ObservableList<TargetTm> tmList = FXCollections.observableArrayList();

        for (Target target : targetList) {
            TargetTm targetTm = new TargetTm(
                    target.getId(),
                    target.getName(),
                    target.getDate()

            );

            tmList.add(targetTm);
        }
        TragetTable.setItems(tmList);
        TargetTm selectedItem = (TargetTm) TragetTable.getSelectionModel().getSelectedItem();

    }

    private List<Target> getAllTargets() {
        List<Target> targetList = null;
        try {
            targetList = TargetRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return targetList;
    }



    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));


        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootaddNewTarget.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENTS");
    }



    @FXML
    void btnSaveTargetOnAction(ActionEvent event) {
        String id = txtTid.getText();
        String name = txtTname.getText();
        String date = txtTdate.getText();


        Target target = new Target(id,name,date);
        boolean isSaved = false;
        try {
            isSaved = TargetRepo.save(target);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Target saved!").show();
                initialize();
                clear();

            }
        } catch (SQLException e) {
            throw   new RuntimeException(e);
        }


    }

    @FXML
    void btnClearTargetOnAction(ActionEvent event) {
        clear();

    }

    public void clear(){
        txtTdate.clear();
        txtTid.clear();
        txtTname.clear();
    }

    @FXML
    void txtAddTargetIdOnAction(ActionEvent event) {

    }

}
