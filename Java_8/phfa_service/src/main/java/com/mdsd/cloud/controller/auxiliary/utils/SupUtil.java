package com.mdsd.cloud.controller.auxiliary.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mdsd.cloud.controller.attachment.entity.MedPhepAttachmentEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-05-23]
 */
@Component
public class SupUtil {

    /**
     * 处理附件数据
     */
    public static List<Map<String, String>> getAttachmentUrl(final List<MedPhepAttachmentEntity> list) {

        final List<Map<String, String>> arr = Lists.newArrayList();
        list.forEach(ent -> {
            final Map<String, String> map = Maps.newHashMap();
            map.put("url", ent.getRequestPath());
            map.put("id", ent.getId().toString());
            arr.add(map);
        });
        return arr;
    }

}
