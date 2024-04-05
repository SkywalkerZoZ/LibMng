package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.PenaltyDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PenaltyDetailMapper {
    List<PenaltyDetail> getPenaltyDetailsByUserId(@Param("userId") Integer userId);
}
