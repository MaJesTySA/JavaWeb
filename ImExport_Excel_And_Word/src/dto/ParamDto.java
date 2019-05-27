package dto;

import org.apache.commons.fileupload.FileItem;
import java.util.HashMap;
import java.util.Map;

public class ParamDto {
    private Map<String,String> paramMap;
    private Map<String, FileItem> filemap;

    public ParamDto() {
        paramMap=new HashMap<>();
        filemap=new HashMap<>();
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, FileItem> getFilemap() {
        return filemap;
    }

    public void setFilemap(Map<String, FileItem> filemap) {
        this.filemap = filemap;
    }
}
