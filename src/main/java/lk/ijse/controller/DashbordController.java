package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.model.Attendence;
import lk.ijse.model.tm.AttendenceTm;
import lk.ijse.repository.AttendenceRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashbordController {

    @FXML
    private JFXButton Attendece;

    @FXML
    private JFXButton Clients;

    @FXML
    private JFXButton Employee;

    @FXML
    private JFXButton Material;

    @FXML
    private JFXButton Orders;

    @FXML
    private JFXButton Products;

    @FXML
    private JFXButton Salary;

    @FXML
    private JFXButton Suppliers;

    @FXML
    private JFXButton Transport;

    @FXML
    private JFXButton logout;
    @FXML
    private TextField lblDate;
    @FXML
    private TableView<AttendenceTm> tblAttendence;
    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TextField lbluser;

    @FXML
    private TableColumn<?, ?> colEid;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPersentOrNot;



    @FXML
    private AnchorPane rootNode;

    @FXML
    private AnchorPane Load;
    private List<Attendence> attendenceList = new ArrayList<>();

    public void initialize() throws SQLException {
        this.attendenceList= getTodayAttendence();
        setCellValueFactory();
        loadAttendenceTable();
        setDate();
        setuser();
    }

    private void setuser() {
        lbluser.setText(LoginformController.user);
    }


    private List<Attendence> getTodayAttendence() {
        List<Attendence> attendenceList = null;
        String today = String.valueOf(LocalDate.now());
        try {
            attendenceList = AttendenceRepo.getTodayAttendence( today);
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
        colEid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
    }
    private void loadAttendenceTable() {
        ObservableList<AttendenceTm> tmList = FXCollections.observableArrayList();

        for (Attendence attendence : attendenceList) {
            AttendenceTm attendenceTm = new AttendenceTm(
                    attendence.getId(),
                    attendence.getName(),
                    attendence.getDate(),
                    attendence.getPresentOrNot(),
                    attendence.getEmployeeid()

            );

            tmList.add(attendenceTm);
        }
        tblAttendence.setItems(tmList);
        AttendenceTm selectedItem = (AttendenceTm) tblAttendence.getSelectionModel().getSelectedItem();

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }


    @FXML
    void Orders(ActionEvent event) throws IOException {
       setUi("orderPage.fxml");
    }

    @FXML
    void btClients(ActionEvent event) throws IOException {
       setUi( "clientpage.fxml");
    }

    @FXML
    void btEmployee(ActionEvent event) throws IOException {
       setUi("employeepage.fxml");
    }

    @FXML
    void btMaterial(ActionEvent event) throws IOException {
       setUi("materialpage.fxml");
    }

    @FXML
    void btProducts(ActionEvent event) throws IOException {
       setUi("productpage.fxml");
    }

    @FXML
    void btSalary(ActionEvent event) throws IOException {
        setUi("salaryForm.fxml");

    }

    @FXML
    void btSuppliers(ActionEvent event) throws IOException {
       setUi("supplierpage.fxml");
    }

    @FXML
    void btTarget(ActionEvent event) throws IOException {
        setUi("targetpage.fxml");
    }

    @FXML
    void btTransport(ActionEvent event) throws IOException {
        setUi("transport.fxml");
    }

    @FXML
    void btaddNewClient(ActionEvent event) throws IOException {
        setUi("addNewClient.fxml");
    }


    @FXML
    void btaddNewEmployee(ActionEvent event) throws IOException {
        setUi("addNewEmployee.fxml");
    }

    @FXML
    void btaddNewMaterial(ActionEvent event) throws IOException {
    }

    @FXML
    void btaddNewOrder(ActionEvent event) throws IOException {
       setUi("addNewOrder.fxml");
    }

    @FXML
    void btaddNewProduct(ActionEvent event) throws IOException {
        setUi("addNewProduct.fxml");
    }

    @FXML
    void btaddNewTraget(ActionEvent event) throws IOException {
      setUi("AddNewTarget.fxml");
    }


    @FXML
    void btattendece(ActionEvent event) throws IOException {
       setUi("attendence.fxml");

    }

    @FXML
    void btloglout(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/loginPage.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENT");
    }
    @FXML
    void btViewReport(ActionEvent event) {

    }

    public void logoClickedOnAction(MouseEvent mouseEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashbord.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("MDG GARMENT");
    }
    @FXML
    private void setUi(String fileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/" + fileName));
        Pane root = fxmlLoader.load();
        try {
            Load.getChildren().clear();
            Load.getChildren().setAll(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void userOnAction(ActionEvent event) {

    }

}
