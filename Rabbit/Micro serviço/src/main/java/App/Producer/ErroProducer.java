package App.Producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ErroProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void gerarResposta(String mensagem){
        amqpTemplate.convertAndSend(
                "aluno-response-erro-queue",
                "aluno-response-erro-queue-rout-key",
                mensagem);
    }

    /*public void gerarResposta(String mensagem){
        amqpTemplate.convertAndSend(
                "aluno-response-erro-queue",
                "aluno-response-erro-queue-rout-key",
                mensagem);
    }*/

}
