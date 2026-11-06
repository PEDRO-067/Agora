/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ArtigoDAO;
import Model.Artigo;
import Service.CrossrefParser;
import Service.CrossrefService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ArtigoCtrl {

    private final ArtigoDAO artigoDAO = new ArtigoDAO();
    private final CrossrefService crossrefService = new CrossrefService();
    private final CrossrefParser crossrefParser = new CrossrefParser();

    public Artigo cadastrarPorDoi(String entrada)
            throws SQLException, IOException, InterruptedException {

        String doi = crossrefService.normalizarDoi(entrada);

        Optional<Artigo> existente = artigoDAO.buscarPorDoi(doi);
        if (existente.isPresent()) {
            return existente.get();
        }

        String json = crossrefService.buscarMetadadosPorDoi(doi);
        Artigo artigo = crossrefParser.parsear(json);

        if (!doi.equalsIgnoreCase(artigo.getDoi())) {
            throw new IOException(
                "O Crossref não retornou os metadados do DOI informado."
            );
        }

        artigo.setDoi(doi);
        int id = artigoDAO.inserir(artigo);
        artigo.setId(id);

        return artigo;
    }

    public Optional<Artigo> buscarPorId(int id) throws SQLException {
        return artigoDAO.buscarPorId(id);
    }

    public List<Artigo> listar() throws SQLException {
        return artigoDAO.listar();
        }

    public boolean alterarStatus(int id, String novoStatus) throws SQLException {
        Optional<Artigo> encontrado = artigoDAO.buscarPorId(id);

        if (encontrado.isEmpty()) {
            return false;
            }

        Artigo artigo = encontrado.get();
        artigo.setStatusLeitura(novoStatus);
        return artigoDAO.atualizarStatus(id, artigo.getStatusLeitura());
    }

    public boolean excluir(int id) throws SQLException {
        return artigoDAO.excluir(id);
    }
}
