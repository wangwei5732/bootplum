package com.wdx.bootplum.common.config;

import com.wdx.bootplum.common.utils.DateTimeUtils;
import freemarker.template.utility.DateUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Test extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.err.println(DateTimeUtils.getCurrentLocalTime());

    }
}
