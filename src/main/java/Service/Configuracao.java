/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class Configuracao {

    private static final String ARQUIVO_CONFIG = "config.properties";
    private static final Properties PROPRIEDADES = carregarPropriedades();

    private Configuracao() {
    }

    public static String get(String chave, String valorPadrao) {
        String valor = PROPRIEDADES.getProperty(chave);
        if (valor == null || valor.trim().isEmpty()) {
            return valorPadrao;
        }
        return valor.trim();
    }

    public static String getObrigatoria(String chave) {
        String valor = get(chave, "");
        if (valor.trim().isEmpty()) {
            throw new IllegalStateException("Configure a propriedade '" + chave + "' no arquivo config.properties.");
        }
        return valor;
    }

    private static Properties carregarPropriedades() {
        Properties propriedades = new Properties();
        Path caminhoLocal = Paths.get(ARQUIVO_CONFIG);

        if (Files.exists(caminhoLocal)) {
            try (InputStream input = Files.newInputStream(caminhoLocal)) {
                propriedades.load(input);
                return propriedades;
            } catch (IOException erro) {
                throw new IllegalStateException("Nao foi possivel ler o arquivo config.properties.", erro);
            }
        }

        try (InputStream input = Configuracao.class.getClassLoader().getResourceAsStream(ARQUIVO_CONFIG)) {
            if (input != null) {
                propriedades.load(input);
            }
        } catch (IOException erro) {
            throw new IllegalStateException("Nao foi possivel ler o arquivo config.properties.", erro);
        }

        return propriedades;
    }
}
