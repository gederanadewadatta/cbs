package id.danafix.cbs.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Document(collection = "account")
@Data
public class Account {
    @Id
    private String id;

    private String branchNumber;

    private String customerName;

    private List<AccountDetail> accountDetail;



    public Account(){
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", branchNumber='" + branchNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", accountDetail=" + accountDetail +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getId().equals(account.getId()) && getBranchNumber().equals(account.getBranchNumber()) && getCustomerName().equals(account.getCustomerName()) && getAccountDetail().equals(account.getAccountDetail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBranchNumber(), getCustomerName(), getAccountDetail());
    }
}
