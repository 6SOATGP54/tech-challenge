package br.com.soat.soat.food.repository;

import br.com.soat.soat.food.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
