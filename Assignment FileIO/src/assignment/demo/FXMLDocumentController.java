/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    
//    @FXML
//    private TableView<TransactionView> transactionFront;
    @FXML
    TableView<TransactionView> transactionFront;
    
     // ID
     @FXML
     TableColumn<TransactionView, String>  acid;
        
        
         // Type
         @FXML
        TableColumn<TransactionView, String>  TrxType;
        
        // Date
          @FXML
        TableColumn<TransactionView, String>  Date;
        
        
        // Time
           @FXML
        TableColumn<TransactionView, String>  Time;
     
        
        // Amount
            @FXML
        TableColumn<TransactionView, String>  Amount;
        




     // Getting Transaction List from File 
    public ObservableList<TransactionView>getListItem(){
        ObservableList<TransactionView>item = FXCollections.observableArrayList();
        
           try {
            RandomAccessFile trxfile = new RandomAccessFile("transactions.txt", "r");

            while (true) {
                String line = trxfile.readLine();
                if (line == null) {
                    break;
                }

                String tokens[] = line.split(";");
                
                item.add(new TransactionView(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]));
               
            }
    
            } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return item; 
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Populating the TableView for Transaction History 
        
        // ID
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
            RandomAccessFile input = new RandomAccessFile("accounts.txt", "r");

            int accountNumber = 0;
            String accountName = "";
            String address = "";
            double balance = 0.0;
            int countData = 0;

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }

                String tokens[] = line.split(":");

                switch (tokens[0]) {
                    case "accountNumber":
                        accountNumber = Integer.parseInt(tokens[1]);
                        countData++;
                        break;
                    case "accountName":
                        accountName = tokens[1];
                        countData++;
                        break;
                    case "address":
                        address = tokens[1];
                        countData++;
                        break;
                    case "balance":
                        balance = Double.parseDouble(tokens[1]);
                        countData++;
                        break;
                    default:
                        System.err.println("Incorrect entry");
                        break;
                }
                /*
                if (tokens[0].equals("accountNumber")) {
                    accountNumber = Integer.parseInt(tokens[1]);
                    countData++;
                } else if (tokens[0].equals("accountName")) {
                    accountName = tokens[1];
                    countData++;
                } else if (tokens[0].equals("address")) {
                    address = tokens[1];
                    countData++;
                } else if (tokens[0].equals("balance")) {
                    balance = Double.parseDouble(tokens[1]);
                    countData++;
                }
                 */

                if (countData % 4 == 0) {
                    BankAccount account = new BankAccount(accountNumber, accountName, address, balance);
                    bankAccountList.add(account);
                }
                /*
                int indexOfColon = line.indexOf(':');
                String label = line.substring(0, indexOfColon);
                String data = line.substring(indexOfColon + 1);
                System.out.println(label+"="+data);
                 */ /*
                String accountNumberText = input.readLine();
                if (accountNumberText == null)
                    break;
                
                // Homework: try to separate out the label from each line
                int accountNumber = Integer.parseInt(accountNumberText);
                String accountName = input.readLine();
                String address = input.readLine();
                double balance = Double.parseDouble(input.readLine());
                
                BankAccount account = new BankAccount(accountNumber, accountName, address, balance);
                
                bankAccountList.add(account);
                 */ {

                }
            }

            filteredBankAccountList.addAll(bankAccountList);
            
            for (int i = 0; i < bankAccountList.size(); i++) {
                System.out.println(bankAccountList.get(i));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        
        
      
        
       
     
        
    }
    
   
    
    

    

    @FXML
    private void handleCreateAction(ActionEvent event) {
        String accountName = nameField.getText();
        String address = addressArea.getText();
        double balance = Double.parseDouble(balanceField.getText());

        BankAccount account = new BankAccount(accountName, address, balance);

        try {
            RandomAccessFile output = new RandomAccessFile("accounts.txt", "rw");
            output.seek(output.length());

            output.writeBytes(account.getAllData() + "\n");

            bankAccountList.add(account);

            output.close();

            clearForm();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            if (account.getAccountName().toLowerCase().contains(name))
                filteredBankAccountList.add(account);
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

        try {
            RandomAccessFile output = new RandomAccessFile("accounts.txt", "rw");
            output.setLength(0);

            for (int i = 0; i < bankAccountList.size(); i++)
                output.writeBytes(bankAccountList.get(i).getAllData() + "\n");

            output.close();

            clearForm();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleKeyFilterAction(KeyEvent event) {
        filter();
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) {
        try {
            
            // WRITE YOUR CODE HERE
            /*
            Write the transaction into a file called
            "transactions.txt". Each trasaction will
            have 5 entries:
            account_number;transaction_type;transaction_date;transaction_time;amount
            */
            
            //double currentBalance = account.getBalance(); 
            RandomAccessFile output = new RandomAccessFile("transactions.txt", "rw");
            output.seek(output.length());
            
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
            double currentBalance=bankAccount.getBalance();
            System.out.println("This is current Balance:" +currentBalance); 
            
            String decideType=transactionType.toString();
            
            if(decideType.equals("DEPOSIT")){
        
                if(bankAccount.deposit((int) amount)){
                    //Success message
                    returnMessage.setText("Deposit action successfully executed!");
                }
                else{
                    returnMessage.setText("Invalid Amount");
                }
                
                
            }
            
            else if(decideType.equals("WITHDRAW")){
                if(bankAccount.withdraw((int) amount)){
                    //Success message
                    returnMessage.setText("Withdraw action successfully executed!");
                }
                else{
                    returnMessage.setText("Invalid Amount or Insufficient Balance");
                }
            }
            
            else{
                // do Nothing! 
            }
            
            
            
            
            
            
            
            output.writeBytes(transaction.getAllData() + "\n");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        amountField.clear();
        accountComboBox.relocate(0, 0);
        
        
        
        try {
             RandomAccessFile outputForBalance = new RandomAccessFile("accounts.txt", "rw");
             outputForBalance.setLength(0);

              for (int i = 0; i < bankAccountList.size(); i++)
                   outputForBalance.writeBytes(bankAccountList.get(i).getAllData() + "\n");

                  outputForBalance.close();
                    
              

                  

                } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        
    }

    @FXML
    private void refreshTrxList(ActionEvent event) {
        transactionFront.setItems(getListItem());
    }
}
