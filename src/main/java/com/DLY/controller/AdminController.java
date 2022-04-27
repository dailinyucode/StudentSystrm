package com.DLY.controller;

import com.DLY.pojo.Admin;
import com.DLY.pojo.Teacher;
import com.DLY.service.AdminService;
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
 * @className: AdminController
 * @author: DaiLinYu
 * @date: 2022/4/19 21:13
 * @version: 1.0
 * @description: TODO
 */
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Autowired
    AdminService adminService;

    @ApiOperation("分页获取和条件获取管理员信息")
    @GetMapping("/getAllAdmin/{index}/{size}")
    public Result getTeachers(
            @ApiParam("管理员名字") @RequestParam(value = "name",required = false) String name,
            @ApiParam("页码") @PathVariable("index") Integer index,
            @ApiParam("页的大小")  @PathVariable("size") Integer size
    ){
        Page<Admin> adminPage = new Page<>(index, size);
        LambdaQueryWrapper<Admin> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(name!=null){
            objectLambdaQueryWrapper.like(Admin::getName,name);
        }
        return Result.ok(adminService.page(adminPage,objectLambdaQueryWrapper));
    }

    @ApiOperation("删除一个或者多个admin信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
            @ApiParam("JSON的管理员id集合,映射为后台List<Integer>") @RequestBody List<Integer> AdminIds){   //需要指明类型
        String filepath="D:\\happy\\IDEA\\StudentSystrm\\target\\classes\\public\\upload";
        AdminIds.forEach(v->{
            Admin byId = adminService.getById(v);
            String portraitPath = byId.getPortraitPath().split("/")[1];
            if(!portraitPath.equals("default.jpg")){
                new File(filepath, portraitPath).delete();
            }
        });
        adminService.removeByIds(AdminIds);      //根据多个id删除元素
        return  Result.ok();
    }

    @ApiOperation("添加或者更新管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @ApiParam("管理员信息参数") @RequestBody Admin admin){
        if (null != admin.getId()) {
            adminService.updateById(admin);
        }else{
            admin.setPassword(MD5.encrypt(admin.getPassword()));
            adminService.save(admin);
        }
        return Result.ok();
    }
}
