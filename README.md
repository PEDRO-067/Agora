# Ágora - Sistema de Gerenciamento de Artigos Científicos

> **Disciplina:** Programação Orientada a Objetos 2 (POO2)  
> **Instituição:** Universidade Tecnológica Federal do Paraná (UTFPR)

## 📖 Sobre o Projeto

A falta de padronização no arquivamento de artigos científicos pode gerar obstáculos para a comunidade acadêmica, como perdas de históricos de visualização e rastreabilidade dos conteúdos. 

Para solucionar esse problema, propomos o **Ágora**, um sistema de gerenciamento de artigos e seus respectivos dados bibliográficos. O objetivo do sistema é servir como um repertório acadêmico organizado para alunos, professores e pesquisadores, permitindo armazenar referências e palavras-chave de trabalhos lidos e estudados.

## 🚀 Principais Funcionalidades

O sistema foi modelado para oferecer uma experiência completa de organização acadêmica:

* **Automação via DOI:** Cadastro de artigos com preenchimento automático de metadados e geração de referências no formato BibTeX a partir de um DOI válido, consultando a API Crossref.
* **Organização Estrutural:** Criação de pastas e associação de artigos a elas, facilitando a separação por disciplinas, conferências ou temas.
* **Gestão do Conhecimento:** Criação, edição e exclusão de anotações específicas para cada artigo.
* **Classificação e Busca:** Associação de *tags* (palavras-chave) aos artigos, com suporte a pesquisa por título, ano ou tag.
* **Acompanhamento:** Atribuição e alteração de status de leitura dos artigos.
* **Acesso Rápido:** Capacidade de abrir o local onde o artigo está hospedado na web diretamente pela aplicação.

## 🛠️ Tecnologias Utilizadas

O projeto foi desenvolvido seguindo boas práticas de engenharia de software e o padrão de arquitetura **MVC (Model, View, Controller)** no back-end.

* **Linguagem Back-End:** Java
* **Interface Gráfica (Front-End):** JavaFX / Swing
* **Banco de Dados:** PostgreSQL
* **Gerenciador de Dependências:** Apache Maven
* **Versionamento:** Git / GitHub

## 🏗️ Estrutura e Modelagem

A arquitetura do sistema foi baseada nos seguintes diagramas UML:
* **Casos de Uso:** Mapeamento das interações do Usuário e do Admin (como a geração de referências) com o sistema.
* **Diagrama de Classes:** Estruturação das entidades principais (`Pasta`, `Artigo`, `Tag` e `Anotacao`) e seus relacionamentos (ex: um artigo pode ter várias tags e várias anotações).
* **Diagramas de Atividades:** Fluxos detalhados para a criação de pastas, edição de status de leitura e geração de referências consultando a API.

## 👥 Autores

Desenvolvido por:
* **Gustavo Yudi Yamada**
* **Pedro Paulo da Silva Oliveira**