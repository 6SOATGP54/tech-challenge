package br.com.soat.soat.food.exeception;

public class PedidoNaoEncontradoExeception extends RuntimeException {

    public PedidoNaoEncontradoExeception(String message) {
        super(message);
    }
}
