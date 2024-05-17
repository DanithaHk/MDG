package lk.ijse.controller;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.SendEmail;
import lk.ijse.Util.ValidateUtil;
import lk.ijse.db.DbConnection;
import lk.ijse.model.*;
import lk.ijse.model.tm.CartTm;
import lk.ijse.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.mail.MessagingException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;


public class addNewOrderController {

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXComboBox<String> cmbProductId;
    @FXML
    private JFXComboBox<String> cmbMaterialName;


    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCid;

    @FXML
    private TableColumn<?, ?> colOContactNumber;

    @FXML
    private TableColumn<?, ?> colMaterialQty;

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
    private TextField txtMaterialId;

    @FXML
    private TextField txtMterialQty;

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
    private  String mQty  = null;
    private  String oId  = null;
    private ObservableList<CartTm> cartList = FXCollections.observableArrayList();
    private LinkedHashMap  <TextField , Pattern >map = new LinkedHashMap<>();
    public void initialize() {
        Pattern patternOId = Pattern.compile("^(C0)[0-9]{5}$");
        map.put(txtOid,patternOId);
        Pattern patternCNumber = Pattern.compile("^[0-9]{10}$");
        map.put(txtCnumber,patternCNumber);
        Pattern patternQty = Pattern.compile("^[0-9]{6}$");
        map.put(txtQty,patternQty);
        Pattern patternMQty = Pattern.compile("^[0-9]{3}$");
        map.put(txtMterialQty,patternMQty);

        setCellValueFactory();
        loadNextOrderId();
        getProductIds();
        getMaterialIds();
        setDate();
    }

    private void getMaterialIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> mIdList = MaterialDetailRepo.getMIds();
            for (String id : mIdList) {
                obList.add(id);
            }

            cmbMaterialName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        colMaterialQty.setCellValueFactory(new PropertyValueFactory<>("materialQtyTotal"));
    }
    private void loadNextOrderId() {
        try {
            String currentId = OrderRepo.currentId();
            System.out.println(currentId);
            String nextId = nextId(currentId);
            System.out.println(nextId);
            txtOid.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String [] split = currentId.split("O");

            int id = Integer.parseInt(split[1]);    //2
            return "O00" + ++id;

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
         oId = txtOid.getText();
        String name = txtOproductName.getText();
        String date = txtOdate.getText();
        String cid = txtOclientId.getText();
        int qty = Integer.parseInt(txtQty.getText());
        String desc = cmbMaterialName.getValue();
        String mId = txtMaterialId.getText();
        int materialQty = Integer.parseInt(txtMterialQty.getText());


            var order = new Order(oId, name, date,qty,cid);
            MaterialDetail materialDetail = new MaterialDetail(oId, desc, mId, materialQty);
    System.out.println(materialDetail);
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
                        tm.getQty(),
                        tm.getDate(),
                        tm.getMaterialQtyTotal(),
                        tm.getTotal()
                );
                odList.add(od);
            }

            AddOrder ad = new AddOrder (order, odList , materialDetail);
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
    void btnAddProductOnAction() {
        String oId = txtOid.getText();
        String cNumber = txtCnumber.getText();
        String cId = txtOclientId.getText();
        String pId = cmbProductId.getValue();
        String pName = txtOproductName.getText();
        double unitPrice = Double.parseDouble(txtOUnitPRrce.getText());
        int qty = Integer.parseInt((txtQty.getText()));
        String date = txtOdate.getText();
        String materialName = cmbMaterialName.getValue();
        String materialId = txtMaterialId.getText();
        int materialQty = Integer.parseInt(txtMterialQty.getText());
        double total = qty * unitPrice;
        String matirialQtyTotal = materialQty * qty +"m";
        mQty= matirialQtyTotal;

        for (int i = 0; i < tblcart.getItems().size(); i++) {
            if (pId.equals(colPid.getCellData(i))) {
                qty += cartList.get(i).getQty();
                total = unitPrice * qty;

                cartList.get(i).setQty(qty);
                cartList.get(i).setTotal(total);

                tblcart.refresh();
                calculateNetTotal();
                return;
            }
        }
        CartTm cartTm = new CartTm( oId, cId, cNumber , pId , pName , unitPrice ,qty ,date ,total, matirialQtyTotal);

        cartList.add(cartTm);

        tblcart.setItems(cartList);
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
    @FXML
    void cmbMaterialNameOnAction(ActionEvent event) {
        String materialName = cmbMaterialName.getValue();
        try {
            Material materialDetail = MaterialRepo.searchBycNumber(materialName);
            if (materialDetail != null) {
                txtMaterialId.setText(materialDetail.getId());
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
    @FXML
    void btnSendMailOnAction(ActionEvent event) throws MessagingException, IOException, JRException, SQLException {
        Client client = new Client();
       SendEmail.sendMail("MDG QUATITON "," hey","mdg " , "virajdilshan2019@gmail.com",getBill());
    }
    @FXML
    void btnGenarateQuotationOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign =
                JRXmlLoader.load("C:\\Users\\user\\IdeaProjects\\MDG\\src\\main\\resources\\Report\\MDGGarmentQuatation.jrxml");
        JasperReport jasperReport =
                JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> data = new HashMap<>();
        String fullBill = lblNetTotal.getText();

       data.put("fullBill",fullBill);
       data.put("materialName",cmbMaterialName.getValue());
       data.put("materialQty",mQty);
       data.put("orderId",oId);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        data,
                        DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);
    }
    private File getBill() throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\user\\IdeaProjects\\MDG\\src\\main\\resources\\Report\\MDGGarmentQuatation.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> data = new HashMap<>();
        String fullBill = lblNetTotal.getText();

        data.put("fullBill",fullBill);
        data.put("materialName",cmbMaterialName.getValue());
        data.put("materialQty",mQty);
        data.put("orderId",oId);


        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        data,
                        DbConnection.getInstance().getConnection());

        // Export the report to a PDF file
        File pdfFile = new File("Order Receipt.pdf");
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

        return pdfFile;
    }
    public void clear(){
        txtOid.clear();
         txtCnumber.clear();
        txtOclientId.clear();
        txtOproductName.clear();
        txtOUnitPRrce.clear();
        txtQty.clear();
        txtOdate.clear();
        txtMaterialId.clear();
        txtMterialQty.clear();

    }

    public void txtKeyRelease(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
          ValidateUtil.validation(map);
            }
        }
    }

