package com.sida.dcloud.event.po;

import com.alibaba.fastjson.JSONObject;
import com.sida.xiruo.xframework.util.UUIDGenerate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Document(collection = "essnapshots")
public class Snapshot implements Serializable {
    public static Snapshot makeEsSnapshot(com.alibaba.fastjson.JSONArray array) {
        Snapshot snapshot = new Snapshot();
        snapshot.snapshotId = UUIDGenerate.getNextId();
        snapshot.entityCount = array.size();
        snapshot.snapshotTime = System.currentTimeMillis();
        array.stream().forEach(obj -> {
            snapshot.dataList.add(((JSONObject)obj).getJSONObject("eventObj").getJSONObject("event").getString("eventData"));
        });
        return snapshot;
    }

//    @Id
    private String snapshotId;
    private int entityCount;
    private long snapshotTime;
    private List<String> dataList = new ArrayList<String>();

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public int getEntityCount() {
        return entityCount;
    }

    public void setEntityCount(int entityCount) {
        this.entityCount = entityCount;
    }

    public long getSnapshotTime() {
        return snapshotTime;
    }

    public void setSnapshotTime(long snapshotTime) {
        this.snapshotTime = snapshotTime;
    }
}