package com.example.demo.repository;

import com.example.demo.objects.Video.Video;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO video(video) VALUE(?)", nativeQuery = true)
     Integer postVideo(byte[] video);

     @Query(value = "SELECT video FROM video WHERE id_video = (?)", nativeQuery = true)
     byte[] getVideoById(Long id);


}
