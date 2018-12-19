package cn.com.taiji.repository;

import cn.com.taiji.domain.ChatTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChatTeamRepository extends JpaRepository<ChatTeam,Long> {


    public ChatTeam findByCname(String name);

    public ChatTeam findByIsexist(int is);
}
