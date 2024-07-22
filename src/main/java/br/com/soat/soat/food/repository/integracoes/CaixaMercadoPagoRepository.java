package br.com.soat.soat.food.repository.integracoes;

import br.com.soat.soat.food.model.EscopoCaixaMercadoPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaMercadoPagoRepository extends JpaRepository<EscopoCaixaMercadoPago,Long> {
}
