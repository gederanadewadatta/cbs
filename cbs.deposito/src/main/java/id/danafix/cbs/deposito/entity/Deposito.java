package id.danafix.cbs.deposito.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Document(collection = "deposito")
@Data
public class Deposito {
    @Id
    private String id;

    private String branchNumber;

    private String accountNumber;


    private Date startDate;
    private Date dueDate;
    private int interest;
    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal balance;
    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal endingBalance;

    public Deposito(){
    }

    public Deposito(String branchNumber, String accountNumber) {
        this.branchNumber = branchNumber;
        this.accountNumber = accountNumber;
    }

    public Deposito(String branchNumber, String accountNumber, Date startDate, Date dueDate, int interest, BigDecimal balance, BigDecimal endingBalance) {
        this.branchNumber = branchNumber;
        this.accountNumber = accountNumber;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.interest = interest;
        this.balance = balance;
        this.endingBalance = endingBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deposito)) return false;
        Deposito deposito = (Deposito) o;
        return getInterest() == deposito.getInterest() && getBranchNumber().equals(deposito.getBranchNumber()) && getAccountNumber().equals(deposito.getAccountNumber()) && getStartDate().equals(deposito.getStartDate()) && getDueDate().equals(deposito.getDueDate()) && getBalance().equals(deposito.getBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBranchNumber(), getAccountNumber(), getStartDate(), getDueDate(), getInterest(), getBalance());
    }

    @Override
    public String toString() {
        return "Deposito{" +
                "id='" + id + '\'' +
                ", branchNumber='" + branchNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", interest=" + interest +
                ", balance=" + balance +
                ", endingBalance=" + endingBalance +
                '}';
    }
}
