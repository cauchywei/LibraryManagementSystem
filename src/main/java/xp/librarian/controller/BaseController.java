package xp.librarian.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.web.bind.annotation.RestController;

import xp.librarian.model.context.AccountContext;
import xp.librarian.model.result.ResultWrapper;
import xp.librarian.repository.UserDao;
import xp.librarian.utils.LoginUtils;

/**
 * @author xp
 */
public class BaseController {

    @Autowired
    private UserDao userDao;

    protected AccountContext getAccount() {
        return getAccount(true);
    }

    protected AccountContext getAccount(boolean force) {
        AccountContext account = LoginUtils.getAccount(userDao);
        if (account == null && force) {
            throw new RememberMeAuthenticationException("not login or token expired.");
        }
        return account;
    }

    public static ResponseEntity renderForAction(boolean success) {
        return new ResponseEntity<>(ResultWrapper.action(success), HttpStatus.OK);
    }

    public static ResponseEntity renderForEntity(Object entity) {
        return new ResponseEntity<>(ResultWrapper.entity(entity), HttpStatus.OK);
    }

    public static ResponseEntity renderForEntities(List entities) {
        return new ResponseEntity<>(ResultWrapper.entities(entities), HttpStatus.OK);
    }

    public static ResponseEntity renderForError(HttpStatus status, Object error) {
        return new ResponseEntity<>(ResultWrapper.error(error), status);
    }

}
