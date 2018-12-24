package cn.com.taiji.service.impl;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.Comment;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.domain.*;
import cn.com.taiji.domain.Blog;
import cn.com.taiji.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
     * 用于显示所有讨论组
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
     * 跳转到博客列表
     */
    public List<Post> blog(){
        List<Post>list1 = new ArrayList<Post>();
        List<Post>list2 = new ArrayList<Post>();
        list1.addAll(this.postRepository.findAll());
        for (Post post:list1
             ) {
            if (post.getIsexist()==1){
                list2.add(post);
            }
        }
        return list2;
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

        chatTeamRepository.saveAndFlush(chatTeam);
    }
    /**
     * @Author 郭兆龙
     * @Date 2018/12/19
     * 用于跳转进博客的详细页面
     */
    public Post toPost(Long bid){
        return postRepository.findByBid(bid);
    }
    /**
     * @Author 郭兆龙
     * @Date 2018/12/20
     * 用于管理员删除讨论组
     */
    @Transactional
    public boolean deleteChatTeam(String cid){

        try{
            ChatTeam chatTeam = chatTeamRepository.findByCid(Long.parseLong(cid));
            chatTeam.setIsexist(0);
            chatTeamRepository.saveAndFlush(chatTeam);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * @Author 郭兆龙
     * @Date 2018/12/20
     * 用于管理员删除博客
     */
    @Transactional
    public boolean deletePost(String bid){

        try{
            Post post=postRepository.findByBid(Long.parseLong(bid));
            post.setIsexist(0);
            postRepository.saveAndFlush(post);
        } catch (Exception e){
            return false;
        }
        return true;
    }
    /**
     * @Author 郭兆龙
     * @Date 2018/12/20
     * 用于管理员新增博客
     */
    @Transactional
    public void savePost(Post post,String userName){
        UserInfo byUserName = userInfoRepository.findByUsername(userName);
        byUserName.getPosts().add(post);
        post.setUserInfo(byUserName);
        userInfoRepository.saveAndFlush(byUserName);
        postRepository.saveAndFlush(post);
    }






    /*
    *
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 11:28 2018/12/18
     * @Param
     * @return
     * 查找一个讨论组的所有博客
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
     * @Author 伊文彬 and 胡玉浩
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
            for (Comment c:blog.getComments()
                 ) {
                c.setIsexist(0);
                commentReposity.saveAndFlush(c);
            }
            blog.setChatTeam(null);
            blog.setUserInfo(null);
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

        byUsername.getBlogs().add(blog);
        byCname.getBlogs().add(blog);
        blog.setChatTeam(byCname);
        blog.setUserInfo(byUsername);
        userInfoRepository.saveAndFlush(byUsername);
        chatTeamRepository.saveAndFlush(byCname);
        blogRepository.save(blog);
    }

    /*
     *搜索框进行模糊查询博客，并进行排序，分组操作
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 15:11 2018/12/20
     * @Param
     * @return
     **/
    public List<Post> findNameLike(String keyword){
        
        Specification<Post> spec=new Specification<Post>() {
            /**
             *  Predicate :封装了单个的查询条件
             *  root:查询对象的属性封装
             *  CriteriaQuery<?> criteriaQuery：我们要执行的查询中的各个部分的信息：select ，from
             *  CriteriaBuilder criteriaBuilder:查询条件的构造器。定义不同的查询条件
             */
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("btittle"),"%"+keyword+"%");
            }
        };
        // 排序--ASC:升序 --DESC:降序
        Sort sort = new Sort(Sort.Direction.ASC,"bid");
        //设置页码
        Pageable pageable = new PageRequest(0, 2, sort);
        Page<Post> page= postRepository.findAll(spec,pageable);
        ///每一页显示的信息条数
        System.out.println("每一页显示的信息条数:" + page.getTotalPages());
        //一共多少数据
        System.out.println("一共多少数据:" + page.getTotalElements());
        List<Post> list=page.getContent();
        for (Post p:list
             ) {
            System.out.println(p);
        }
        return list;
    }

   
}
