package App.Service;

import App.DTO.Response;
import App.Entity.Aluno;
import App.Enum.Status;
import App.Producer.Producer;
import App.Repository.AlunoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repository;
    private final Producer producer;


    public AlunoService(AlunoRepository repository, Producer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public ResponseEntity<List<Response>> ListarAlunos()
    {
        try
        {
            List<Aluno> alunos = repository.findAll();
            List<Response> response = new ArrayList<>();
            for(Aluno aluno : alunos)
            {
                Response dto = new Response(aluno.getNome(), aluno.getMatricola(), aluno.getNotas(),aluno.getMedia(),aluno.getStatus());
                response.add(dto);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }


    public ResponseEntity<Response> NovoAluno(String nome,
                                              Double nota1,
                                              Double nota2,
                                              Double nota3,
                                              Double nota4)
    {
        try
        {
            if(nota1 != null &&
               nota2 != null &&
               nota3 != null &&
               nota4 != null &&
               nome != null)
            {
                List<Double> notas = new ArrayList<>();
                int matricola = (int) (11111 + Math.random() * 99999);
                notas.add(nota1);
                notas.add(nota2);
                notas.add(nota3);
                notas.add(nota4);
               Aluno entity = new Aluno();
               entity.setNome(nome);
               entity.setNotas(notas);
               entity.setMatricola("mtr_"+matricola);
               //Double media = (nota1 + nota2 + nota3 + nota4)/4;
               entity.setMedia(0.0);
               entity.setStatus(Status.AGUARDANDO);
               repository.save(entity);
               Response response = new Response(entity.getNome(),entity.getMatricola(), entity.getNotas(),entity.getMedia(),entity.getStatus());
               producer.integrar(response);
               return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }


}



