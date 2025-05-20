package com.mdsd.cloud.controller.dji.dto;

import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

/**
 * @author WangYunwei [2025-05-20]
 */
@Getter
@Setter
public class AircraftDto {

    private InetSocketAddress inetSocketAddress;

    private Process process;
}
