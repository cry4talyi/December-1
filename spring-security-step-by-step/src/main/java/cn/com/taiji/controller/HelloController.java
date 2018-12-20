package cn.com.taiji.controller;

import cn.com.taiji.domain.Blog;
import cn.com.taiji.domain.ChatTeam;
import cn.com.taiji.domain.Comment;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.repository.BlogRepository;
import cn.com.taiji.repository.ChatTeamRepository;
import cn.com.taiji.repository.CommentReposity;
import cn.com.taiji.repository.UserInfoRepository;
import cn.com.taiji.service.impl.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iandtop on 2018/12/11.
 */
@RestController
public class HelloController {
@Autowired
    Service service;

    @DeleteMapping("/ct/chat/delete")
    public ResponseEntity<String> deleteBlog( String bid){
        System.err.println(bid);
        boolean b = service.deleteBlog(bid);
        if (b){
            return ResponseEntity.ok("删除成功");
        }else {
        return ResponseEntity.status(404).body("删除失败");
        }
    }
    
    /*
     *
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 16:24 2018/12/19
     * @Param
     * @return
     * 设置讨论组成员为组长
     **/
    @RequestMapping(value = "/ct/chat/setHead",method = RequestMethod.POST)
    public ResponseEntity<String> setHead( String bid){
    
    
       return null;
    }




}
