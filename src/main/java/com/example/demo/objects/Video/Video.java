package com.example.demo.objects.Video;


import jakarta.persistence.*;

@Entity
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVideo")
    private Long id;

    @Column(name = "videoNome")
    private String name;

    @Lob
    @Column(name = "video", columnDefinition = "LONGBLOB")
    private byte[] video;

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

