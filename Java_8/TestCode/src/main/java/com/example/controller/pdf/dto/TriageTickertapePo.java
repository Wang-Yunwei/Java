package com.example.controller.pdf.dto;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author WangYunwei [2022-01-29]
 */
@Getter
@Setter
public class TriageTickertapePo {

    private Map<String, String> map;

    private HttpServletResponse response;
}
