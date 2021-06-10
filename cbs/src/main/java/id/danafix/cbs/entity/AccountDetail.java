package id.danafix.cbs.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "accountdetail")
@Data
public class AccountDetail {
    private String accountNumber;
    private String accountType;
    private Date openingAccount;
    private String accountSettingId;
    private String accountStatus;

    @NumberFormat(pattern = "#,###,###,###.##")
    private BigDecimal balance;
}
