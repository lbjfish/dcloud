package com.sida.dcloud.system.thread;

import com.sida.dcloud.system.common.dto.CollectorControl;
import com.sida.dcloud.system.common.pool.DriverPool;
import com.sida.dcloud.system.util.PageUtils;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

/**
 * Created by xieguopei on 2017/8/9.
 */
public class CollectorThread extends RecursiveTask<Map> {
    private DriverPool driverPool;
    private Map<String, String> paramMap;
    private Map<String, Object> paramObject;
    private Set<String> keys;
    private Integer begin;
    private Integer size;

    public Map<String, Object> getParamObject() {
        return paramObject;
    }

    public void setParamObject(Map<String, Object> paramObject) {
        this.paramObject = paramObject;
    }

    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public DriverPool getDriverPool() {
        return driverPool;
    }

    public void setDriverPool(DriverPool driverPool) {
        this.driverPool = driverPool;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public CollectorThread(Integer begin, Integer size, DriverPool driverPool, Map<String, String> paramMap) {
        this.driverPool = driverPool;
        this.paramMap = paramMap;
        this.begin = begin;
        this.size = size;

        setKeys(paramMap.keySet());
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Map compute() {
        List<CollectorControl> controls = Lists.newLinkedList();
        CollectorThread collectorThread;
        String code;
        String url;
        int dump;

        dump = getBegin().intValue() + 1;

        code = (String) getKeys().toArray()[dump];

        url = getParamMap().get(code);

        try {
            controls = PageUtils.getControlMes(url);

            getParamObject().put(
                    code,
                    controls
            );

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(dump < getSize()) {
            collectorThread = new CollectorThread(
                    dump + 1,
                    getSize(),
                    getDriverPool(),
                    getParamMap()
            );

            collectorThread.fork();

            getParamObject().putAll(collectorThread.join());
        }

        return getParamObject();
    }
}
