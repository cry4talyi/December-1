package cn.com.taiji.controller;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.ChatTeam;
import cn.com.taiji.repository.BlogRepository;
import cn.com.taiji.repository.ChatTeamRepository;
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

    @Autowired
    private ChatTeamRepository chatTeamRepository;

    @RequestMapping("ct/{name}")
    public String blog(@PathVariable("name")     String  name,Model model){
        System.out.println(name);
        ChatTeam chatTeam = chatTeamRepository.findByCname( name);
        model.addAttribute("name",name);
       model.addAttribute("blogs",chatTeam.getBlogs());
        return "repo";
    }


    @Autowired
    BlogRepository blogRepository;

    @RequestMapping("ct/{name}/{bid}")
    public String blog(@PathVariable("name") String name, @PathVariable("bid") String bid, Model
            model) {
        model.addAttribute("blog",blogRepository.findById(Long.parseLong(bid)).get());

        return "bid";
    }



    @RequestMapping("ct/{name}/add")
    public String toadd(@PathVariable(name = "name") String name,Model model){
        model.addAttribute("name",name);
        System.out.println(name);
        return "add";
    }

    /**
     *
     */


    @GetMapping("/ct/chatteam/commit")
    public String add(String chatteam,String btittle,String bcontext){
        Blog blog = new Blog();
        blog.setBtittle(btittle);
        blog.setBcontext(bcontext);
        blog.setIsexits(1);
         ChatTeam byCname = chatTeamRepository.findByCname(chatteam);
        byCname.getBlogs().add(blog);
        blogRepository.save(blog);
        chatTeamRepository.saveAndFlush(byCname);
        String url = "redirect:"+"/ct/"+chatteam;
        return url;
    }





}
