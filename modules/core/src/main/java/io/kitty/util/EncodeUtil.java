package io.kitty.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Objects;

public class EncodeUtil {
    private static final String DEFAULT_URL_ENCODING = "UTF-8";
    private static final Charset UTF_8 = Charset.forName(DEFAULT_URL_ENCODING);

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        Objects.requireNonNull(input, "encodeBase64 params is null");
        byte[] encoded = Base64.getEncoder().encode(input);
        return new String(encoded, UTF_8);
    }

    /**
     * Base64解码.
     */
    public static byte[] decodeBase64(String input) {
        Objects.requireNonNull(input, "decodeBase64 params is null");
        byte[] encodeInput = input.getBytes(UTF_8);
        return Base64.getDecoder().decode(encodeInput);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String part) {

        try {
            return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }
}
