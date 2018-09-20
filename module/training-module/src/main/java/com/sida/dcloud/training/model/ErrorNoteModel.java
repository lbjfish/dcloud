package com.sida.dcloud.training.model;

import com.sida.dcloud.training.po.ErrorNote;
import com.sida.dcloud.xdomain.model.AggregateRoot;

public class ErrorNoteModel extends AggregateRoot<ErrorNote> {
    public ErrorNoteModel(ErrorNote po) {
        super(po);
    }
}
