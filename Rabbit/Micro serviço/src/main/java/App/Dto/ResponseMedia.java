package App.Dto;

import App.Enum.Status;

public record ResponseMedia(
        String matricola,
        Status status,
        Double media
) {
}
