package com.sida.dcloud.training.util;

import com.sida.dcloud.event.po.training.TrainingEventType;
import com.sida.dcloud.training.common.TrainingConstants;
import com.sida.dcloud.training.dto.ExamRuleMixDto;
import com.sida.dcloud.training.event.dispatcher.ExamTrackDispatcher;
import com.sida.dcloud.training.po.*;
import com.sida.dcloud.training.service.*;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.dcloud.training.po.*;
import com.sida.dcloud.training.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Component
@Order(1)
public class TrainingCacheUtil implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(TrainingCacheUtil.class);
    private static String CACHE_KEY_ICON_GROUP_ALL = "CACHE_IN_REDIS_FOR_TRAINING_MODULE_ICON_GROUP_ALL";
    private static String CACHE_KEY_CHAPTER_SECTION_ALL = "CACHE_IN_REDIS_FOR_TRAINING_MODULE_CHAPTER_SECTION_ALL";
    private static String CACHE_KEY_SPECIAL_GROUP_ALL = "CACHE_IN_REDIS_FOR_TRAINING_MODULE_SPECIAL_GROUP_ALL";
    private static String CACHE_KEY_EXERCISE_ALL = "CACHE_IN_REDIS_FOR_TRAINING_MODULE_EXERCISE_ALL";
    private static String CACHE_KEY_EXAM_RULE_ALL = "CACHE_IN_REDIS_FOR_TRAINING_MODULE_EXAM_RULE_ALL";
    private static String CACHE_KEY_PAPER_RULE_ALL = "CACHE_IN_REDIS_FOR_TRAINING_MODULE_PAPER_RULE_ALL";
    private static String CACHE_KEY_EXAM_TRACK_ALL = "CACHE_IN_REDIS_FOR_TRAINING_MODULE_EXAM_TRACK_ALL";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IconGroupService iconGroupService;
    @Autowired
    private ChapterSectionService chapterSectionService;
    @Autowired
    private SpecialGroupService specialGroupService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private ExamRuleMixService examRuleMixService;

    @Autowired
    private ExamTrackDispatcher examTrackDispatcher;

    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    @Override
    public void run(String... strings) throws Exception {
//        char[] array = new char[100];
//        Arrays.fill(array, '*');
//        LOG.info(new String(array));
        initAllIconGroupCacheInRedis(false);
        initAllChapterSectionCacheInRedis(false);
        initAllSpecialGroupCacheInRedis(false);
        initAllExerciseCacheInRedis(false);
        initAllExamRuleCacheInRedis(false);
    }

    public void initAllExamRuleCacheInRedis(boolean forceReload) {
        if(forceReload || redisUtil.getEntriesFromMap(CACHE_KEY_EXAM_RULE_ALL) == null) {
            LOG.info("开始处理抽题规则缓存");
            long start = System.currentTimeMillis() / 1000;
            Map<String, String> subjectDicMap = redisUtil.getDicNameCodeMapByGroupCode(TrainingConstants.DIC_GROUP_CODE_SUBJECT);
            if(subjectDicMap != null && !subjectDicMap.isEmpty()) {
                Map<String, ExamRuleMixDto> dtoMap = new HashMap<>();
                subjectDicMap.keySet().forEach(subject -> {
                    ExamRuleMixDto dto = examRuleMixService.loadRuleBySubject(subject);
                    dtoMap.put(subject, dto);
                });
                redisUtil.putInMap(CACHE_KEY_EXAM_RULE_ALL, dtoMap);
                LOG.info("处理抽题规则缓存完成，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
            }

            LOG.info("抽题规则查无数据，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
        }
    }

    public void updatePartExamRuleCacheInRedis(ExamRuleMixDto dto, TrainingEventType eventType) {
        if(redisUtil.getEntriesFromMap(CACHE_KEY_EXAM_RULE_ALL) != null) {
            Map<String, ExamRuleMixDto> dtoMap = Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_EXAM_RULE_ALL)).orElse(new HashMap<>());
            switch(eventType) {
                case TRAINING_EVENT_EXAM_RULE_MIX_UPDATE:
                    dtoMap.put(dto.getSubject(), dto);
                    break;
            }
            redisUtil.putInMap(CACHE_KEY_EXAM_RULE_ALL, dtoMap);
        }
    }

    public ExamRuleMixDto getExamRuleBySubject(String subject) {
        return (ExamRuleMixDto)Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_EXAM_RULE_ALL)).get().get(subject);
    }

    public void initAllExerciseCacheInRedis(boolean forceReload) {
        if(forceReload || redisUtil.getEntriesFromMap(CACHE_KEY_EXERCISE_ALL) == null) {
            LOG.info("开始处理题库缓存");
            long start = System.currentTimeMillis() / 1000;
            List<Exercise> poList = exerciseService.selectByCondition();
            if(!poList.isEmpty()) {
                Map<String, Exercise> poMap = new HashMap<>();
                poList.forEach(po -> poMap.put(po.getId(), po));
                redisUtil.putInMap(CACHE_KEY_EXERCISE_ALL, poMap);
                LOG.info("处理题库缓存完成，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
                createPaperRuleCacheInRedis(forceReload);
            }
            LOG.info("题库查无数据，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
        }
    }

    public void updatePartExerciseCacheInRedis(Exercise po, TrainingEventType eventType) {
        if(redisUtil.getEntriesFromMap(CACHE_KEY_SPECIAL_GROUP_ALL) != null) {
            Map<String, Exercise> poMap = Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_EXERCISE_ALL)).orElse(new HashMap<>());
            updatePartPaperRuleCacheInRedis(po, eventType);
            switch(eventType) {
                case TRAINING_EVENT_EXERCISE_INSERT:
                case TRAINING_EVENT_EXERCISE_UPDATE:
                    poMap.put(po.getId(), po);
                    break;
                case TRAINING_EVENT_EXERCISE_REMOVE:
                    Arrays.stream(po.getStringIds().split(",")).forEach(id -> poMap.remove(id));
                    break;
            }
            redisUtil.putInMap(CACHE_KEY_EXERCISE_ALL, poMap);
        }
    }

    private void updatePartPaperRuleCacheInRedis(Exercise po, TrainingEventType eventType) {
        Map<String, List<String>> examMap;
        if((examMap = redisUtil.getEntriesFromMap(CACHE_KEY_PAPER_RULE_ALL)) != null) {
            switch (eventType) {
                case TRAINING_EVENT_EXERCISE_INSERT:
                case TRAINING_EVENT_EXERCISE_UPDATE:
                    //章节id与题型组合key
                    String key = String.format("%s_%s", po.getSectionId(), po.getType());
                    List<String> exerciseIdList = Optional.ofNullable(examMap.get(key)).orElse(new ArrayList<>());
                    exerciseIdList.add(po.getId());
                    examMap.put(key, exerciseIdList);
                    break;
                case TRAINING_EVENT_EXERCISE_REMOVE:
                    Map<String, Exercise> poMap = Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_EXERCISE_ALL)).get();
                    Arrays.stream(po.getStringIds().split(",")).forEach(id -> {
                        Exercise exercise = poMap.get(id);
                        //章节id与题型组合key
                        String k = String.format("%s_%s", po.getSectionId(), po.getType());
                        List<String> list = Optional.ofNullable(examMap.get(k)).get();
                        list.remove(k);
                        examMap.put(k, list);
                    });
                    break;
            }
            redisUtil.putInMap(CACHE_KEY_PAPER_RULE_ALL, examMap);
        }
    }

    /**
     * 在内存中重建抽题规则
     * @param forceReload
     */
    public void createPaperRuleCacheInRedis(boolean forceReload) {
        Map map;
        if((map = redisUtil.getEntriesFromMap(CACHE_KEY_EXERCISE_ALL)) != null) {
            if(forceReload || redisUtil.getEntriesFromMap(CACHE_KEY_PAPER_RULE_ALL) == null) {
                Map<String, List<String>> examMap = new HashMap<>();
                map.values().forEach(po -> {
                    Exercise exercise = (Exercise) po;
                    //章节id与题型组合key
                    String key = String.format("%s_%s", exercise.getSectionId(), exercise.getType());
                    List<String> exerciseIdList = Optional.ofNullable(examMap.get(key)).orElse(new ArrayList<>());
                    exerciseIdList.add(exercise.getId());
                    examMap.put(key, exerciseIdList);
                });
                redisUtil.putInMap(CACHE_KEY_PAPER_RULE_ALL, examMap);
            }
        }
    }

    public Map<String, List<String>> getPaperRule() {
        return redisUtil.getEntriesFromMap(CACHE_KEY_PAPER_RULE_ALL);
    }

    public Exercise getExerciseById(String exerciseId) {
        return (Exercise)Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_EXERCISE_ALL)).get().get(exerciseId);
    }

    public void initAllSpecialGroupCacheInRedis(boolean forceReload) {
        if(forceReload || redisUtil.getEntriesFromMap(CACHE_KEY_SPECIAL_GROUP_ALL) == null) {
            LOG.info("开始处理专项分组缓存");
            long start = System.currentTimeMillis() / 1000;
            List<SpecialGroup> poList = specialGroupService.selectByCondition();
            if(!poList.isEmpty()) {
                Map<String, SpecialGroup> poMap = new HashMap<>();
                poList.forEach(po -> poMap.put(po.getId(), po));
                redisUtil.putInMap(CACHE_KEY_SPECIAL_GROUP_ALL, poMap);
                LOG.info("处理专项分组缓存完成，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
            }
            LOG.info("专项分组查无数据，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
        }
    }


    public void updatePartSpecialGroupCacheInRedis(SpecialGroup po, TrainingEventType eventType) {
        if(redisUtil.getEntriesFromMap(CACHE_KEY_SPECIAL_GROUP_ALL) != null) {
            Map<String, SpecialGroup> poMap = Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_SPECIAL_GROUP_ALL)).orElse(new HashMap<>());
            switch(eventType) {
                case TRAINING_EVENT_SPECIAL_GROUP_INSERT:
                case TRAINING_EVENT_SPECIAL_GROUP_UPDATE:
                    poMap.put(po.getId(), po);
                    break;
                case TRAINING_EVENT_SPECIAL_GROUP_REMOVE:
                    Arrays.stream(po.getStringIds().split(",")).forEach(id -> poMap.remove(id));
                    break;
                case TRAINING_EVENT_CHAPTER_SECTION_INCREASE_TOTAL:
                    SpecialGroup specialGroup = poMap.get(po.getStringIds());
                    if(specialGroup != null) {
                        specialGroup.setTotal(specialGroup.getTotal() + po.getIncrease());
                        poMap.put(specialGroup.getId(), specialGroup);
                    }
                    break;
            }
            redisUtil.putInMap(CACHE_KEY_SPECIAL_GROUP_ALL, poMap);
        }
    }

    public void initAllChapterSectionCacheInRedis(boolean forceReload) {
        if(forceReload || redisUtil.getEntriesFromMap(CACHE_KEY_CHAPTER_SECTION_ALL) == null) {
            LOG.info("开始处理章节缓存");
            long start = System.currentTimeMillis() / 1000;
            List<ChapterSection> poList = chapterSectionService.selectByCondition();
            if(!poList.isEmpty()) {
                Map<String, ChapterSection> poMap = new HashMap<>();
                poList.forEach(po -> poMap.put(po.getId(), po));
                redisUtil.putInMap(CACHE_KEY_CHAPTER_SECTION_ALL, poMap);
                LOG.info("处理章节缓存完成，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
            }
            LOG.info("章节查无数据，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
        }
    }

   public void updatePartChapterSectionCacheInRedis(ChapterSection po, TrainingEventType eventType) {
        if(redisUtil.getEntriesFromMap(CACHE_KEY_CHAPTER_SECTION_ALL) != null) {
            Map<String, ChapterSection> poMap = Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_CHAPTER_SECTION_ALL)).orElse(new HashMap<>());
            switch(eventType) {
                case TRAINING_EVENT_CHAPTER_SECTION_INSERT:
                case TRAINING_EVENT_CHAPTER_SECTION_UPDATE:
                    poMap.put(po.getId(), po);
                    break;
                case TRAINING_EVENT_CHAPTER_SECTION_REMOVE:
                    Arrays.stream(po.getStringIds().split(",")).forEach(id -> poMap.remove(id));
                    break;
                case TRAINING_EVENT_CHAPTER_SECTION_INCREASE_TOTAL:
                    ChapterSection chapterSection = poMap.get(po.getStringIds());
                    if(chapterSection != null) {
                        chapterSection.setTotal(chapterSection.getTotal() + po.getIncrease());
                        poMap.put(chapterSection.getId(), chapterSection);
                    }
                    break;
            }
            redisUtil.putInMap(CACHE_KEY_CHAPTER_SECTION_ALL, poMap);
        }
    }

    public ChapterSection getChapterSectionById(String sectionId) {
        return (ChapterSection)Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_CHAPTER_SECTION_ALL)).get().get(sectionId);
    }

    public void initAllIconGroupCacheInRedis(boolean forceReload) {
        if(forceReload || redisUtil.getEntriesFromMap(CACHE_KEY_ICON_GROUP_ALL) == null) {
            LOG.info("开始处理图标分组缓存");
            long start = System.currentTimeMillis() / 1000;
            List<IconGroup> poList = iconGroupService.selectByCondition();
            if(!poList.isEmpty()) {
                Map<String, IconGroup> poMap = new HashMap<>();
                poList.forEach(po -> poMap.put(po.getId(), po));
                redisUtil.putInMap(CACHE_KEY_ICON_GROUP_ALL, poMap);
                LOG.info("处理图标分组缓存完成，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
            }
            LOG.info("图标分组查无数据，耗时 [{}]", Xiruo.secondToDesc(System.currentTimeMillis() / 1000 - start));
        }
    }

    public void updatePartIconGroupCacheInRedis(IconGroup po, TrainingEventType eventType) {
        if(redisUtil.getEntriesFromMap(CACHE_KEY_ICON_GROUP_ALL) != null) {
            Map<String, IconGroup> poMap = Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_ICON_GROUP_ALL)).orElse(new HashMap<>());
            switch(eventType) {
                case TRAINING_EVENT_ICON_GROUP_INSERT:
                case TRAINING_EVENT_ICON_GROUP_UPDATE:
                    poMap.put(po.getId(), po);
                    break;
                case TRAINING_EVENT_ICON_GROUP_REMOVE:
                    Arrays.stream(po.getStringIds().split(",")).forEach(id -> poMap.remove(id));
                    break;
                case TRAINING_EVENT_ICON_GROUP_INCREASE_TOTAL:
                    poMap.values().forEach(value -> {
                        if(po.getIdPath().startsWith(value.getId())) {
                            value.setTotal(value.getTotal() + po.getIncrease());
                        }
                    });
                    break;
            }
            redisUtil.putInMap(CACHE_KEY_ICON_GROUP_ALL, poMap);
        }
    }

    // 试卷
    public void saveExamPaper(ExamTrack examPaper) {
        Map<String, ExamTrack> examMap = Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_EXAM_TRACK_ALL)).orElse(new HashMap<>());
        examMap.put(examPaper.getUserId(), examPaper);
        redisUtil.putInMap(CACHE_KEY_EXAM_TRACK_ALL, examMap);
    }

    public ExamTrack getExamPaperByUserId(String userId) {
        return (ExamTrack)Optional.ofNullable(redisUtil.getEntriesFromMap(CACHE_KEY_EXAM_TRACK_ALL)).get().get(userId);
    }

    public void scanExamPaper() {
        Map<String, ExamTrack> examMap;
        if((examMap = redisUtil.getEntriesFromMap(CACHE_KEY_EXAM_TRACK_ALL)) != null) {
            examMap.values().forEach(examTrack -> {
                if(examTrack.isDirty()) {
                    examTrackDispatcher.shuffleExam(examTrack);
                }
                ExamRuleMixDto rule = getExamRuleBySubject(examTrack.getSubject());
                //判断是否超时
                if(examTrack.getStartTime().getTime() + rule.getExamNumRule().getExamMinutes() * 60 * 1000 > System.currentTimeMillis()) {
                    examMap.remove(examTrack.getUserId());
                }
            });
            redisUtil.putInMap(CACHE_KEY_EXAM_TRACK_ALL, examMap);
        }
    }

    public void answerExamPaper(ExamAnswerTrack answer) {
        ExamTrack examPaper = getExamPaperByUserId(answer.getUserId());
        ExamAnswerTrack track = examPaper.getExamAnswerTrackMap().get(answer.getId());
        Exercise exercise = getExerciseById(track.getExerciseId());
        track.setAnswer(answer.getAnswer());
        track.setThatTime(new Date());
        track.setDirty(true);
        examPaper.setDirty(true);
        examPaper.setUncomplete(examPaper.getUncomplete() - 1);
        boolean right = track.getAnswer().equals(track.getRightAnswer());
        if(right) {
            examPaper.setScore(examPaper.getScore() + exercise.getScore());
        } else {
            switch(getRightState(exercise, answer)) {
                case TrainingConstants.RIGHT_STATE_ALL:
                    examPaper.setScore(examPaper.getScore() + exercise.getScore());
                    break;
                case TrainingConstants.RIGHT_STATE_PART:
                    examPaper.setScore(examPaper.getScore() + Math.round(exercise.getScore() / 2));
                    break;
                case TrainingConstants.RIGHT_STATE_NO:
                    break;
            }
        }
        saveExamPaper(examPaper);
    }

    private int getRightState(Exercise exercise, ExamAnswerTrack answer) {
        //字典exercise_type题型，值为2表示多选题
        if(exercise.getType().equals("2")) {
            AtomicInteger errorCounter = new AtomicInteger(0);
            Stream.of(answer.getAnswer()).forEach(c -> {
                if(exercise.getAnswer().indexOf(c) < 0) {
                    errorCounter.incrementAndGet();
                }
            });
            if(errorCounter.get() == 0) {
                //比如正确答案ABC，输入BAC，这种是正确
                if(exercise.getAnswer().length() == answer.getAnswer().length()) {
                    return TrainingConstants.RIGHT_STATE_ALL;
                } else {
                    return TrainingConstants.RIGHT_STATE_PART;
                }
            }
        }
        return TrainingConstants.RIGHT_STATE_NO;
    }
}
