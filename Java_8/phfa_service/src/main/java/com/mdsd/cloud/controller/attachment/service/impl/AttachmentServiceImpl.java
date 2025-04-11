package com.mdsd.cloud.controller.attachment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.attachment.dto.AttListOrDetailPao;
import com.mdsd.cloud.controller.attachment.dto.AttSavePao;
import com.mdsd.cloud.controller.attachment.entity.MedPhepAttachmentEntity;
import com.mdsd.cloud.controller.attachment.mapper.MedPhepAttachmentMapper;
import com.mdsd.cloud.controller.attachment.service.AttachmentService;
import com.mdsd.cloud.response.exception.BusinessException;
import com.mdsd.cloud.utils.HandleString;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2022-04-11]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AttachmentServiceImpl implements AttachmentService {

    @NonNull
    MedPhepAttachmentMapper medPhepAttachmentMapper;

    @Value("${linux.attachment.path}")
    private String filePath;

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 附件 - 保存
     */
    @Override
    public String attSave(final MultipartFile multipartFile, final AttSavePao attSavePao) {

        if (!multipartFile.isEmpty()) {
            try {
                final MedPhepAttachmentEntity medPhepAttachmentEntity = new MedPhepAttachmentEntity();
                //判断系统环境
                if ("Linux".equals(System.getProperties().getProperty("os.name"))) {
                    final File file = new File(this.filePath, Objects.requireNonNull(multipartFile.getOriginalFilename()));
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    multipartFile.transferTo(file);
                } else {
                    this.filePath = "D:\\";
                    final File file = new File(this.filePath, Objects.requireNonNull(multipartFile.getOriginalFilename()));
                    multipartFile.transferTo(file);
                }
                //拼接请求附件路径
                if (Strings.isNotBlank(attSavePao.getRequestPath())) {
                    medPhepAttachmentEntity.setRequestPath(attSavePao.getRequestPath());
                } else {
                    medPhepAttachmentEntity.setRequestPath(MessageFormat.format(CommonConstants.REQUEST_PATH, InetAddress.getLocalHost().getHostAddress(), this.port, this.contextPath, multipartFile.getOriginalFilename()));
                }
                //拼接附件存储路径
                final String stringBuilder = this.filePath + multipartFile.getOriginalFilename();
                //保存到数据库
                medPhepAttachmentEntity.setBusinessId(attSavePao.getBusinessId()).setFileName(multipartFile.getOriginalFilename()).setFileSize(multipartFile.getSize()).setPhysicalPath(stringBuilder).
                        setFileType(attSavePao.getFileType()).setRemark(attSavePao.getRemark());
                final boolean insert = medPhepAttachmentEntity.insert();
                if (insert) {
                    return medPhepAttachmentEntity.getRequestPath();
                }
            } catch (final IOException e) {
                e.printStackTrace();
                throw new BusinessException("附件上传失败！");
            }
        }
        throw new BusinessException("附件上传失败！");
    }

    /**
     * 附件 - 删除
     */
    @Override
    public boolean attDelete(final List<Long> ids) {

        Assert.noNullElements(ids, "入参对象为: NULL");
        final boolean[] result = new boolean[1];
        final List<MedPhepAttachmentEntity> medAttachmentEntities = this.medPhepAttachmentMapper.selectList(Wrappers.<MedPhepAttachmentEntity>lambdaQuery().in(MedPhepAttachmentEntity::getId, ids));
        Optional.ofNullable(medAttachmentEntities).filter(list -> list.size() > 0).ifPresent(list -> {
            final List<Long> collect = list.stream().map(MedPhepAttachmentEntity::getId).collect(Collectors.toList());
            //删除文件信息
            final boolean delResult = this.medPhepAttachmentMapper.deleteBatchIds(collect) > 0;
            if (delResult) {
                final List<String> filePaths = list.stream().map(MedPhepAttachmentEntity::getPhysicalPath).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(filePaths)) {
                    filePaths.forEach(str -> {
                        //删除文件
                        result[0] = new File(str).delete();
                    });
                }

            } else {
                throw new BusinessException("文件信息不存在！");
            }
        });
        return result[0];
    }

    /**
     * 附件 - 列表or详情
     */
    @Override
    public Object attListOrDetail(final AttListOrDetailPao attListOrDetailPao) {

        Assert.notNull(attListOrDetailPao, "入参对象为: NULL");
        if (null != attListOrDetailPao.getAttId()) {
            return this.medPhepAttachmentMapper.selectById(attListOrDetailPao.getAttId());
        } else {
            Assert.notNull(attListOrDetailPao.getBusinessId(), "业务ID为: NULL");
            //组装查询条件
            final QueryWrapper<MedPhepAttachmentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("DELETE_FLAG", 0);
            queryWrapper.eq("BUSINESS_ID", attListOrDetailPao.getBusinessId());
            if (StringUtils.isNotBlank(attListOrDetailPao.getFileName())) {
                queryWrapper.eq("FILE_NAME", HandleString.repString(attListOrDetailPao.getFileName()));
            }
            return this.medPhepAttachmentMapper.selectList(queryWrapper);
        }
    }
}
