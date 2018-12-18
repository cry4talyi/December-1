package cn.com.taiji.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
//@ToString
public class Comment {

    @GeneratedValue
    @Id
    private Long id;

    private String statement;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "BLOG_ID")
    private Blog blog;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "POST_ID")
    private Post post;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "USERINFO_ID")
    private UserInfo userInfo;

    @Column(columnDefinition = "INT default 0")
    private int isexist;


    private String sub;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public int getIsexist() {
        return isexist;
    }

    public void setIsexist(int isexist) {
        this.isexist = isexist;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", statement='" + statement + '\'' +

                ", userInfo=" + userInfo +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
