package App.Consumer;

import App.Facede.AlunoFacede;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SucessoConsumer {

    private final AlunoFacede facede;

    public SucessoConsumer(AlunoFacede facede) {
        this.facede = facede;
    }

    @RabbitListener(queues = {"alunoaluno-response-sucesso-queue"})
    public void receive(@Payload Message message) {
        System.out.println("Message " + message + "  " + LocalDateTime.now());
        String payload = String.valueOf(message.getPayload());

        facede.SucessoProcesso(payload);
    }

}
