package App.Facede;

import App.DTO.Response;
import App.Producer.Producer;
import io.github.classgraph.Resource;
import org.springframework.stereotype.Service;

@Service
public class AlunoFacede {

    private final Producer producer;

    public AlunoFacede(Producer producer) {
        this.producer = producer;
    }

    public Response SolicitarProcessamento(Response response)
    {
        try
        {
            producer.integrar(response);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public void ErroProcesso(String payload) {
        System.err.println("Erro ao processar média Insuficiente" + payload);
    }

    public void SucessoProcesso(String payload) {
        System.out.println("Cadastrado com sucesso" + payload);
    }

}
