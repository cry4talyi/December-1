package cn.com.taiji.init;

import cn.com.taiji.domain.*;
import cn.com.taiji.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iandtop on 2018/12/11.
 */
@Service
public class DataInit {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ChatTeamRepository chatTeamRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentReposity commentReposity;

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    public void dataInit() {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        adminRole.setDescription("管理员");
        roleRepository.save(adminRole);

        Role normalRole = new Role();
        normalRole.setName("ROLE_USER");
        normalRole.setDescription("普通用户");
        roleRepository.save(normalRole);

//********************************************


        Blog blog1 = new Blog();
        blog1.setBcontext("aaaaaaa");
        blog1.setBtittle("the first");
        blog1.setIsexist(1);

        Blog blog2 = new Blog();
        blog2.setBcontext("bbbbbb");
        blog2.setBtittle("the second");
        blog2.setIsexist(1);


        List list1 = new ArrayList();
        list1.add(blog1);
        list1.add(blog2);
        ChatTeam chatTeam1 = new ChatTeam();
        chatTeam1.setCname("diyizu");
        chatTeam1.setBlogs(list1);


        List chatlist = new ArrayList();
        chatlist.add(chatTeam1);

        List<Blog> list2 = new ArrayList<>();
        list2.add(blog2);

        ChatTeam chatTeam2 = new ChatTeam();
        chatTeam2.setCname("dierzu");
        chatTeam2.setBlogs(list2);



    // ****************************



        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        roles.add(normalRole);
        UserInfo admin = new UserInfo();
        admin.setPassword(passwordEncoder.encode("1"));
        admin.setUsername("a");
        admin.setRoles(roles);
        admin.setChatTeams(chatlist );


        roles = new ArrayList<>();
        roles.add(normalRole);
        UserInfo user = new UserInfo();
        user.setPassword(passwordEncoder.encode("1"));
        user.setUsername("u");
        user.setRoles(roles);


        blog1.setUserInfo(user);
        blog2.setUserInfo(admin);
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(user);
        admin.setBlogs(list1);
        chatTeam1.setUserInfos(userInfos);


        blog1.setChatTeam(chatTeam1);


        Comment c1 = new Comment();
        c1.setBlog(blog1);
        c1.setStatement("woshishei");
        c1.setUserInfo(user);
        List commentlist = new ArrayList();
        commentlist.add(c1);
        blog1.setComments(commentlist);
        user.setComments(commentlist);


       Post post = new Post();
       post.setBtittle("zheshitiezi");
       post.setBcontext("zheshitiezizhengwen");
       post.setUserInfo(user);
       post.setComments(commentlist);


        userInfoRepository.save(admin);
        userInfoRepository.save(user);


        postRepository.save(post);


        chatTeamRepository.save(chatTeam1);
        chatTeamRepository.save(chatTeam2);

        blogRepository.save(blog1);
        blogRepository.save(blog2);

        commentReposity.save(c1);

        //permission.
        roles = new ArrayList<>();
        roles.add(normalRole);
        Permission permission1 = new Permission();
        permission1.setUrl("/helloUser");
        permission1.setName("普通用户URL");
        permission1.setDescription("普通用户的访问路径");
        permission1.setRoles(roles);
        permissionRepository.save(permission1);






        Permission permission2 = new Permission();
        permission2.setUrl("/helloAdmin");
        permission2.setName("管理员URL");
        permission2.setDescription("管理员的访问路径");
        List<Role> roles2 = new ArrayList<>();
        roles2.add(adminRole);
        permission2.setRoles(roles2);
        permissionRepository.save(permission2);


        Permission permission3 = new Permission();
        permission3.setUrl("/ct/diyizu/add");
        permission3.setName("第一组的添加");
        permission3.setDescription("管理员的访问路径");
        List<Role> roles3 = new ArrayList<>();
        roles3.add(adminRole);
        permission3.setRoles(roles3);
        permissionRepository.save(permission3);
    }
}
