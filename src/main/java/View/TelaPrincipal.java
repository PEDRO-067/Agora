package View;

import javafx.scene.layout.BorderPane;

public class TelaPrincipal extends BorderPane {
    
    public TelaPrincipal() {
        this.setBackground(TemaUI.getFundo(TemaUI.COR_FUNDO_APP));
        
        PainelCabecalho cabecalho = new PainelCabecalho();
        PainelMenuLateral menuLateral = new PainelMenuLateral();
        PainelConteudo conteudo = new PainelConteudo();
        
        this.setTop(cabecalho);
        this.setLeft(menuLateral);
        this.setCenter(conteudo);
    }
}
