package com.DLY.mapper;

import com.DLY.pojo.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86178
* @description 针对表【tb_student】的数据库操作Mapper
* @createDate 2022-04-19 20:55:31
* @Entity com.DLY.pojo.Student
*/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}




