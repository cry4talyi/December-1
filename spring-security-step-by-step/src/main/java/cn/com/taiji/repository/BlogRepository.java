package cn.com.taiji.repository;

import cn.com.taiji.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BlogRepository extends JpaRepository<Blog, Long> {

}
