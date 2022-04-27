package com.DLY.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.DLY.pojo.Student;
import com.DLY.service.StudentService;
import com.DLY.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2022-04-19 20:55:31
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

}




