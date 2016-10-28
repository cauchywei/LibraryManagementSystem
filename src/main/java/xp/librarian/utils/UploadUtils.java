package xp.librarian.utils;

import java.io.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xp
 */
public class UploadUtils {

    private static final Logger LOG = LoggerFactory.getLogger(UploadUtils.class);

    public static String PATH = "./uploads";

    private static File path;

    static {
        path = new File(PATH);
        if (!path.exists()) {
            assert path.mkdirs();
        }
    }

    public static File getPath() {
        return path;
    }

    public static String makeUrl(File file) {
        return makeUrl(file.getName());
    }

    public static String makeUrl(String filename) {
        return String.format("//uploads/%s/", filename);
    }

    public static String upload(MultipartFile file) throws RuntimeException {
        if (file == null) {
            return null;
        }
        File dstFile = new File(UploadUtils.getPath(), UUID.randomUUID().toString());
        try {
            OutputStream dstOutput = new BufferedOutputStream(new FileOutputStream(dstFile));
            dstOutput.write(file.getBytes());
            dstOutput.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(null, e);
        }
        return UploadUtils.makeUrl(dstFile);
    }

}
