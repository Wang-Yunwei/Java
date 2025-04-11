package com.mdsd.cloud.feign;

import com.mdsd.cloud.feign.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author WangYunwei [2022-08-12]
 */
@Component
@FeignClient(name = "AuthServer", url = "${mdsd.ip.server}:${mdsd.port.auth}/cloud-rbac")
public interface AuthServerFeign {

    @PostMapping(name = "获取权限Token", value = "/access_token")
    Map<String, Object> getAuthToken(@RequestParam(value = "grant_type") String grantType,
                                     @RequestParam(value = "username") String username,
                                     @RequestParam(value = "password") String password,
                                     @RequestParam(value = "client_id") String clientId,
                                     @RequestParam(value = "client_secret") String clientSecret);

    @PostMapping(name = "获取业务Token", value = "/resources/getClientAccTokenByJwt")
    Map<String, Object> getBusinessToken(@RequestBody AuthGetBusinessTokenPo param);

    @PostMapping(name = "获取医院列表", value = "/resources/getHospitals")
    Map<String, Object> getHospitals(@RequestBody AuthGetHospitalsPo param);

    @PostMapping(name = "获取登录用户信息", value = "/resources/getLoginUser")
    Map<String, Object> getLoginUser(@RequestBody AuthGetLoginUserPo param, @RequestHeader(value = "jwt-token") String authJwtToken);

    @PostMapping(name = "获取所有用户", value = "/resources/getAllUserByDeptGroup")
    Map<String, Object> getAllUserByDeptGroup(@RequestBody AuthGetAllUserByDeptGroupPo param,
                                              @RequestHeader(value = "jwt-token") String businessJwtToken);

    @PostMapping(name = "获取医院所有部门", value = "/resources/getDeptTree")
    Map<String, Object> getDepartments(@RequestBody AuthGetDepartmentsPo param);

    @PostMapping(name = "根据Sign获取医院信息", value = "/resources/getHospitalInfo")
    Map<String, Object> getHospitalInfo(@RequestBody GetHospitalInfoPo param);

    @PostMapping(name = "根据角色查用户", value = "/resources/getUserByRoleSign")
    Map<String, Object> getUserByRoleSign(@RequestBody AuthGetUserByRoleSignPo param, @RequestHeader(value = "jwt-token") String authJwtToken);

    @GetMapping(name = "校验业务Token", value = "/authentication/checkTokenExpire")
    Map<String, Object> checkTokenExpire(@RequestHeader(value = "jwt-token") String businessJwtToken);
}
