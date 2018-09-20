package com.sida.dcloud.training.model;

import com.sida.dcloud.training.dto.ExamRuleMixDto;
import com.sida.dcloud.xdomain.model.AggregateRoot;

public class ExamRuleMixModel extends AggregateRoot<ExamRuleMixDto> {

    public ExamRuleMixModel(ExamRuleMixDto po) {
        super(po);
    }
}
