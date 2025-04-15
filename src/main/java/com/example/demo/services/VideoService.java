package com.example.demo.services;
import javax.sound.sampled.AudioSystem;

import com.example.demo.objects.Video.DTOs.ToModificationDto;
import com.example.demo.objects.Video.DTOs.VideoDto;
import com.example.demo.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    private static final String MODEL_PATH = "C:\\vosk-model-pt-fb-v0.1.1-20220516_2113";
//     static final String MODEL_PATH = "C:\\Users\\Pedro\\Documents\\vosk-model-small-pt-0.3";

    public Integer postVideo(byte[] video){return  videoRepository.postVideo(video);}

    public byte[] getVideoById(Long id){return videoRepository.getVideoById(id);}


    public static VideoDto transcribeVideo(MultipartFile videoFile) throws Exception {
        // 1. Criar arquivos temporários
        File tempVideoFile = File.createTempFile("video-", ".mp4");
        File tempAudioFile = File.createTempFile("audio-", ".wav");

        try {
            // 2. Salvar o arquivo recebido
            videoFile.transferTo(tempVideoFile);

            // 3. Extrair áudio do vídeo
            extractAudio(tempVideoFile.getAbsolutePath(), tempAudioFile.getAbsolutePath());

            // 4. Transcrever áudio para texto
            VideoDto retorno = new VideoDto();
            retorno.setTranscricao(transcribeAudio(tempAudioFile.getAbsolutePath()));
            System.out.println(retorno.getTranscricao());
            return retorno;

        } catch (Exception e){
            System.out.println(e);
            return null;
        }finally {
            // 5. Limpar arquivos temporários
            tempVideoFile.delete();
//            tempAudioFile.delete();
        }
    }
    private static void extractAudio(String videoPath, String audioPath) throws IOException, InterruptedException {
        System.out.println(videoPath);
        System.out.println(audioPath);

        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",
                "-i", videoPath,
                "-af", "speechnorm=e=6:r=0.0001:l=1", // Filtros de qualidade
                "-ar", "16000",                                       // Taxa de amostragem obrigatória
                "-ac", "1",                                           // Mono
                "-sample_fmt", "s16",                                 // Formato PCM
                "-y",                                                 // Sobrescrever
                audioPath
        );

        // Redirecione erros para debug
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // Leia a saída do FFmpeg para debug
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[FFmpeg] " + line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("FFmpeg falhou com código: " + exitCode);
        }
    }



    private static String transcribeAudio(String audioFilePath) throws Exception {
        File audioFile = new File(audioFilePath);

        // Configuração otimizada do buffer
        int bufferSize = Math.min((int)audioFile.length(), 1048576); // 1MB ou tamanho do arquivo

        LibVosk.setLogLevel(LogLevel.DEBUG);

        try (Model model = new Model(MODEL_PATH);
             InputStream ais = new BufferedInputStream(new FileInputStream(audioFile), 2 * bufferSize);
             Recognizer recognizer = new Recognizer(model, 16000)) {

            // Configurações importantes para melhor captura
            recognizer.setWords(true);
//            recognizer.setPartialWords(true);  // Captura palavras incompletas
            recognizer.setMaxAlternatives(10);  // Aumenta alternativas

            byte[] buffer = new byte[bufferSize];
            StringBuilder result = new StringBuilder();
            long lastUpdateTime = System.currentTimeMillis();
            int bytesRead;

            while ((bytesRead = ais.read(buffer)) != -1) {
                if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                    // Extrai o texto garantindo codificação UTF-8
                    String partialResult = new String(
                            recognizer.getResult().getBytes(StandardCharsets.ISO_8859_1),
                            StandardCharsets.UTF_8
                    );
                    result.append(partialResult);
                }
            }

            String finalResult = new String(
                    recognizer.getFinalResult().getBytes(StandardCharsets.ISO_8859_1),
                    StandardCharsets.UTF_8
            );
            result.append(finalResult);

            return extractTextFromJson(result.toString());

        }

            // Processamento final garantindo acentos



    }

    private static String extractTextFromJson(String jsonResult) {
        // Exemplo de saída: {"text": "isto é um teste"}
        int start = jsonResult.indexOf("\"text\" : \"") + 10;
        int end = jsonResult.indexOf("\"", start);
        return jsonResult.substring(start, end);
    }
    Recognizer createOptimizedRecognizer(Model model) throws IOException {
        // Parâmetros otimizados para PT-BR
        return new Recognizer(
                model,
                16000
                // Palavras desconhecidas
                // Ajuste fino
                // Normalização de texto
        );
    }

    public List<String> transcribeVideoAndReturnTranscripstionsModificade(ToModificationDto toModificationDto,
                                                                            VideoDto videoDto){
        List<String> retorno = new ArrayList<String>();
        String palavraParaModificacao = toModificationDto.getPalavraEscolhida();
        int posicao = videoDto.getTranscricao().indexOf(palavraParaModificacao);

        if(posicao != -1){

            for(int i = 0; i < toModificationDto.getPalavrasParaModificacao().size(); i++){
                String modificacao = videoDto.getTranscricao().replace(palavraParaModificacao,
                                     toModificationDto.getPalavrasParaModificacao().get(i));


                 retorno.add(modificacao);

            }
        }


        return retorno;
    }
}
