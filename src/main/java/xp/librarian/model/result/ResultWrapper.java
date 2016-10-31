package xp.librarian.model.result;

import java.io.*;
import java.util.*;

import lombok.Data;
import lombok.NonNull;

/**
 * @author xp
 */
@Data
public class ResultWrapper implements Serializable {

    private static final long serialVersionUID = -8067540196249444283L;

    private Boolean success;

    private Object entity;

    private List entities;

    private Object error;

    public static ResultWrapper action(boolean success) {
        ResultWrapper result = new ResultWrapper();
        result.success = success;
        return result;
    }

    public static ResultWrapper entity(Object entity) {
        ResultWrapper result = new ResultWrapper();
        result.success = true;
        result.entity = entity;
        return result;
    }

    public static ResultWrapper entities(List entities) {
        ResultWrapper result = new ResultWrapper();
        result.success = true;
        result.entities = entities;
        return result;
    }

    public static ResultWrapper error(Object error) {
        ResultWrapper result = new ResultWrapper();
        result.success = false;
        result.error = error;
        return result;
    }

}
