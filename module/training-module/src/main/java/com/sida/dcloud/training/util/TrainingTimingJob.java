package com.sida.dcloud.training.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingTimingJob {
    private static final Logger LOG = LoggerFactory.getLogger(TrainingTimingJob.class);

    @Autowired
    private TrainingCacheUtil trainingCacheUtil;

    public void scanExamPaperJob() {
        trainingCacheUtil.scanExamPaper();
    }
}
