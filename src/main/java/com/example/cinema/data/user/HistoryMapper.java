package com.example.cinema.data.user;
import com.example.cinema.po.historyItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Mapper
@Repository(value = "historyMapper")
public interface HistoryMapper {
      List<historyItem> getHistoryByUserId(int userId);

     void insertHistory(historyItem history);
}
