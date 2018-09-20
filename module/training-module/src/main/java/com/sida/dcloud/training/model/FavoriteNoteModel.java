package com.sida.dcloud.training.model;

import com.sida.dcloud.training.po.FavoriteNote;
import com.sida.dcloud.xdomain.model.AggregateRoot;

public class FavoriteNoteModel extends AggregateRoot<FavoriteNote> {
    public FavoriteNoteModel(FavoriteNote po) {
        super(po);
    }
}
