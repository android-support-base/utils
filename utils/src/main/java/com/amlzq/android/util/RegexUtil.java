package com.amlzq.android.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by amlzq on 2017/11/24.
 * <p>
 * 正则表达式验证器
 */

public class RegexUtil {

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     * +86:1开头，0-9，11位数
     */
    public static final String REGEX_MOBILE_86 = "^1[0-9]{10}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";


    /**
     * 正则表达式：验证金额
     */
    public static final String REGEX_MONEY = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";

    /**
     * 校验用户名
     *
     * @param username 用户名
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password 密码
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile 手机号
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile, String regex) {
        if (TextUtils.isEmpty(mobile)) {
            return false;
        } else {
            return Pattern.matches(regex, mobile);
        }
    }

    /**
     * 校验邮箱
     *
     * @param email 邮箱
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese 汉字
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard 身份证
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url 链接
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * @param ipAddr ipAddr
     * @return 校验IP地址
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * @param mobile mobile
     * @return 处理手机号显示
     * e.g:152****4799
     */
    public static String mobile(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * @param email email
     * @return 处理邮箱显示
     * e.g:abc***feg@163.com
     */
    public static String email(String email) {
        return email;
    }

    /**
     * @param idCard idCard
     * @return 处理身份证显示
     * e.g:4304*****7733
     */
    public static String idCard(String idCard) {
        return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
    }

    /**
     * @param realname 真实姓名
     * @return 处理实名显示
     * e.g:*东坡
     */
    public static String idRealname(String realname) {
        char[] r = realname.toCharArray();
        if (r.length == 2) {
            realname = realname.replaceFirst(realname.substring(1), "*");
        }
        if (r.length > 2) {
            realname = realname.replaceFirst(realname.substring(1, r.length - 1), "*");
        }
        return realname;
    }

    /**
     * @param money money
     * @return 判断小数点后2位的数字的正则表达式
     */
    public static boolean isMoney(String money) {
        return Pattern.matches(REGEX_MONEY, money);
    }

    /**
     * 正则表达式：验证媒体文件
     */
    public static final String REGEX_VIDEO = "(mp4|flv|avi|rm|rmvb|wmv)";

    public static boolean isVideo(String fileName) {
        Pattern pattern = Pattern.compile(REGEX_VIDEO);
        return pattern.matcher(fileName).find();
    }

}