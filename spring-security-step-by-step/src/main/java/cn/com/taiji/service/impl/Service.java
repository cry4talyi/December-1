package cn.com.taiji.service.impl;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

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


    /*
    *
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 11:28 2018/12/18
     * @Param
     * @return
     **/
    
    public List<Blog> chatFindBname(String name){
        System.out.println(chatTeamRepository.findByCname(name).getBlogs());
        return chatTeamRepository.findByCname(name).getBlogs();
    }
}
