package com.DLY.controller;

import com.DLY.pojo.Clazz;
import com.DLY.service.ClazzService;
import com.DLY.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: StudentSystrm
 * @package: com.DLY.controller
 * @className: ClazzController
 * @author: DaiLinYu
 * @date: 2022/4/19 21:13
 * @version: 1.0
 * @description: TODO
 */
@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    @Autowired
    ClazzService clazzService;

    @ApiOperation("分页获取和条件获取班级信息")
    @GetMapping("/getClazzsByOpr/{index}/{size}")
    public Result getClazzsByOpr(
        @ApiParam("页码") @PathVariable("index") Integer index,
        @ApiParam("页的大小")  @PathVariable("size") Integer size,
        @ApiParam("查询条件年级名称")  @RequestParam(value = "gradeName",required = false) String gradeName,
        @ApiParam("查询条件班级名称")  @RequestParam(value = "name",required = false) String name
    ){
        Page<Clazz> clazzPage = new Page<>(index,size);
        return Result.ok(clazzService.getClazzsByOpr(clazzPage,gradeName,name));
    }

    @ApiOperation("保存和和更新接口")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
           @ApiParam("班级参数") @RequestBody Clazz clazz){
        clazzService.saveOrUpdateClazz(clazz);
        return Result.ok();
    }

    @ApiOperation("删除一个和删除多个")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(
           @ApiParam("删除id的集合") @RequestBody List<Integer> list){
        clazzService.deleteClazz(list);
        return Result.ok();
    }

    @ApiOperation("查询全部的班级")
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        return Result.ok(clazzService.list());
    }
}
