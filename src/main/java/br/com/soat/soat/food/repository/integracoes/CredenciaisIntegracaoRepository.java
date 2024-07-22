package br.com.soat.soat.food.repository.integracoes;

import br.com.soat.soat.food.model.CredenciaisAcesso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredenciaisIntegracaoRepository extends JpaRepository<CredenciaisAcesso,Long> {

}
