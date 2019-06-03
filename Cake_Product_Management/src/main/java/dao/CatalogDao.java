package dao;

import entity.Catalog;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface CatalogDao {

    @Insert("<script>"+
            "insert into catalog(title,pid,info) values"+
            "<foreach collection='list' item='catalog' separator=','>"+
            "(#{catalog.title},#{catalog.pid},#{catalog.info})"+
            "</foreach>"+
            "</script>")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void batchInsert(List<Catalog> list);

    @Delete("delete from catalog where id=#{id}")
    void delete(int id);

    //查询某一个分类自己的信息，id,title,pid,info
    @Select("select * from catalog where id=#{id}")
    @Results(id = "all",value = {
            //id作为主键，需要设置id=true
            @Result(column = "id",property = "id",id=true),
            @Result(column = "title",property = "title"),
            @Result(column = "pid",property = "pid"),
            @Result(column = "info",property = "info"),
            //设置子类，通过id字段进行主键关联。
            @Result(column = "id",property = "children",
                    many=@Many(select = "selectByPid" ))
    })
    Catalog select(int id);

    //查询多个，因为Pid并不唯一，会返回同一个Pid下的所有子类
    @Select("select * from catalog where pid=#{pid}")
    @ResultMap("all")
    List<Catalog> selectByPid(int pid);
}
