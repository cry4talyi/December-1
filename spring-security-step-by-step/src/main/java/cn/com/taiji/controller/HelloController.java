package cn.com.taiji.controller;

import cn.com.taiji.domain.*;
import cn.com.taiji.repository.*;
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

    /*
    *
     * @Author 胡玉浩
     * @Description //TODO
     * @Date 9:50 2018/12/20
     * @Param
     * @return
     * 删除页面
     **/
    
    @DeleteMapping("/manage/chat/delete")
    public ResponseEntity<String> deleteBlog( String bid){

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
    @RequestMapping(value = "/manage/chat/setHead",method = RequestMethod.POST)
    public void setHead( String uid){
        service.setAsManager( uid);

    }
//    @GetMapping("manage")
//    public String aaaaaa(){
//
//        return "权限足够";
//    }






}
