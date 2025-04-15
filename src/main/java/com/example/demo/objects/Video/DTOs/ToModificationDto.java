package com.example.demo.objects.Video.DTOs;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class ToModificationDto {
    private String palavraEscolhida;
    private List<String> palavrasParaModificacao;
    private MultipartFile file;



    public String getPalavraEscolhida() {
        return palavraEscolhida;
    }

    public void setPalavraEscolhida(String palavraEscolhida) {
        this.palavraEscolhida = palavraEscolhida;
    }

    public List<String> getPalavrasParaModificacao() {
        return palavrasParaModificacao;
    }

    public void setPalavrasParaModificacao(List<String> palavrasParaModificacao) {
        this.palavrasParaModificacao = palavrasParaModificacao;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public byte[] getFile(MultipartFile file) throws IOException {
        return file.getBytes();
    }

}
