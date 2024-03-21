package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.Penalty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PenaltyMapper {
    List<Penalty> getPenalty(Penalty penalty);
    //返回影响的行数
    int delPenaltyByPenaltyId(@Param("penaltyId") Integer penaltyId);
    //返回影响的行数
    int addPenalty(Penalty penalty);
    int updatePenalty(Penalty penalty);
}
