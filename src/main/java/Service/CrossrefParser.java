package Service;

import Model.Artigo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

// Converte os metadados recebidos do Crossref em um Artigo.
public class CrossrefParser {
    public Artigo parsear(String json) {
        // A resposta é lida como JSON, os metadados ficam em "message".
        JsonObject resposta = JsonParser.parseString(json).getAsJsonObject();
        JsonElement mensagem = resposta.get("message");
        if (mensagem == null || mensagem.isJsonNull()) {
            throw new IllegalArgumentException("A resposta não contém os metadados em message.");
        }
        JsonObject dados = mensagem.getAsJsonObject();

        Artigo artigo = new Artigo();
        String doi = lerTexto(dados.get("DOI"));
        if (doi != null) {
            artigo.setDoi(doi);
        }

        artigo.setTipoPublicacao(lerTexto(dados.get("type")));
        artigo.setEditora(lerTexto(dados.get("publisher")));
        artigo.setVolume(lerTexto(dados.get("volume")));
        artigo.setNumero(lerTexto(dados.get("issue")));
        artigo.setNumeroArtigo(lerTexto(dados.get("article-number")));
        artigo.setPaginas(lerTexto(dados.get("page")));
        artigo.setUrl(lerTexto(dados.get("URL")));

        // Título e veículo chegam em listas de textos.
        String titulo = primeiroTexto(dados.get("title"));

        artigo.setTitulo(titulo);
        artigo.setVeiculo(primeiroTexto(dados.get("container-title")));
        artigo.setAutores(lerAutores(dados.get("author")));
        preencherData(artigo, dados);
        artigo.setResumo(limparMarcacao(lerTexto(dados.get("abstract"))));

        // O local e as organizações do congresso ficam em event.
        JsonElement evento = dados.get("event");
        if (evento != null && !evento.isJsonNull()) {
            JsonObject dadosEvento = evento.getAsJsonObject();
            artigo.setEndereco(limparMarcacao(lerTexto(dadosEvento.get("location"))));
            List<String> organizacoes = new ArrayList<>();
            JsonElement patrocinadores = dadosEvento.get("sponsor");
            if (patrocinadores != null && !patrocinadores.isJsonNull()) {
                for (JsonElement patrocinador : patrocinadores.getAsJsonArray()) {
                    String nome = limparMarcacao(lerTexto(patrocinador));
                    if (nome != null) {
                        organizacoes.add(nome);
                    }
                }
            }
            if (!organizacoes.isEmpty()) {
                artigo.setOrganizacao(String.join("; ", organizacoes));
            }
        }

        return artigo;
    }

    // Textos ausentes ou em branco ficam sem valor no artigo.
    private String lerTexto(JsonElement valor) {
        if (valor == null || valor.isJsonNull()) {
            return null;
        }
        String texto = valor.getAsString().strip();
        return texto.isEmpty() ? null : texto;
    }

    private String primeiroTexto(JsonElement lista) {
        if (lista != null && !lista.isJsonNull()) {
            for (JsonElement item : lista.getAsJsonArray()) {
                String texto = limparMarcacao(lerTexto(item));
                if (texto != null) {
                    return texto;
                }
            }
        }
        return null;
    }

    private List<String> lerAutores(JsonElement lista) {
        List<String> autores = new ArrayList<>();
        if (lista == null || lista.isJsonNull()) {
            return autores;
        }
        for (JsonElement item : lista.getAsJsonArray()) {
            if (item.isJsonNull()) {
                continue;
            }
            JsonObject autor = item.getAsJsonObject();
            String nome = lerTexto(autor.get("name")); // Nome de autoria coletiva.
            if (nome == null) {
                String prenome = lerTexto(autor.get("given"));
                String sobrenome = lerTexto(autor.get("family"));
                nome = ((prenome == null ? "" : prenome) + " "
                        + (sobrenome == null ? "" : sobrenome)).strip();
            }
            nome = limparMarcacao(nome);
            if (nome != null && !nome.isBlank()) {
                autores.add(nome);
            }
        }
        return autores;
    }

    private void preencherData(Artigo artigo, JsonObject dados) {
        // Prioriza issued e usa ano e mês da mesma data de publicação.
        for (String campo : List.of("issued", "published", "published-print", "published-online")) {
            JsonElement data = dados.get(campo);
            if (data == null || data.isJsonNull()) {
                continue;
            }
            JsonElement valor = data.getAsJsonObject().get("date-parts");
            if (valor == null || valor.isJsonNull()) {
                continue;
            }
            JsonArray datas = valor.getAsJsonArray();
            if (datas.isEmpty() || datas.get(0).isJsonNull()) {
                continue;
            }
            JsonArray partes = datas.get(0).getAsJsonArray();
            if (partes.isEmpty() || partes.get(0).isJsonNull()) {
                continue;
            }
            artigo.setAno(partes.get(0).getAsInt());
            if (partes.size() > 1 && !partes.get(1).isJsonNull()) {
                artigo.setMes(partes.get(1).getAsInt());
            }
            return;
        }
    }

    // Remove etiquetas XML/JATS e converte entidades como &amp; em texto.
    private String limparMarcacao(String texto) {
        if (texto == null || (!texto.contains("<") && !texto.contains("&"))) {
            return texto;
        }
        XMLInputFactory fabrica = XMLInputFactory.newFactory();
        fabrica.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
        fabrica.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        fabrica.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);

        try {
            XMLStreamReader leitor = fabrica.createXMLStreamReader(
                    new StringReader("<raiz>" + texto + "</raiz>"));
            try {
                StringBuilder resultado = new StringBuilder();
                while (leitor.hasNext()) {
                    int evento = leitor.next();
                    if (evento == XMLStreamConstants.CHARACTERS || evento == XMLStreamConstants.CDATA) {
                        resultado.append(leitor.getText());
                    } else if (evento == XMLStreamConstants.START_ELEMENT
                            || evento == XMLStreamConstants.END_ELEMENT) {
                        String nome = leitor.getLocalName();
                        nome = nome.substring(nome.lastIndexOf(':') + 1);
                        if (List.of("p", "title", "sec", "br").contains(nome)) {
                            resultado.append(' ');
                        }
                    }
                }
                String limpo = resultado.toString().replaceAll("\\s+", " ").strip();
                return limpo.isBlank() ? null : limpo;
            } finally {
                leitor.close();
            }
        } catch (XMLStreamException e) {
            // Mantém o conteúdo original se a marcação não puder ser lida.
            return texto;
        }
    }
}
