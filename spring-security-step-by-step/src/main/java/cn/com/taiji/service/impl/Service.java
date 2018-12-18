package cn.com.taiji.service.impl;

import cn.com.taiji.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

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


}
