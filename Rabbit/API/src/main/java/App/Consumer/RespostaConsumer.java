package App.Consumer;

import App.DTO.ResponseMedia;
import App.Entity.Aluno;
import App.Repository.AlunoRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class RespostaConsumer {

    private final AlunoRepository alunoRepository;

    public RespostaConsumer(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }


    @RabbitListener(queues = {"aluno-response-media-queue"})
    public void receive(@Payload Message message) throws IOException {
        String payload = (String) message.getPayload();
        ObjectMapper mapper =new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ResponseMedia dado = mapper.readValue(payload, ResponseMedia.class);
        System.out.println(dado);
        Aluno aluno = alunoRepository.findBymatricola(dado.matricola()).get();
        System.out.println(aluno.getNome()+" "+aluno.getMatricola());
        aluno.setMedia(dado.media());
        aluno.setStatus(dado.status());
        System.out.println(aluno.getMedia());
        System.out.println(aluno.getStatus());
        alunoRepository.save(aluno);
    }

}
