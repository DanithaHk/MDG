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
import lk.ijse.model.Attendence;
import lk.ijse.model.Employee;
import lk.ijse.model.tm.AttendenceTm;
import lk.ijse.repository.AttendenceRepo;
import lk.ijse.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendenceController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPersentOrNot;

    @FXML
    private AnchorPane rootAttendence;

    @FXML
    private TableView<AttendenceTm> tableAttendence;

    @FXML
    private TextField txtADate;

    @FXML
    private TextField txtAEmployeeId;

    @FXML
    private TextField txtAId;

    @FXML
    private TextField txtAName;

    @FXML
    private TextField txtAPresentOrNot;
    private List<Attendence> attendenceList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.attendenceList= getAllAttendence();
        setCellValueFactory();
        loadAttendenceTable();
    }



    private List<Attendence> getAllAttendence() {
        List<Attendence> attendenceList = null;
        try {
            attendenceList = AttendenceRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return attendenceList;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPersentOrNot.setCellValueFactory(new PropertyValueFactory<>("presentOrNot"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
    }
    private void loadAttendenceTable() {
        ObservableList<AttendenceTm> tmList = FXCollections.observableArrayList();

        for (Attendence attendence : attendenceList) {
            AttendenceTm attendenceTm = new AttendenceTm(
                    attendence.getId(),
                    attendence.getName(),
                    attendence.getDate(),
                    attendence.getPresentOrNot(),
                    attendence.getEmployeeId()

            );

            tmList.add(attendenceTm);
        }
        tableAttendence.setItems(tmList);
        System.out.println(tmList.toString());
        AttendenceTm selectedItem =  tableAttendence.getSelectionModel().getSelectedItem();

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootAttendence.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENTS");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtAId.getText();

        boolean isDeleted = false;
        try {
            isDeleted = AttendenceRepo.delete(id);
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
    void btnSaveOnAction(ActionEvent event) {
        String id = txtAId.getText();
        String name = txtAName.getText();
        String date = txtADate.getText();
        int presentOrNot = Integer.parseInt(txtAPresentOrNot.getText());
        String employeeId = txtAEmployeeId.getText();


        Attendence attendence = new Attendence(id,name,date,presentOrNot,employeeId);
        boolean isSaved = false;
        try {
            isSaved = AttendenceRepo.save(attendence);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, " saved!").show();
                initialize();
                clear();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtAId.getText();
        Attendence attendence = null;
        try {
            attendence = AttendenceRepo.search(id);
            if (attendence != null) {
                txtAName.setText(attendence.getName());
                txtADate.setText(attendence.getDate());
                txtAPresentOrNot.setText(String.valueOf(attendence.getPresentOrNot()));
                txtAEmployeeId.setText(attendence.getEmployeeId());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtAId.getText();
        String name = txtAName.getText();
        String date = txtADate.getText();
        int presentOrNot = Integer.parseInt(txtAPresentOrNot.getText());
        String employeeId = txtAEmployeeId.getText();


        Attendence attendence = new Attendence(id, name, date, presentOrNot, employeeId);

        boolean isUpdate = false;
        try {
            isUpdate = AttendenceRepo.update(attendence);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                initialize();
                clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void clear(){
        txtAId.clear();
        txtAName.clear();
        txtADate.clear();
        txtAPresentOrNot.clear();
        txtAEmployeeId.clear();
    }
}
