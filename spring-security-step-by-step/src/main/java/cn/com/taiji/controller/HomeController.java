package cn.com.taiji.controller;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.ChatTeam;
import cn.com.taiji.repository.BlogRepository;
import cn.com.taiji.repository.ChatTeamRepository;
import cn.com.taiji.service.impl.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.taiji.service.impl.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by iandtop on 2018/12/11.
 */
@Controller
public class HomeController {

    @Autowired
    private Service service;

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping({"", "/", "/index"})
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
     * @Date 2018/12/18
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
     * @Date 2018/12/18
     * 跳转到博客
     */
    @GetMapping("/blog")
    public String blog(Model model){
        model.addAttribute("post",service.blog());
        return "blog";
    }

    /**
     * @Author 郭兆龙
     * @Date 2018/12/18
     * 用于新增讨论组
     */
    @GetMapping("/toAddChat")
    public String addChatTeam(){
        return "addChat";
    }

    @PostMapping("/addChat")
    public String addChat(String name){
        service.addChat(name);
        return "redirect:/ct";
    }






    /*
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 11:19 2018/12/18
     * @Param
     * @return
     **/
    //跳转到讨论组详情页面
    @RequestMapping("ct/{name}")
    public String blog(@PathVariable("name") String name, Model model) {
        logger.info("姓名为{}",name);
        List<Blog> blogs = service.chatFindBname(name);
        List<Blog> blogList = new ArrayList<>();
        for (Blog b:blogs
             ) {
            if(b.getIsexist()==1){
                blogList.add(b);
            }
        }
        model.addAttribute("blogs",blogList );
        return "repo";
    }


    @GetMapping("ct/{name}/add")
    public String add(@PathVariable("name") String name,Model model){
        model.addAttribute("name",name);

        return "add";
    }


    @PostMapping("/ct/chatteam/commit")
    public String add(String chatteam,String btittle,String bcontext,Model model){

        Blog blog = new Blog();
        blog.setIsexist(1);
        blog.setBcontext(bcontext);
        blog.setBtittle(btittle);


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=null;
        if("anonymousUser".equals(principal)) {
            model.addAttribute("name","anonymous");
        }else {
            user = (User)principal;
            model.addAttribute("name",user.getUsername());
        }

        service.saveBlog(blog,chatteam,user.getUsername());

        String url = "redirect:"+"/ct/"+chatteam;
        return url;
    }





}
