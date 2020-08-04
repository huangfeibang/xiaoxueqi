package com.example.cinema.blImpl.management.hall;

import com.example.cinema.po.Hall;


public interface HallServiceForBl {
    /**
     * 搜索影厅
     * @param id
     * @return
     */
    Hall getHallById(int id);
}
