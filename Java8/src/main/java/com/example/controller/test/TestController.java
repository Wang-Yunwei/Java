package com.example.controller.test;

import com.example.fegin.PhepFeginService;
import com.example.response.ResponseDto;
import com.example.response.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYunwei [2021-12-28]
 */
@RestController
@Api(value = "WEB - TestController", produces = MediaType.APPLICATION_JSON_VALUE, tags = "TEST")
public class TestController {

    PhepFeginService phepFeginService;

    public TestController(final PhepFeginService phepFeginService) {

        this.phepFeginService = phepFeginService;
    }

    @ApiOperation(value = "TEST - 获取UUID")
    @GetMapping(name = "获取UUID", path = "/test1")
    public ResponseDto<?> test1() {

        return ResponseUtil.wrapSuccess(this.phepFeginService.randomUUID());
    }
}
