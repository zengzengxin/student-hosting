package com.luwei.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * 客户端导出工具类
 * Created by jdq on 2017/7/19.
 */
public class ExportUtil {

    public static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    /**
     * 导出文件流到客户端
     *
     * @param response
     * @param contentType
     * @param fileName
     */
    public static void exportToClient(HttpServletResponse response, String contentType, InputStream in, String fileName) {
        response.setCharacterEncoding("utf-8" );
        response.setContentType(contentType + ";charset=UTF-8" );
        OutputStream os = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8" ));
            os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = in.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.flush();
        } catch (IOException e) {
            logger.error(" ExportUtil exportToClient() error", e);
        } finally {
            try {
                if (Objects.nonNull(os)) {
                    os.close();
                }
                in.close();
            } catch (IOException e) {
                logger.error(" ExportUtil exportToClient() error", e);
            }
        }
    }

}
