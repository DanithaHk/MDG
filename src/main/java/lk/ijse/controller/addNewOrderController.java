package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.*;
import lk.ijse.model.tm.CartTm;
import lk.ijse.repository.AddOrderRepo;
import lk.ijse.repository.ClientRepo;
import lk.ijse.repository.OrderRepo;
import lk.ijse.repository.ProductRepo;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class addNewOrderController {

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXComboBox<String> cmbProductId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCid;

    @FXML
    private TableColumn<?, ?> colOContactNumber;

    @FXML
    private TableColumn<?, ?> colOdate;

    @FXML
    private TableColumn<?, ?> colOid;

    @FXML
    private TableColumn<?, ?> colOqty;

    @FXML
    private TableColumn<?, ?> colPid;

    @FXML
    private TableColumn<?, ?> colPname;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> coltotal;

    @FXML
    private Label lblNetTotal;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CartTm> tblcart;

    @FXML
    private TextField txtOUnitPRrce;
    @FXML
    private TextField txtOclientId;

    @FXML
    private TextField txtCnumber;

    @FXML
    private TextField txtOdate;

    @FXML
    private TextField txtOid;

    @FXML
    private TextField txtOproductName;

    @FXML
    private TextField txtOproductid;

    @FXML
    private TextField txtQty;
    private double netTotal = 0;
    private ObservableList<CartTm> cartList = FXCollections.observableArrayList();
    public void initialize() {
        setCellValueFactory();
        loadNextOrderId();
        getProductIds();
        setDate();
    }



    private void setCellValueFactory() {
        colOid.setCellValueFactory(new PropertyValueFactory<>("oId"));
        colCid.setCellValueFactory(new PropertyValueFactory<>("cId"));
        colOContactNumber.setCellValueFactory(new PropertyValueFactory<>("cNumber"));
        colPid.setCellValueFactory(new PropertyValueFactory<>("pId"));
        colPname.setCellValueFactory(new PropertyValueFactory<>("pName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colOdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }
    private void loadNextOrderId() {
        try {
            String currentId = OrderRepo.currentId();
            String nextId = nextId(currentId);

            txtOid.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("o");
            int id = Integer.parseInt(split[1]);    //2
            return "o" + ++id;

        }
        return "O1";
    }

    private void getProductIds() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            List<String> idList = ProductRepo.getIds();
            for (String id : idList) {
                list.add(id);
            }

            cmbProductId.setItems(list);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnAddOrderOnAction(ActionEvent event) {
        String oId = txtOid.getText();
        String name = txtOproductName.getText();
        String date = txtOdate.getText();
        int qty = Integer.parseInt((txtQty.getText()));
        System.out.println(qty);
        String cid = txtOclientId.getText();

            var order = new Order(oId, name, date,qty,cid);

            List<Order_detail> odList = new ArrayList<>();
            for (int i = 0; i < tblcart.getItems().size(); i++) {
                CartTm tm = cartList.get(i);

                Order_detail od = new Order_detail(
                       tm.getOId(),
                       tm.getCId(),
                        tm.getCNumber(),
                        tm.getPId(),
                        tm.getPName(),
                        tm.getUnitPrice(),
                        tm.getDate(),
                        tm.getQty()
                );
                odList.add(od);
            }

            AddOrder ad = new AddOrder (order, odList);
            try {
                boolean isPlaced = AddOrderRepo.addOrder(ad);
                if(isPlaced) {
                    new Alert(Alert.AlertType.CONFIRMATION, "order placed!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "order not placed!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        String oId = txtOid.getText();
        String cNumber = txtCnumber.getText();
        String cId = txtOclientId.getText();
        String pId = String.valueOf(cmbProductId.getValue());
        String pName = txtOproductName.getText();
        double unitPrice = Double.parseDouble(txtOUnitPRrce.getText());
        int qty = Integer.parseInt((txtQty.getText()));
        String date = txtOdate.getText();
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblcart.getSelectionModel().getSelectedIndex();
                cartList.remove(selectedIndex);
                tblcart.refresh();
                calculateNetTotal();
            }
        });
        for (int i = 0; i < tblcart.getItems().size(); i++) {
            if (pId.equals(colPid.getCellData(i))) {
                qty += cartList.get(i).getQty();
                total = unitPrice * qty;

                cartList.get(i).setQty(qty);
                cartList.get(i).setTotal(total);

                tblcart.refresh();
                calculateNetTotal();
                /*txtQty.setText("");*/
                return;
            }
        }
        CartTm cartTm = new CartTm( oId, cId, cNumber , pId , pName , unitPrice ,qty ,date ,total, btnRemove);
        System.out.println(cartTm.toString());

        cartList.add(cartTm);

        tblcart.setItems(cartList);
        txtQty.setText("");
        calculateNetTotal();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
    clear();
    }

    @FXML
    void cmbProductIdOnAction(ActionEvent event) {
        String id = cmbProductId.getValue();
        try {
            Product product = ProductRepo.searchById(id);
            if (product != null) {
                txtOproductName.setText(product.getDesc());
                txtOUnitPRrce.setText(String.valueOf(product.getCostPerUnit()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void txtClientContactNumberOnAction(ActionEvent event) {
        String cNumber = txtCnumber.getText();
        try {
            Client client = ClientRepo.searchBycNumber(cNumber);
            if (client != null) {
                txtOclientId.setText(client.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtOdate.setText(String.valueOf(now));
    }

    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i < tblcart.getItems().size(); i++) {
            netTotal += (double) coltotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }
    public void clear(){

    }
}
