package utils;

import dto.ParamDto;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RequestUtil {
    //从request流中解析参数与上传的文件
    public static ParamDto parseParam(HttpServletRequest request){
        ParamDto result=new ParamDto();
        ServletFileUpload upload=new ServletFileUpload(new DiskFileItemFactory());
        upload.setHeaderEncoding("UTF-8");
        //解析文件
        try {
            List<FileItem> fileItemList=upload.parseRequest(request);
            for(FileItem fileItem:fileItemList){
                //普通表单
                if(fileItem.isFormField()){
                    result.getParamMap().put(fileItem.getFieldName(),fileItem.getString("UTF-8"));
                }else{
                    //文件流
                    result.getFilemap().put(fileItem.getFieldName(),fileItem);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
