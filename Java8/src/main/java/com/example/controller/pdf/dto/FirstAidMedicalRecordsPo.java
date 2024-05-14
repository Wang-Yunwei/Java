package com.example.controller.pdf.dto;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-05-19]
 */
@Getter
@Setter
public class FirstAidMedicalRecordsPo {

    private Map<String, Float> pointMap;

    private List<Map<String, Float>> valueMap;

    private HttpServletResponse response;
}
