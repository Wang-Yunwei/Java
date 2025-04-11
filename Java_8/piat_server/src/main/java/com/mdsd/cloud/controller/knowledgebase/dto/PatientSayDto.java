package com.mdsd.cloud.controller.knowledgebase.dto;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：wgy
 * @date ： 2021/9/14 16:55
 */
@Data
public class PatientSayDto {

    private String patientSayid;

    private String categoryId;

    private String description;

    private String isKey;

    private String pym;

    private BigDecimal sortValue;

    private String isValid;



}