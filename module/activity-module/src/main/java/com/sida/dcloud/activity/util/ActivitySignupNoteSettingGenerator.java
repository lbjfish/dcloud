package com.sida.dcloud.activity.util;

import com.google.common.base.CaseFormat;
import com.sida.dcloud.activity.po.ActivitySignupNoteSetting;
import com.sida.dcloud.activity.service.CustomerActivitySignupNoteService;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.po.common.TableMeta;
import com.sida.xiruo.xframework.util.UUID;
import com.sida.xiruo.xframework.util.UUIDGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Scope("singleton")
public final class ActivitySignupNoteSettingGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(ActivitySignupNoteSettingGenerator.class);

    private static final String IGNORE_FIELDS = ",id,org_id,delete_flag,red_string1,red_string2,red_string3,red_string4,red_string5,red_string6,";

    @Autowired
    private CustomerActivitySignupNoteService customerActivitySignupNoteService;

    private List<ActivitySignupNoteSetting> settingList;

    @PostConstruct
    private void init() {
        settingList = new ArrayList<>();
        try {
            int count = 0;
            while (customerActivitySignupNoteService == null) {
                Thread.sleep(500);
                if(count++ > 10) {
                    LOG.warn("customerActivitySignupNoteService 未初始化完成");
                    return;
                } else {
                    LOG.warn("customerActivitySignupNoteService current status is null serial = {}", count);
                }
            }
            List<TableMeta> metaList = customerActivitySignupNoteService.findTableMeta();
            metaList.stream().filter(meta -> IGNORE_FIELDS.indexOf(String.format(",%s,", meta.getColumnName())) < 0).forEach(meta -> settingList.add(fillActivitySignupNoteSetting(meta)));
        }catch(Exception e) {
            LOG.error("customerActivitySignupNoteService is null.", e);
        }
    }

    private ActivitySignupNoteSetting fillActivitySignupNoteSetting(TableMeta tableMeta) {
        ActivitySignupNoteSetting setting = new ActivitySignupNoteSetting();
        setting.setName(tableMeta.getColumnName());
        setting.setCode(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableMeta.getColumnName()));
        setting.setDisplayName(tableMeta.getColumnComment());
        setting.setDeleteFlag(false);
        setting.setHideStatus(false);
        setting.setAllowEmpty(true);
        setting.setSizeLimit(0);
        setting.setSort(settingList.size());
        setting.setVersion(Xiruo.dateToString(new Date(), "yyyyMMdd"));
        return setting;
    }

    @PreDestroy
    public void destroy() {
        settingList.clear();
        settingList = null;
    }

    public List<ActivitySignupNoteSetting> generate(String version) {
        settingList.forEach(setting -> {
            setting.setId(UUIDGenerate.getNextId());
            setting.setVersion(version);
        });
        return settingList;
    }
}
