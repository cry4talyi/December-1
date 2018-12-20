package cn.com.taiji.service.impl;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.Comment;
import cn.com.taiji.domain.UserInfo;
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

    /**
     * @Author 郭兆龙
     * @Date 2018/12/18
     * 用于讨论组跳转
     * @return
     */
    public List<ChatTeam> chat(){
        List<ChatTeam>list=new ArrayList<ChatTeam>();
        List<ChatTeam>list2=new ArrayList<ChatTeam>();
        list.addAll(this.chatTeamRepository.findAll());

        for (int i=0;i<list.size();i++){
            if (list.get(i).getIsexist()==1){
                list2.add(list.get(i));
            }

        }

        return list2;
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
        chatTeam.setIsexist(1);
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
    /*
    *
     * @Author 伊文斌 and 胡玉浩
     * @Description //TODO
     * @Date 10:05 2018/12/20
     * @Param
     * @return
     * 删除帖子
     **/
    
    @Transactional
    public boolean deleteBlog(String bid){

        try{
            Blog blog = blogRepository.findById(Long.parseLong(bid)).get();
            blog.setIsexist(0);
            blogRepository.saveAndFlush(blog);
        } catch (Exception e){
            return false;
        }

        return true;
    }

    /*
    *
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 15:29 2018/12/19
     * @Param
     * @return
     * 在讨论组页面显示所有讨论组成员
     **/
    public List<UserInfo> chatTeamFindUser(String name){
        List<UserInfo> list2 =new ArrayList<>();
        List<UserInfo> list =chatTeamRepository.findByCname(name).getUserInfos();
        for (UserInfo u:list
                ) {
            list2.add(u);
        }
        return list2;
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
