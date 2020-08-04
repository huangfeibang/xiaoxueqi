package com.example.cinema.data.promotion;

import com.example.cinema.po.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository(value = "activityMapper")
public interface ActivityMapper {

    int insertActivity(Activity activity);

    int insertActivityAndMovie(@Param("activityId") int activityId, @Param("movieId") List<Integer> movieId);

    List<Activity> selectActivities();

    List<Activity> selectActivitiesByMovie(int movieId);

    Activity selectById(int id);

    List<Activity> selectActivitiesWithoutMovie();

    void deleteActivityById(int id);

}
