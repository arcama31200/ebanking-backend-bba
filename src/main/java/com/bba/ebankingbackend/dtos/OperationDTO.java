package com.bba.ebankingbackend.dtos;

import lombok.Data;
import com.bba.ebankingbackend.enums.OperationType;

import java.util.Date;

@Data
public class OperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}

