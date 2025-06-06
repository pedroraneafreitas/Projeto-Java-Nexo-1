package com.example.demo.controllers;

import com.example.demo.objects.Video.DTOs.ToModificationDto;
import com.example.demo.objects.Video.DTOs.ToModificationReturnDto;
import com.example.demo.objects.Video.DTOs.VideoDto;
import com.example.demo.services.VideoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService service;

    @CrossOrigin(origins = "*")
    @GetMapping("/video/{id}")
    public ResponseEntity<byte[]> getVideoById(@PathVariable  Long id){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(service.getVideoById(id));
    }

    @CrossOrigin("*")
    @PostMapping(value  = "/video")
    public ResponseEntity<Integer> postVideo(@org.jetbrains.annotations.NotNull
                                            @RequestBody  MultipartFile file) throws IOException {
        return ResponseEntity.ok(service.postVideo(file.getBytes()));
    }

    @CrossOrigin("*")
    @PostMapping(value = "/video/describe")
    public ResponseEntity<VideoDto> transcribeVideo(@org.jetbrains.annotations.NotNull
                                  @RequestBody  MultipartFile file) throws Exception {
        VideoDto dto = service.transcribeVideo(file);
        return ResponseEntity.ok(dto);

    }



    @CrossOrigin("*")
    @PostMapping("/video/describe/modify")
    public ResponseEntity<ToModificationReturnDto> transcribeVideoAndReturnTranscripstionsModificade( ToModificationDto toModificationDto) throws Exception {
        byte[] videoEmbyte = toModificationDto.getFile().getBytes();
        VideoDto videoDto = service.transcribeVideo(toModificationDto.getFile());
        ToModificationReturnDto retorno = new ToModificationReturnDto(service.transcribeVideoAndReturnTranscripstionsModificade(toModificationDto,
                videoDto),
                videoEmbyte);
        return ResponseEntity.ok(retorno);
    }

}
