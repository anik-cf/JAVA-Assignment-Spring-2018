/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author kmhasan
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea addressArea;
    @FXML
    private TextField balanceField;
    private ObservableList<BankAccount> bankAccountList;
    private ObservableList<BankAccount> filteredBankAccountList;
    @FXML
    private ListView<BankAccount> accountListView;

    private BankAccount selectedAccount;
    @FXML
    private ComboBox<BankAccount> accountComboBox;
    @FXML
    private ComboBox<TransactionType> transactionTypeComboBox;
    @FXML
    private TextField amountField;
    @FXML
    private DatePicker transactionDatePicker;
    @FXML
    private TextField transactionTimeField;
    @FXML
    private Label returnMessage;
    @FXML
    private Label statusBar;

    @FXML
    TableView<TransactionView> transactionFront;

    @FXML
    TableColumn<TransactionView, String> acid;

    @FXML
    TableColumn<TransactionView, String> TrxType;

    // Date
    @FXML
    TableColumn<TransactionView, String> Date;

    // Time
    @FXML
    TableColumn<TransactionView, String> Time;

    // Amount
    @FXML
    TableColumn<TransactionView, String> Amount;

    @FXML
    private TableColumn<TransactionView, String> trxi;
    
    public final String USERNAME = "root";
    public final String PASSWORD = "";
    public final String DBURL = "jdbc:mysql://localhost:3306/assignmentdb37";
   
    

    // Getting Transaction List from File 
    public ObservableList<TransactionView> getListItem() {
        ObservableList<TransactionView> item = FXCollections.observableArrayList();

        try {
            statusBar.setText("Connecting to Database...");
            Connection connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
            statusBar.setText("Connected to Database!");
            ResultSet rs = connection.createStatement().executeQuery("select * from transactions");
            while (rs.next()) {
                TransactionView account = new TransactionView(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6));
                item.add(account); 
            }
               

            } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error fetching the Transactions!");
        }

        return item;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Populating the TableView for Transaction History 
        // ID
        initInterface();

    }

    public void initInterface() {
        
        trxi.setCellValueFactory(new PropertyValueFactory<>("trxID"));
        
        acid.setCellValueFactory(new PropertyValueFactory<>("bankAccount"));

        // Type
        TrxType.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        //Date
        Date.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        // Time
        Time.setCellValueFactory(new PropertyValueFactory<>("transactionTime"));

        // Amount
        Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        transactionFront.setItems(null);
        transactionFront.setItems(getListItem());

        //transactionFront.getColumns().addAll(acid,TrxType,Date,Time,Amount); 
        // END OF TRANSACTION WAR
        bankAccountList = FXCollections.observableArrayList();
        filteredBankAccountList = FXCollections.observableArrayList();

        accountListView.setItems(filteredBankAccountList);
        accountComboBox.setItems(filteredBankAccountList);

        ObservableList<TransactionType> transactionTypeList = FXCollections.observableArrayList();
        transactionTypeList.addAll(TransactionType.values());
        transactionTypeComboBox.setItems(transactionTypeList);

        LocalDate currentDate = LocalDate.now();
        transactionDatePicker.setValue(currentDate);

        LocalTime currentTime = LocalTime.now();
        transactionTimeField.setText(currentTime.toString());

        try {
            statusBar.setText("Connecting to Database...");
            Connection connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
            statusBar.setText("Connected to Database!");
            ResultSet rs = connection.createStatement().executeQuery("select * from users");
            while (rs.next()) {
                BankAccount account = new BankAccount(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
                bankAccountList.add(account);
            }
            filteredBankAccountList.addAll(bankAccountList);

            for (int i = 0; i < bankAccountList.size(); i++) {
                System.out.println(bankAccountList.get(i));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class
                    .getName()).log(Level.SEVERE, null, ex);
            statusBar.setText("Database Connection Error!");
        }
    }

    public void initDB(String query) {

        try {
            statusBar.setText("Connecting to Database...");
            Connection connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
            statusBar.setText("Connected to Database!");

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statusBar.setText("Record Updated!");
            //connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class
                    .getName()).log(Level.SEVERE, null, ex);
            statusBar.setText("Invalid input or Database Error!");
        }

    }

    @FXML
    private void handleCreateAction(ActionEvent event) {

        String accountName = nameField.getText();
        String address = addressArea.getText();
        double balance = Double.parseDouble(balanceField.getText());

        BankAccount account = new BankAccount(accountName, address, balance);

        //Database Query 
        String query = "insert into users values(NULL,'" + accountName + "', '" + address + "', '" + balance + "');";

        initDB(query);
        String createMsg = statusBar.getText() + " - " + accountName + "- Account has been added!";
        statusBar.setText(createMsg);
        initInterface(); // update whole interface

        //Alert message for success!
        Alert alert = new Alert(AlertType.INFORMATION, "New account added for " + accountName + "! ", ButtonType.CANCEL);
        alert.showAndWait();

    }

    private void clearForm() {
        numberField.clear();
        nameField.clear();
        addressArea.clear();
        balanceField.clear();
    }

    private void displayData(BankAccount account) {
        numberField.setText(account.getAccountNumber() + "");
        nameField.setText(account.getAccountName());
        addressArea.setText(account.getAddress().replaceAll(";", "\n"));
        balanceField.setText(account.getBalance() + "");
    }

    @FXML
    private void handleListClickAction(MouseEvent event) {
        selectedAccount = accountListView.getSelectionModel().getSelectedItem();
        displayData(selectedAccount);
    }

    private void filter() {
        filteredBankAccountList.clear();
        String name = searchField.getText().toLowerCase();
        for (int i = 0; i < bankAccountList.size(); i++) {
            BankAccount account = bankAccountList.get(i);
            if (account.getAccountName().toLowerCase().contains(name)) {
                filteredBankAccountList.add(account);
            }
        }
    }

    @FXML
    private void handleFilterAction(ActionEvent event) {
        filter();
    }

    @FXML
    private void handleResetAction(ActionEvent event) {
        clearForm();
        numberField.setText((BankAccount.getTotalAccounts() + 1) + "");
    }

    @FXML
    private void handleSaveAction(ActionEvent event) {
        String updatedAddress = addressArea.getText();
        selectedAccount.setAddress(updatedAddress);
        String query = "update users set address='" + selectedAccount.getAddress() + "' where accountid='" + selectedAccount.getAccountNumber() + "';";
        initDB(query);
        Alert alert = new Alert(AlertType.INFORMATION, "Address updated for " + selectedAccount.getAccountName() + "! ", ButtonType.CANCEL);
        alert.showAndWait();

    }

    @FXML
    private void handleKeyFilterAction(KeyEvent event) {
        filter();
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) {

        BankAccount bankAccount = accountComboBox.getSelectionModel().getSelectedItem();

        TransactionType transactionType = transactionTypeComboBox.getSelectionModel().getSelectedItem();
        LocalDate transactionDate = transactionDatePicker.getValue();
        LocalTime transactionTime = LocalTime.parse(transactionTimeField.getText());
        double amount = Double.parseDouble(amountField.getText());

        Transaction transaction = new Transaction(bankAccount,
                transactionType,
                transactionDate,
                transactionTime,
                amount);

        //Finding CurrentBalance 
        double currentBalance = bankAccount.getBalance();
        System.out.println("This is current Balance:" + currentBalance);

        String decideType = transactionType.toString();

        if (decideType.equals("DEPOSIT")) {

            if (bankAccount.deposit((int) amount)) {
                //Success message
                returnMessage.setText("Deposit action successfully executed!");
            } else {
                returnMessage.setText("Invalid Amount");
            }

        } else if (decideType.equals("WITHDRAW")) {
            if (bankAccount.withdraw((int) amount)) {
                //Success message
                returnMessage.setText("Withdraw action successfully executed!");
            } else {
                returnMessage.setText("Invalid Amount or Insufficient Balance");
            }
        } else {
            // do Nothing! 
        }

        // Query For Transaction Table
        String query = "insert into transactions values(NULL, '"+bankAccount.getAccountNumber()+"','"+transaction.getTransactionDate()+"','"+transaction.getTransactionTime()+"',"
                + "'"+transaction.getTransactionType()+"','"+transaction.getAmount()+"');";
        
        initDB(query);
         //Alert message for success!
        Alert alert = new Alert(AlertType.INFORMATION, "Transaction successful for " + bankAccount.getAccountName() + "! ", ButtonType.CANCEL);
        alert.showAndWait();
        
        initInterface();         

        

    }


   
}
