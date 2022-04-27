package com.DLY;

import com.DLY.mapper.AdminMapper;
import com.DLY.pojo.Admin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.ws.soap.Addressing;
import java.util.List;

@SpringBootTest
class StudentSystrmApplicationTests {

    @Autowired
    AdminMapper adminMapper;
    @Test
    void contextLoads() {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        List<Admin> admins = adminMapper.selectList(adminQueryWrapper);
        admins.forEach(admin->{
            System.out.println(admin.toString());
        });
    }
}
