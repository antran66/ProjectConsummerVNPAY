package com.vnpay.common;

import com.google.inject.AbstractModule;
import com.vnpay.dao.JdbcTemplate;
import com.vnpay.dao.JdbcTemplateImpl;
import com.vnpay.service.ConsumerService;
import com.vnpay.service.ConsumerServiceImpl;

/**
 * configuration binding for injector
 */
public class AppInjector extends AbstractModule {
    @Override
    protected void configure(){
        bind(ConsumerService.class).to(ConsumerServiceImpl.class);
        bind(JdbcTemplate.class).to(JdbcTemplateImpl.class);
    }
}
