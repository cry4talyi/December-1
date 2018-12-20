package cn.com.taiji.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@ToString
public class ChatTeam {

    @Id
    @GeneratedValue
    private Long cid;

    private String sub;
    private String cname;

//    @OneToMany
//    @JoinColumn(name = "chatid")
//    @JsonIgnore
//    List<Blog> blogs;

    @OneToMany(mappedBy = "chatTeam")
   // @JoinColumn(name = "chatid")
    @JsonIgnore
    List<Blog> blogs;

    @Column(columnDefinition = "INT default 1")
    private int isexist;



    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(name = "charuser"
            , joinColumns = {@JoinColumn(name = "cid")}
            , inverseJoinColumns = {@JoinColumn(name = "uid")})
    @JsonIgnore
    //@Fetch(FetchMode.SELECT)
    List<UserInfo> userInfos;


    @Override
    public String toString() {
        return "ChatTeam{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +

                '}';
    }

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

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }
}
