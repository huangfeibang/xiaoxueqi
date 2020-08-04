package com.example.cinema.controller.router;

import com.example.cinema.vo.CommentMessage;
import com.example.cinema.vo.MovieVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class ViewController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/index")
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/signUp")
    public String getSignUp() {
        return "signUp";
    }

    @RequestMapping(value = "/admin/movie/manage")
    public String getAdminMovieManage() {
        return "adminMovieManage";
    }

    @RequestMapping(value = "/admin/session/manage")
    public String getAdminSessionManage() {
        return "adminScheduleManage";
    }

    @RequestMapping(value = "/admin/cinema/manage")
    public String getAdminCinemaManage() {
        return "adminCinemaManage";
    }

    @RequestMapping(value = "/admin/promotion/manage")
    public String getAdminPromotionManage() {
        return "adminPromotionManage";
    }

    @RequestMapping(value = "/admin/cinema/statistic")
    public String getAdminCinemaStatistic() {
        return "adminCinemaStatistic";
    }

    @RequestMapping(value = "/admin/movieDetail")
    public String getAdminMovieDetail(@RequestParam int id) {
        return "adminMovieDetail";
    }

    @RequestMapping(value = "/user/home")
    public ModelAndView getUserHome() {
        Map<String, Object> map = new HashMap<>();
        List<MovieVO> movies = jdbcTemplate.query("select * from movie where status=0", new RowMapper<MovieVO>() {
            @Override
            public MovieVO mapRow(ResultSet resultSet, int i) throws SQLException {
                MovieVO movieVO = new MovieVO();

                movieVO.setId(resultSet.getInt("id"));
                movieVO.setPosterUrl(resultSet.getString("poster_url"));
                movieVO.setName(resultSet.getString("name"));
                return movieVO;
            }
        });
        map.put("movies", movies);

        List<MovieVO> movies2 = jdbcTemplate.query("select * from movie where status=1", new RowMapper<MovieVO>() {
            @Override
            public MovieVO mapRow(ResultSet resultSet, int i) throws SQLException {
                MovieVO movieVO = new MovieVO();

                movieVO.setId(resultSet.getInt("id"));
                movieVO.setPosterUrl(resultSet.getString("poster_url"));
                movieVO.setName(resultSet.getString("name"));
                return movieVO;
            }
        });
        map.put("movies2", movies2);

        return new ModelAndView("userHome", map);
    }

    @RequestMapping(value = "/user/buy")
    public String getUserBuy() {
        return "userBuy";
    }

    @RequestMapping(value = "/user/movieDetail")
    public ModelAndView getUserMovieDetail(@RequestParam int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        List<CommentMessage> commentMessages = jdbcTemplate.query("select comment.*, username from comment, user where user.id=comment.userId and movieid=?", new RowMapper<CommentMessage>() {
            @Override
            public CommentMessage mapRow(ResultSet resultSet, int i) throws SQLException {
                CommentMessage commentMessage = new CommentMessage();
                commentMessage.setUserName(resultSet.getString("username"));
                commentMessage.setContent(resultSet.getString("content"));
                commentMessage.setDate(resultSet.getDate("Date"));
                commentMessage.setMark(resultSet.getLong("mark"));
                return commentMessage;
            }
        },id);
        map.put("comments", commentMessages);
        return new ModelAndView("userMovieDetail", map);
    }

    @RequestMapping(value = "/user/movieDetail/buy")
    public String getUserMovieBuy(@RequestParam int id) {
        return "userMovieBuy";
    }

    @RequestMapping(value = "/user/cost")
    public String getUserCost() {
        return "userCost";
    }

    @RequestMapping(value = "/user/movie")
    public String getUserMovie() {
        return "userMovie";
    }

    @RequestMapping(value = "/user/member")
    public String getUserMember() {
        return "userMember";
    }

    @RequestMapping(value = "/admin/vip/manage")
    public String getAdminVipManage() {
        return "adminVipManage";
    }

    @RequestMapping(value = "/admin/refund/manage")
    public String getAdminRefundManage() {
        return "adminRefundManage";
    }

    @RequestMapping(value = "/user/info")
    public String getUserInfo() {
        return "userInfo";
    }

    @RequestMapping(value = "/boss/manage")
    public String getBossManage() {
        return "bossManage";
    }


    @RequestMapping("/user/movie/more")
    public ModelAndView getMore(@RequestParam int id) {

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return new ModelAndView("userComment", map);

    }
}
