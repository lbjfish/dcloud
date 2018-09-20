package com.sida.dcloud.training.model;

import com.sida.dcloud.training.po.Exercise;
import com.sida.dcloud.xdomain.model.AggregateRoot;

public class ExerciseModel extends AggregateRoot<Exercise> {

    public ExerciseModel(Exercise po) {
        super(po);
    }
}
