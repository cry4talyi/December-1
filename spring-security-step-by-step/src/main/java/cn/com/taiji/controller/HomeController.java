package cn.com.taiji.controller;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.ChatTeam;
import cn.com.taiji.repository.BlogRepository;
import cn.com.taiji.repository.ChatTeamRepository;
import cn.com.taiji.service.impl.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    private Service service;
    
    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @GetMapping({"", "/", "/index"})
    public String index(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)) {
            model.addAttribute("name", "anonymous");
        } else {
            User user = (User) principal;
            model.addAttribute("name", user.getUsername());
        }
        return "/index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "/login";
    }
    
    
    /*
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 11:19 2018/12/18
     * @Param
     * @return
     **/
    //
    @RequestMapping("ct/{name}")
    public String blog(@PathVariable("name") String name, Model model) {
        logger.info("姓名为{}",name);
        model.addAttribute("blogs", service.chatFindBname(name));
        return "repo";
    }
}
