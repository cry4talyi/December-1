package cn.com.taiji.service.impl;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.ChatTeam;
import cn.com.taiji.domain.Post;
import cn.com.taiji.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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




}
