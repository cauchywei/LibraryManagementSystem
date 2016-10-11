package xp.librarian.utils;

import java.security.*;

import org.springframework.security.crypto.codec.Hex;

import lombok.Data;
import xp.librarian.model.context.AccountContext;
import xp.librarian.model.dto.UserDto;
import xp.librarian.repository.UserDao;

/**
 * @author xp
 */
public class LoginUtils {

    public static final String ATTRIBUTE_NAME = "account";

    private static final String SECRET_KEY = ":as2319ca";

    private static final String COOKIE_NAME = "librarian.token";

    private static final int COOKIE_LIFE_TIME = 7 * 24 * 3600;
    private static final long COOKIE_LIFE_TIME_MS = COOKIE_LIFE_TIME * 1000L;

    @Data
    private static class Token {

        private Integer userId;

        private Long expiryTime;

        @Override
        public String toString() {
            return String.format("[Token: %d, %d]", userId, expiryTime);
        }
    }

    public static String makeTokenSignature(Token token) throws IllegalStateException {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        String data = token.toString() + SECRET_KEY;
        return new String(Hex.encode(digest.digest(data.getBytes())));
    }

    public static boolean isTokenExpired(Long expiryTime) {
        return expiryTime == null || expiryTime < System.currentTimeMillis();
    }

    public static AccountContext getAccount(UserDao userDao) {
        Token token = CookieUtils.getCookie(COOKIE_NAME);
        if (token == null) {
            return null;
        }
        if (isTokenExpired(token.getExpiryTime())) {
            CookieUtils.delCookie(COOKIE_NAME);
            return null;
        }
        UserDto user = userDao.get(token.getUserId());
        if (user == null) {
            CookieUtils.delCookie(COOKIE_NAME);
            return null;
        }
        return AccountContext.fromDTO(user);
    }

    public static boolean login(Integer userId) {
        Token token = new Token();
        token.userId = userId;
        token.expiryTime = System.currentTimeMillis() + COOKIE_LIFE_TIME_MS;
        CookieUtils.setCookie(COOKIE_NAME, token, COOKIE_LIFE_TIME);
        return true;
    }

    public static boolean logout() {
        CookieUtils.delCookie(COOKIE_NAME);
        return true;
    }



}
