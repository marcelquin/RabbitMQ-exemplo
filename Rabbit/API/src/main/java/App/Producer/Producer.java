package App.Producer;

import App.DTO.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Producer {

    @Autowired
    private AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void integrar (Response response) throws JsonProcessingException {
        amqpTemplate.convertAndSend(
                "aluno-request",
                "aluno-request-key",
                //response
                objectMapper.writeValueAsString(response)
        );
    }



}
