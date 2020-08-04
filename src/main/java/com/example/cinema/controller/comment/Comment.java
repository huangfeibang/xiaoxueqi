package com.example.cinema.controller.comment;


import com.example.cinema.config.InterceptorConfiguration;
import com.example.cinema.vo.UserForm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class Comment {

    final
    JdbcTemplate jdbcTemplate;

    public Comment(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/comment/submit")
    public String CommentPut(@RequestParam Map<String, Object> data,
                                   HttpSession session){

        //UserForm userForm = session.setAttribute(InterceptorConfiguration.SESSION_KEY,userForm);
        UserForm userForm = (UserForm)session.getAttribute(InterceptorConfiguration.SESSION_KEY);
        String username = userForm.getUsername();
        List<Map<String ,Object>> resultSet = jdbcTemplate.queryForList("select id from user where username=?",username);

        Integer userId = Integer.parseInt(resultSet.get(0).get("id").toString());
        System.out.println(userId);
        int rs = jdbcTemplate.update("insert into comment(userId, date, content, mark, movieid)" +
                        " values (?, current_timestamp(), ?,?,?)",
                userId,
                data.get("content").toString(),
                Integer.parseInt(data.get("mark").toString())*10,
                data.get("id").toString()
                );


        String url = "/user/movieDetail?id="+data.get("id").toString();
        return "redirect:"+url;
    }
}
