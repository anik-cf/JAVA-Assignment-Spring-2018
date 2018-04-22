package assignment.database;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author th3an
 */
public class TransactionView {
    
    private int trxID;
    private int bankAccount;
    private String transactionType;
    private String transactionDate;
    private String transactionTime;
    private double amount;

    public TransactionView(int trxID, int bankAccount, String transactionType, String transactionDate, String transactionTime, Double amount) {
        this.trxID=trxID; 
        this.bankAccount = bankAccount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.amount = amount;
    }

    public int getTrxID() {
        return trxID;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public double getAmount() {
        return amount;
    }
    

}
