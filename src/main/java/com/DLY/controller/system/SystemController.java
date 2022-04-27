package com.DLY.controller.system;

import com.DLY.dto.LoginForm;
import com.DLY.pojo.Admin;
import com.DLY.pojo.Student;
import com.DLY.pojo.Teacher;
import com.DLY.service.AdminService;
import com.DLY.service.StudentService;
import com.DLY.service.TeacherService;
import com.DLY.util.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @projectName: StudentSystrm
 * @package: com.DLY.controller.system
 * @className: SystemConcroller
 * @author: DaiLinYu
 * @date: 2022/4/19 21:22
 * @version: 1.0
 * @description: TODO
 */

@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
@Slf4j
public class SystemController {

    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;

    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取图片流
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码文本放入session域，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //将验证码图片响应给浏览器
        try {
            //imageIO下的一个工具类
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("登入接口")
    @PostMapping("/login")
    public Result login(
            @ApiParam("登入对象") @RequestBody LoginForm loginForm, HttpSession session){
        //验证码校验
        String sessionCode = session.getAttribute("verifiCode").toString();
        String loginfromCode = loginForm.getVerifiCode();

        //验证码失效或者没有输入的情况
        if("".equals(sessionCode) || null==sessionCode){
            return Result.fail().message("验证码失效请重试!");
        }
        //不区分大小写去比较这个字符串是否相同
        if(!loginfromCode.equalsIgnoreCase(sessionCode)){
            return Result.fail().message("验证码输入有误！");
        }
        //重session域中移除现有验证码
        session.removeAttribute("verifiCode");

        //创建一个map来穿法用户响应数据
        Map<String,Object> map=new HashMap<>();
        //分用户类型进行校验
        switch (loginForm.getUserType()){
            case 1:
                //查询用户密码并对比
                LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
                adminLambdaQueryWrapper.eq(Admin::getName,loginForm.getUsername())
                                       .eq(Admin::getPassword, MD5.encrypt(loginForm.getPassword()));
                Admin admin = adminService.getOne(adminLambdaQueryWrapper);
                if(null!=admin){
                    map.put("token",JwtHelper.createToken(admin.getId().longValue(),1));
                }else {
                    try {
                        throw new RuntimeException("用户名和密码有误");
                    } catch (RuntimeException e) {
                        return Result.fail().message(e.getMessage());
                    }
                }
                return Result.ok(map);
            case 2:
                LambdaQueryWrapper<Student> studentLambdaQueryWrapper=new LambdaQueryWrapper<>();
                studentLambdaQueryWrapper.eq(Student::getName,loginForm.getUsername()).eq(Student::getPassword,MD5.encrypt(loginForm.getPassword()));
                Student student = studentService.getOne(studentLambdaQueryWrapper);
                if(null!=student){
                    map.put("token",JwtHelper.createToken(student.getId().longValue(),2));
                }else {
                    try {
                        throw new RuntimeException("用户名和密码有误");
                    } catch (RuntimeException e) {
                        return Result.fail().message(e.getMessage());
                    }
                }
                return Result.ok(map);
            case 3:
                LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
                teacherLambdaQueryWrapper.eq(Teacher::getName,loginForm.getUsername()).eq(Teacher::getPassword,MD5.encrypt(loginForm.getPassword()));
                Teacher teacher = teacherService.getOne(teacherLambdaQueryWrapper);
                if(null!=teacher){
                    map.put("token",JwtHelper.createToken(teacher.getId().longValue(),3));
                }else {
                    try {
                        throw new RuntimeException("用户名和密码有误");
                    } catch (RuntimeException e) {
                        return Result.fail().message(e.getMessage());
                    }
                }
                return Result.ok(map);
            default:
                return Result.fail().message("类型错了");
        }
    }

    @ApiOperation("返回用户响应数据")
    //返回登入之后的响应数据
    @GetMapping("/getInfo")
    public Result getInfo(
          @ApiParam("toker")  @RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从toker解析出用户信息  和用户id和类型
        Long userId=JwtHelper.getUserId(token);
        Integer userType=JwtHelper.getUserType(token);
        Map<String,Object> map=new HashMap<>();
        switch (userType) {
            case 1:
                Admin admin = adminService.getById(userId);
                map.put("userType",userType);
                map.put("user",admin);
                return Result.ok(map);
            case 2:
                Student student = studentService.getById(userId);
                map.put("userType",userType);
                map.put("user",student);
                return Result.ok(map);
            case 3:
                Teacher teacher = teacherService.getById(userId);
                map.put("userType",userType);
                map.put("user",teacher);
                return Result.ok(map);
            default:
                return Result.fail().message("没有该类型");
        }
    }

    @ApiOperation("文件上传")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
           @ApiParam("文件上传图片参数") @RequestPart("multipartFile") MultipartFile multipartFile) throws IOException {
        //文件名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //传过来的文件后缀
        String originalFilename = multipartFile.getOriginalFilename();
        String[] split = originalFilename.split("\\.");
        String fileName= uuid+'.'+split[1];
        //保存文件   将文件发送到第三方
        String filepath="D:\\happy\\IDEA\\StudentSystrm\\target\\classes\\public\\upload";
        multipartFile.transferTo(new File(filepath,fileName));
        return Result.ok("upload/"+fileName);
    }

    @ApiOperation("修改密码")
    @PostMapping("updatePwd/{oldpassword}/{password}")
    public Result updatePwd(
            @ApiParam("新密码") @PathVariable("password") String password,
            @ApiParam("旧密码") @PathVariable("oldpassword") String oldpassword,
            @ApiParam("Tocken") @RequestHeader("token") String token
    ){
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        switch (userType){
            case 1:
                if(adminService.getById(userId).getPassword().equals(MD5.encrypt(oldpassword))){
                    UpdateWrapper<Admin> adminQueryWrapper = new UpdateWrapper<>();
                    adminQueryWrapper.eq("id",userId).set("password",MD5.encrypt(password));
                    adminService.update(adminQueryWrapper);
                    return Result.ok();
                }else{
                    return Result.fail().message("旧密码错误");
                }
            case 2:
                if(studentService.getById(userId).getPassword().equals(MD5.encrypt(oldpassword))){
                    UpdateWrapper<Student> studentWrapper = new UpdateWrapper<>();
                    studentWrapper.eq("id",userId).set("password",MD5.encrypt(password));
                    studentService.update(studentWrapper);
                    return Result.ok();
                }else{
                    return Result.fail().message("旧密码错误");
                }
            case 3:
                if(teacherService.getById(userId).getPassword().equals(MD5.encrypt(oldpassword))){
                    UpdateWrapper<Teacher> teacherUpdateWrapper = new UpdateWrapper<>();
                    teacherUpdateWrapper.eq("id",userId).set("password",MD5.encrypt(password));
                    teacherService.update(teacherUpdateWrapper);
                    return Result.ok();
                }else{
                    return Result.fail().message("旧密码错误");
                }
            default:
                return Result.fail().message("错了");
        }
    }
}
