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
import lk.ijse.model.Order;
import lk.ijse.model.tm.OrderTm;
import lk.ijse.model.tm.SalaryTm;
import lk.ijse.repository.OrderRepo;
import lk.ijse.repository.SalaryRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderpageController {
    @FXML
    private TableColumn<?, ?> clQty;

    @FXML
    private TableColumn<?, ?> colCid;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colOid;

    @FXML
    private TableView<OrderTm> orderTable;

    @FXML
    private AnchorPane rootOrderFrom;

    @FXML
    private TextField txtOclientId;

    @FXML
    private TextField txtOdate;

    @FXML
    private TextField txtOid;

    @FXML
    private TextField txtOname;

    @FXML
    private TextField txtOqty;

    private List<Order> orderList = new ArrayList<>();
    public void initialize() {
        this.orderList = getAllOrders();
        loadOrderTable();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colOid.setCellValueFactory(new PropertyValueFactory<>("oId"));
        colCid.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        clQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void loadOrderTable() {
        ObservableList<OrderTm> tmList = FXCollections.observableArrayList();

        for (Order order : orderList) {
            OrderTm orderTm = new OrderTm(
                    order.getOId(),
                    order.getName(),
                    order.getDate(),
                    order.getQty(),
                    order.getCid()
            );

            tmList.add(orderTm);
        }
        orderTable.setItems(tmList);
        OrderTm selectedItem = (OrderTm) orderTable.getSelectionModel().getSelectedItem();
    }

    private List<Order> getAllOrders() {
        List<Order> orderList = null;
        try {
            orderList = OrderRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }

    @FXML
    void btnDeleteOrderOnAction(ActionEvent event) {
        String oId = txtOid.getText();

        boolean isDeleted = false;
        try {
            isDeleted = OrderRepo.delete(oId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSearchOrderOnAction(ActionEvent event) {
        String id = txtOid.getText();
        Order order = null;
        try {
            order = OrderRepo.search(id);
            if (order != null) {
                txtOclientId.setText(order.getCid());
                txtOname.setText(order.getName());
                txtOdate.setText(order.getDate());
                txtOqty.setText(String.valueOf(order.getQty()));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOrderOnAction(ActionEvent event) {
        String oId = txtOid.getText();
        String cid = txtOclientId.getText();
        String name = txtOname.getText();
        String date = txtOdate.getText();
        int qty = Integer.parseInt(txtOqty.getText());

        Order order = new Order(oId,name,date,qty,cid);

        boolean isUpdate = false;
        try {
            isUpdate = OrderRepo.update(order);
            if(isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void txtAddOrderIdOnAction(ActionEvent event) {

    }

}
