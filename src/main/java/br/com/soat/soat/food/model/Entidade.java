package br.com.soat.soat.food.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;

    protected void prePersistSubClasses() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @PrePersist
    public void setarDataCadastro(){
        this.setDataCadastro(LocalDateTime.now());
        prePersistSubClasses();
    }

}
