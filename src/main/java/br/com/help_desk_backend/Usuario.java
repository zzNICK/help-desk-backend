//package br.com.help_desk_backend;
//
//import javax.persistence.*;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Entity
//@Table(name = "users")
//public class Usuario {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String username;
//
//    // Mapeia a coluna do banco de dados (que guardará o hash de 60 caracteres)
//    @Column(name = "password_hash", nullable = false, length = 60)
//    private String password;
//
//    // Anotação que executa o código AUTOMATICAMENTE antes de salvar no banco
//    @PrePersist
//    public void criptografarSenha() {
//        if (this.password != null && !this.password.startsWith("$2a$")) {
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Custo 12
//            this.password = encoder.encode(this.password);
//        }
//    }
//
//    // Getters, Setters e Construtores (pode usar Lombok se preferir)
//    public void setPassword(String password) { this.password = password; }
//    public String getPassword() { return password; }
//    // ... outros getters e setters
//
//    @Column(name = "password_hash", nullable = false, length = 60)
//// 1. Impede que o Jackson envie a senha nas respostas de APIs (evita expor no JSON)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private String password;
//
//    // 2. Sobrescreva o método toString() para nunca printar o hash real em logs
//    @Override
//    public String toString() {
//        return "Usuario{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", password=' [PROTEGIDO] '" + // Mascara o valor no log
//                '}';
//    }
//}