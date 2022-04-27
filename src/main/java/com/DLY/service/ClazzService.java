package com.DLY.service;

import com.DLY.pojo.Clazz;
import com.DLY.pojo.Grade;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86178
* @description 针对表【tb_clazz】的数据库操作Service
* @createDate 2022-04-19 20:55:11
*/
public interface ClazzService extends IService<Clazz> {

    Page<Clazz> getClazzsByOpr(Page page,String gradeName,String name);

    boolean saveOrUpdateClazz(Clazz clazz);

    void deleteClazz(List<Integer> list);
}
