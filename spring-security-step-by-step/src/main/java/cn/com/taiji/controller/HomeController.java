package cn.com.taiji.controller;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.ChatTeam;
import cn.com.taiji.repository.BlogRepository;
import cn.com.taiji.repository.ChatTeamRepository;

import cn.com.taiji.service.impl.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * Created by iandtop on 2018/12/11.
 */
@Controller
public class HomeController {
    @Autowired
    Service service;

    @GetMapping({"","/","/index"})
    public String index(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)) {
            model.addAttribute("name","anonymous");
        }else {
            User user = (User)principal;
            model.addAttribute("name",user.getUsername());
        }
        return "/index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    /**
     * @author 郭兆龙
     * 跳转到讨论组页面
     * @param model
     * @return
     */
    @GetMapping("/ct")
    public String chatTeam(Model model){
        model.addAttribute("chat",service.chat());
        return "ct";
    }

    /**
     * @Author 郭兆龙
     * 跳转到博客
     */
    @GetMapping("/blog")
    public String blog(Model model){
        model.addAttribute("post",service.blog());
        return "blog";
    }

}
