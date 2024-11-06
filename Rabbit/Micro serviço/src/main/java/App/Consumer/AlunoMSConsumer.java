package App.Consumer;

import App.Dto.Response;
import App.Dto.ResponseMedia;
import App.Enum.Status;
import App.Producer.RespostaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class AlunoMSConsumer {

    private final RespostaProducer respostaProducer;

    public AlunoMSConsumer(RespostaProducer respostaProducer) {
        this.respostaProducer = respostaProducer;
    }


    @RabbitListener(queues = { "aluno-request"})
    public void receberMensagem (@Payload Message message) throws JsonProcessingException {
        String dados = (String) message.getPayload();
        System.out.println("Payload: "+message);
        System.out.println("Json: "+dados);
        ObjectMapper mapper =new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Response dado = mapper.readValue(dados, Response.class);
        System.out.println("dto extraido: "+dado);
        System.out.println("dto extraido: "+dado.nome());
        System.out.println("dto extraido: "+dado.media());
        System.out.println("dto extraido: "+dado.notas());
        Double notas = 0.0;
        Double media = 0.0;
        System.out.println("nota: "+notas);
        for(Double nota : dado.notas())
        {
            notas +=nota;
        }
        media = notas/4;
        System.out.println("Nota: "+notas);
        System.out.println("Media: "+media);
        if(media > 7)
        {
            ResponseMedia dadoResposta = new ResponseMedia(dado.matricola(),Status.APROVADO,media);
            respostaProducer.integrar(dadoResposta);
        }
        else
        {
            ResponseMedia responseMedia = new ResponseMedia(dado.matricola(),Status.REPROVADO,media);
            respostaProducer.integrar(responseMedia);
        }

    }


}
