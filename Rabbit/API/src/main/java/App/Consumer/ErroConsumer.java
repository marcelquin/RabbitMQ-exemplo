package App.Consumer;

import App.Facede.AlunoFacede;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ErroConsumer {

    private final AlunoFacede facede;

    public ErroConsumer(AlunoFacede facede) {
        this.facede = facede;
    }

    @RabbitListener(queues = {"aluno-response-erro-queue"})
    public void receive(@Payload Message message) {
        System.out.println("Message " + message + "  " + LocalDateTime.now());
        String payload = String.valueOf(message.getPayload());

        facede.ErroProcesso(payload);
    }

}
