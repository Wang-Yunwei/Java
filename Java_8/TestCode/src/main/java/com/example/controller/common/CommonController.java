package com.example.controller.common;

import com.example.response.ResponseDto;
import com.example.response.ResponseUtil;
import com.example.utils.PrimaryKeyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYunwei [2024-06-05]
 */
@RestController
@RequestMapping("/common")
@Api(value = "WEB - CommonController", produces = MediaType.APPLICATION_JSON_VALUE, tags = "公共的")
public class CommonController {

    @ApiOperation("COMMON - 获取UUID")
    @GetMapping(name="获取UUID",path = "/getUUID/{num}")
    public ResponseDto<Object> getUUID(@PathVariable Integer num){

        return ResponseUtil.wrapSuccess(PrimaryKeyUtil.getUUID(num));
    }
}
