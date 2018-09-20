package com.sida.dcloud.content.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContentTimingJob {
    private static final Logger LOG = LoggerFactory.getLogger(ContentTimingJob.class);

    @Autowired
    private ContentCacheUtil contentCacheUtil;

//    public void scanExamPaperJob() {
//        activityCacheUtil.scanExamPaper();
//    }
}
