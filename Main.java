/*
 * Class Project Phase 2
 * Team 37
 * Abdullah Alsenydi, Abdulelah Bin Morebah, Mohammed Alghufaili,
 * Vincent Ortiz, Zackary Jensen
 * Description: The Main class will create a Login object using javafx.
 */
package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	public void start(Stage stage) {
		// create a Login object
		Login login = new Login();
		Scene scene = new Scene(login, 400, 300);
		stage.setTitle("SunDevil Pizza");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}