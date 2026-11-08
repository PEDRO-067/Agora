package Controller;

import DAO.PastaDAO;
import DAO.ArtigoDAO;
import Model.Pasta;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Coordena o cadastro de pastas e a organização dos artigos. */
public class PastaCtrl {
    private final PastaDAO dao = new PastaDAO();

    public Pasta cadastrar(String nome) throws SQLException {
        Pasta item = new Pasta(nome);
        item.setNome(item.getNome().trim());

        item.setId(dao.inserir(item));
        return item;
    }

    public List<Pasta> listar() throws SQLException {
        return dao.listar();
    }

    public Optional<Pasta> buscarPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Pasta> listarPorArtigo(int idArtigo) throws SQLException {
        return dao.listarPorArtigo(idArtigo);
    }

    public boolean renomear(int id, String nome) throws SQLException {
        Pasta item = new Pasta(id, nome);
        item.setNome(item.getNome().trim());

        return dao.atualizar(item);
    }

    public boolean excluir(int id) throws SQLException {
        return dao.excluir(id);
    }

    public boolean associarArtigo(int id, int idArtigo) throws SQLException {
        if (dao.buscarPorId(id).isEmpty() || new ArtigoDAO().buscarPorId(idArtigo).isEmpty()) {
            throw new IllegalArgumentException("Selecione pasta e artigo cadastrados.");
        }
        return dao.associarArtigo(id, idArtigo);
    }

    public boolean removerArtigo(int id, int idArtigo) throws SQLException {
        return dao.removerArtigo(id, idArtigo);
    }

}
