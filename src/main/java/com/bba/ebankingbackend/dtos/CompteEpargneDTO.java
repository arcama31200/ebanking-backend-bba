package com.bba.ebankingbackend.dtos;
import lombok.Data;
import com.bba.ebankingbackend.enums.AccountStatus;
import java.util.Date;
@Data
public class CompteEpargneDTO extends CompteDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private ClientDTO clientDTO;
    private double interestRate;
}
