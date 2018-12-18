package cn.com.taiji.controller;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.ChatTeam;
import cn.com.taiji.domain.Comment;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.repository.BlogRepository;
import cn.com.taiji.repository.ChatTeamRepository;
import cn.com.taiji.repository.CommentReposity;
import cn.com.taiji.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iandtop on 2018/12/11.
 */
@Controller
public class HelloController {
    @GetMapping("/hello")
    public String getWelcomeMsg() {
        return "Hello,World";
    }

    @Autowired
    UserInfoRepository userInfoRepository;



    @Autowired
    private ChatTeamRepository chatTeamRepository;


    private Logger logger= LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/helloUser")
//    @PreAuthorize("hasAnyRole('USER')")
    public String getHelloUser() {
        return "Hello,User";
    }

    @GetMapping("/helloAdmin")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public String getHelloAdmin() {
        return "Hello,Admin";
    }

    @RequestMapping("getAll")
    public String getall(){
        List list =userInfoRepository.findAll();
        return list.toString();
    }


//    @RequestMapping("ct/{name}")
//    public String blog(@PathVariable("name")     String  name){
//        System.out.println(name);
//
//        return chatTeamRepository.findByCname( name).toString();
//    }



//    @RequestMapping("ct/{name}/{id}")
//    public String blog(@PathVariable("name")     String  name/*Model model*/){
//        System.out.println(name);
//       // ChatTeam chatTeam =
//
//        //model.addAttribute("blogs",chatTeam.getBlogs());
//        return chatTeamRepository.findByCname( name).toString();
//    }

    @Autowired
    BlogRepository blogRepository;

//    @RequestMapping("ct/{name}/{id}")
//    public String get(@PathVariable("id")     String id/*Model model*/){
//
//
//
//        System.out.println(id);
//        // ChatTeam chatTeam =
//
//        //model.addAttribute("blogs",chatTeam.getBlogs());
////        return chatTeamRepository.findByCname( name).toString();
//        return blogRepository.findById(Long.parseLong(id)).get().toString();
//    }
//@GetMapping("/ct/chatteam/commit")
//public String add(String chatteam,String btittle,String bcontext){
//    System.err.println(chatteam);
//    System.err.println(btittle);
//    System.err.println(bcontext);
//    return "aaa";
//}

    @Autowired
    CommentReposity commentReposity;

//    @RequestMapping("test")
//    public ChatTeam   test1(){
//        ChatTeam ct = null;
//        //System.out.println(chatTeamRepository.findById(8l).get());
//        try{
//             ct = chatTeamRepository.findById(5l).get();
//
//        }catch (Exception e){
//            if(ct==null){
//                ChatTeam chatTeam = new ChatTeam();
//                chatTeam.setCname("查找失败");
//                return chatTeam;
//            }
//        }
//
//        return ct;
//    }
    @RequestMapping("test")
    public UserInfo test1() {
      return   commentReposity.findById(9l).get().getUserInfo();

    }



























    List<Blog> blogs2;//临时存储此人的博客信息

    @GetMapping("/hellouser")
    public String getHelloAdmin(Model model, HttpServletRequest request) {
        String pageNum = request.getParameter("name");
        System.out.println(pageNum);//用户名
        logger.debug("用户名 is {}"+pageNum);
        UserInfo us= userInfoRepository.findByUsername(pageNum);
        System.out.println(us);//用户信息
        System.out.println("这里"+us.getPosts());//用户的博客
        List<ChatTeam> chatTeam= us.getChatTeams();
        System.out.println(chatTeam);//用户的参加的组
        List<String>  cnames=new ArrayList<>();
        for (ChatTeam cname:chatTeam) {
            String cname1=cname.getCname();
            cnames.add(cname1);
        }
        List<Blog> blogs1=us.getBlogs();
        System.out.println(blogs1);//用户的帖子
        blogs2=us.getBlogs();
        List<String>  bnames=new ArrayList<>();
        for (Blog blog:blogs1) {
            String bname=blog.getBtittle();
            bnames.add(bname);
        }

        List<Comment> commentlist = us.getComments();
        List<String> blogcom=new ArrayList<>();
        for (Comment com:commentlist) {
            Blog bloglist = com.getBlog();
            blogcom.add(bloglist.getBtittle());
            System.out.println(bloglist.getUserInfo());//用户评论的帖子的作者信息
            System.out.println(bloglist.getComments());//用户评论的帖子的评论

        }


        model.addAttribute("username",pageNum);
        model.addAttribute("cnames",cnames);
        model.addAttribute("bnames",bnames);
        model.addAttribute("blogcom",blogcom);
        // model.addAttribute("username",us.getUsername());
        // model.addAttribute("blog",us.getChatTeams());
        return "personal";
    }
    //查看博客详细信息
    @GetMapping("/helloBlog")
    public String getHelloBlog(Model model,HttpServletRequest request) {
        String pageNum = request.getParameter("name");
        System.out.println(blogs2);
        for (Blog blog:blogs2) {
            if (blog.getBtittle().equals(pageNum)){
                model.addAttribute("bcontext",blog);

                return "blog";
            }

        }
        return "personal";
    }
    @GetMapping("/byeBlog")
    public String getByeBlog(Model model,HttpServletRequest request) {
        String pageNum = request.getParameter("name");
        System.out.println(pageNum);
        return "personal";
    }
}
