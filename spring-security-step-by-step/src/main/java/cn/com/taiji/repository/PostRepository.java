package cn.com.taiji.repository;


import cn.com.taiji.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {


    public Post findByBid(Long bid);

}
