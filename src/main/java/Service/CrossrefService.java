package Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

/*
 * Faz a comunicação HTTP com o Crossref para obter metadados JSON ou uma
 * referência BibTeX a partir de um DOI.
 * O resultado das duas operações é uma String com o texto recebido da API.
 */
public class CrossrefService {
    // Endereço da coleção de publicações. O DOI codificado é acrescentado ao final.
    private static final String URL_BASE = "https://api.crossref.org/works/";
    private final HttpClient cliente;

    // Cria o serviço com o cliente HTTP do Java.
    public CrossrefService() {
        // newBuilder() permite configurar o cliente antes de criá-lo com build().
        // O limite de 10 segundos vale para estabelecer uma nova conexão.
        // Redirect.NORMAL permite seguir redirecionamentos.
        this(HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build());
    }

    public CrossrefService(HttpClient cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                    "O cliente HTTP não pode ser nulo."
            );
        }

        this.cliente = cliente;
    }

    // Somente obtém o JSON completo dos metadados de um DOI,
    // O JSON deverá ser parseado para cadastro de artigos.
    public String buscarMetadadosPorDoi(String doi) throws IOException, InterruptedException {
        // Sem complemento, a rota é /works/{doi}. O cabeçalho Accept solicita JSON.
        return consultar(doi, "", "application/json");
    }

    // Obtém uma referência já formatada em BibTeX pelo Crossref a partir do DOI
    public String buscarBibtexPorDoi(String doi) throws IOException, InterruptedException {
        return consultar(doi, "/transform", "application/x-bibtex");
    }

    // Faz a consulta ao CrossRef e retorna JSON ou BibTek conforme os parâmetros passados
    private String consultar(String doi, String complemento, String formato)
            throws IOException, InterruptedException {
        
        // Normaliza o DOI
        String doiNormalizado = normalizarDoi(doi);

        // Codifica o DOI como parte da URL. 
        // Utilizado %20 para representar espaços 
        String doiCodificado = URLEncoder.encode(doiNormalizado, StandardCharsets.UTF_8)
                .replace("+", "%20");

        // Monta o pedido. O mesmo DOI usa uma rota
        // para JSON e outra terminada em /transform para obter BibTeX.
        // O timeout de 20 segundos limita a espera da resposta da requisição.
        // Accept informa o formato desejado e User-Agent identifica o Ágora.
        // GET indica uma consulta, sem enviar dados para cadastrar no Crossref.
        HttpRequest requisicao = HttpRequest.newBuilder(
                URI.create(URL_BASE + doiCodificado + complemento))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", formato)
                .header("User-Agent", "Agora/1.0 (projeto academico)")
                .GET()
                .build();

        HttpResponse<String> resposta;
        try {
            // Envia a requisição e aguarda a resposta (texto em java)
            resposta = cliente.send(requisicao,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (HttpTimeoutException e) {
            throw new IOException("O tempo de espera da consulta ao Crossref foi excedido.", e);
        }

        // Interrompe a consulta se a API retornar um erro HTTP.
        int codigo = resposta.statusCode();
        if (codigo == 404) {
            throw new IOException("DOI não encontrado no Crossref.");
        }
        if (codigo == 429) {
            throw new IOException("Limite de consultas do Crossref atingido. Tente novamente mais tarde.");
        }
        if (codigo == 406) {
            throw new IOException("O Crossref não disponibilizou a referência no formato solicitado.");
        }
        if (codigo != 200) {
            throw new IOException("Não foi possível consultar o Crossref. Código HTTP: " + codigo + ".");
        }

        // Verifica se o conteudo recebido existe (não nulo)
        String conteudo = resposta.body();
        if (conteudo == null || conteudo.isBlank()) {
            throw new IOException("O Crossref retornou uma resposta vazia.");
        }

        return conteudo;
    }

    // Normaliza o DOI informado pelo usuário.
    // Aceita o identificador puro ou alguns prefixos de DOI normalmente copiados pelo usuário
    private String normalizarDoi(String doi) {
        // Identifica doi nulo ou vazio
        if (doi == null || doi.isBlank()) {
            throw new IllegalArgumentException("O DOI deve ser informado.");
        }

        // strip() remove espaços nas extremidades do texto.
        String resultado = doi.strip();
        for (String prefixo : List.of("https://doi.org/", "http://doi.org/",
                "https://dx.doi.org/", "http://dx.doi.org/", "doi:")) {
            // Compara o início do texto ignorando maiúsculas e minúsculas.
            if (resultado.regionMatches(true, 0, prefixo, 0, prefixo.length())) {
                // Conserva somente o trecho depois do prefixo reconhecido.
                resultado = resultado.substring(prefixo.length()).strip();
                break;
            }
        }

        if (resultado.isBlank()) {
            throw new IllegalArgumentException("O DOI deve ser informado.");
        }
        return resultado;
    }
    
}
