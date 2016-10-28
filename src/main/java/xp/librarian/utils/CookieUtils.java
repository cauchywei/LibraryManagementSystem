package xp.librarian.utils;

import java.io.*;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;

import xp.librarian.model.context.InvalidCookieException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * @author xp
 */
public class CookieUtils {

    private static final String DELIMITER = ":";

    private static KryoPool kryoPool;

    static {
        KryoFactory kryoFactory = Kryo::new;
        kryoPool = new KryoPool.Builder(kryoFactory).softReferences().build();
    }

    private static <T> byte[] serialize(T object) {
        if (object == null) {
            return new byte[0];
        }
        return kryoPool.run((kryo) -> {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Output output = new Output(stream);
            kryo.writeClassAndObject(output, object);
            output.close();
            return stream.toByteArray();
        });
    }

    private static <T> T deserialize(byte[] bytes) {
        return kryoPool.run((kryo) -> {
            Input input = new Input(bytes);
            @SuppressWarnings("unchecked")
            T object = (T) kryo.readClassAndObject(input);
            input.close();
            return object;
        });
    }

    private static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String encode(String value) {
        return encode(value.getBytes());
    }

    private static byte[] decode(String cookie) {
        try {
            return Base64.getDecoder().decode(cookie.getBytes());
        } catch (IllegalArgumentException e) {
            throw new InvalidCookieException("base64 decode failed.");
        }
    }

    private static String decodeToString(String cookie) {
        return new String(decode(cookie));
    }

    private static String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }

    public static <T> T getCookie(String name) {
        HttpServletRequest request = ServletUtils.getRequest();

        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return deserialize(decode(cookie.getValue()));
            }
        }
        return null;
    }

    public static <T> void setCookie(String name, T value, int maxAge) {
        HttpServletRequest request = ServletUtils.getRequest();
        HttpServletResponse response = ServletUtils.getResponse();

        Cookie cookie = new Cookie(name, encode(serialize(value)));
        cookie.setMaxAge(maxAge);
        cookie.setPath(getCookiePath(request));
        cookie.setSecure(request.isSecure());

        response.addCookie(cookie);
    }

    public static void delCookie(String name) {
        HttpServletRequest request = ServletUtils.getRequest();
        HttpServletResponse response = ServletUtils.getResponse();

        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));

        response.addCookie(cookie);
    }

}
