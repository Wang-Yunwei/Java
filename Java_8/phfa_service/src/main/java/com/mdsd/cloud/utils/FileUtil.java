package com.mdsd.cloud.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author WangYunwei [2021-10-18]
 */
@Slf4j
public class FileUtil {

    public static final String FILE_SEPARATOR = "/";

    public static final String DEFAULT_MODULE = "default";

    public static final String UTF_8 = "UTF-8";

    public static final String ISO_8859_1 = "ISO-8859-1";

    public static final String ZIP_SUFFIX = ".zip";

    public static final String POINT = ".";

    /**
     * 字符集
     */
    private static final char[] CODE_OPTION = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    /**
     * 封装下载返回
     */
    public static void wrapDownloadResponse(final HttpServletResponse response, final String fileName) {

        FileUtil.wrapDownloadResponse(response, fileName, -1);
    }

    /**
     * 封装下载返回
     */
    public static void wrapDownloadResponse(final HttpServletResponse response, final String fileName,
                                            final int fileLength) {

        response.setContentType("application/octet-stream;charset=UTF-8");
        if (fileLength > 0) {
            response.setContentLength(fileLength);
        }
        String headerValue = "attachment;";
        headerValue += " filename=\"" + encodeUriComponent(fileName) + "\";";
        headerValue += " filename*=utf-8''" + encodeUriComponent(fileName);
        response.setHeader("Content-Disposition", headerValue);
        response.setCharacterEncoding(FileUtil.UTF_8);
    }

    /**
     * <pre>
     * 符合 RFC 3986 标准的“百分号URL编码”
     * 在这个方法里，空格会被编码成%20，而不是+
     * 和浏览器的encodeURIComponent行为一致
     * </pre>
     */
    public static String encodeUriComponent(final String value) {

        try {
            return URLEncoder.encode(value, UTF_8).replaceAll("\\+", "%20");
        } catch (final UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(final String fileName) {

        final File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.info("删除单个文件: {} {}", fileName, "成功!");
                return true;
            } else {
                log.info("删除单个文件: {} {}", fileName, "失败!");
                return false;
            }
        }
        log.info("删除单个文件: {} {}", fileName, "不存在!");
        return false;
    }
}
