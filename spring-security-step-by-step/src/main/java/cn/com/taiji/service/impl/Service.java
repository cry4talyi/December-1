package cn.com.taiji.service.impl;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.Comment;
import cn.com.taiji.domain.UserInfo;
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
    * 作者：李伟函
    * 返回帖子对象
    * */
    public Blog displayBlog(String id){
//        return blogRepository.findById(Long.parseLong(id)).get();
        System.out.println(blogRepository.findById(Long.valueOf(id)).get());
        return blogRepository.findById(Long.valueOf(id)).get();
    }

    /*
     * 作者：李伟函
     * 保存回复
     * */
    public void saveReply(String textarea,String username,String bid){
        Blog blog= blogRepository.findById(Long.valueOf(bid)).get();
        UserInfo userInfo= userInfoRepository.findByUsername(username);
        Comment comment= new Comment();
        comment.setStatement(textarea);
        comment.setUserInfo(userInfo);
        comment.setBlog(blog);
        commentReposity.saveAndFlush(comment);

    }


}
