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
* **Interface Gráfica (Front-End):** JavaFX (o protótipo histórico usa Swing)
* **Banco de Dados:** PostgreSQL
* **Gerenciador de Dependências:** Apache Maven
* **Versionamento:** Git / GitHub

## 🏗️ Estrutura e Modelagem

A arquitetura do sistema foi baseada nos seguintes diagramas UML:
* **Casos de Uso:** Mapeamento das interações do Usuário e do Admin (como a geração de referências) com o sistema.
* **Diagrama de Classes:** Estruturação das entidades principais (`Pasta`, `Artigo`, `Tag` e `Anotacao`) e seus relacionamentos (ex: um artigo pode ter várias tags e uma anotação).
* **Diagramas de Atividades:** Fluxos detalhados para a criação de pastas, edição de status de leitura e geração de referências consultando a API.

## 👥 Autores

Desenvolvido por:
* **Gustavo Yudi Yamada**
* **Pedro Paulo da Silva Oliveira**
## Executar a aplicação

Use JDK 24 ou superior e Maven. O alvo de compilação permanece Java 24. O projeto principal é o pom.xml da raiz.
A pasta paper-manager-ui-prototype preserva o protótipo Swing independente.

1. Inicie o PostgreSQL e crie um banco chamado agora.
2. Copie config.example.properties para config.properties e preencha a conexão.
   Esse arquivo local está ignorado pelo Git. Preserve sua configuração se já existir.
3. No pgAdmin, conectado ao banco agora, execute schema.sql.
   Pelo terminal, também é possível usar o comando abaixo.
4. Execute o Maven na raiz do projeto ou abra o projeto no NetBeans com JDK 24.

    psql -U postgres -d agora -v ON_ERROR_STOP=1 --single-transaction -f schema.sql
    mvn clean compile
    mvn javafx:run

No NetBeans, executar com a classe principal
com.mycompany.agoraproject.AgoraProject também abre a interface.

Se o terminal Windows não encontrar o Maven, use as instalações do NetBeans,
ajustando os caminhos conforme sua máquina.

    $env:JAVA_HOME = 'C:/Program Files/Apache NetBeans/jdk'
    & 'C:/Program Files/Apache NetBeans/java/maven/bin/mvn.cmd' javafx:run

A API pública do Crossref dispensa chave. A internet é necessária para cadastrar
um DOI novo e gerar BibTeX. A consulta e a organização da biblioteca usam o banco
local. Os metadados ausentes são apresentados como "Não informado".

## Usar a biblioteca

- Cadastre um DOI pelo botão superior. Um DOI já cadastrado abre o registro existente.
- Pesquise pelo título e ano. Combine esses critérios com pasta e tag na lateral.
- Use Gerenciar pastas e Gerenciar tags para criar, renomear ou excluir organizações.
- Abra um artigo para consultar todos os metadados, alterar status, editar sua anotação,
  adicionar ou retirar pastas e tags, abrir a URL ou gerar e copiar BibTeX.
- A exclusão de artigo remove sua anotação e seus vínculos. Pastas e tags permanecem.
  Excluir uma pasta ou tag remove seus vínculos e preserva os artigos.

A implementação segue o Model e o diagrama de classes atuais, com zero ou uma
anotação por artigo. O texto do levantamento usa o plural genericamente. Cada
artigo pode estar em várias pastas e ter várias tags, compartilhadas com outros
artigos. O banco garante as relações com chaves estrangeiras e vínculos únicos.

## Testes de integração

O teste executável usa o PostgreSQL configurado e cria um esquema temporário
exclusivo. Ao terminar, remove apenas esse esquema. O usuário do banco precisa
ter permissão de criação de esquema. A biblioteca existente é preservada.

Execute na raiz do projeto, em PowerShell, com java e Maven configurados.

    mvn test-compile dependency:build-classpath "-Dmdep.outputFile=target/classpath.txt"
    $testClasspath = 'target/test-classes;target/classes;' + (Get-Content target/classpath.txt -Raw).Trim()
    java -cp $testClasspath IntegracaoTest
    java -cp $testClasspath IntegracaoTest --api

A opção --api acrescenta cadastro real no Crossref e obtenção de BibTeX.
O teste verifica validações, persistência, anotação ainda sem data de edição,
duplicidades, busca combinada, relacionamentos e exclusões em cascata.
O comando mvn test isoladamente não executa esse teste manual.

A classe Configuracao também aceita propriedades Java -Ddb.url, -Ddb.user e
-Ddb.password. O teste usa esse recurso para selecionar o esquema temporário.

## Organização da implementação

- Model mantém as regras dos objetos. Anotacao valida nome, texto e artigoId.
- DAO executa SQL parametrizado e reconstrói os objetos a partir dos registros.
- Controller coordena consultas, validações e ações solicitadas pela interface.
- Service consulta Crossref, interpreta JSON e lê a configuração.
- View usa JavaFX e chama Controllers. OperacaoUI executa banco/API em uma Task,
  devolvendo os resultados à thread JavaFX e apresentando falhas em diálogos.

A criação das tabelas é explícita pelo schema.sql. Ele pode ser reaplicado ao
esquema criado por este projeto. Bancos com tabelas criadas manualmente com outras
colunas precisam ser comparados com esse arquivo antes da execução.
