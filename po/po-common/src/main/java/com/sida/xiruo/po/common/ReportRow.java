package com.sida.xiruo.po.common;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 *
 * @author Tung
 * @version 1.0
 * @date 3/20/2018.
 * @update
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportRow implements Serializable {
    @JsonIgnore
    private Map<String,Serializable> row = new LinkedHashMap<>();

    private ReportRow() {
    }

    public static ReportRow build(String id) {
        ReportRow _row = new ReportRow();
        _row.row.put("id", id);
        return _row;
    }

    @JsonAnyGetter
    public Map<String,Serializable> getRow(){
        return  row;
    }

    @JsonAnySetter
    public void setRow(String key, Object value) {
        if (value instanceof Serializable)
            this.row.put(key, (Serializable) value);
    }


    public ReportRow put(String key, Serializable value) {
        row.put(key, value);
        return this;
    }


}
