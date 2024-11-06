package App.Producer;

import App.Dto.ResponseMedia;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespostaProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void integrar (ResponseMedia response) throws JsonProcessingException {
        amqpTemplate.convertAndSend(
                "aluno-response-media-queue",
                "aluno-response-media-rout-key",
                //response
                objectMapper.writeValueAsString(response)
        );
    }

}
