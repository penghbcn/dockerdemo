package org.yohm.springcloud.fileupload.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yohm.springcloud.fileupload.model.TestModel;

import java.util.List;

/**
 * 功能简述
 * (测试用)
 *
 * @author 海冰
 * @create 2019-04-20
 * @since 1.0.0
 */
public interface TestMapper {

    @Select("select id,data from test_t order by id desc limit 1")
    TestModel selectLastOne();

    @Insert("insert into test_t (`data`) values(#{data})")
    int insertOne(@Param("data") String data);

    @Insert("<script>" +
            "insert into test_t (`num`) values " +
            "<foreach collection='list' item='item' index='index' separator=','>" +
            "(#{item})" +
            "</foreach>" +
            "</script>")
    int batchInsertNum(List<Integer> nums);
}
