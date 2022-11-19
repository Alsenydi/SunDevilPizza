/*
 * Class Project Phase 2
 * Team 37
 * Abdullah Alsenydi, Abdulelah Bin Morebah, Mohammed Alghufaili,
 * Vincent Ortiz, Zackary Jensen
 * Description: The Order class will allow the student user to check the order status
 */

package application;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class OrderStatus extends GridPane {
	// instance variables
	private Label logo;
	private Button logoutButton;
	
	// constructor
	public OrderStatus(String arr[]) {
		// instantiate instance variables
		logo = new Label("Sundevil Pizza");
		logoutButton = new Button("Logout");
		
		//creating label1 and 2
		Label label1 = new Label("Enter your ASURITE  ID");
		Label label2 = new Label("Order Status:");
		
		// storing the student id
		String oldId = arr[6];
		
		//creating text field for the input and output
		TextField input = new TextField();
		TextField output = new TextField();
		// set the size
		input.setPrefHeight(20);
		input.setPrefWidth(230);
		output.setPrefHeight(20);
		output.setPrefWidth(230);
		
		//creating buttons
		Button button1 = new Button("Refresh status");
		Button button2 = new Button("Back");
		// set the size
		button1.setPrefWidth(150);
		button2.setPrefWidth(150);
		
		// set the colors
		button1.setStyle("-fx-background-color: #FFC627");
		button2.setStyle("-fx-background-color: #FFC627");

		/*
		 * ButtonHandler will verify whether the inputed id has any order and
		 * it'll show the order status in the text field.
		 */
		button1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public  void handle(ActionEvent event) {
				 {
					 
					 // storing the ASURITE  ID
					 String id = input.getText();					 
					 
					 try {
						// Create a BufferedReader object to read in previous orders 
						BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));
						
						// create status variable to verify if the id has any previous orders 
						String status = "null";
						String orerNum = null;
						// to store each line of the file 
						String line;
						// go through each line and check id and replace status if id has previous order
						while((line = reader.readLine()) != null) {
							String[] myArr = line.split(",");
							if(id.equals(myArr[6]) ) {
								status = myArr[7];
								orerNum = myArr[8];
							 }

						}
						
						// if student doesn't have previous order
						if(status == "null") {
							output.setText("No order was placed on your ASU ID");
						}
						
						// display the latest order status if student has previous orders
						else {
							output.setText("Your order number "+ orerNum +" is "+status);
						}
						reader.close();
					} 
					 catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 

				}
				
			}
		});
		
		// go back to the order Scene
		button2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public  void handle(ActionEvent event) {
				//implementing the calculation logic
				Stage stage = (Stage) button2.getScene().getWindow();
				Order order = new Order(oldId);
				Scene scene = new Scene(order, 600, 300);
				stage.setScene(scene);
				stage.show();
			}
		});
		
		
		// update the sizes and colors of each variable
				logo.setStyle("-fx-font-weight: bold; -fx-text-fill: #8C1D40");
				logoutButton.setStyle("-fx-background-color: gray");
				logoutButton.setOnAction(new LogoutHandler());
				
				// Add all components to the GridPane
				Group g = new Group(label1,label2,button1,button2,input,output);
				this.add(g, 4, 2);
				
				label1.setLayoutY(30);
				label1.setLayoutX(200);
				
				input.setLayoutY(70);
				input.setLayoutX(175);

				
				label2.setLayoutY(140);
				label2.setLayoutX(90);
				
				output.setLayoutY(140);
				output.setLayoutX(175);
				
				button2.setLayoutY(200);
				button2.setLayoutX(100);
				button1.setLayoutY(200);
				button1.setLayoutX(300);

				this.add(logo, 1, 0, 1, 1);
				this.add(logoutButton, 5, 3, 1, 1);
				this.setHgap(10);
				this.setVgap(10);
				
				
				
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
