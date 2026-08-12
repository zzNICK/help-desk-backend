package br.com.help_desk_backend.shared.domain;

import br.com.help_desk_backend.shared.domain.enums.HttpCustomEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseBody<T> {
    private static final long serialVersionUID = 1L;
    private LocalDateTime timestamp;
    private Integer nrStatus;
    private transient T body;
    private String txMensagem;

    public ApiResponseBody(HttpCustomEnum status, String menssage, final T body) {
        this.timestamp = LocalDateTime.now();
        this.nrStatus = status.getStatus().value();
        this.txMensagem = menssage;
        this.body = body;
    }
}
