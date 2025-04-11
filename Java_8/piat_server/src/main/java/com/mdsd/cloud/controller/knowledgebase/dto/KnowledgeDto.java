package com.mdsd.cloud.controller.knowledgebase.dto;


import lombok.Data;

import java.math.BigDecimal;
/**
 * @author ：wgy
 * @date ： 2021/9/14 16:55
 */

@Data
public class KnowledgeDto  {


    private String knowledgeId;

    private String categoryId;

    private String patientSayid;

    private String systemLevel;

    private String systemArea;

    private String knowledgeDetail;

    private BigDecimal sortValue;

    private String isValid;


}