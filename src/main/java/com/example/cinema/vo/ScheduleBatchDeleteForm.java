package com.example.cinema.vo;

import java.util.List;

public class ScheduleBatchDeleteForm {
    /**
     * 要删除的排片信息id列表
     */
    private List<Integer> scheduleIdList;

    public List<Integer> getScheduleIdList() {
        return scheduleIdList;
    }

    public void setScheduleIdList(List<Integer> scheduleIdList) {
        this.scheduleIdList = scheduleIdList;
    }
}
