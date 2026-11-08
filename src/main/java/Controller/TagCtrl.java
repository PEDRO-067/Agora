package Controller;

import DAO.TagDAO;
import DAO.ArtigoDAO;
import Model.Tag;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Coordena o cadastro de tags e a organização dos artigos. */
public class TagCtrl {
    private final TagDAO dao = new TagDAO();

    public Tag cadastrar(String nome) throws SQLException {
        Tag item = new Tag(nome);
        item.setNome(item.getNome().trim());
        verificarNome(item);
        item.setId(dao.inserir(item));
        return item;
    }

    public List<Tag> listar() throws SQLException {
        return dao.listar();
    }

    public Optional<Tag> buscarPorId(int id) throws SQLException {
        return dao.buscarPorId(id);
    }

    public List<Tag> listarPorArtigo(int idArtigo) throws SQLException {
        return dao.listarPorArtigo(idArtigo);
    }

    public boolean renomear(int id, String nome) throws SQLException {
        Tag item = new Tag(id, nome);
        item.setNome(item.getNome().trim());
        verificarNome(item);
        return dao.atualizar(item);
    }

    public boolean excluir(int id) throws SQLException {
        return dao.excluir(id);
    }

    public boolean associarArtigo(int id, int idArtigo) throws SQLException {
        if (dao.buscarPorId(id).isEmpty() || new ArtigoDAO().buscarPorId(idArtigo).isEmpty()) {
            throw new IllegalArgumentException("Selecione tag e artigo cadastrados.");
        }
        return dao.associarArtigo(id, idArtigo);
    }

    public boolean removerArtigo(int id, int idArtigo) throws SQLException {
        return dao.removerArtigo(id, idArtigo);
    }

    private void verificarNome(Tag tag) throws SQLException {
        for (Tag existente : dao.listar()) {
            if (existente.getId() != tag.getId()
                    && existente.getNome().trim().equalsIgnoreCase(tag.getNome())) {
                throw new IllegalArgumentException("Já existe uma tag com esse nome.");
            }
        }
    }

}
