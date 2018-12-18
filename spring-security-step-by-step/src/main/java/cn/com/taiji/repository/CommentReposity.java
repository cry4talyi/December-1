package cn.com.taiji.repository;

import cn.com.taiji.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReposity  extends JpaRepository<Comment, Long> {
}
