package com.rm.springboot.project.repository;

import com.rm.springboot.project.entity.Comments;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    Optional<Comments> findByNewsIdAndId(Long newsId, Long commentId);

    List<Comments> findByNewsId(Long newsId);

    List<Comments> findByNewsId(Long newsId, Pageable pageable);

    void deleteByNewsIdAndId(Long newsId, Long commentId);
}
