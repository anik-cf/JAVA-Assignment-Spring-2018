package assignment.demo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author th3an
 */
public class TransactionView {
    
    private String bankAccount;
    private String transactionType;
    private String transactionDate;
    private String transactionTime;
    private String amount;

    public TransactionView(String bankAccount, String transactionType, String transactionDate, String transactionTime, String amount) {
        this.bankAccount = bankAccount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.amount = amount;
    }

    public String getBankAccount() {
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

    public String getAmount() {
        return amount;
    }
    

}
