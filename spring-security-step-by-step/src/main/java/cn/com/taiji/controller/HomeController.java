package cn.com.taiji.controller;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.Post;
import cn.com.taiji.service.impl.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "chatTeam";
    }

    /**
     * @Author 郭兆龙
     * @Date 2018/12/18
     * 跳转到博客
     */
    @GetMapping("/blog")
    public String blog(Model model){
        model.addAttribute("post",service.blog());
        return "blogsView";
    }

    /**
     * @Author 郭兆龙
     * @Date 2018/12/18
     * 用于跳转到新增讨论组页面
     */
    @GetMapping("/toAddChat")
    public String addChatTeam(){
        return "addChat";
    }
    /**
     * @Author 郭兆龙
     * @Date 2018/12/18
     * 用于新增讨论组
     */
    @PostMapping("/addChat")
    public String addChat(String name){
        service.addChat(name);
        return "redirect:/manage";
    }
    /**
     * @Author 郭兆龙
     * @Date 2018/12/19
     * 用于从博客列表进入到详细页面
     */
    @GetMapping("/ct/blog/{bid}")
    public String post(Model model,@PathVariable(name = "bid")String bid ){
        model.addAttribute("posts",service.toPost(Long.valueOf(bid)));
        return "post";
    }

    /**
     * @Author 郭兆龙
     * @Date 2018/12/20
     * 用于管理员显示所有讨论组界面
     */
    @GetMapping("/manage")
    public String backChatTeam(Model model){
        model.addAttribute("chat",service.chat());
        return "manageChatTeam";
    }
    /**
     * @Author 郭兆龙
     * @Date 2018/12/20
     * 用于管理员删除讨论组
     */
    @DeleteMapping("/manage/delete")
    public ResponseEntity<String> deleteBlog(String cid){
        System.err.println(cid);
        boolean b = service.deleteChatTeam(cid);
        if (b){
            return ResponseEntity.ok("删除成功");
        }else {
            return ResponseEntity.status(404).body("删除失败");
        }
    }

    /**
     * @Author 郭兆龙
     * @Date 2018/12/20
     * 用于管理员删除博客
     */
    @DeleteMapping("/manage/post/delete")
    public ResponseEntity<String> deletePost(String bid){
        System.err.println(bid);
        boolean b = service.deletePost(bid);
        if (b){
            return ResponseEntity.ok("删除成功");
        }else {
            return ResponseEntity.status(404).body("删除失败");
        }
    }
    /**
     * @Author 郭兆龙
     * @Date 2018/12/20
     * 用于管理员跳转进博客
     */
    @GetMapping("/manage/blog")
    public String toManageBlog(Model model){
        model.addAttribute("post",service.blog());
        return "back-PostViews";
    }

    /**
     * @Author 郭兆龙
     * @Date 2018/12/20
     * 用于管理员跳转到新增博客的页面
     */
    @GetMapping("/toAddBlog")
    public String toAddBlog(){
        return "addPost";
    }

    /**
     * @Author 郭兆龙
     * @Date 2018/12/20
     * 用于管理员新增博客功能
     */
    @PostMapping("/manage/blog/commit")
    public String blogCommit(String btittle,String bcontext,Model model){
        Post post = new Post();
        post.setBtittle(btittle);
        post.setBcontext(bcontext);
        post.setIsexist(1);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=null;
        if("anonymousUser".equals(principal)) {
            model.addAttribute("name","anonymous");
        }else {
            user = (User)principal;
            model.addAttribute("name",user.getUsername());
        }
        service.savePost(post,user.getUsername());
        return "redirect:/manage/blog";
    }
















    /*
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 11:19 2018/12/18
     * @Param
     * @return
     **/
    //跳转到前端讨论组详情页面
    @RequestMapping("ct/{name}")
    public String blog(@PathVariable("name") String name, Model model) {
        logger.info("姓名为{}",name);
        model.addAttribute("chatname",name);//获取讨论组名字
        model.addAttribute("blogs",service.chatFindBname(name) );
        model.addAttribute("userName",service.chatTeamFindUser(name));
        return "repo";
    }
    
    /*
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 11:19 2018/12/18
     * @Param
     * @return
     **/
    //跳转到后端讨论组详情页面
    @RequestMapping("manage/{name}")
    public String backBlog(@PathVariable("name") String name, Model model) {
        logger.info("111111姓名为{}",name);
        model.addAttribute("chatname",name);//获取讨论组名字
        model.addAttribute("blogs",service.chatFindBname(name) );
        model.addAttribute("userName",service.chatFindBname(name));
        return "back-repo";
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




    /*
    * 作者：李伟函
    *
    * */
    @RequestMapping("ct/{name}/{bid}")
    public String blog(@PathVariable("name") String name, @PathVariable("bid") String bid, Model
            model) {
        model.addAttribute("blog",service.displayBlog(bid));

        return "blog";
    }

    /*
    * 作者：李伟函
    * */
    @RequestMapping("/addReply/{username}/{bid}")
    public  String reply(@PathVariable("username") String username, @PathVariable("bid") String bid,String ttt,Model model){
        System.out.println(ttt);
        System.out.println(username);
        System.out.println(bid);
        service.saveReply(ttt,username,bid);
        Blog blog=service.displayBlog(bid);
        String name =blog.getChatTeam().getCid().toString();

        String url = "redirect:"+"/ct/"+name+"/"+bid;
       return url;
    }







}
