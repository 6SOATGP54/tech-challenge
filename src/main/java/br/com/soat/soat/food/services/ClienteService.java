package br.com.soat.soat.food.services;


import br.com.soat.soat.food.model.Cliente;
import br.com.soat.soat.food.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {


    @Autowired
    ClienteRepository clienteRepository;

    public Cliente cadastroEupdateCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente buscarClienteCPF(Long cpf) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findByCpf(String.valueOf(cpf));
        return clienteEncontrado.orElseGet(Cliente::new);
    }
}
