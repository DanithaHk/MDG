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
import lk.ijse.repository.AddEmployeeRepo;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class addNewEmployeeController {

    @FXML
    private TableView<AddEmployeeTm> addemployeetable;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNumber;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJobRole;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private AnchorPane rootaddNewEmployee;

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

    @FXML
    private TextField txtUsername;
    private List<Employee> employeeList = new ArrayList<>();
    private LinkedHashMap<TextField,Pattern> map =new LinkedHashMap<>();

    public void initialize() throws SQLException {
        Pattern patternId = Pattern.compile("^(E0)[0-9]{5}$");
        map.put(txtEid,patternId);

        this.employeeList = getAllEmployees();
        setCellValueFactory();
        loadClientTable();
    }

    private List<Employee> getAllEmployees() {
        List<Employee> employeeList = null;
        try {
            employeeList = AddEmployeeRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    private void loadClientTable() {
        ObservableList<AddEmployeeTm> tmList = FXCollections.observableArrayList();

        for (Employee employee : employeeList) {
            AddEmployeeTm addEmployeeTm = new AddEmployeeTm(
                    employee.getId(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getContactNumber(),
                    employee.getJobRole(),
                    employee.getUsername()
            );

            tmList.add(addEmployeeTm);
        }
        addemployeetable.setItems(tmList);
        AddEmployeeTm selectedItem = (AddEmployeeTm) addemployeetable.getSelectionModel().getSelectedItem();

    }



    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {
        String id = txtEid.getText();
        String name = txtEname.getText();
        String address = txtEaddress.getText();
        String contactNumber = txtEcontactNumber.getText();
        String jobRole = txtEjobRole.getText();
        String userName = txtUsername.getText();

        Employee employee = new Employee(id,name,address,contactNumber,jobRole,userName);
        boolean isSaved = false;
        try {
            isSaved = AddEmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                clear();
                initialize();
            }
        } catch (SQLException e) {
             new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));


        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootaddNewEmployee.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENT");
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();

    }
    public void clear() {
       txtEid.clear();
       txtEname.clear();
       txtEaddress.clear();
       txtEcontactNumber.clear();
       txtEjobRole.clear();
       txtUsername.clear();
    }
    @FXML
    void txtKeyRelese(KeyEvent event) {


    }

    @FXML
    void txtAddEmployeeIdOnAction(ActionEvent event) throws SQLException {
    }


}
