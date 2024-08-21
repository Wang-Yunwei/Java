package com.example.controller.pdf.dto;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author WangYunwei [2022-02-07]
 */
@Getter
@Setter
public class TriageDetailPo {

    private Map<String, String> map;

    private HttpServletResponse response;
}
