/*
 * Class Project Phase 2
 * Team 37
 * Abdullah Alsenydi, Abdulelah Bin Morebah, Mohammed Alghufaili,
 * Vincent Ortiz, Zackary Jensen
 * Description: The Chef class will allow the user to update the order status
 * of the student's pizza.
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

public class Chef extends BorderPane {
	// instance variables
	private Label logo;
	private Label toCookLabel;
	private Label cookingLabel;
	private Label readyLabel;
	private Label pizzaOrder;
	private ChoiceBox<String> toCook;
	private ChoiceBox<String> cooking;
	private ChoiceBox<String> ready;
	private Button cookThePizza;
	private Button pizzaReady;
	private Button logoutButton;
	private VBox toCookBox;
	private VBox cookingBox;
	private VBox readyBox;
	private HBox holder;

	// constructor
	public Chef() {
		// instantiate instance variables
		logo = new Label("Sundevil Pizza");
		toCookLabel = new Label("Ready to Cook");
		cookingLabel = new Label("Cooking");
		readyLabel = new Label("Finished Pizzas");
		pizzaOrder = new Label("\n\n\n\n\n");
		
		toCook = new ChoiceBox<String>();
		cooking = new ChoiceBox<String>();
		ready = new ChoiceBox<String>();
		
		cookThePizza = new Button("Begin Cooking");
		pizzaReady = new Button("Pizza is Ready");
		logoutButton = new Button("Logout");
		
		toCookBox = new VBox(toCookLabel, toCook, cookThePizza);
		toCookBox.setSpacing(50);
		toCookBox.setAlignment(Pos.BASELINE_CENTER);
		cookingBox = new VBox(cookingLabel, cooking, pizzaReady);
		cookingBox.setSpacing(50);
		cookingBox.setAlignment(Pos.BASELINE_CENTER);
		readyBox = new VBox(readyLabel, ready);
		readyBox.setSpacing(50);
		readyBox.setAlignment(Pos.BASELINE_CENTER);
		holder = new HBox(toCookBox, cookingBox, readyBox);
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
				if (temp[7].compareTo("Ready to cook") == 0) {
					toCook.getItems().add(temp[8]);
				}
				else if (temp[7].compareTo("COOKING") == 0) {
					cooking.getItems().add(temp[8]);
				}
				else if (temp[7].compareTo("READY") == 0) {
					ready.getItems().add(temp[8]);
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
		setAction(toCook);
		setAction(cooking);
		setAction(ready);
		
		//Set actions for all Buttons
		logoutButton.setOnAction(new LogoutHandler());
		
		//cookThePizza will change the order status from Ready to cook to COOKING
		cookThePizza.setOnAction((event) -> {
			String orderNum = toCook.getValue();
			if (orderNum == null) {
				pizzaOrder.setStyle("-fx-text-fill: #FF0000");
				pizzaOrder.setText("No pizza order has been chosen to cook\n\n\n\n\n");
			}
			else {
				pizzaOrder.setStyle("-fx-text-fill: #000000");
				pizzaOrder.setText(orderNum +" is now cooking\n\n\n\n\n");
				
				//now update the pizza order in the text file and
				//change the order numbers of the ChoiceBoxes
				try {
					String[] temp = new String[9];
					String line; //current line of the file
					String oldLine = ""; //line to be replaced
					String newLine = ""; //edited line
					String fileContent = "";
					BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));
					
					//finds the pizza order in the file and saves it
					while ((line = reader.readLine()) != null) {
						temp = line.split(",");
						
						if (temp[8].compareTo(orderNum) == 0) {
							temp[7] = "COOKING";
							oldLine = line;
							newLine = temp[0];
							for (int i = 1; i < temp.length; i++)
								newLine = newLine + "," + temp[i];
						}
						fileContent = fileContent + line + System.lineSeparator();
					}
					
					//overwrites the pizza order in the file
					BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt"));
					fileContent = fileContent.replaceAll(oldLine, newLine);
					writer.write(fileContent);
					
					//updates both ChoiceBoxes
					toCook.getItems().remove(orderNum);
					cooking.getItems().add(orderNum);
					
					reader.close();
					writer.close();
				}
				catch (IOException e) {
					pizzaOrder.setStyle("-fx-text-fill: #FF0000");
			        pizzaOrder.setText("There was an issue updating the order status\n\n\n\n\n");
			    }
			}
		} );
		
		//pizzaReady will change the order status from COOKING to READY
		pizzaReady.setOnAction((event) -> {
			String orderNum = cooking.getValue();
			if (orderNum == null) {
				pizzaOrder.setStyle("-fx-text-fill: #FF0000");
				pizzaOrder.setText("No pizza order has been chosen to finish cooking\n\n\n\n\n");
			}
			else {
				pizzaOrder.setStyle("-fx-text-fill: #000000");
				pizzaOrder.setText(orderNum +" is now finished\nAn email has been sent to the customer\n\n\n\n");
				
				//now update the pizza order in the text file and
				//change the order numbers of the ChoiceBoxes
				try {
					String[] temp = new String[9];
					String line; //current line of the file
					String oldLine = ""; //line to be replaced
					String newLine = ""; //edited line
					String fileContent = "";
					BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));
					
					//finds the pizza order in the file and saves it
					while ((line = reader.readLine()) != null) {
						temp = line.split(",");
						
						if (temp[8].compareTo(orderNum) == 0) {
							temp[7] = "READY";
							oldLine = line;
							newLine = temp[0];
							for (int i = 1; i < temp.length; i++)
								newLine = newLine + "," + temp[i];
						}
						fileContent = fileContent + line + System.lineSeparator();
					}
					
					//overwrites the pizza order in the file
					BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt"));
					fileContent = fileContent.replaceAll(oldLine, newLine);
					writer.write(fileContent);
					
					//updates both ChoiceBoxes
					cooking.getItems().remove(orderNum);
					ready.getItems().add(orderNum);
					
					reader.close();
					writer.close();
				}
				catch (IOException e) {
					pizzaOrder.setStyle("-fx-text-fill: #FF0000");
			        pizzaOrder.setText("There was an issue updating the order status\n\n\n\n\n");
			    }
			}
		});
		
		// update the sizes and colors of each variable
		toCookLabel.setStyle("-fx-font-weight: bold");
		cookingLabel.setStyle("-fx-font-weight: bold");
		readyLabel.setStyle("-fx-font-weight: bold");
		logo.setStyle("-fx-font-weight: bold; -fx-text-fill: #8C1D40");
		cookThePizza.setStyle("-fx-background-color: #FFC627");
		pizzaReady.setStyle("-fx-background-color: #FFC627");
		logoutButton.setStyle("-fx-background-color: gray");

		// Add all components to the Chef BorderPane
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
					// Create a BufferedReader object to read in all orders
					BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));
					
					while ((line = reader.readLine()) != null) {
						temp = line.split(",");
						
						//check what the order number of the line is, and then print
						//the order that matches it
						if (orderNumber.compareTo(temp[8]) == 0) {
							pizzaOrder.setStyle("-fx-text-fill: #000000");
							String order = "Student ID: " + temp[6] + "\n Order Number: " + temp[8] +
									"\nPizza Price: $" + temp[5] + "\nPizza Type: " + temp[0] + 
									"\nPizza Toppings: ";
							if (temp[1].compareTo("Null") != 0)
								order += "Extra Cheese, ";
							if (temp[2].compareTo("Null") != 0)
								order += "Onion, ";
							if (temp[3].compareTo("Null") != 0)
								order += "Mushroom, ";
							if (temp[4].compareTo("Null") != 0)
								order += "Olives";
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