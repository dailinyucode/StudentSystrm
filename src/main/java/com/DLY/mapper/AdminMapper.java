package com.DLY.mapper;

import com.DLY.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86178
* @description 针对表【tb_admin】的数据库操作Mapper
* @createDate 2022-04-19 20:44:15
* @Entity com.DLY.pojo.Admin
*/
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}




