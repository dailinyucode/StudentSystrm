package com.DLY.controller;

import com.DLY.pojo.Grade;
import com.DLY.service.GradeService;
import com.DLY.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @projectName: StudentSystrm
 * @package: com.DLY.controller
 * @className: GradeController
 * @author: DaiLinYu
 * @date: 2022/4/19 21:13
 * @version: 1.0
 * @description: TODO
 */
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    GradeService gradeService;

    @ApiOperation("查询年级信息,分页带条件")
    @GetMapping("/getGrades/{index}/{size}")
    public Result getGrades(
            @ApiParam("分页查询页码数") @RequestParam(value = "gradeName",required = false) String classname,  //模糊查询参数
            @ApiParam("分页查询页大小") @PathVariable("index") Integer index,
            @ApiParam("分页查询模糊匹配班级名") @PathVariable("size") Integer size){

        if(null==classname) {
            //分页条件查询 传入index和size
            Page<Grade> gradePage = new Page<>(index,size);
            Page<Grade> page = gradeService.page(gradePage);  //获取页面对象
            return Result.ok(page);   //将分页对象交给前端人员解析
        }else{
            //分页条件查询 传入index和size
            Page<Grade> gradePage = new Page<>(index,size);
            LambdaQueryWrapper<Grade> gradeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            gradeLambdaQueryWrapper.like(Grade::getName,classname);
            Page<Grade> page = gradeService.page(gradePage, gradeLambdaQueryWrapper);
            return Result.ok(page);   //将分页对象交给前端人员解析
        }
    }

    @ApiOperation("添加或者修改年级信息Grade")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
            @ApiParam("JSON的grade对象转换后台数据模型") @RequestBody Grade grade){
        if(grade.getId()!=null){
            gradeService.updateById(grade);   //通过id修改信息
        }else{
            gradeService.save(grade);     //添加信息
        }
        return Result.ok();
    }

    @ApiOperation("删除一个或者多个grade信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
            @ApiParam("JSON的年级id集合,映射为后台List<Integer>") @RequestBody List<Integer> gradeIds){   //需要指明类型
        gradeService.removeByIds(gradeIds);      //根据多个id删除元素
        return  Result.ok();
    }

    @ApiOperation("查询全部的年级")
    @GetMapping("/getGrades")
    public Result getGrades(){
        return Result.ok(gradeService.list());
    }
}
