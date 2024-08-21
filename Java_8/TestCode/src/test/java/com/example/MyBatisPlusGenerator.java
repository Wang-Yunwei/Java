package com.xue.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author WangYunwei [2022-03-07]
 */
@SpringBootTest
public class MyBatisPlusGenerator {

    DataSourceConfig.Builder builder = new DataSourceConfig.Builder("jdbc:oracle:thin:@//192.168.0.129:1521/helowin", "PHEP", "mdsd");

    /**
     * 快速生成
     */
    @Test
    public void fastGenerator() {

        FastAutoGenerator.create(this.builder)
                //全局配置
                .globalConfig(builder -> {
                    builder.enableSwagger() // 开启 swagger 模式
                            .outputDir("D://generator"); // 指定输出目录
                })
                //包配置
                .packageConfig(builder -> {
                    builder.pathInfo(Collections.singletonMap(OutputFile.xml, "D://generator")); // 设置mapperXml生成路径
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(Lists.newArrayList("MED_PHEP_RESCUERS"))// 设置需要生成的表名
                            .controllerBuilder()
                            .entityBuilder();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    /**
     * 交互式生成
     */
    @Test
    public void interactiveGenerator() {

        FastAutoGenerator.create(this.builder)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.enableSwagger())
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名:")))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder()
                        .enableHyphenStyle()//开启驼峰转连字符
                        .entityBuilder()
                        .enableLombok()//开启lombok模型
                        .formatFileName("%sEntity")//格式化文件名称
                        .idType(IdType.ASSIGN_UUID)//指定生成的主键的ID类型
                        .superClass(Model.class)
                        .addTableFills(new Column("create_time", FieldFill.INSERT))//添加表字段填充
                        .enableTableFieldAnnotation()//开启生成实体时生成字段注解
                        .enableChainModel()//开启链式模型
                        .fileOverride()//覆盖已有文件
                        .build()
                )
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 处理 all 情况
     */
    protected static List<String> getTables(final String tables) {

        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}
