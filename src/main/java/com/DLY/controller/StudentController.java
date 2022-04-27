package com.DLY.controller;

import com.DLY.pojo.Admin;
import com.DLY.pojo.Student;
import com.DLY.pojo.Teacher;
import com.DLY.service.StudentService;
import com.DLY.util.MD5;
import com.DLY.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Supplier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

/**
 * @projectName: StudentSystrm
 * @package: com.DLY.controller
 * @className: StudentController
 * @author: DaiLinYu
 * @date: 2022/4/19 21:14
 * @version: 1.0
 * @description: TODO
 */
@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    StudentService studentService;

    @ApiOperation("分页获取和条件获取学生信息")
    @GetMapping("/getStudentByOpr/{index}/{size}")
    public Result getStudentByOpr(
            @ApiParam("班级名字") @RequestParam(value = "clazzName",required = false) String clazzName,
            @ApiParam("学生名字") @RequestParam(value = "name",required = false) String name,
            @ApiParam("页码") @PathVariable("index") Integer index,
            @ApiParam("页的大小")  @PathVariable("size") Integer size
    ){
        Page<Student> studentPage = new Page<>(index, size);
        LambdaQueryWrapper<Student> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(clazzName!=null){
            objectLambdaQueryWrapper.eq(Student::getClazzName,clazzName);
        }
        if(name!=null){
            objectLambdaQueryWrapper.like(Student::getName,name);
        }
        return Result.ok(studentService.page(studentPage,objectLambdaQueryWrapper));
    }

    @ApiOperation("删除一个或者多个Student信息")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(
            @ApiParam("JSON的学生id集合,映射为后台List<Integer>") @RequestBody List<Integer> StudentIds){   //需要指明类型
        String filepath="D:\\happy\\IDEA\\StudentSystrm\\target\\classes\\public\\upload";
        StudentIds.forEach(v->{
            Student byId = studentService.getById(v);
            String portraitPath = byId.getPortraitPath().split("/")[1];
            if(!portraitPath.equals("default.jpg")){
                new File(filepath, portraitPath).delete();
            }
        });
        studentService.removeByIds(StudentIds);      //根据多个id删除元素
        return  Result.ok();
    }

    @ApiOperation("添加或者更新学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
           @ApiParam("学生信息参数") @RequestBody Student student){
        if (null != student.getId()) {
            studentService.updateById(student);
        }else{
            student.setPassword(MD5.encrypt(student.getPassword()));
            studentService.save(student);
        }
        return Result.ok();
    }
}
