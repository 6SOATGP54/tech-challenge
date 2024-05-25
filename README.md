# Contexto e Problema
Há uma lanchonete de bairro que está expandindo devido seu grande sucesso. Porém, com a expansão e sem um sistema de controle de pedidos, o atendimento aos clientes pode ser caótico e confuso. Por exemplo, imagine que um cliente faça um pedido complexo, como um hambúrguer personalizado com ingredientes específicos, acompanhado de batatas fritas e uma bebida. O atendente pode anotar o pedido em um papel e entregá-lo à cozinha, mas não há garantia de que o pedido será preparado corretamente.

Sem um sistema de controle de pedidos, pode haver confusão entre os atendentes e a cozinha, resultando em atrasos na preparação e entrega dos pedidos. Os pedidos podem ser perdidos, mal interpretados ou esquecidos, levando à insatisfação dos clientes e a perda de negócios.

Em resumo, um sistema de controle de pedidos é essencial para garantir que a lanchonete possa atender os clientes de maneira eficiente, gerenciando seus pedidos e estoques de forma adequada. Sem ele, expandir a lanchonete pode acabar não dando certo, resultando em clientes insatisfeitos e impactando os negócios de forma negativa.

Para solucionar o problema, a lanchonete irá investir em um sistema de autoatendimento de *fast food*, que é composto por uma série de dispositivos e interfaces que permitem aos clientes selecionar e fazer pedidos sem precisar interagir com um atendente.

# Especificação
Especificação dos requisitos funcionais do sistema.

## Pedido
Os clientes são apresentados a uma interface de seleção na qual podem optar por se identificarem via CPF, se cadastrarem com nome, e-mail ou não se identificar, podendo montar o combo na seguinte sequência, sendo todas elas opcionais:

1. Lanche
2. Acompanhamento
3. Bebida
4. Sobremesa

Em cada etapa é exibido o nome, descrição e preço de cada produto.

## Pagamento
O sistema deverá possuir uma opção de pagamento integrada para MVP. A forma de pagamento oferecida será via QRCode do Mercado Pago.

## Acompanhamento
Uma vez que o pedido é confirmado e pago, ele é enviado para a cozinha para ser preparado. Simultaneamente deve aparecer em um monitor para o cliente acompanhar o progresso do seu pedido com as seguintes etapas:

- Recebido
- Em preparação
- Pronto
- Finalizado

## Entrega
Quando o pedido estiver pronto, o sistema deverá notificar o cliente que ele está pronto para retirada. Ao ser retirado, o pedido deve ser atualizado para o status finalizado.

Além das etapas do cliente, o estabelecimento precisa de um acesso administrativo.

## Gerenciar clientes
Com a identificação dos clientes o estabelecimento pode trabalhar em campanhas promocionais.

## Gerenciar produtos e categorias
Os produtos dispostos para escolha do cliente serão gerenciados pelo estabelecimento, definindo nome, categoria, preço, descrição e imagens. Para esse sistema teremos categorias fixas:

- Lanche
- Acompanhamento
- Bebida
- Sobremesa

## Acompanhamento de pedidos
Deve ser possível acompanhar os pedidos em andamento e tempo de espera de cada pedido.

As informações dispostas no sistema de pedidos precisarão ser gerenciadas pelo estabelecimento através de um painel administrativo.

# Desenvolvimento do Projeto
Documentação do sistema através da abordagem de Design Dirigido pelo Domínio (DDD) com método Event Storming.

## Realização do pedido e pagamento
<p align="center">
    <img src="./docs/diagrams/ddd-realizacao-pedido-e-pagamento.jpg">Diagrama da realização e pagamento do pedido</img>
</p>

## Preparação do pedido e entrega
<p align="center">
    <img src="./docs/diagrams/ddd-preparacao-pedido-e-entrega.jpg">Diagrama da preparação e entrega do pedido</img>
</p>

**Contextos delimitados**:
* **Lanchonete**: contexto interno.
* **Mercado Pago**: contexto externo.

## Dicionário
Dicionário em linguagem ubíqua:
- **Acompanhamento** é um dos quatro tipos de produtos ofertados pela lanchonete na montagem do pedido, de escolha opcional, para o cliente.
- **Bebida** é um dos quatro tipos de produtos ofertados pela lanchonete na montagem do pedido, de escolha opcional, para o cliente.
- **Campanha promocional** é uma ação pontual que retira uma porcentagem do preço final de determinado(s) produto(s) para clientes identificados.
- **Categoria do produto** se refere à classificação dos produtos, na qual pode ser "lanche", "bebida", "acompanhamento" ou "sobremesa".
- **Cliente** se refere à pessoa que frequenta o estabelecimento e pode realizar pedidos através do sistema de autoatendimento.
- **Combo** se refere a uma combinação de produtos oferecida aos clientes. 
- **Cozinha** é a área responsável da lanchonete pela execução das etapas de preparação e entrega do pedido, conforme solicitado pelo cliente através do sistema de autoatendimento.
- **Lanche** é um dos quatro tipos de produtos ofertados pela lanchonete na montagem do pedido, de escolha opcional, para o cliente.
- **Pagamento** se refere ao ato de quitação do valor do pedido que poderá ser feito via QRCode do Mercado Pago.
- **Pedidos em andamento** se refere aos pedidos que foram feitos pelos clientes e estão atualmente em processo de preparação pela equipe da cozinha.
- **Pedido em preparação** é a etapa em que o pedido se encontra após a equipe da cozinha iniciar o processo de preparação dos produtos que compõem o pedido.
- **Pedido finalizado** é a etapa em que o pedido se encontra após o cliente receber o pedido.
- **Pedido pronto** é a etapa em que o pedido se encontra após a equipe da cozinha finalizar o processo de preparação dos produtos que compõem o pedido.
- **Pedido recebido** é a etapa em que o pedido se encontra após a efetivação do pagamento.
- **Pedido** se refere à solicitação feita por um cliente para adquirir produtos da lanchonete, como lanches, acompanhamentos, bebidas e sobremesas através da interface de seleção disponível no sistema de autoatendimento.
- **Produto** se refere a qualquer item disponível para seleção e compra pelos clientes na lanchonete, como lanches, acompanhamentos, bebidas e sobremesas com nome, descrição, categoria, preço e uma imagem associada.
- **Sobremesa** é um dos quatro tipos de produtos ofertados pela lanchonete na montagem do pedido, de escolha opcional, para o cliente.
- **Status do pedido** se refere ao estado em que o pedido do cliente se encontra, como "recebido", "em preparação", "pronto" e "finalizado".
- **Tempo de espera do pedido** se refere ao período de tempo estimado entre o momento em que o cliente faz o pedido e o momento em que ele o recebe. 

# Execução do Projeto
:warning: Necesário possuir [Docker](https://www.docker.com/) instalado. Como super usuário, execute:

```bash
docker-compose up
```

A execução do projeto consiste em:
- 1 instância para o banco de dados
- 1 instância para a aplicação

Após a aplicação subir, acesse http://localhost:8091/api/swagger-ui/index.html para visualizar a API via Swagger.

# Membros
Grupo nº 54 da turma 6SOAT/2024 do curso *lato sensu* "Especialização em Arquitetura de Software" composto por:
- Bruno Matias
- Clederson Cruz
- Lucas Santiago
