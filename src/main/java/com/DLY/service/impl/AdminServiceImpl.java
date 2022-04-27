package com.DLY.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.DLY.pojo.Admin;
import com.DLY.service.AdminService;
import com.DLY.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author 86178
* @description 针对表【tb_admin】的数据库操作Service实现
* @createDate 2022-04-19 20:44:15
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




