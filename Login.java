/*
 * Class Project Phase 2
 * Team 37
 * Abdullah Alsenydi, Abdulelah Bin Morebah, Mohammed Alghufaili,
 * Vincent Ortiz, Zackary Jensen
 * Description: The Login class will allow the user to to switch between 
 * the Chef, ProcessingAgent, and Order scenes depending on which id the user inputs.
 */

package application;

//import classes necessary to create the scene
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

//import classes necessary to compare inputted id with list of valid ids
import java.util.ArrayList;
import java.io.*;

public class Login extends BorderPane {
	// instance variables
	private Label errorMessage;
	private Label logo;
	private Label instruction;
	private Button loginButton;
	private TextField id;
	private VBox info;

	// constructor
	public Login() {
		// instantiate instance variables
		errorMessage = new Label("");
		logo = new Label("Sundevil Pizza");
		instruction = new Label("Enter your ASURITE ID");
		loginButton = new Button("Log in");
		id = new TextField("ASURITE User ID");
		info = new VBox();

		// update the sizes and colors of each variable
		errorMessage.setStyle("-fx-text-fill: red");
		logo.setStyle("-fx-font-weight: bold; -fx-text-fill: #8C1D40");
		instruction.setStyle("-fx-font-weight: bold");
		id.setStyle("-fx-text-inner-color: gray; -fx-border-width:2; -fx-border-color: #FFC627");
		loginButton.setStyle("-fx-background-color: #FFC627");
		loginButton.setOnAction(new ButtonHandler());

		// add to the VBox
		info.getChildren().addAll(errorMessage, instruction, id);
		info.setSpacing(10);
		info.setPadding(new Insets(10));
		info.setAlignment(Pos.CENTER);

		// Add all components to the Login BorderPane
		this.setTop(logo);
		this.setAlignment(logo, Pos.CENTER);
		this.setCenter(info);
		this.setAlignment(info, Pos.TOP_CENTER);
		this.setBottom(loginButton);
		this.setAlignment(loginButton, Pos.CENTER);
	}

	/*
	 * ButtonHandler will verify whether the inputed username is legit and either
	 * switch screens accordingly, or display an error message.
	 */
	private class ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Stage stage = (Stage) loginButton.getScene().getWindow();
			try {
				//Create a BufferedReader object to read in ASURITE id list into
				//an ArrayList of id
				//The first id will be chef id and the second id will be ProcessingAgent
				//The rest of the list will be Student ids.
				ArrayList<String> idList = new ArrayList<String>();
				BufferedReader reader = new BufferedReader(new FileReader("ASURITE_id.txt"));
				String idLine = null;
				while ((idLine = reader.readLine()) != null) {
					idList.add(idLine);
				}
				//reader is no longer needed, so it is closed
				reader.close();
				
				//compare the inputed id with the ArrayList
				//throw out an error message if id is not found in list
				String inputtedId = id.getText();
				int location = -1;
				
				for (int i = 0; i < idList.size(); i++) {
					if (inputtedId.compareTo(idList.get(i)) == 0) {
						location = i;
						break;
					}
				}
				//id is chef id, change to chef scene
				if (location == 0) {
					Chef chef = new Chef();
					Scene scene = new Scene(chef, 675, 300);
					stage.setScene(scene);
					stage.show();
				}
				//id is ProcessingAgent id, change to ProcessingAgent scene
				else if (location == 1) {
					ProcessingAgent proAgent = new ProcessingAgent();
					Scene scene = new Scene(proAgent, 600, 300);
					stage.setScene(scene);
					stage.show();
				}
				//id is Student id, change to Order scene
				else if (location < idList.size() && location != -1) {
					Order order = new Order(inputtedId);
					Scene scene = new Scene(order, 600, 300);
					stage.setScene(scene);
					stage.show();
				}
				//id is not found in list, show error message
				else {
					errorMessage.setText("ASURITE id is not found in list.\nPlease try again.");
				}
				
			}
			catch (FileNotFoundException fe) {
				errorMessage.setText("File Not Found");
			}
			catch (IOException e) {
				errorMessage.setText("IOException");
			} 
		}
	}
}