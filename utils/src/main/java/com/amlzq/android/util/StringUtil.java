package com.amlzq.android.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-7-22
 */

public class StringUtil {

    /**
     * @hide
     */
    private StringUtil() {
        throw new AssertionError();
    }

    public static String get(String string, String defaultValue) {
        return string != null && !string.isEmpty() ? string : defaultValue;
    }

    /**
     * is null or its length is 0 or it is made by space
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param source source
     * @return if string is null or its size is 0 or it is made by space, return
     * true, else return false.
     */
    public static boolean isBlank(String source) {
        return (source == null || source.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param source source
     * @return if string is null or its size is 0, return true, else return
     * false.
     */
    public static boolean notEmpty(CharSequence source) {
        return (source != null && source.length() > 0);
    }

    /**
     * get length of CharSequence
     * <pre>
     * length(null) = 0;
     * length(\"\") = 0;
     * length(\"abc\") = 3;
     * </pre>
     *
     * @param source source
     * @return if str is null or empty, return 0, else return
     * {@link CharSequence#length()}.
     */
    public static int length(CharSequence source) {
        return source == null ? 0 : source.length();
    }

    /**
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param source source
     * @return null Object to empty string
     */
    public static String nullTextToEmpty(Object source) {
        return (source == null ? "" : (source instanceof String ? (String) source : source.toString()));
    }

    /**
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     *
     * @param source source
     * @return capitalize first letter
     */
    public static String capitalizeFirstLetter(String source) {
        if (TextUtils.isEmpty(source)) {
            return source;
        }

        char c = source.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? source
                : new StringBuilder(source.length()).append(Character.toUpperCase(c)).append(source.substring(1)).toString();
    }

    /**
     * encoded in utf-8
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     *
     * @param source source
     * @return utf8Encode
     */
    public static String utf8Encode(String source) {
        if (notEmpty(source) && source.getBytes().length != source.length()) {
            try {
                return URLEncoder.encode(source, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return source;
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     *
     * @param source       source
     * @param defultReturn defultReturn
     * @return utf8Encode
     */
    public static String utf8Encode(String source, String defultReturn) {
        if (notEmpty(source) && source.getBytes().length != source.length()) {
            try {
                return URLEncoder.encode(source, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return source;
    }

    /**
     * get innerHtml from href
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     *
     * @param href href
     * @return get innerHtml from href
     * <ol>
     * <li>if href is null, return ""</li>
     * <li>if not match regx, return source</li>
     * <li>return the last string that match regx</li>
     * </ol>
     */
    public static String getHrefInnerHtml(String href) {
        if (TextUtils.isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    /**
     * 在html中处理特殊字符
     *
     * @param source source
     * @return process special char in html
     */
    public static String htmlEscapeCharsToString(String source) {
        return TextUtils.isEmpty(source) ? source
                : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;",
                "\"");
    }

    /**
     * 将全角字符转换为半角字符
     *
     * @param source source
     * @return transform half width char to full width char
     */
    public static String fullWidthToHalfWidth(String source) {
        if (TextUtils.isEmpty(source)) return source;
        char[] array = source.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 12288) {
                array[i] = ' ';
                // } else if (array[i] == 12290) {
                // array[i] = '.';
            } else if (array[i] >= 65281 && array[i] <= 65374) {
                array[i] = (char) (array[i] - 65248);
            } else {
                array[i] = array[i];
            }
        }
        return new String(array);
    }

    /**
     * 将半角字符转换为全角字符
     *
     * @param source source
     * @return transform half width char char to full width
     */
    public static String halfWidthToFullWidth(String source) {
        if (TextUtils.isEmpty(source)) return source;
        char[] array = source.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ' ') {
                array[i] = (char) 12288;
                // } else if (array[i] == '.') {
                // array[i] = (char)12290;
            } else if (array[i] >= 33 && array[i] <= 126) {
                array[i] = (char) (array[i] + 65248);
            } else {
                array[i] = array[i];
            }
        }
        return new String(array);
    }

    /**
     * todo:待测试
     *
     * @param source 源字符串
     * @return 提取一个字符串中的整数和小数部分
     * @author https://blog.csdn.net/tuesdayma/article/details/76412800
     */
    public static String parseAmount(String source) {
        //先判断有没有整数，如果没有整数那就肯定就没有小数
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(source);
        String result = "";
        if (m.find()) {
            Map<Integer, String> map = new TreeMap<Integer, String>();
            Pattern p2 = Pattern.compile("(\\d+\\.\\d+)");
            m = p2.matcher(source);
            //遍历小数部分
            while (m.find()) {
                result = m.group(1) == null ? "" : m.group(1);
                int i = source.indexOf(result);
                String s = source.substring(i, i + result.length());
                map.put(i, s);
                //排除小数的整数部分和另一个整数相同的情况下，寻找整数位置出现错误的可能，还有就是寻找重复的小数
                // 例子中是排除第二个345.56时第一个345.56产生干扰和寻找整数345的位置时，前面的小数345.56会干扰
                source = source.substring(0, i) + source.substring(i + result.length());
            }
            //遍历整数
            Pattern p3 = Pattern.compile("(\\d+)");
            m = p3.matcher(source);
            while (m.find()) {
                result = m.group(1) == null ? "" : m.group(1);
                int i = source.indexOf(result);
                //排除jia567.23.23在第一轮过滤之后留下来的jia.23对整数23产生干扰
                if (String.valueOf(source.charAt(i - 1)).equals(".")) {
                    //将这个字符串删除
                    source = source.substring(0, i - 1) + source.substring(i + result.length());
                    continue;
                }
                String s = source.substring(i, i + result.length());
                map.put(i, s);
                source = source.substring(0, i) + source.substring(i + result.length());
            }
            result = "";
            for (Map.Entry<Integer, String> e : map.entrySet()) {
                result += e.getValue() + ",";
            }
            result = result.substring(0, result.length() - 1);
        } else {
            result = "";
        }
        return result;
    }

}