package br.com.soat.soat.food.services;


import br.com.soat.soat.food.controller.PedidoController;
import br.com.soat.soat.food.dtos.ItemOrdemVendaDTO;
import br.com.soat.soat.food.dtos.OrdemVendaMercadoPagoDTO;
import br.com.soat.soat.food.enums.EndpointsIntegracaoEnum;
import br.com.soat.soat.food.enums.StatusPedido;
import br.com.soat.soat.food.exeception.QRCodeExeception;
import br.com.soat.soat.food.model.CredenciaisAcesso;
import br.com.soat.soat.food.model.EscopoCaixaMercadoPago;
import br.com.soat.soat.food.model.Pedido;
import br.com.soat.soat.food.model.Produto;
import br.com.soat.soat.food.repository.PedidoProdutoRepository;
import br.com.soat.soat.food.repository.PedidoRepository;
import br.com.soat.soat.food.repository.integracoes.CaixaMercadoPagoRepository;
import br.com.soat.soat.food.repository.integracoes.CredenciaisIntegracaoRepository;
import br.com.soat.soat.food.repository.integracoes.LojaMercadoLivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PedidoService {

    public static final String PATH_WEBHOOK = "/api/integracoes/mercadoPago/pagamentoRecebido";
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PedidoProdutoRepository pedidoProdutoRepository;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    CredenciaisIntegracaoRepository credenciaisIntegracaoRepository;

    @Autowired
    CaixaMercadoPagoRepository caixaMercadoPagoRepository;

    public String cadastroEupdatePedido(Pedido pedido) {

        List<Produto> produtos = produtoService.listarProdutos();

        CredenciaisAcesso credenciaisAcesso = credenciaisIntegracaoRepository
                .findById(pedido.getCredencialId())
                .orElse(null);
        EscopoCaixaMercadoPago escopoCaixaMercadoPago = caixaMercadoPagoRepository
                .findById(pedido.getCredencialId())
                .orElse(new EscopoCaixaMercadoPago());

        List<ItemOrdemVendaDTO> itemOrdemVendaDTOS = new ArrayList<>();

        pedido.getPedidoProdutos().forEach(i -> {
            produtos.stream().filter(p -> i.getProduto().equals(p.getId())).findFirst().ifPresent(s -> {
                itemOrdemVendaDTOS.add(new ItemOrdemVendaDTO(
                        null,
                        s.getCategoria(),
                        s.getNome(),
                        s.getDescricao(),
                        s.getPreco(),
                        i.getQuantidade(),
                        "unit",
                        s.getPreco().multiply(BigDecimal.valueOf(i.getQuantidade()))));
            });
        });

        BigDecimal total = itemOrdemVendaDTOS.stream()
                .map(ItemOrdemVendaDTO::total_amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String identificacaoPedido = UUID.randomUUID().toString();

        OrdemVendaMercadoPagoDTO ordemVendaMercadoPagoDTO = new OrdemVendaMercadoPagoDTO("Pedido " + identificacaoPedido,
                identificacaoPedido,
                itemOrdemVendaDTOS,
                "Pedido " + identificacaoPedido,
                total,
                credenciaisAcesso.getWebHook().concat(PATH_WEBHOOK));

        pedido.setReferencia(identificacaoPedido);

        if (credenciaisAcesso != null) {
            Map<Object, Object> parametros = new HashMap<>();

            parametros.put("user_id", credenciaisAcesso.getUsuario());
            parametros.put("external_pos_id", escopoCaixaMercadoPago.getExternal_id());
            String url = EndpointsIntegracaoEnum.GERARQRCODE.parametrosUrl(parametros);

            Object o = RequestServices.requestToMercadoPago(ordemVendaMercadoPagoDTO,
                    credenciaisAcesso,
                    url,
                    HttpMethod.POST,
                    EndpointsIntegracaoEnum.GERARQRCODE);

            if(o != null){
                return (String) o;
            }else{
                throw new QRCodeExeception("Não foi possivel gerar o QRCODE");
            }
        }

        return null;
    }

    public Pedido atualizarStatusPedido(PedidoController.PedidoDTO pedidoDTO) {
        Optional<Pedido> pedidoEncontrado = pedidoRepository.findById(pedidoDTO.id());
        int pedidoAtualizado = 0;

        if (pedidoEncontrado.isPresent()) {
            StatusPedido statusPedido = pedidoEncontrado.get().getStatusPedido();
            switch (statusPedido) {
                case RECEBIDO:
                    pedidoEncontrado.get().setStatusPedido(StatusPedido.PREPARACAO);
                    break;
                case PREPARACAO:
                    pedidoEncontrado.get().setStatusPedido(StatusPedido.PRONTO);
                    break;
                case PRONTO:
                    pedidoEncontrado.get().setStatusPedido(StatusPedido.FINALIZADO);
                    break;
            }

            pedidoAtualizado = pedidoRepository.statusPedido(pedidoEncontrado.get().getStatusPedido(), pedidoEncontrado.get().getId());
        }

        return pedidoAtualizado == 0 ? new Pedido() : pedidoEncontrado.get();
    }

    public List<PedidoController.PedidosRecebidosDTO> pesquisarPedidosRecebidos() {
        return pedidoProdutoRepository.pesquisarPedidosRecebidos();
    }

    public List<Pedido> pesquisarPedidosProntos() {
        return pedidoRepository.findByStatusPedido(StatusPedido.PRONTO);
    }

    public List<Pedido> pesquisarPedidosEmPreparacao() {
        return pedidoRepository.findByStatusPedido(StatusPedido.PREPARACAO);
    }

    public List<PedidoController.PedidosEmAbertoDTO> pesquiserPedidosEmAberto() {
        return pedidoProdutoRepository.pesquisarPedidosEmAberto();
    }
}
