package com.example.cinema.controller.user;

import com.example.cinema.blImpl.user.AccountServiceImpl;
import com.example.cinema.config.InterceptorConfiguration;
import com.example.cinema.po.User;
import com.example.cinema.po.historyItem;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController()
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm, HttpSession session) {
        UserVO user = accountService.login(userForm);
        if (user == null) {
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        //注册session
        session.setAttribute(InterceptorConfiguration.SESSION_KEY, userForm);
        return ResponseVO.buildSuccess(user);
    }

    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserForm userForm) {
        return accountService.registerAccount(userForm);
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session) {
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }

    @GetMapping("/get/history")
    public ResponseVO getHistory(@RequestParam int userId) {
        return accountService.getHistoryByUserId(userId);
    }

    @PostMapping("insert/history")
    public ResponseVO insertHistory(@RequestBody historyItem history) {
        return accountService.insertHistory(history);
    }

    @GetMapping("/get/user")
    public ResponseVO getUserById(@RequestParam int userId) {
        return accountService.getUserById(userId);
    }

    @PostMapping("/update/user")
    public ResponseVO updateUser(@RequestBody User user) {
        return accountService.updateUser(user);
    }

    @GetMapping("/get/all/user")
    public ResponseVO getAllUser() {
        return accountService.getAllUser();
    }

    @PostMapping("/delete/user")
    public ResponseVO deleteUser(@RequestParam int userId) {
        return accountService.deleteUser(userId);
    }

}
