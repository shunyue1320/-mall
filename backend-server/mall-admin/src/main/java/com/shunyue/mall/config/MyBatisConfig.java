package com.shunyue.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.shunyue.mall.mapper","com.shunyue.mall.dao"})
public class MyBatisConfig {
}
