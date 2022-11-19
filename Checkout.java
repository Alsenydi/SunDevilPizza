/*
 * Class Project Phase 2
 * Team 37
 * Abdullah Alsenydi, Abdulelah Bin Morebah, Mohammed Alghufaili,
 * Vincent Ortiz, Zackary Jensen
 * Description: The Order class will allow the student user to checkout and order
 * a pizza.
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
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Checkout extends GridPane {
	// instance variables
	private Label logo;
	private Button logoutButton;
	
	// constructor
	public Checkout(String arr[]) {
		// instantiate instance variables
		logo = new Label("Sundevil Pizza");
		logoutButton = new Button("Logout");

		//creating label1 and 
		Label label1 = new Label("Enter your ASURITE  ID to complete your payment");
		
		//creating text field for the input and output
		TextField input = new TextField();
		TextField output = new TextField();
		
		// storing the student id
		String oldId = arr[6];
		
		// set the output to the total cost of the order
		output.setText("Your total is $"+arr[5]);
		
		input.setPrefHeight(20);
		input.setPrefWidth(200);
		output.setPrefHeight(20);
		output.setPrefWidth(200);

		//creating buttons
		Button button1 = new Button("Checkout");
		Button button2 = new Button("Back");
		// set the size
		button1.setPrefWidth(150);
		button2.setPrefWidth(150);
		
		// set the colors
		button1.setStyle("-fx-background-color: #FFC627");
		button2.setStyle("-fx-background-color: #FFC627");

		
		// save the order in the text file
		button1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public  void handle(ActionEvent event) {
				String inputtedId = input.getText();
				// inputted id is valid
				if(inputtedId.equals(arr[6])) {
					
					 try {
						// Create a BufferedReader object to read in previous orders 
						BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));
						
						// create status variable to store the last previous orders number
						String orderNumStr = "null";
						int i = 1;
						
						// to store each line of the file 
						String line;
						
						// go through each line and check order number
						while((line = reader.readLine()) != null) {
							String[] myArr = line.split(",");
								// add 1+ to the previous order number
								i = 1 + Integer.parseInt(myArr[8]);
								orderNumStr=String.valueOf(i);
								arr[8] = orderNumStr;
						}
						// no previous orders
						if(arr[8] == "null") {
							orderNumStr=String.valueOf(i);
							arr[8] = orderNumStr;

						}
						// close reader
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
					// set the order status to ACCEPTED 
					arr[7]="ACCEPTED";
					output.setText("Order is ACCEPTED");
					
					// save the order in the text file
					try (BufferedWriter bw = new BufferedWriter(new FileWriter("orders.txt", true))) {
						
				        for(int i=0;i<8;i++) {
				        if(arr[i]==null)	{
				        	bw.write("Null");
					        bw.write(",");
				        	}
				        else {
				        	bw.write(arr[i]);
					        bw.write(",");
				        	}
				        }
				        bw.write(arr[8]);
				        
				        bw.newLine();
				        // close the writer
						bw.close();

				    } catch (IOException e1) {
				        e1.printStackTrace();
				  }
				}
				// inputted id is invalid
				else {
					arr[7]="rejected";
					output.setText("Order is rejected");
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
				Group g = new Group(label1,button1,button2,input,output);
				this.add(g, 4, 2);
				
				label1.setLayoutY(30);
				label1.setLayoutX(200);
				
				input.setLayoutY(70);
				input.setLayoutX(175);
				
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
