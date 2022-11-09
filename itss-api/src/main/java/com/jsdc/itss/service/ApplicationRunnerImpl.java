package com.jsdc.itss.service;

import cn.hutool.cron.CronUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }
}