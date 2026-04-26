package io.github.scrvrdn.eartraining.view.impl;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import io.github.scrvrdn.eartraining.events.StageReadyEvent;
import io.github.scrvrdn.eartraining.view.SceneManager;
import io.github.scrvrdn.eartraining.view.scenetypes.SceneType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class SceneManagerImpl implements SceneManager, ApplicationListener<StageReadyEvent> {

    @Value("classpath:fxml/mainmenu.fxml")
    private Resource mainMenuFxml;

    @Value("classpath:fxml/intervalsection.fxml")
    private Resource intervalSectionFxml;

    @Value("classpath:fxml/intervalsettings.fxml")
    private Resource intervalSettingsFxml;

    @Value("classpath:css/style.css")
    private Resource styleResource;

    private final ApplicationContext context;
    private final Map<SceneType, Scene> cachedScenes = new HashMap<>();
    private Stage stage;

    public SceneManagerImpl(AnnotationConfigApplicationContext context) {
        this.context = context;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        stage = event.getStage();
        
        try {
            
            FXMLLoader loader = new FXMLLoader(mainMenuFxml.getURL());
            loader.setControllerFactory(context::getBean);
            Parent parent = loader.load();

            Scene scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        preloadScenes();
        showScene(SceneType.MAIN_MENU);
    }
   

    @Override
    public void showScene(SceneType sceneType) {
        Scene scene = cachedScenes.get(sceneType);
        if (scene != null) {
            stage.setScene(scene);
            stage.show();
        }
    }

    private void preloadScenes() {
        try {
            cachedScenes.put(SceneType.MAIN_MENU, loadScene(mainMenuFxml.getURL()));
            cachedScenes.put(SceneType.INTERVAL_SECTION, loadScene(intervalSectionFxml.getURL()));
            cachedScenes.put(SceneType.INTERVAL_SETTINGS, loadScene(intervalSettingsFxml.getURL()));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
    }

    private Scene loadScene(URL url) throws Exception {
            FXMLLoader loader = new FXMLLoader(url);
            loader.setControllerFactory(context::getBean);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            scene.getStylesheets().add(styleResource.getURL().toExternalForm());
            return scene;
    }


}
