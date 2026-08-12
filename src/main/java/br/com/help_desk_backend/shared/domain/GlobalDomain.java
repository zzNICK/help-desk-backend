package br.com.help_desk_backend.shared.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public abstract class GlobalDomain implements Serializable{

    private static final long serialVersionUID = -1241232446441658L;

    private Long id;

    public GlobalDomain() {
    }

}
