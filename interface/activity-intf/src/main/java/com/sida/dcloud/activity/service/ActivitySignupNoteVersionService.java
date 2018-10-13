package com.sida.dcloud.activity.service;

import com.github.pagehelper.Page;
import com.sida.dcloud.activity.po.ActivitySignupNoteVersion;
import com.sida.xiruo.xframework.service.IBaseService;

public interface ActivitySignupNoteVersionService extends IBaseService<ActivitySignupNoteVersion> {
    int insertVersion(String version);
    void changeCurrentVersion(String version);
    int deleteByVersion(String version);
    int resumeByVersion(String version);
    String getCurrentUsedVerion();
    ActivitySignupNoteVersion selectByVerion(String version);
    Page<ActivitySignupNoteVersion> findPageList(ActivitySignupNoteVersion po);
}