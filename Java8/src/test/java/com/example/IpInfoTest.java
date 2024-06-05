package com.example;

import com.jthinking.common.util.ip.IPInfo;
import com.jthinking.common.util.ip.IPInfoUtils;

/**
 * @author WangYunwei [2024-01-19]
 */
public class IpInfoTest {

    public static void main(String[] args){
        // 获取IP信息
        IPInfo ipInfo = IPInfoUtils.getIpInfo("222.92.145.250");
        System.out.printf("==> %s%n",ipInfo.getCountry()); // 国家中文名称
        System.out.printf("==> %s%n",ipInfo.getProvince()); // 中国省份中文名称
        System.out.printf("==> %s%n",ipInfo.getAddress()); // 详细地址
        System.out.printf("==> %s%n",ipInfo.getIsp()); // 互联网服务提供商
        System.out.printf("==> %s%n",ipInfo.isOverseas()); // 是否是国外
        System.out.printf("==> %s%n",ipInfo.getLat()); // 纬度
        System.out.printf("==> %s%n",ipInfo.getLng()); // 经度
    }
}
