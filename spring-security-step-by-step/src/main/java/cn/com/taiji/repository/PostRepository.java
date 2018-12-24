package cn.com.taiji.repository;


import cn.com.taiji.domain.Post;
import javafx.geometry.Pos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>,JpaSpecificationExecutor<Post> {


    public Post findByBid(Long bid);
    
    

//    @Query(value = "select bid from Post where btittle like '%?1%'")
    List<Post> findByBtittleLike(String keyword);
    

}
