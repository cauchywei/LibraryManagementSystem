package xp.librarian.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import xp.librarian.model.context.AccessForbiddenException;
import xp.librarian.model.context.BusinessException;
import xp.librarian.model.context.ResourceNotFoundException;
import xp.librarian.model.context.UnauthorizedException;
import xp.librarian.model.result.ResultWrapper;

/**
 * @author xp
 */
@RestControllerAdvice
public class ErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

    // 200

    @ExceptionHandler({BusinessException.class})
    public Object handleLogicException(BusinessException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    // 400

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleBindException(BindException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    @ExceptionHandler({ServletRequestBindingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleServletRequestBindingException(ServletRequestBindingException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    // 401

    @ExceptionHandler({UnauthorizedException.class})
    public Object handleUnauthorizedException(UnauthorizedException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    // 403

    @ExceptionHandler({AccessForbiddenException.class})
    public Object handleAccessForbiddenException(AccessForbiddenException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    // 404

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleNoResourceFoundException(ResourceNotFoundException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    // 405

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Object handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    // 500

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleRuntimeException(RuntimeException e) {
        LOG.info(null, e);
        return ResultWrapper.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleException(Exception e) {
        LOG.info(null, e);
        return ResultWrapper.error(e.getLocalizedMessage());
    }

}
