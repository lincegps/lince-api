package br.com.dlsolutions.lincegps.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Endereco implements Serializable {

    private static final long serialVersionUID = -5748543339093552951L;

    private String cidade;
    private String bairro;
}
