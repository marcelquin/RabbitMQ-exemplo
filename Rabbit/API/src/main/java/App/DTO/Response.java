package App.DTO;

import App.Enum.Status;

import java.util.List;

public record Response(
        String nome,
        String matricola,
        List<Double> notas,
        Double media,
        Status status
) {
}
