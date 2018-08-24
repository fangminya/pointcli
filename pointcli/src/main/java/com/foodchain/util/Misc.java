package com.foodchain.util;

import com.foodchain.bean.ResultBean;
import com.google.gson.Gson;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public final class Misc {

    private static final String DF_YMD = "yyyy-MM-dd";

    private static final char[] CODES = "0123456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ".toCharArray();

    public static boolean isEmpty(Object object) {
        return null == object || String.valueOf(object).trim().length() == 0;
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static String toJson(boolean success, String message, Object data) {
        ResultBean bean = new ResultBean();
        bean.setSuccess(success);
        bean.setMessage(message);
        bean.setData(data);
        return new Gson().toJson(bean);
    }

    public static String randomCode(int len) {
        return random(len);
    }

    private static String random(int len) {
        Random random = new Random();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            buf.append(Misc.CODES[random.nextInt(Misc.CODES.length)]);
        }
        return buf.toString();
    }

    /**
     * @param: [password, salt:盐值, hashIterations:散列次数]
     * @return: java.lang.String
     * @Description: MD5盐值加密
     */
    public static String getShiroMd5Pwd (String password, String salt, int hashIterations) {
        password = new SimpleHash("md5", password, ByteSource.Util.bytes(salt), hashIterations).toHex();
        return password;
    }

    /**
     * 格式化日期为YYYY-MM-DD形式的字符串。
     * 如果参数为空，则返回当前时间
     *
     * @param date 日期
     * @return 格式化的日期字符串
     */
    public static String getDefDate(Date date) {
        if (null == date) {
            date = new Date(System.currentTimeMillis());
        }
        DateFormat df = new SimpleDateFormat(DF_YMD);
        return df.format(date);
    }
}
