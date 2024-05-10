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
import lk.ijse.model.tm.AddEmployeeTm;
import lk.ijse.model.tm.EmployeeTm;
import lk.ijse.repository.AddEmployeeRepo;
import lk.ijse.repository.ClientRepo;
import lk.ijse.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeepageController {

    @FXML
    private TableView<EmployeeTm> tableEmployee;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNumber;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJobrole;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colUser;

    @FXML
    private AnchorPane rootEmployeeform;

    @FXML
    private TextField txtEUsername;

    @FXML
    private TextField txtEaddress;

    @FXML
    private TextField txtEcontactNumber;

    @FXML
    private TextField txtEid;

    @FXML
    private TextField txtEjobRole;

    @FXML
    private TextField txtEname;

    private List<Employee> employeeList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.employeeList = getAllEmployee();
        setCellValueFactory();
        loadEmployeeTable();
    }

    private List<Employee> getAllEmployee() {
        List<Employee> employeeList = null;
        try {
            employeeList = EmployeeRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }



    private void loadEmployeeTable() {
        ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();

        for (Employee employee : employeeList) {
            EmployeeTm employeeTm = new EmployeeTm(
                    employee.getId(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getContactNumber(),
                    employee.getJobRole(),
                    employee.getUsername()
            );

            tmList.add(employeeTm);
        }
        tableEmployee.setItems(tmList);
        EmployeeTm selectedItem = (EmployeeTm) tableEmployee.getSelectionModel().getSelectedItem();


    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colJobrole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) throws IOException {
        String id = txtEid.getText();
        String name = txtEname.getText();
        String address = txtEaddress.getText();
        String contactNumber = txtEcontactNumber.getText();
        String jobRole = txtEjobRole.getText();
        String username = txtEUsername.getText();

        Employee employee = new Employee(id,name,address,contactNumber,jobRole,username);
        boolean isSaved = false;
        try {
            isSaved = EmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                initialize();
                clear();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void clear(){
        txtEid.clear();
        txtEname.clear();
        txtEaddress.clear();
        txtEcontactNumber.clear();
        txtEjobRole.clear();
        txtEUsername.clear();

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootEmployeeform.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENT");
    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) {
        String id = txtEid.getText();

        boolean isDeleted = false;
        try {
            isDeleted = EmployeeRepo.delete(id);
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
    void btnSearchEmployeeOnAction(ActionEvent event) {
        String id = txtEid.getText();
        Employee employee = null;
        try {
            employee = EmployeeRepo.search(id);
            if (employee != null) {
                txtEname.setText(employee.getName());
                txtEaddress.setText(employee.getAddress());
                txtEcontactNumber.setText(employee.getContactNumber());
                txtEjobRole.setText(employee.getJobRole());
                txtEUsername.setText(employee.getUsername());
            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateEmployeeOnAction(ActionEvent event) {
        String id = txtEid.getText();
        String name = txtEname.getText();
        String addres = txtEaddress.getText();
        String contactNumber = txtEcontactNumber.getText();
        String jobRole = txtEjobRole.getText();
        String username = txtEUsername.getText();

        Employee employee = new Employee(id,name,addres,contactNumber,jobRole,username);

        boolean isUpdate = false;
        try {
            isUpdate = EmployeeRepo.update(employee);
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
    void txtAddProductIdOnAction(ActionEvent event) {

    }

}
