package cn.com.taiji.service.impl;

import cn.com.taiji.domain.*;
import cn.com.taiji.domain.Blog;
import cn.com.taiji.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {

    


    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private ChatTeamRepository chatTeamRepository;
    @Autowired
    private CommentReposity commentReposity;
    @Autowired
    private PostRepository postRepository;

    /**
     * @Author 郭兆龙
     * @Date 2018/12/18
     * 用于讨论组跳转
     * @return
     */
    public List<ChatTeam> chat(){
        return this.chatTeamRepository.findAll();
    }

    /**
     * @Author 郭兆龙
     * @Date 2018/12/18
     * 跳转到博客
     */
    public List<Post> blog(){
        return this.postRepository.findAll();
    }

    /**
     * @Author 郭兆龙
     * @Date 2018/12/18
     * 用于新增讨论组
     */
    public void addChat(String cname){
        ChatTeam chatTeam = new ChatTeam();
        chatTeam.setIsexist(0);
        chatTeam.setCname(cname);
        chatTeamRepository.save(chatTeam);
        chatTeamRepository.saveAndFlush(chatTeam);
    }




    /*
    *
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 11:28 2018/12/18
     * @Param
     * @return
     **/
//    public List<Blog> chatFindBname(String name){
//        System.out.println(chatTeamRepository.findByCname(name).getBlogs());
//        return chatTeamRepository.findByCname(name).getBlogs();
//    }
    public List<Blog> chatFindBname(String name){
        List<Blog> list2 =new ArrayList<>();
        List<Blog> list =chatTeamRepository.findByCname(name).getBlogs();
        for (Blog b:list
                ) {
            if (b.getIsexist()==1){
                list2.add(b);
            }
        }
        return list2;
    }
    
    //删除帖子
    public boolean deleteBlog(String bid){
        System.err.println("bid是："+bid);
        try{
            Blog blog = blogRepository.findById(Long.parseLong(bid)).get();
            blog.setIsexist(0);
            blogRepository.saveAndFlush(blog);
        } catch (Exception e){
            return false;
        }
        
        return true;
    }


    @Transactional
    public void saveBlog(Blog blog,String chatteam,String username){

        UserInfo byUsername = userInfoRepository.findByUsername(username);
        ChatTeam byCname = chatTeamRepository.findByCname(chatteam);
        System.out.println(byCname.getCname());
        System.out.println(byCname.getCname());
        byUsername.getBlogs().add(blog);
        byCname.getBlogs().add(blog);
        blog.setChatTeam(byCname);
        blog.setUserInfo(byUsername);
        userInfoRepository.saveAndFlush(byUsername);
        chatTeamRepository.saveAndFlush(byCname);
        blogRepository.save(blog);
    }




    
}
