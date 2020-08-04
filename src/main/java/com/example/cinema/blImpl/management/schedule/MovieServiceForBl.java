package com.example.cinema.blImpl.management.schedule;

import com.example.cinema.po.Movie;
import com.example.cinema.vo.MovieVO;

public interface MovieServiceForBl {
    /**
     * 根据id查找电影
     * @param id
     * @return
     */
    Movie getMovieById(int id);
}
