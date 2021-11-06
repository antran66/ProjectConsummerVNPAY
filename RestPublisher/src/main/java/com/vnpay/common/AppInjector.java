package com.vnpay.common;

import com.google.inject.AbstractModule;
import com.vnpay.service.PublisherService;
import com.vnpay.service.PublisherServiceImpl;

/**
 * Configuration binding for injector
 */
public class AppInjector extends AbstractModule {
    @Override
    protected void configure(){
        bind(PublisherService.class).to(PublisherServiceImpl.class);
    }
}
