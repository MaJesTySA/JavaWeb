package utils;

import org.apache.commons.fileupload.FileItem;
import java.io.File;
//保存文件
public class FileUtil {
    public static final String SAVE_PATH="d:/upload/";

    public static String save(FileItem fileItem,String path) throws Exception {
        //处理同名文件，加时间戳
        String fileName=System.currentTimeMillis()+"_"+fileItem.getName();
        //写入到d:/upload/fileName
        fileItem.write(new File(path+fileName));
        return fileName;
    }
}
