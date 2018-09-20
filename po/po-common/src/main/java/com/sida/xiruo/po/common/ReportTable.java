package com.sida.xiruo.po.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Tung
 * @version 1.0
 * @date 3/14/2018.
 * @update
 */
public class ReportTable implements Serializable {
    private List<ReportTitleItem> dataTitle;
    private List<ReportRow> data;

    public List<ReportTitleItem> getDataTitle() {
        return dataTitle;
    }

    public List<ReportRow> getData() {
        return data;
    }

    public void setData(List<ReportRow> data) {
        this.data = data;
    }

    public ReportTable addColumn(String id, String name) {
        if (dataTitle == null) {
            dataTitle = new ArrayList<>();
        }
        dataTitle.add(new ReportTitleItem(id, name));
        return this;
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
