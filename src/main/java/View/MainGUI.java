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
        palcoPrincipal.setOnCloseRequest(evento -> {
            if (telaPrincipal.isDisabled()) evento.consume();
        });
        palcoPrincipal.setMinWidth(900);
        palcoPrincipal.setMinHeight(600);
        palcoPrincipal.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
