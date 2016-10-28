package xp.librarian.handler;

import static xp.librarian.controller.BaseController.renderForError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import xp.librarian.model.context.BusinessException;

/**
 * @author xp
 */
@RestControllerAdvice
public class ErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

    // 200

    @ExceptionHandler({BusinessException.class})
    public Object handleLogicException(BusinessException e) {
        return renderForError(HttpStatus.OK, e.getMessage());
    }

    // 400

    @ExceptionHandler({BindException.class})
    public Object handleBindException(BindException e) {
        return renderForError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({ServletRequestBindingException.class})
    public Object handleServletRequestBindingException(ServletRequestBindingException e) {
        return renderForError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return renderForError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return renderForError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public Object handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return renderForError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // 401

    @ExceptionHandler({AuthenticationException.class})
    public Object handleAuthenticationException(AuthenticationException e) {
        return renderForError(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    // 403

    @ExceptionHandler({AccessDeniedException.class})
    public Object handleAccessDeniedException(AccessDeniedException e) {
        return renderForError(HttpStatus.FORBIDDEN, e.getMessage());
    }

    // 404

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleNoResourceFoundException(ResourceNotFoundException e) {
        return renderForError(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNoHandlerFoundException(NoHandlerFoundException e) {
        return renderForError(HttpStatus.NOT_FOUND, e.getMessage());
    }

    // 405

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return renderForError(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    // 500

    @ExceptionHandler(RuntimeException.class)
    public Object handleRuntimeException(RuntimeException e) {
        LOG.info(null, e);
        return renderForError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        LOG.info(null, e);
        return renderForError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
