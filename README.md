# Relatório de Implementação: Sistema de Gestão de Investimentos NexusTeam

## 1. Introdução

O presente relatório detalha a implementação do sistema NexusBank, uma plataforma de gestão de ativos e carteiras de investimentos desenvolvida em Java. O objetivo principal do sistema é permitir o controle de diferentes tipos de ativos financeiros e a gestão de perfis de investidores, garantindo a conformidade com regras de mercado e perfis de risco.

## 2. Arquitetura e Modelagem de Dados

O sistema foi construído utilizando os pilares da Orientação a Objetos (OO): Herança, Abstração, Encapsulamento e Polimorfismo. A organização do código foi dividida em pacotes semânticos como Ativos, Investidor, Carteira, Mercado e Erros.

### 2.1. Hierarquia de Ativos

A classe abstrata Ativos serve como base para todos os produtos financeiros. A implementação utiliza interfaces para definir comportamentos específicos:

Interfaces de Renda: RendaFixa e RendaVariavel.

Interfaces de Origem: Nacional e Internacional (esta última incluindo suporte para conversão de moeda baseada na cotação do dólar).

Tipos Implementados:

- Ações: Possuem lógica interna para definir o tipo (Ordinária, Preferencial ou Unit) com base no final do ticker.

- FIIs: Implementam a interface TaxaPorcentagem para exibição obrigatória de taxas de administração.

- Tesouro: Focado em renda fixa com datas de vencimento.

- Criptomoedas e Stocks: Representam ativos internacionais com campos específicos como algoritmo de consenso e bolsa de negociação.

### 2.2. Gestão de Investidores

A estrutura de investidores diferencia Pessoa Física (PF) de Pessoa Jurídica (PJ):

- Pessoa Física: Segmentada por perfis de risco (Conservador, Moderado, Arrojado). O sistema bloqueia automaticamente a compra de ativos de alto risco (como Criptomoedas) para perfis incompatíveis.

- Pessoa Jurídica: Possui liberdade total de movimentação, independentemente do tipo de ativo.

- Regra de Qualificação: Investidores com patrimônio inferior a R$ 1.000.000,00 são impedidos de adquirir ativos marcados como "exclusivos para investidores qualificados".

## 3. Funcionalidades Principais

### 3.1. Gestão de Carteira e Movimentações

Cada investidor possui uma Carteira que armazena ItemCarteira. O sistema gerencia:

- Compras e Vendas: Atualiza quantidades e preços médios.

- Validação de Saldo: Impede a venda de ativos que o investidor não possui em quantidade suficiente.

- Cálculos de Exposição: Gera percentuais em tempo real de Renda Fixa vs. Variável e ativos Nacionais vs. Internacionais.

### 3.2. Processamento em Lote

O sistema utiliza a classe LeitorLotes para processar arquivos CSV. Isso permite a importação massiva de investidores, ativos e movimentações históricas, facilitando a carga inicial de dados.

### 3.3. Persistência e Relatórios

Logs de Transações: Todas as movimentações são registradas em arquivos CSV individuais por investidor na pasta movimentacoes.

Relatórios JSON: O sistema exporta o estado atual da carteira de um investidor para o formato JSON, permitindo a interoperabilidade com outros sistemas ou auditoria manual.

## 4. Robustez e Tratamento de Erros

Foi implementada uma hierarquia de exceções personalizadas para garantir a estabilidade:

- DadosInvalidosException: Captura erros de entrada e violações de regras de negócio.

- ErrosLeituraArq: Gerencia falhas no acesso a sistemas de arquivos ou inconsistências nos CSVs.

- ErrosNumbersFormato: Especialização para tratar erros de parsing numérico de forma amigável ao usuário.

## 5. Interface do Usuário

A interação ocorre via console através da classe Menu, que oferece navegação intuitiva dividida em submenus de ativos e investidores. Inclui recursos visuais simples (cores via ANSI) e animações de carregamento para melhorar a experiência do usuário.

## 6. Conclusão

A implementação cumpre integralmente os requisitos propostos, apresentando um código limpo, modular e de fácil manutenção. O uso rigoroso de encapsulamento e a separação de responsabilidades garantem que novas regras de mercado possam ser adicionadas ao sistema com impacto mínimo na estrutura existente.

# Diagrama de Classes
![Investor Portfolio Flow-2026-01-17-011907.svg](Investor%20Portfolio%20Flow-2026-01-17-011907.svg)

<a href="https://mermaid.ai/app/projects/1dc65ede-fb4f-4ce1-8bfe-4f3d375d5d74/diagrams/bd1c1400-069d-4b56-85bc-443160b3e24b/share/invite/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkb2N1bWVudElEIjoiYmQxYzE0MDAtMDY5ZC00YjU2LTg1YmMtNDQzMTYwYjNlMjRiIiwiYWNjZXNzIjoiVmlldyIsImlhdCI6MTc2ODYxMzgxNH0.PNTVV6FxMxjCwd1niBHeJ2IjUcpQX6E4KuUOdxRI9F8" target="_blank" rel="noopener noreferrer">Diagrama Mermaid mais ampliado</a>