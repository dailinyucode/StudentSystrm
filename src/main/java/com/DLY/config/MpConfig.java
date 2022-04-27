package com.DLY.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: StudentSystrm
 * @package: com.DLY.config
 * @className: MpConfig
 * @author: DaiLinYu
 * @date: 2022/4/19 20:05
 * @version: 1.0
 * mybatis的分页插件配置
 */
@Configuration
public class MpConfig {
    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor=new PaginationInterceptor();
        //paginationInterceptor.setLimit(限制单页大小)
        return paginationInterceptor;
    }
}
