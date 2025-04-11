package com.mdsd.cloud.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.util.dto.chestpain.CPCPatien;
import com.mdsd.cloud.util.dto.chestpain.ChestPainExcel;
import com.mdsd.cloud.util.dto.chestpain.ChestpainDto;
import com.mdsd.cloud.util.dto.hrm.HRMPatient;
import com.mdsd.cloud.util.dto.hrm.HrmDto;
import com.mdsd.cloud.util.dto.hrm.HrmExcel;
import com.mdsd.cloud.util.dto.hrn.HRNPatient;
import com.mdsd.cloud.util.dto.hrn.HrnDto;
import com.mdsd.cloud.util.dto.hrn.HrnExcel;
import com.mdsd.cloud.util.dto.stroke.STPatient;
import com.mdsd.cloud.util.dto.stroke.StrokeDto;
import com.mdsd.cloud.util.dto.stroke.StrokeExcel;
import com.mdsd.cloud.util.dto.trauma.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author WangYunwei [2024-12-04]
 */
@Slf4j
public class TurnExcelInfoJson {

    static ObjectMapper obm = new ObjectMapper();

    public static void main(String[] args) {

        TurnExcelInfoJson turnExcelInfoJson = new TurnExcelInfoJson();

        // 12320585467202976Q   太仓市第一人民医院
//        String dy_stroke_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "太仓市" + File.separator + "太仓市第一人民医院" + File.separator + "卒中.xlsx";
//        String dy_stroke_outFile = "D:\\workspace\\mdsd\\mdsd531\\太仓市\\太仓市第一人民医院\\StrokeJson.txt";
//        turnExcelInfoJson.stroke(dy_stroke_readFile, dy_stroke_outFile, "12320585467202976Q");

//        String dy_trauma_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "太仓市" + File.separator + "太仓市第一人民医院" + File.separator + "创伤.xlsx";
//        String dy_trauma_outFile = "D:\\workspace\\mdsd\\mdsd531\\太仓市\\太仓市第一人民医院\\TraumaJson.txt";
//        turnExcelInfoJson.trauma(dy_trauma_readFile,dy_trauma_outFile,"12320585467202976Q");

//        String dy_hrm_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "太仓市" + File.separator + "太仓市第一人民医院" + File.separator + "孕产妇.xls";
//        String dy_hrm_outFile = "D:\\workspace\\mdsd\\mdsd531\\太仓市\\太仓市第一人民医院\\HrmJson.txt";
//        turnExcelInfoJson.hrm(dy_hrm_readFile, dy_hrm_outFile, "12320585467202976Q");

//        String dy_hrn_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "太仓市" + File.separator + "太仓市第一人民医院" + File.separator + "新生儿.xls";
//        String dy_hrn_outFile = "D:\\workspace\\mdsd\\mdsd531\\太仓市\\太仓市第一人民医院\\HrnJson.txt";
//        turnExcelInfoJson.hrn(dy_hrn_readFile, dy_hrn_outFile, "12320585467202976Q");

        // 12320585467202984K   太仓市中医医院
//        String zy_chestpain_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "太仓市" + File.separator + "太仓市中医医院" + File.separator + "胸痛.xlsx";
//        String zy_chestpain_outFile = "D:\\workspace\\mdsd\\mdsd531\\太仓市\\太仓市中医医院\\ChestpainJson.txt";
//        turnExcelInfoJson.chestpain(zy_chestpain_readFile,zy_chestpain_outFile,"12320585467202984K");

//        String zy_trauma_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "太仓市" + File.separator + "太仓市中医医院" + File.separator + "创伤.xlsx";
//        String zy_trauma_outFile = "D:\\workspace\\mdsd\\mdsd531\\太仓市\\太仓市中医医院\\TraumaJson.txt";
//        turnExcelInfoJson.trauma(zy_trauma_readFile,zy_trauma_outFile,"12320585467202984K");

//        String zy_stroke_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "太仓市" + File.separator + "太仓市中医医院" + File.separator + "卒中.xlsx";
//        String zy_stroke_outFile = "D:\\workspace\\mdsd\\mdsd531\\太仓市\\太仓市中医医院\\StrokeJson.txt";
//        turnExcelInfoJson.stroke(zy_stroke_readFile, zy_stroke_outFile, "12320585467202984K");


        // 吴中医院
        String wz_trauma_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "吴中五大中心数据" + File.separator + "创伤.xlsx";
        String wz_trauma_outFile = "D:\\workspace\\mdsd\\mdsd531\\吴中五大中心数据\\TraumaJson.txt";
        turnExcelInfoJson.trauma(wz_trauma_readFile,wz_trauma_outFile,"68560200");

        String wz_stroke_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "吴中五大中心数据" + File.separator + "卒中.xlsx";
        String wz_stroke_outFile = "D:\\workspace\\mdsd\\mdsd531\\吴中五大中心数据\\StrokeJson.txt";
                turnExcelInfoJson.stroke(wz_stroke_readFile, wz_stroke_outFile, "68560200");

        String wz_chestpain_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "吴中五大中心数据" + File.separator + "胸痛.xlsx";
        String wz_chestpain_outFile = "D:\\workspace\\mdsd\\mdsd531\\吴中五大中心数据\\ChestpainJson.txt";
                turnExcelInfoJson.chestpain(wz_chestpain_readFile,wz_chestpain_outFile,"68560200");

        String wz_hrm_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "吴中五大中心数据" + File.separator + "孕产妇.xlsx";
        String wz_hrm_outFile = "D:\\workspace\\mdsd\\mdsd531\\吴中五大中心数据\\HrmJson.txt";
        turnExcelInfoJson.hrm(wz_hrm_readFile, wz_hrm_outFile, "68560200");

        String wz_hrn_readFile = "D:" + File.separator + "workspace" + File.separator + "mdsd" + File.separator + "mdsd531" + File.separator + "吴中五大中心数据" + File.separator + "新生儿.xlsx";
        String wz_hrn_outFile = "D:\\workspace\\mdsd\\mdsd531\\吴中五大中心数据\\HrnJson.txt";
        turnExcelInfoJson.hrn(wz_hrn_readFile, wz_hrn_outFile, "68560200");
    }

    void hrn(String readFile, String outFile, String HospitalId) {
        Date date = new Date();
        ArrayList<String> list = new ArrayList<>();
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行, 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(readFile, HrnExcel.class, new PageReadListener<HrnExcel>(dataList -> {
            for (HrnExcel demoData : dataList) {
//                demoData.setREGISTER_ID(UUID.randomUUID().toString().replace("-", ""));
                demoData.setHOSPITAL_ID(HospitalId);
                demoData.setCREATE_DATE(date);
                demoData.setUPDATE_DATE(date);
                demoData.setSTATUS("1");

                HrnDto hrnDto = new HrnDto();
                hrnDto.setPatient(new HRNPatient());

                BeanUtils.copyProperties(demoData, hrnDto.getPatient());

                if ("男".equals(demoData.getGENDER())) {
                    hrnDto.getPatient().setGENDER("1");
                } else {
                    hrnDto.getPatient().setGENDER("2");
                }
                try {
                    list.add(obm.writeValueAsString(hrnDto));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        })).sheet().doRead();
        String commaSeparated = String.join(",", list);
        try {
            /**
             * Java17 将数据写入本地文件
             *
             * APPEND: 表示字节应该被写入文件的末尾。
             * CREATE: 如果文件不存在，则创建该文件。
             * CREATE_NEW: 只有在文件不存在时才打开文件；如果文件存在，则抛出 FileAlreadyExistsException。
             * DELETE_ON_CLOSE: 在文件关闭时删除文件。
             * DSYNC: 要求每个更新到文件的内容或元数据都被同步地写入底层存储设备。
             * READ: 打开文件进行读取。
             * SPARSE: 指示当文件大小增加时应创建稀疏文件（仅在支持此概念的文件系统上有效）。
             * SYNC: 要求每个更新到文件内容或元数据都被同步地写入底层存储设备。
             * TRUNCATE_EXISTING: 如果文件已存在并且以写模式打开，则将其截断为零长度。
             * WRITE: 打开文件进行写入
             *
             * 例如: Files.write(Paths.get(outFile), list, StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
             *       Files.write(Paths.get(outFile), list, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
             */
            Files.write(Paths.get(outFile), (commaSeparated + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFile))) {
//            // 使用 BufferedWriter 将数据写入本地文件, 对大文件更高效
//            for (String line : list) {
//                writer.write(line);
//                writer.newLine(); // 写入新行符
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            // 使用 jackson 将数据写入本地文件
//            obm.writerWithDefaultPrettyPrinter().writeValue(new File(outFile), obm.writeValueAsString(list));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    void hrm(String readFile, String outFile, String HospitalId) {
        Date date = new Date();
        ArrayList<String> list = new ArrayList<>();
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行, 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(readFile, HrmExcel.class, new PageReadListener<HrmExcel>(dataList -> {
            for (HrmExcel demoData : dataList) {
//                demoData.setREGISTER_ID(UUID.randomUUID().toString().replace("-", ""));
                demoData.setHOSPITAL_ID(HospitalId);
                demoData.setCREATE_DATE(date);
                demoData.setUPDATE_DATE(date);
                demoData.setSTATUS("1");

                HrmDto hrmDto = new HrmDto();
                hrmDto.setPatient(new HRMPatient());

                BeanUtils.copyProperties(demoData, hrmDto.getPatient());

                if ("男".equals(demoData.getGENDER())) {
                    hrmDto.getPatient().setGENDER("1");
                } else {
                    hrmDto.getPatient().setGENDER("2");
                }
                try {
                    list.add(obm.writeValueAsString(hrmDto));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        })).sheet().doRead();
        String commaSeparated = String.join(",", list);
        try {
            /**
             * Java17 将数据写入本地文件
             *
             * APPEND: 表示字节应该被写入文件的末尾。
             * CREATE: 如果文件不存在，则创建该文件。
             * CREATE_NEW: 只有在文件不存在时才打开文件；如果文件存在，则抛出 FileAlreadyExistsException。
             * DELETE_ON_CLOSE: 在文件关闭时删除文件。
             * DSYNC: 要求每个更新到文件的内容或元数据都被同步地写入底层存储设备。
             * READ: 打开文件进行读取。
             * SPARSE: 指示当文件大小增加时应创建稀疏文件（仅在支持此概念的文件系统上有效）。
             * SYNC: 要求每个更新到文件内容或元数据都被同步地写入底层存储设备。
             * TRUNCATE_EXISTING: 如果文件已存在并且以写模式打开，则将其截断为零长度。
             * WRITE: 打开文件进行写入
             *
             * 例如: Files.write(Paths.get(outFile), list, StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
             *       Files.write(Paths.get(outFile), list, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
             */
            Files.write(Paths.get(outFile), (commaSeparated + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFile))) {
//            // 使用 BufferedWriter 将数据写入本地文件, 对大文件更高效
//            for (String line : list) {
//                writer.write(line);
//                writer.newLine(); // 写入新行符
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            // 使用 jackson 将数据写入本地文件
//            obm.writerWithDefaultPrettyPrinter().writeValue(new File(outFile), obm.writeValueAsString(list));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    void chestpain(String readFile, String outFile, String HospitalId) {
        Date date = new Date();
        ArrayList<String> list = new ArrayList<>();
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行, 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(readFile, ChestPainExcel.class, new PageReadListener<ChestPainExcel>(dataList -> {
            for (ChestPainExcel demoData : dataList) {
//                demoData.setREGISTER_ID(UUID.randomUUID().toString().replace("-", ""));
                demoData.setHOSPITAL_ID(HospitalId);
                demoData.setREGIST_TIME(date);
                demoData.setCREATE_DATE(date);
                demoData.setUPDATE_DATE(date);
                demoData.setSTATUS("1");

                ChestpainDto chestpainDto = new ChestpainDto();
                chestpainDto.setPatient(new CPCPatien());

                BeanUtils.copyProperties(demoData, chestpainDto.getPatient());

                if ("男".equals(demoData.getGENDER())) {
                    chestpainDto.getPatient().setGENDER("1");
                } else {
                    chestpainDto.getPatient().setGENDER("2");
                }
                try {
                    list.add(obm.writeValueAsString(chestpainDto));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        })).sheet().doRead();
        String commaSeparated = String.join(",", list);
        try {
            /**
             * Java17 将数据写入本地文件
             *
             * APPEND: 表示字节应该被写入文件的末尾。
             * CREATE: 如果文件不存在，则创建该文件。
             * CREATE_NEW: 只有在文件不存在时才打开文件；如果文件存在，则抛出 FileAlreadyExistsException。
             * DELETE_ON_CLOSE: 在文件关闭时删除文件。
             * DSYNC: 要求每个更新到文件的内容或元数据都被同步地写入底层存储设备。
             * READ: 打开文件进行读取。
             * SPARSE: 指示当文件大小增加时应创建稀疏文件（仅在支持此概念的文件系统上有效）。
             * SYNC: 要求每个更新到文件内容或元数据都被同步地写入底层存储设备。
             * TRUNCATE_EXISTING: 如果文件已存在并且以写模式打开，则将其截断为零长度。
             * WRITE: 打开文件进行写入
             *
             * 例如: Files.write(Paths.get(outFile), list, StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
             *       Files.write(Paths.get(outFile), list, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
             */
            Files.write(Paths.get(outFile), (commaSeparated + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFile))) {
//            // 使用 BufferedWriter 将数据写入本地文件, 对大文件更高效
//            for (String line : list) {
//                writer.write(line);
//                writer.newLine(); // 写入新行符
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            // 使用 jackson 将数据写入本地文件
//            obm.writerWithDefaultPrettyPrinter().writeValue(new File(outFile), obm.writeValueAsString(list));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    void stroke(String readFile, String outFile, String HospitalId) {
        Date date = new Date();
        ArrayList<String> list = new ArrayList<>();
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行, 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(readFile, StrokeExcel.class, new PageReadListener<StrokeExcel>(dataList -> {
            for (StrokeExcel demoData : dataList) {
//                demoData.setREGISTER_ID(UUID.randomUUID().toString().replace("-", ""));
                demoData.setHOSPITAL_ID(HospitalId);
                demoData.setCREATE_DATE(date);
                demoData.setUPDATE_DATE(date);
                demoData.setSTATUS("1");

                StrokeDto strokeDto = new StrokeDto();
                strokeDto.setPatient(new STPatient());

                BeanUtils.copyProperties(demoData, strokeDto.getPatient());

                if ("男".equals(demoData.getGENDER())) {
                    strokeDto.getPatient().setGENDER("1");
                } else {
                    strokeDto.getPatient().setGENDER("2");
                }
                try {
                    list.add(obm.writeValueAsString(strokeDto));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        })).sheet().doRead();
//        System.out.println(list.get(0));
        String commaSeparated = String.join(",", list);
        try {
            /**
             * Java17 将数据写入本地文件
             *
             * APPEND: 表示字节应该被写入文件的末尾。
             * CREATE: 如果文件不存在，则创建该文件。
             * CREATE_NEW: 只有在文件不存在时才打开文件；如果文件存在，则抛出 FileAlreadyExistsException。
             * DELETE_ON_CLOSE: 在文件关闭时删除文件。
             * DSYNC: 要求每个更新到文件的内容或元数据都被同步地写入底层存储设备。
             * READ: 打开文件进行读取。
             * SPARSE: 指示当文件大小增加时应创建稀疏文件（仅在支持此概念的文件系统上有效）。
             * SYNC: 要求每个更新到文件内容或元数据都被同步地写入底层存储设备。
             * TRUNCATE_EXISTING: 如果文件已存在并且以写模式打开，则将其截断为零长度。
             * WRITE: 打开文件进行写入
             *
             * 例如: Files.write(Paths.get(outFile), list, StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
             *       Files.write(Paths.get(outFile), list, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
             */
            Files.write(Paths.get(outFile), (commaSeparated + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFile))) {
//            // 使用 BufferedWriter 将数据写入本地文件, 对大文件更高效
//            for (String line : list) {
//                writer.write(line);
//                writer.newLine(); // 写入新行符
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            // 使用 jackson 将数据写入本地文件
//            obm.writerWithDefaultPrettyPrinter().writeValue(new File(outFile), obm.writeValueAsString(list));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    void trauma(String readFile, String outFile, String HospitalId) {
        Date date = new Date();
        ArrayList<String> list = new ArrayList<>();
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行, 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(readFile, TraumaExcel.class, new PageReadListener<TraumaExcel>(dataList -> {
            for (TraumaExcel demoData : dataList) {
//                demoData.setREGISTER_ID(UUID.randomUUID().toString().replace("-", ""));
                demoData.setHOSPITAL_ID(HospitalId);
                demoData.setREGISTER_TIME(date);
                demoData.setCREATE_DATE(date);
                demoData.setSTATUS(1);

                TraumaDto traumaDto = new TraumaDto();
                traumaDto.setRegist(new TCRegister());
//                traumaDto.setPhepInfo(new TCPhep());
//                traumaDto.setEmergencyInfo(new TCEmergency());
//                traumaDto.setRecoveryInfo(new TCRecovery());
//                traumaDto.setOperationInfo(new TCOperation());
//                traumaDto.setIcuInfo(new TCIcu());
//                traumaDto.setRescureInfo(new TCRescuer());
//                traumaDto.setTcTransferInfo(new TCTransfer());

                BeanUtils.copyProperties(demoData, traumaDto.getRegist());
//                BeanUtils.copyProperties(demoData, traumaDto.getPhepInfo());
//                BeanUtils.copyProperties(demoData, traumaDto.getEmergencyInfo());
//                BeanUtils.copyProperties(demoData, traumaDto.getRecoveryInfo());
//                BeanUtils.copyProperties(demoData, traumaDto.getOperationInfo());
//                BeanUtils.copyProperties(demoData, traumaDto.getIcuInfo());
//                BeanUtils.copyProperties(demoData, traumaDto.getRescureInfo());
//                BeanUtils.copyProperties(demoData, traumaDto.getTcTransferInfo());

                if ("男".equals(demoData.getGENDER())) {
                    traumaDto.getRegist().setGENDER(1);
                } else {
                    traumaDto.getRegist().setGENDER(2);
                }
                try {
                    list.add(obm.writeValueAsString(traumaDto));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        })).sheet().doRead();
//        System.out.println(list.get(0));
        String commaSeparated = String.join(",", list);
        try {
            /**
             * Java17 将数据写入本地文件
             *
             * APPEND: 表示字节应该被写入文件的末尾。
             * CREATE: 如果文件不存在，则创建该文件。
             * CREATE_NEW: 只有在文件不存在时才打开文件；如果文件存在，则抛出 FileAlreadyExistsException。
             * DELETE_ON_CLOSE: 在文件关闭时删除文件。
             * DSYNC: 要求每个更新到文件的内容或元数据都被同步地写入底层存储设备。
             * READ: 打开文件进行读取。
             * SPARSE: 指示当文件大小增加时应创建稀疏文件（仅在支持此概念的文件系统上有效）。
             * SYNC: 要求每个更新到文件内容或元数据都被同步地写入底层存储设备。
             * TRUNCATE_EXISTING: 如果文件已存在并且以写模式打开，则将其截断为零长度。
             * WRITE: 打开文件进行写入
             *
             * 例如: Files.write(Paths.get(outFile), list, StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);
             *       Files.write(Paths.get(outFile), list, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
             */
            Files.write(Paths.get(outFile), (commaSeparated + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFile))) {
//            // 使用 BufferedWriter 将数据写入本地文件, 对大文件更高效
//            for (String line : list) {
//                writer.write(line);
//                writer.newLine(); // 写入新行符
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            // 将对象写入指定路径的文件中
//            obm.writerWithDefaultPrettyPrinter().writeValue(new File(outFile), list);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
