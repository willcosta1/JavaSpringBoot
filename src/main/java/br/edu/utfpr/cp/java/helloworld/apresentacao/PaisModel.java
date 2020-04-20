package br.edu.utfpr.cp.java.helloworld.apresentacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor //Gerar um construtor sem argumentos automaticamente
public class PaisModel {
    @Id @GeneratedValue
    private Long id;
    @Size (min = 3, max = 15, message = "O nome deve ter entre 3 e 15 caracteres")
    private String nome;
    @Size (min = 2, max = 3, message = "A sigla deve ter entre 2 e 3 caracteres")
    private String sigla;

}