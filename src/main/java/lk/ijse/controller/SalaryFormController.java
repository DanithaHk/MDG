package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.model.Attendence;
import lk.ijse.model.Employee;
import lk.ijse.model.Salary;
import lk.ijse.model.tm.AddEmployeeTm;
import lk.ijse.model.tm.SalaryTm;
import lk.ijse.repository.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalaryFormController {

    @FXML
    private JFXComboBox<String> cmbEmployeeId;

    @FXML
    private TableColumn<?, ?> colBasicSalary;

    @FXML
    private TableColumn<?, ?> colBonus;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEid;
    @FXML
    private AnchorPane rootSalary;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colSId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableView<SalaryTm> tblsalary;

    @FXML
    private TextField txtBasicSalary;

    @FXML
    private TextField txtBouns;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtSid;
    private List<Salary> salaryList = new ArrayList<>();

    public void initialize() {
        this.salaryList = getAllSalary();
        loadSalaryTable();
        setCellValueFactory();
        //loadNextSupplierId();
        getEmployeeId();
        setDate();
    }

    private List<Salary> getAllSalary() {
        List<Salary> salaryList = null;
        try {
            salaryList = SalaryRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salaryList;
    }

    private void loadSalaryTable() {
        ObservableList<SalaryTm> tmList = FXCollections.observableArrayList();

        for (Salary salary : salaryList) {
            SalaryTm salaryTm = new SalaryTm(
                    salary.getSId(),
                    salary.getEId(),
                    salary.getName(),
                    salary.getDate(),
                    salary.getBasicSalary(),
                    salary.getBouns(),
                    salary.getTotal()
            );

            tmList.add(salaryTm);
        }
        tblsalary.setItems(tmList);
        SalaryTm selectedItem = (SalaryTm) tblsalary.getSelectionModel().getSelectedItem();
    }

    private void setCellValueFactory() {
        colSId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colEid.setCellValueFactory(new PropertyValueFactory<>("eId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colBonus.setCellValueFactory(new PropertyValueFactory<>("bouns"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

  /*  private void loadNextSupplierId() {
        try {
            String currentId = SalaryRepo.currentId();
            System.out.println(currentId);
            String nextId = nextId(currentId);

            txtSid.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");
            int id = Integer.parseInt(split[1]);
            return "O" + ++id;

        }
        return "O1";
    }*/

    private void setBouns() {
        String id = cmbEmployeeId.getValue();
        try {
            Attendence attendence = AttendenceRepo.searchByEId(id);
            if (attendence != null) {
                int dateCount = AttendenceRepo.getdateCount(id);
                if (dateCount >= 25) {
                    txtBouns.setText(String.valueOf(5000));
                }
                if (dateCount < 25) {
                    txtBouns.setText("0");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        private void getEmployeeId() {
        ObservableList<String> eIdList = FXCollections.observableArrayList();
        try {
            List<String> idList = EmployeeRepo.getIds();
            for (String id : idList) {
                eIdList.add(id);
            }
            cmbEmployeeId.setItems(eIdList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtSid.getText();

        boolean isDeleted = false;
        try {
            isDeleted = SalaryRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String sId = txtSid.getText();
        String eId = cmbEmployeeId.getValue();
        String name = txtDate.getText();
        String date = txtDate.getText();
        double basicSalary = Double.parseDouble(txtBasicSalary.getText());
        double bouns = Double.parseDouble(txtBouns.getText());
        double total = basicSalary + bouns;

        Salary salary = new Salary(sId,eId,name,date,basicSalary,bouns,total);
        boolean isSaved = false;
        try {
            isSaved = SalaryRepo.save(salary);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, " saved!").show();
                initialize();
                clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clear() {
        txtSid.clear();
        txtBasicSalary.clear();
        txtEmployeeName.clear();
    }

    @FXML
   void btnSearchOnAction(ActionEvent event) {
      String id = txtSid.getText();
        Salary salary = null;
        try {
            salary = SalaryRepo.search(id);
            if (salary != null) {
                cmbEmployeeId.setValue(salary.getEId());
                txtEmployeeName.setText(salary.getName());
                txtDate.setText(salary.getDate());
                txtBasicSalary.setText(String.valueOf(salary.getBasicSalary()));
                txtBouns.setText(String.valueOf(salary.getBouns()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String sId = txtSid.getText();
        String eId = cmbEmployeeId.getValue();
        String name = txtEmployeeName.getText();
        String date = txtDate.getText();
        double basicSalary = Double.parseDouble(txtBasicSalary.getText());
        double bouns = Double.parseDouble(txtBouns.getText());
        double total = basicSalary+bouns;

        Salary salary = new Salary(sId,eId,name,date,basicSalary,bouns,total);

        boolean isUpdate = false;
        try {
            isUpdate = SalaryRepo.update(salary);
            if(isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbEmpolyeeIdOnAction(ActionEvent event) {
        String id = cmbEmployeeId.getValue();
        try {
            Employee employee = EmployeeRepo.searchById(id);
            if (employee != null) {
                setBouns();
                txtEmployeeName.setText(employee.getName());
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }
}
