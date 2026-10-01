package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {
    
    @Override
    public void start(Stage palcoPrincipal) {
        TelaPrincipal telaPrincipal = new TelaPrincipal();
        
        Scene cena = new Scene(telaPrincipal, 1024, 768);
        
        palcoPrincipal.setTitle("Ágora - Gerenciador de Artigos");
        palcoPrincipal.setScene(cena);
        palcoPrincipal.setMaximized(true);
        palcoPrincipal.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
