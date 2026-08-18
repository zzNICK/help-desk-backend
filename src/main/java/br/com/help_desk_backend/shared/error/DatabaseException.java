package br.com.help_desk_backend.shared.error;

public class DatabaseException extends RuntimeException  {

    private final String operacao;

    public DatabaseException(String operacao, Throwable cause) {
        super("Erro na operação JDBC [" + operacao + "]", cause);
        this.operacao = operacao;
    }

    public String getOperacao() {
        return operacao;
    }
}