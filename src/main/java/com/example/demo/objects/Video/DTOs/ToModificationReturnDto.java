package com.example.demo.objects.Video.DTOs;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class ToModificationReturnDto {
    private List<String> palavrasModificadas;
    private byte[] file;

    public ToModificationReturnDto(List<String> palavrasModificadas, byte[] file) {
        this.palavrasModificadas = palavrasModificadas;
        this.file = file;
    }

    public List<String> getPalavrasModificadas() {
        return palavrasModificadas;
    }

    public void setPalavrasModificadas(List<String> palavrasModificadas) {
        this.palavrasModificadas = palavrasModificadas;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
    public byte[] getFile(MultipartFile file) throws IOException {
        return file.getBytes();
    }
}
