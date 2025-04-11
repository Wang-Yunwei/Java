package com.mdsd.cloud.controller.secondarytriage.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @description :
 * @author: wgy
 * @create: 2021-11-01 11:14
 **/

@Getter
@Setter
public class SecondPatientDto extends Model {
    
    private LocalDateTime starttime;

    private LocalDateTime endtime;

    private String triagetime;

    private String resetlevel;

    private String waitfortime;

}
