-- cliente definition

-- Drop table

-- DROP TABLE cliente;

CREATE TABLE cliente (
                                id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
                                data_cadastro timestamp NULL,
                                cpf varchar(255) NULL,
                                nome varchar(255) NULL,
                                email varchar(255) NULL,
                                CONSTRAINT pk_cliente PRIMARY KEY (id)
);


-- credenciais_acesso definition

-- Drop table

-- DROP TABLE credenciais_acesso;

CREATE TABLE credenciais_acesso (
                                           id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
                                           data_cadastro timestamp NULL,
                                           nome varchar(255) NULL,
                                           "token" text NULL,
                                           usuario varchar(255) NULL,
                                           CONSTRAINT pk_credenciaisacesso PRIMARY KEY (id),
                                           CONSTRAINT uc_credenciaisacesso_usuario UNIQUE (usuario)
);


-- dia_semana definition

-- Drop table

-- DROP TABLE dia_semana;

CREATE TABLE dia_semana (
                                   id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
                                   data_cadastro timestamp NULL,
                                   dia varchar(255) NULL,
                                   CONSTRAINT pk_dia_semana PRIMARY KEY (id)
);


-- produto definition

-- Drop table

-- DROP TABLE produto;

CREATE TABLE produto (
                                id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
                                data_cadastro timestamp NULL,
                                nome varchar(255) NULL,
                                descricao varchar(500) NULL,
                                preco numeric NULL,
                                categoria varchar(255) NULL,
                                imagem varchar(255) NULL,
                                CONSTRAINT pk_produto PRIMARY KEY (id)
);


-- escopo_loja_mercado_pago definition

-- Drop table

-- DROP TABLE escopo_loja_mercado_pago;

CREATE TABLE escopo_loja_mercado_pago (
                                                 id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
                                                 data_cadastro timestamp NULL,
                                                 "name" varchar(255) NULL,
                                                 external_id varchar(255) NULL,
                                                 user_id int8 NULL,
                                                 street_number varchar(255) NULL,
                                                 street_name varchar(255) NULL,
                                                 city_name varchar(255) NULL,
                                                 state_name varchar(255) NULL,
                                                 latitude float8 NOT NULL,
                                                 longitude float8 NOT NULL,
                                                 reference varchar(255) NULL,
                                                 credenciais_id int8 NULL,
                                                 CONSTRAINT pk_escopolojamercadopago PRIMARY KEY (id),
                                                 CONSTRAINT unique_userid UNIQUE (user_id),
                                                 CONSTRAINT fk_loja_to_credenciais FOREIGN KEY (credenciais_id) REFERENCES credenciais_acesso(id)
);


-- intervalo definition

-- Drop table

-- DROP TABLE intervalo;

CREATE TABLE intervalo (
                                  id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
                                  data_cadastro timestamp NULL,
                                  "open" time NULL,
                                  "close" time NULL,
                                  dia_da_semana_id int8 NULL,
                                  CONSTRAINT pk_intervalo PRIMARY KEY (id),
                                  CONSTRAINT fk_intervalo_on_dia_da_semana FOREIGN KEY (dia_da_semana_id) REFERENCES dia_semana(id)
);


-- loja_to_business_hours definition

-- Drop table

-- DROP TABLE loja_to_business_hours;

CREATE TABLE loja_to_business_hours (
                                               dia_semana_id int8 NOT NULL,
                                               loja_id int8 NOT NULL,
                                               CONSTRAINT uc_loja_to_business_hours_dia_semana UNIQUE (dia_semana_id),
                                               CONSTRAINT fk_lojtobushou_on_dia_da_semana FOREIGN KEY (dia_semana_id) REFERENCES dia_semana(id),
                                               CONSTRAINT fk_lojtobushou_on_escopo_loja_mercado_pago FOREIGN KEY (loja_id) REFERENCES escopo_loja_mercado_pago(id)
);


-- pedido definition

-- Drop table

-- DROP TABLE pedido;

CREATE TABLE pedido (
                               id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
                               data_cadastro timestamp NULL,
                               cliente int8 NULL,
                               status_pedido varchar(255) NULL,
                               CONSTRAINT pk_pedido PRIMARY KEY (id),
                               CONSTRAINT fk_pedido_on_cliente FOREIGN KEY (cliente) REFERENCES cliente(id)
);


-- pedido_produto definition

-- Drop table

-- DROP TABLE pedido_produto;

CREATE TABLE pedido_produto (
                                       id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
                                       data_cadastro timestamp NULL,
                                       pedido_id int8 NULL,
                                       produto_id int8 NULL,
                                       quantidade int4 NOT NULL,
                                       CONSTRAINT pk_pedido_produto PRIMARY KEY (id),
                                       CONSTRAINT fk_pedido_produto_on_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id),
                                       CONSTRAINT fk_pedido_produto_on_produto FOREIGN KEY (produto_id) REFERENCES produto(id)
);


-- escopo_caixa_mercado_pago definition

-- Drop table

-- DROP TABLE escopo_caixa_mercado_pago;

CREATE TABLE escopo_caixa_mercado_pago (
                                                  id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
                                                  data_cadastro timestamp NULL,
                                                  category int8 NULL,
                                                  external_id varchar(255) NULL,
                                                  external_store_id varchar(255) NULL,
                                                  fixed_amount bool NULL,
                                                  "name" varchar(255) NULL,
                                                  store_id int8 NULL,
                                                  idapi int8 NULL,
                                                  CONSTRAINT pk_escopocaixamercadopago PRIMARY KEY (id),
                                                  CONSTRAINT fk_caixa_to_loja FOREIGN KEY (store_id) REFERENCES escopo_loja_mercado_pago(user_id)
);


ALTER TABLE credenciais_acesso
    ADD web_hook TEXT;

ALTER TABLE pedido
    ADD pagamento VARCHAR(255);

ALTER TABLE pedido
    ADD referencia VARCHAR(255);