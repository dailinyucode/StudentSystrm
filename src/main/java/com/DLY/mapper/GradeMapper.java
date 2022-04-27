package com.DLY.mapper;

import com.DLY.pojo.Grade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86178
* @description 针对表【tb_grade】的数据库操作Mapper
* @createDate 2022-04-19 20:55:22
* @Entity com.DLY.pojo.Grade
*/
@Mapper
public interface GradeMapper extends BaseMapper<Grade> {

}




