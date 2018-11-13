package com.sida.dcloud.training.uow;

import com.sida.dcloud.training.model.ExamTrackModel;
import com.sida.dcloud.training.po.ExamAnswerTrack;
import com.sida.dcloud.training.po.ExamTrack;
import com.sida.dcloud.training.service.ExamAnswerTrackService;
import com.sida.dcloud.training.service.ExamTrackService;
import com.sida.dcloud.xdomain.common.SpringBeansManager;
import com.sida.dcloud.xdomain.repositories.impl.mybatis.MybatisUnitOfWork;
import com.sida.xiruo.xframework.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ExamTrackUnitOfWork extends MybatisUnitOfWork<ExamTrackModel> {
    private static final Logger LOG = LoggerFactory.getLogger(ExamTrackUnitOfWork.class);

    private ExamAnswerTrackService examAnswerTrackService;

    public ExamTrackUnitOfWork() {
        examAnswerTrackService =
                (ExamAnswerTrackService)SpringBeansManager.getInstance().getServiceBean(ExamAnswerTrack.class.getSimpleName());
    }

    @Override
    @Transactional
    public void insertNew() {
        newEntities.forEach(entity -> {
            service.insert(entity.getPo());
            insertAnswerTrack(entity.getPo());
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDirty() {
        dirtyEntities.forEach(entity -> {
            ((ExamTrackService)service).completeExam(entity.getPo());
        });
    }

    private void insertAnswerTrack(ExamTrack po) {
        Map<String, ExamAnswerTrack> map = po.getExamAnswerTrackMap();
        if(map != null && !map.isEmpty()) {
            AtomicInteger atomicInt = new AtomicInteger(0);
            List<ExamAnswerTrack> list = new ArrayList<>(map.values());
            list.forEach(answerTrack -> {
                answerTrack.setId(UUIDGenerate.getNextId());
                answerTrack.setExamId(po.getId());
                answerTrack.setSequence(atomicInt.incrementAndGet());
            });
            examAnswerTrackService.insertPos(list);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void shuffleExam(ExamTrack po) {
        ((ExamTrackService)service).shuffleExam(po);
        Map<String, ExamAnswerTrack> map = po.getExamAnswerTrackMap();
        if(map != null && !map.isEmpty()) {
            map.values().forEach(answer -> {
                examAnswerTrackService.updateByPrimaryKey(answer);
            });
        }
    }
}
