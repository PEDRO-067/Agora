# PaperManager - protótipo da interface principal

Protótipo **somente visual** da janela principal, feito em Java Swing +
MigLayout. Não há banco de dados, model, controller, service, repository
ou qualquer funcionalidade real — apenas a estrutura da tela para validar
o layout antes de continuar o desenvolvimento.

## Como executar

**Opção 1 — Maven (linha de comando)**

```bash
mvn compile exec:java
```

**Opção 2 — IDE (IntelliJ, Eclipse, NetBeans)**

Importe a pasta como projeto Maven. A dependência do MigLayout
(`com.miglayout:miglayout-swing:11.4.3`) é baixada automaticamente pelo
Maven a partir do Maven Central. Depois, rode a classe `Main`.

## Estrutura de pacotes

```
com.papermanager.ui
├── Main.java          — ponto de entrada
├── MainFrame.java      — janela principal (header + sidebar + conteúdo)
├── HeaderPanel.java     — barra superior (logo, pesquisa, configurações)
├── SidebarPanel.java    — menu lateral fixo
├── ContentPanel.java    — área principal ("Minha Biblioteca")
├── PaperCard.java       — card de um paper fictício
├── NavButton.java        — botão de navegação estilizado (reutilizado
│                            em Biblioteca/Favoritos/Lendo/Lidos, pastas e tags)
├── RoundedPanel.java     — painel com cantos arredondados (cards, chips,
│                            campo de busca)
└── UITheme.java          — cores e fontes centralizadas
```

## Como a interface foi estruturada

`MainFrame` usa um único `JPanel` raiz com `MigLayout`, dividido em duas
colunas (`220px` fixos para a sidebar, o restante crescendo para o
conteúdo) e duas linhas (`64px` fixos para o header, o restante para o
corpo). O header ocupa as duas colunas na primeira linha (`cell 0 0 2 1`).

Cada painel filho (`HeaderPanel`, `SidebarPanel`, `ContentPanel`,
`PaperCard`) tem seu próprio `MigLayout` interno — nenhuma coordenada
absoluta é usada em nenhum lugar do código.

## Uso do MigLayout

- `"insets 0, gap 0, fill"` no painel raiz remove espaçamentos padrão e
  permite controle total via `col`/`row` constraints.
- Colunas/linhas fixas usam `!` (ex: `[220!]`, `[64!]`) e as flexíveis
  usam `grow`/`fill`.
- `cell col row spanx spany` posiciona componentes diretamente na grade
  (usado para o header ocupar as duas colunas).
- `push` entre colunas (no `HeaderPanel`) empurra o campo de pesquisa e o
  botão de configurações para a direita, mantendo o logo fixo à esquerda.
- `wrap` no `SidebarPanel` empilha os itens verticalmente sem precisar
  declarar `wrap` a cada `add`.
- Um `JPanel` "filler" com `"growy, pushy, wrap"` empurra os botões
  "Adicionar Paper" / "Nova Pasta" para o rodapé da sidebar.

Como tudo usa `grow`/`fill` em vez de tamanhos fixos, a janela se adapta
normalmente ao redimensionamento.

## Componentes Swing utilizados

`JFrame`, `JPanel`, `JLabel`, `JTextField`, `JTextArea` (para o título do
paper, que precisa quebrar linha), `JButton`, `JToggleButton` (base do
`NavButton`), `ButtonGroup` (garante seleção única entre os itens do
menu), `JScrollPane`, `JSeparator`. Os "ícones" são caracteres Unicode,
como o próprio protótipo pediu — a estrutura já isola o texto do ícone
em `NavButton`, então trocar por uma biblioteca de ícones real (ex:
Ikonli/FontAwesome) depois é só trocar o `String icon` por um
`Icon`/`ImageIcon`.

## Como expandir depois

- **Navegação real**: hoje os `NavButton` só participam de um
  `ButtonGroup` (seleção visual). O próximo passo é registrar um
  `ItemListener`/`ActionListener` em cada botão que chame um
  `Controller` e troque o conteúdo de `ContentPanel` (por exemplo, com
  um `CardLayout` interno para alternar entre "Biblioteca", "Favoritos",
  "Pasta X" etc.).
- **Dados reais**: `ContentPanel` hoje monta os três `PaperCard` com
  dados fixos no código. Isso deve virar uma lista populada a partir do
  `Service`/`Repository` de Paper.
- **Pesquisa**: o campo de busca do `HeaderPanel` é só um `JTextField`
  com placeholder. Futuramente, um `DocumentListener` ou o Enter do
  campo dispara a busca via `Controller`.
- **Novas telas** (cadastro, edição, anotações, referências): podem
  entrar como novos painéis dentro da área de conteúdo (`CardLayout`)
  ou como `JDialog`, sem alterar `MainFrame`, `HeaderPanel` ou
  `SidebarPanel`.
