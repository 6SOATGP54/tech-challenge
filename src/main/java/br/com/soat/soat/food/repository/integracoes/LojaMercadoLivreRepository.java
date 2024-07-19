package br.com.soat.soat.food.repository.integracoes;

import br.com.soat.soat.food.model.EscopoLojaMercadoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LojaMercadoLivreRepository extends JpaRepository<EscopoLojaMercadoPago,Long> {
}
