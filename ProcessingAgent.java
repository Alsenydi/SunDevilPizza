/*
 * Class Project Phase 3
 * Team 37
 * Abdullah Alsenydi, Abdulelah Bin Morebah, Mohammed Alghufaili,
 * Vincent Ortiz, Zackary Jensen
 * Description: The ProcessingAgent class will process pizza orders and allow the
 * Chef to view processed orders.
 */

package application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProcessingAgent extends BorderPane {
	// instance variables
	private Label logo;
	private Label cookingLabel;
	private Label pizzaOrder;
	private ChoiceBox<String> cooking;
	private Button pizzaReady;
	private Button logoutButton;
	private VBox cookingBox;
	private HBox holder;

	// constructor
	public ProcessingAgent() {
		// instantiate instance variables
		logo = new Label("Sundevil Pizza");
		cookingLabel = new Label("Order number");
		pizzaOrder = new Label("\n\n\n\n\n");

		cooking = new ChoiceBox<String>();

		pizzaReady = new Button("Ready to cook");
		logoutButton = new Button("Logout");

		cookingBox = new VBox(cookingLabel, cooking, pizzaReady);
		cookingBox.setSpacing(50);
		cookingBox.setAlignment(Pos.BASELINE_CENTER);

		holder = new HBox(cookingBox);
		holder.setSpacing(50);

		//BufferedReader object will read in all orders and add the order numbers
		//to the appropriate ChoiceBox
		try {
			// Create a BufferedReader object to read in all orders
			BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));
			String[] temp = new String[9];
			String line; //current line of the file

			while ((line = reader.readLine()) != null) {
				temp = line.split(",");

				//check what the order status of the line is, and then sort it into the
				//appropriate ChoiceBox
				if (temp[7].compareTo("ACCEPTED") == 0) {
					cooking.getItems().add(temp[8]);
				}
			}

			reader.close();
		}
		catch (FileNotFoundException e) {
			pizzaOrder.setStyle("-fx-text-fill: #FF0000");
			pizzaOrder.setText("The file orders.txt is not found\n\n\n\n");
		}
		catch (IOException e) {
			pizzaOrder.setStyle("-fx-text-fill: #FF0000");
			pizzaOrder.setText("IOException\n\n\n\n");
		}

		//set up a listener for all ChoiceBoxes
		setAction(cooking);

		//Set actions for all Buttons
		logoutButton.setOnAction(new LogoutHandler());

		//pizzaReady will change the order status from COOKING to READY
		pizzaReady.setOnAction((event) -> {
			String orderNum = cooking.getValue();
			if (orderNum == null) {
				pizzaOrder.setStyle("-fx-text-fill: #FF0000");
				pizzaOrder.setText("No pizza order has been chosen to get ready for cooking!\n\n\n\n\n");
			}
			else {
				pizzaOrder.setStyle("-fx-text-fill: #000000");
				pizzaOrder.setText("Order number #"+orderNum +" is now ready to cook\n\n\n\n");

				//now update the pizza order in the text file and
				//change the order numbers of the ChoiceBoxes
				try {
					String[] temp = new String[9];
					// The current line
					String line;
					//The line to be replaced with the current line.
					String oldLine = "";
					// The new line
					String newLine = "";
					String fileContent = "";
					BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));

					// Find the pizza order in the file and save it
					while ((line = reader.readLine()) != null) {
						temp = line.split(",");

						if (temp[8].compareTo(orderNum) == 0) {
							temp[7] = "Ready to cook";
							oldLine = line;
							newLine = temp[0];
							for (int i = 1; i < temp.length; i++)
								newLine = newLine + "," + temp[i];
						}
						fileContent = fileContent + line + System.lineSeparator();
					}

					// Overwriting pizza order in the file
					BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt"));
					fileContent = fileContent.replaceAll(oldLine, newLine);
					writer.write(fileContent);

					// Updating the choiceboxes
					cooking.getItems().remove(orderNum);

					reader.close();
					writer.close();
				}
				catch (IOException e) {
					pizzaOrder.setStyle("-fx-text-fill: #FF0000");
					pizzaOrder.setText("There was an error while processing the order status\n\n\n\n\n");
				}
			}
		});

		// Updating sizes & colors of the variables
		cookingLabel.setStyle("-fx-font-weight: bold");
		logo.setStyle("-fx-font-weight: bold; -fx-text-fill: #8C1D40");
		pizzaReady.setStyle("-fx-background-color: #FFC627");
		logoutButton.setStyle("-fx-background-color: gray");

		// Adding the components to Chef
		this.setTop(logo);
		this.setAlignment(logo, Pos.CENTER);
		this.setMargin(logo, new Insets(10, 10, 10, 10));
		this.setCenter(holder);
		this.setAlignment(holder, Pos.CENTER);
		this.setMargin(holder, new Insets(10, 10, 10, 10));
		this.setRight(pizzaOrder);
		this.setAlignment(pizzaOrder, Pos.CENTER_LEFT);
		this.setMargin(pizzaOrder, new Insets(10, 10, 10, 10));
		this.setBottom(logoutButton);
		this.setAlignment(logoutButton, Pos.BOTTOM_RIGHT);
		this.setMargin(logoutButton, new Insets(10, 10, 10, 10));
	}

	/*
	 * setAction will print the pizza order for whichever order number
	 * has been chosen
	 */
	private void setAction(ChoiceBox<String> cb) {
		cb.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String orderNumber = cb.getValue();
				String[] temp = new String[9];
				String line; //current line of the file
				try {
					// Creating bufferedreader to read in all the orders
					BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));

					while ((line = reader.readLine()) != null) {
						temp = line.split(",");

						// Check the line of order number then set it to the order number

						if (orderNumber.compareTo(temp[8]) == 0) {
							pizzaOrder.setStyle("-fx-text-fill: #000000");
							String order = "Student ID: " + temp[6] + "\n Order Number: " + temp[8] +
									"\nPizza Price: $" + temp[5] + "\nPizza Type: " + temp[0] +
									"\nPizza Toppings: ";
							for(int j = 1; j < 4; j++) {
								order += temp[j] + ", ";
							}
							order += temp[4];

							pizzaOrder.setText(order);
							break;
						}
					}

					reader.close();
				}
				catch (FileNotFoundException e) {
					pizzaOrder.setStyle("-fx-text-fill: #FF0000");
					pizzaOrder.setText("The file orders.txt is not found\n\n\n\n");
				}
				catch (IOException e) {
					pizzaOrder.setStyle("-fx-text-fill: #FF0000");
					pizzaOrder.setText("IOException\n\n\n\n");
				}
			}
		});
	}

	/*
	 * LogoutHandler will return to the Login scene
	 */
	private class LogoutHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Stage stage = (Stage) logoutButton.getScene().getWindow();
			Login login = new Login();
			Scene scene = new Scene(login, 400, 300);
			stage.setScene(scene);
			stage.show();
		}
	}
}