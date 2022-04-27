package com.DLY.service.impl;

import com.DLY.mapper.GradeMapper;
import com.DLY.pojo.Grade;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.DLY.pojo.Clazz;
import com.DLY.service.ClazzService;
import com.DLY.mapper.ClazzMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86178
* @description 针对表【tb_clazz】的数据库操作Service实现
* @createDate 2022-04-19 20:55:11
*/
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
    implements ClazzService{
    @Autowired
    ClazzMapper clazzMapper;

    @Override
    public Page<Clazz> getClazzsByOpr(Page page, String gradeName,String name) {
            LambdaQueryWrapper<Clazz> clazzLambdaQueryWrapper = new LambdaQueryWrapper<>();
            if(gradeName!=null){
                clazzLambdaQueryWrapper.eq(Clazz::getGradeName,gradeName);
            }
            if(name!=null){
                clazzLambdaQueryWrapper.like(Clazz::getName,name);
            }
            return clazzMapper.selectPage(page,clazzLambdaQueryWrapper);
    }

    @Override
    public boolean saveOrUpdateClazz(Clazz clazz) {
        if(clazz.getId()!=null){
            clazzMapper.updateById(clazz);
        }else{
            clazzMapper.insert(clazz);
        }
        return true;
    }

    @Override
    public void deleteClazz(List<Integer> list) {
         clazzMapper.deleteBatchIds(list);
    }

}




