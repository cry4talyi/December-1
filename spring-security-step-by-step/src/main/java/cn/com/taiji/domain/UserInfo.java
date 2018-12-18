package cn.com.taiji.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by iandtop on 2018/12/11.
 */
@Entity
@Data

public class UserInfo {
    @Id
    @GeneratedValue
    private long uid;

    private String sub;

    private String username;

    private String password;

    //EAGER,立即从数据库中进行加载数据;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserRole"
            , joinColumns = {@JoinColumn(name = "uid")}
            , inverseJoinColumns = {@JoinColumn(name = "rid")})
    private List<Role> roles;


    @ManyToMany(mappedBy = "userInfos")
//    @JoinTable(name = "USERCHAT"
//            , joinColumns = {@JoinColumn(name = "uid")}
//            , inverseJoinColumns = {@JoinColumn(name = "cid")})
//    @Fetch(FetchMode.SELECT)
   // @ManyToMany()
    //@Fetch(FetchMode.SELECT)
    @JsonIgnore
    private List<ChatTeam> ChatTeams;


    @OneToMany(mappedBy = "userInfo")
    //@JoinColumn(name = "BLOD_ID")
    @JsonIgnore
    private List<Blog> blogs;


    @OneToMany(mappedBy = "userInfo")
    //@JoinColumn(name = "BLOD_ID")
    @JsonIgnore
    private List<Post> Posts;


    @OneToMany(mappedBy = "userInfo")
    @JsonIgnore
    private List<Comment> comments;


    @Override
    public String toString() {
        return "UserInfo{" +
                "uid=" + uid +
                ", username='" + username ;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<ChatTeam> getChatTeams() {
        return ChatTeams;
    }

    public void setChatTeams(List<ChatTeam> chatTeams) {
        ChatTeams = chatTeams;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<Post> getPosts() {
        return Posts;
    }

    public void setPosts(List<Post> posts) {
        Posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
