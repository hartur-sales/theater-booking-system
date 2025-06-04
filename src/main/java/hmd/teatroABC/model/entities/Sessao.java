package hmd.teatroABC.model.entities;

import java.util.ResourceBundle;

import static hmd.teatroABC.util.FXMLLoaderUtil.BUNDLE;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 21/11/2024
 * @brief Enum Sessao
 */

public enum Sessao {
    MANHA("Manha"),
    TARDE("Tarde"),
    NOITE("Noite");

    private final String nome;

    public String getNome() {
        return nome;
    }

    Sessao(String nome) {
        this.nome = nome;
    }
//    MANHA("sessao.manha"),
//    TARDE("sessao.tarde"),
//    NOITE("sessao.noite");
//
//    private final String chave;
//
//    Sessao(String chave) {
//        this.chave = chave;
//    }
//
//    public String getNomeTraduzido() {
//        return BUNDLE.getString(chave);
//    }
}
