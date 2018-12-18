package cn.com.taiji.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString
public class Blog {

    @Id
    @GeneratedValue
    private Long bid;

    private String sub;
    private String btittle;

    @Column(columnDefinition = "TEXT")
    private String bcontext;

    @Column(columnDefinition = "INT default 0")
    private int isexist;

    @ManyToOne
    @JoinColumn(name = "USERINFO_ID")
    @JsonIgnore
    private UserInfo userInfo;


   @OneToMany(mappedBy = "blog")
   @JsonIgnore
   private List<Comment> comments;


   @ManyToOne
    @JoinColumn(name = "CHATTEAM_ID")
    @JsonIgnore
    private ChatTeam chatTeam;


    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getBtittle() {
        return btittle;
    }

    public void setBtittle(String btittle) {
        this.btittle = btittle;
    }

    public String getBcontext() {
        return bcontext;
    }

    public void setBcontext(String bcontext) {
        this.bcontext = bcontext;
    }

    public int getIsexist() {
        return isexist;
    }

    public void setIsexist(int isexist) {
        this.isexist = isexist;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public ChatTeam getChatTeam() {
        return chatTeam;
    }

    public void setChatTeam(ChatTeam chatTeam) {
        this.chatTeam = chatTeam;
    }
}
