package com.gitee.pifeng.monitoring.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 压缩工具类
 * </p>
 *
 * @author 皮锋
 * @custom.date 2021/12/20 12:49
 */
public class ZipUtils {

    /**
     * 1M = 1024k = 1048576字节
     */
    private final static int LENGTH = 1048576;

    /**
     * <p>
     * 私有化构造方法
     * </p>
     *
     * @author 皮锋
     * @custom.date 2021/12/20 12:50
     */
    private ZipUtils() {
    }

    /**
     * <p>
     * 字符串是否需要进行gzip压缩
     * </p>
     *
     * @param str 输入字符串
     * @return boolean 否需要进行gzip压缩
     * @author 皮锋
     * @custom.date 2021/12/20 21:02
     */
    public static boolean isNeedGzip(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        // 1M = 1024k = 1048576字节
        int length = 1048576;
        if (str.length() <= length) {
            return false;
        }
        return true;
    }

}