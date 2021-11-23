package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.Raknykas;
import interpreter.InterpreterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class DesktopLauncher extends Application {

	static Stage stage;

	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new Raknykas(), config);

		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.stage = new Stage();

		File sceneFile = Gdx.files.internal("core/assets/InterpreterScene.fxml").file();
		URL url = sceneFile.toURI().toURL();

		System.out.println("URL: " + url);

		InterpreterController controller = new InterpreterController();
		FXMLLoader loader = new FXMLLoader(url);
		loader.setController(controller);
		Parent root = loader.load();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setX(0);

	}

}
