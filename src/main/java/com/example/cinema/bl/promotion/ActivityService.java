package com.example.cinema.bl.promotion;

import com.example.cinema.vo.ActivityForm;
import com.example.cinema.vo.ResponseVO;

public interface ActivityService {
    
    ResponseVO publishActivity(ActivityForm activityForm);

    ResponseVO getActivities();

    //根据id删除活动
    ResponseVO deleteActivityById(int id);

}
