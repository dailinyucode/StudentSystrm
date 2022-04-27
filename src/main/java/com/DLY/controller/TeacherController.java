package com.DLY.controller;

import com.DLY.pojo.Admin;
import com.DLY.pojo.Student;
import com.DLY.pojo.Teacher;
import com.DLY.service.TeacherService;
import com.DLY.util.MD5;
import com.DLY.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @className: TeacherControlle
 * @author: DaiLinYu
 * @date: 2022/4/19 21:14
 * @version: 1.0
 * @description: TODO
 */
@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @ApiOperation("分页获取和条件获取老师信息")
    @GetMapping("/getTeachers/{index}/{size}")
    public Result getTeachers(
        @ApiParam("班级名字") @RequestParam(value = "clazzName",required = false) String clazzName,
        @ApiParam("老师名字") @RequestParam(value = "name",required = false) String name,
        @ApiParam("页码") @PathVariable("index") Integer index,
        @ApiParam("页的大小")  @PathVariable("size") Integer size
    ){
        Page<Teacher> teacherPage = new Page<>(index, size);
        LambdaQueryWrapper<Teacher> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(clazzName!=null){
            objectLambdaQueryWrapper.eq(Teacher::getClazzName,clazzName);
        }
        if(name!=null){
            objectLambdaQueryWrapper.like(Teacher::getName,name);
        }
        return Result.ok(teacherService.page(teacherPage,objectLambdaQueryWrapper));
    }

    @ApiOperation("删除一个或者多个Teacher信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @ApiParam("JSON的老师id集合,映射为后台List<Integer>") @RequestBody List<Integer> TeacherIds){   //需要指明类型
        String filepath="D:\\happy\\IDEA\\StudentSystrm\\target\\classes\\public\\upload";
        TeacherIds.forEach(v->{
            Teacher byId = teacherService.getById(v);
            String portraitPath = byId.getPortraitPath().split("/")[1];
            if(!portraitPath.equals("default.jpg")){
                new File(filepath, portraitPath).delete();
            }
        });
        teacherService.removeByIds(TeacherIds);      //根据多个id删除元素
        return  Result.ok();
    }
    @ApiOperation("添加或者更新老师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @ApiParam("老师信息参数") @RequestBody Teacher teacher){
        if (null != teacher.getId()) {
            teacherService.updateById(teacher);
        }else{
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
            teacherService.save(teacher);
        }
        return Result.ok();
    }
}
