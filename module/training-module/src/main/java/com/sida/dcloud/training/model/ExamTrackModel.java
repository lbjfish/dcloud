package com.sida.dcloud.training.model;

import com.sida.dcloud.training.po.ExamTrack;
import com.sida.dcloud.training.uow.ExamTrackUnitOfWork;
import com.sida.dcloud.xdomain.model.AggregateRoot;

public class ExamTrackModel extends AggregateRoot<ExamTrack> {
    public static void shuffleModel(ExamTrackModel model) {
        ((ExamTrackUnitOfWork)model.getUnitOfWork()).shuffleExam(model.getPo());
    }

    public ExamTrackModel(ExamTrack po) {
        super(po);
    }

}
