package com.DLY.mapper;

import com.DLY.pojo.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86178
* @description 针对表【tb_teacher】的数据库操作Mapper
* @createDate 2022-04-19 20:55:39
* @Entity com.DLY.pojo.Teacher
*/
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

}




