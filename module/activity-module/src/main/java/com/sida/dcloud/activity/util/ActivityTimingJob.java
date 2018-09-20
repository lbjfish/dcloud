package com.sida.dcloud.activity.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityTimingJob {
    private static final Logger LOG = LoggerFactory.getLogger(ActivityTimingJob.class);

    @Autowired
    private ActivityCacheUtil activityCacheUtil;

//    public void scanExamPaperJob() {
//        activityCacheUtil.scanExamPaper();
//    }
}
