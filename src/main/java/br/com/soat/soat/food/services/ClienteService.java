package br.com.soat.soat.food.services;


import br.com.soat.soat.food.dtos.Response;
import br.com.soat.soat.food.model.Cliente;
import br.com.soat.soat.food.repository.ClienteRepository;
import br.com.soat.soat.food.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Value("${registrar.usuario.cognito}")
    String urlCadastro;

    @Value("${login.usuario.cognito}")
    String urlLogin;

    @Value("${senha.default.cognito}")
    String senhaDefault;

    @Autowired
    ClienteRepository clienteRepository;

    public Response cadastroEupdateCliente(Cliente cliente) {
        Usuario usuario = new Usuario(cliente.getCpf(),cliente.getEmail(),"+5583981395903");

        ResponseEntity<Response> request =
                    Utils.request(urlCadastro, HttpMethod.POST, usuario, Response.class);

        return request != null ? request.getBody() :
                new Response("Não foi possivel efetuar o cadastro" , 500);
    }

    public Response loginCliente(Cliente cliente) {
        Login login = new Login(cliente.getCpf(),senhaDefault);

        ResponseEntity<Response> request =
                Utils.request(urlLogin,
                        HttpMethod.POST,
                        login,
                        Response.class);

        return request != null ? request.getBody() :
                new Response("Não foi possivel efetuar o Login" , 500);
    }

    public Cliente buscarClienteCPF(Long cpf) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findByCpf(String.valueOf(cpf));
        return clienteEncontrado.orElseGet(Cliente::new);
    }

    public record Usuario(String cpf, String email, String telefone) {
    }

    public record Login(String usuario, String senha) {
    }

}
