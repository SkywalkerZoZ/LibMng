package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.BookInstance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookInstanceMapper {
    List<BookInstance> getBookInstances(BookInstance bookInstance);
    //返回影响的行数
    int delBookInstanceByInstanceId(@Param("instanceId") Integer instanceId);
    //返回影响的行数
    int addBookInstance(BookInstance bookInstance);

}
