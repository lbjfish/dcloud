package com.sida.dcloud.training.util;

import com.sida.dcloud.training.common.TrainingConstants;
import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.dto.ExamRuleMixDto;
import com.sida.dcloud.training.po.*;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.dcloud.training.po.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public final class ExamPaperBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(ExamPaperBuilder.class);

    private static ExamPaperBuilder instance = null;
    public static ExamPaperBuilder createPaper(ExamTrack examPaper) {
        Optional.ofNullable(examPaper).orElseThrow(() -> new TrainingException("必须指定试卷"));
        instance = Optional.ofNullable(instance).orElse(new ExamPaperBuilder());
        instance.init(examPaper);
        return instance;
    }

    private TrainingCacheUtil trainingCacheUtil;
    private ExamTrack examPaper;
    Map<String, String> subjectDic;
    Map<String, String> exerciseTypeDic;
    private ExamPaperBuilder() {}

    private void init(ExamTrack examPaper) {
        this.examPaper = examPaper;
    }

    public ExamPaperBuilder shareCache(TrainingCacheUtil trainingCacheUtil) {
        this.trainingCacheUtil = trainingCacheUtil;
        subjectDic = trainingCacheUtil.getRedisUtil().getDicNameCodeMapByGroupCode(TrainingConstants.DIC_GROUP_CODE_SUBJECT);
        exerciseTypeDic = trainingCacheUtil.getRedisUtil().getDicNameCodeMapByGroupCode(TrainingConstants.DIC_GROUP_CODE_EXERCISE_TYPE);
        return this;
    }

    public ExamPaperBuilder buildPaper() {
        Optional.ofNullable(trainingCacheUtil).orElseThrow(() -> new TrainingException("没有指定数据来源"));
        //试卷未过期
        if(trainingCacheUtil.getExamPaperByUserId(examPaper.getUserId()) != null) {
            examPaper = trainingCacheUtil.getExamPaperByUserId(examPaper.getUserId());
        } else {
            ExamRuleMixDto mixRule = trainingCacheUtil.getExamRuleBySubject(examPaper.getSubject());
            List<ExamTypeRule> typeRuleList = mixRule.getExamTypeRuleList();
            List<ExamSectionRule> sectionRuleList = mixRule.getExamSectionRuleList();
            ExamNumRule numRule = mixRule.getExamNumRule();
            LOG.info("所属科目 [{}({})]", subjectDic.get(examPaper.getSubject()), examPaper.getSubject());
            LOG.info("数量抽题规则 [题量({}), 合格分数({}), 考试时间({}分钟)]", numRule.getExerciseSum(), numRule.getQualifiedScore(), numRule.getExamMinutes());
            LOG.info("题型抽题规则");
            typeRuleList.stream().map(typeRule -> String.format("题型 [%s(%s)] 占比 [%d%%]", exerciseTypeDic.get(typeRule.getType()), typeRule.getType(), typeRule.getRate())).forEach(LOG::info);
            LOG.info("章节抽题规则");
            sectionRuleList.stream().map(sectionRule -> String.format("章节 [%s(%s)] 占比 [%d%%]", trainingCacheUtil.getChapterSectionById(sectionRule.getSectionId()), sectionRule.getSectionId(), sectionRule.getRate())).forEach(LOG::info);

            //总题量
            int total = numRule.getExerciseSum();
            examPaper.setTotal(total);
            //未做题数
            examPaper.setUncomplete(total);
            //得分
            examPaper.setScore(0);
            //抽题结果 - 试题
            Map<String, ExamAnswerTrack> examAnswerTrackMap = new HashMap<>(total);
            examPaper.setExamAnswerTrackMap(examAnswerTrackMap);

            //章节与题型笛卡尔分组缓存
            Map<String, List<String>> pageRuleMap = trainingCacheUtil.getPaperRule();
            sectionRuleList.forEach(sectionRule -> {
                typeRuleList.forEach(typeRule -> {
                    if (total > examAnswerTrackMap.size()) {
                        //章节id与题型组合key
                        String key = String.format("%s_%s", sectionRule.getSectionId(), typeRule.getType());
                        List<String> exerciseIdList;
                        if ((exerciseIdList = pageRuleMap.get(key)) != null) {
                            //四舍五入
                            long itemNum = Math.round(total * sectionRule.getRate() * 0.01 * typeRule.getRate() * 0.01);
                            if (examAnswerTrackMap.size() + itemNum > total) {
                                itemNum = total - examAnswerTrackMap.size();
                                if (itemNum > 0) {
                                    doExamPaper(exerciseIdList, examAnswerTrackMap, itemNum);
                                }
                            }
                        }
                    }
                });
            });
            //放入redis缓存
            trainingCacheUtil.saveExamPaper(examPaper);
        }
        return this;
    }

    /**
     *
     * @param exerciseIdList
     * @param examAnswerTrackMap
     * @param itemNum
     */
    private void doExamPaper(List<String> exerciseIdList, Map<String, ExamAnswerTrack> examAnswerTrackMap, long itemNum) {
        //set可去重
        Set<String> idSet = new HashSet<>();
        int itemSize = exerciseIdList.size();
        int rndIndex;
        while(idSet.size() < itemNum) {
            rndIndex = (int)Xiruo.rand(0, itemSize);
            idSet.add(exerciseIdList.get(rndIndex));
        }
        idSet.forEach(id -> {
            ExamAnswerTrack answer = new ExamAnswerTrack();
            Exercise exercise = trainingCacheUtil.getExerciseById(id);
            answer.setSequence(examAnswerTrackMap.size() + 1);
            answer.setUserId(examPaper.getUserId());
            answer.setExamId(examPaper.getId());
            answer.setExerciseId(id);
            answer.setRightAnswer(exercise.getAnswer());
            examAnswerTrackMap.put(answer.getId(), answer);
        });
    }

    public ExamTrack getExamPaper() {
        return examPaper;
    }
}
