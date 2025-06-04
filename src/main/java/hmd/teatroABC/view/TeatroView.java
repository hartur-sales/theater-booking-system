package hmd.teatroABC.view;

import hmd.teatroABC.model.entities.Teatro;
import hmd.teatroABC.util.FXMLLoaderUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static hmd.teatroABC.model.entities.Teatro.TELA_INICIAL;
import static hmd.teatroABC.util.FXMLLoaderUtil.BUNDLE;

/**
 * @author Davy Lopes, Murilo Nunes, Hartur Sales
 * @date 19/11/2024
 * @brief Class TeatroView
 */

public class TeatroView extends Application {
    static Teatro teatro = new Teatro();

    @Override
    public void start(Stage stage) throws IOException {
        teatro.carregarPecas();
        teatro.carregarPessoas();
        FXMLLoader fxmlLoader = FXMLLoaderUtil.loadFXML(TELA_INICIAL);
        Scene scene = new Scene(fxmlLoader.getRoot(), 1188, 770);

        stage.getIcons().add(new Image(Objects.requireNonNull(TeatroView.class.getResourceAsStream("/images/icon.png"))));
//        stage.setTitle(BUNDLE.getString("stage_titulo"));
        stage.setTitle("Teatro ABC");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}