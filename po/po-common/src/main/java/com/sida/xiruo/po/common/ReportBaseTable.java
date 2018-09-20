package com.sida.xiruo.po.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportBaseTable {
    private List<ReportBaseTable.ReportTitleItem> dataTitle;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ReportBaseTable addColumn(String id, String name) {
        if (dataTitle == null) {
            dataTitle = new ArrayList<>();
        }
        dataTitle.add(new ReportBaseTable.ReportTitleItem(id, name));
        return this;
    }
    public List<ReportTitleItem> getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(List<ReportTitleItem> dataTitle) {
        this.dataTitle = dataTitle;
    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class ReportTitleItem implements Serializable {
        private String id;
        private String name;

        public ReportTitleItem(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
