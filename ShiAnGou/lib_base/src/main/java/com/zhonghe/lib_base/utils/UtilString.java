package com.zhonghe.lib_base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Date: 2017/8/3.
 * Author: whyang
 */
public class UtilString {
    /**
     * 字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 字符串是否为非空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 字符串是否为空格串
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 字符串是否非空格串
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }


    /**
     * 将null转换为空串,如果参数为非null，则直接返回
     * @param str
     * @return
     */
    public static String nullToEmpty(String str) {
        return (str == null ? "" : str);
    }


    /**
     * 将null转换为字符串"null",如果参数为非null，则直接返回
     * @param str
     * @return
     */
    public static String nullToString(String str) {
        return (str == null ? "null" : str);
    }


    /**
     * 半角转全角
     * @param half
     * @return 全角字符串.
     */
    public static String halfToFull(String half) {
        if(isEmpty(half)) return half;

        char c[] = half.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }

        return new String(c);
    }


    /**
     * 全角转半角
     * @param full
     * @return 半角字符串
     */
    public static String fullToHalf(String full) {
        if(isEmpty(full)) return full;

        char c[] = full.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }

        String result = new String(c);
        return result;
    }

    /**
     * 处理html中的特殊字符串
     *
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     *
     * @param html
     * @return
     */
    public static String htmlEscapeCharsToString(String html) {
        return isBlank(html) ? html : html.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }


    /**
     * 将字符串用UTF-8编码
     *
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     *
     * @param str
     * @return
     * @throws
     */
//    public static String utf8UrlEncode(String str) {
//        return urlEncode(str, CstCharset.UTF_8);
//    }

    /**
     * 将字符串用指定的编码进行编码，发生异常时，源字符串直接返回，不做编码
     * @param str
     * @param charset
     * @return
     */
    public static String urlEncode(String str, String charset) {
        if (!isEmpty(str)) {
            try {
                return URLEncoder.encode(str, charset);
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }

        return str;
    }

    /**
     * 将字符串用UTF-8解码
     * @param str
     * @return
     */
//    public static String utf8UrlDecode(String str) {
//        return urlDecode(str, CstCharset.UTF_8);
//    }

    /**
     * 将字符串用指定的编码进行解码，发生异常时，源字符串直接返回，不做解码
     * @param str
     * @param charset
     * @return
     */
    public static String urlDecode(String str, String charset) {
        if (!isEmpty(str)) {
            try {
                return URLDecoder.decode(str, charset);
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }

        return str;
    }
}
