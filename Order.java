/*
 * Class Project Phase 2
 * Team 37
 * Abdullah Alsenydi, Abdulelah Bin Morebah, Mohammed Alghufaili,
 * Vincent Ortiz, Zackary Jensen
 * Description: The Order class will allow the student user to create and order
 * a pizza, and view the status of previously ordered pizzas.
 */

package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class Order extends GridPane
{
	// instance variables
		private Label logo;
		private Button logoutButton;


		// constructor
		public Order(String inputtedId) {
			// instantiate instance variables
			logo = new Label("Sundevil Pizza");
			logoutButton = new Button("Logout");
			

			// creating an array
			String[] arr = new String[9];
			// storing data in the array
			arr[8] = "null";
			arr[6] = inputtedId;
			//creating label2
			Label label2 = new Label("Pizza Type");
			
			//creating radioButtons
			RadioButton rb1 = new RadioButton("Cheese");
			RadioButton rb2 = new RadioButton("Pepperoni");
			RadioButton rb3 = new RadioButton("Veggi");
			
			// Grouping radioButtons
			ToggleGroup grb = new ToggleGroup();
			rb1.setToggleGroup(grb);
			rb2.setToggleGroup(grb);
			rb3.setToggleGroup(grb);
			
			//creating 3rd label
			Label label3 = new Label("Toppings");
			
			//creating checkBoxs
			CheckBox box1 = new CheckBox("ExtraCheese");
			CheckBox box2 = new CheckBox("Onion");
			CheckBox box3 = new CheckBox("Mushroom");
			CheckBox box4 = new CheckBox("Olives");
			
			//creating text field for the output
			TextField output = new TextField();
			
			//setting up the size
			output.setPrefHeight(20);
			output.setPrefWidth(170);
			
			//creating buttons
			Button button1 = new Button("Calculate Total");
			Button button2 = new Button("Check your orders");
			Button button3 = new Button("Next");
			// set the size
			button1.setPrefWidth(150);
			button2.setPrefWidth(150);
			button3.setPrefWidth(100);
			// set the colors
			button1.setStyle("-fx-background-color: #FFC627");
			button2.setStyle("-fx-background-color: #FFC627");
			button3.setStyle("-fx-background-color: #FFC627");
			
			button1.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public  void handle(ActionEvent event) {
					//implementing the calculation logic
					double price=0;
					String sPrice;
					
					if(box1.isSelected()) {
						
						price=price+1.50;
					}
					if(box2.isSelected()) {
						
						price=price+1.50;
					}
					if(box3.isSelected()) {
						
						price=price+1.50;
					}
					if(box4.isSelected()) {
						
						price=price+1.50;
					}
					
					if(rb1.isSelected()) {
						price=price+10.00;
						sPrice=Double.toString(price);
						output.setText("$"+sPrice);
					}
					
					else if(rb2.isSelected()) {
						price=price+12.00;
						sPrice=Double.toString(price);
						output.setText("$"+sPrice);
					}
					
					else if(rb3.isSelected()) {
						price=price+15.00;
						sPrice=Double.toString(price);
						output.setText("$"+sPrice);
					}
					
					else {
						output.setText("Please select a pizza type!");
					}
				}
			});
			
			// go to the check order status Scene
			button2.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public  void handle(ActionEvent event) {
					//implementing the calculation logic
					Stage stage = (Stage) button2.getScene().getWindow();
					OrderStatus orderStatus = new OrderStatus(arr);
					Scene scene = new Scene(orderStatus, 600, 300);
					stage.setScene(scene);
					stage.show();
				}
			});

			
			// go to the checkout Scene
			button3.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public  void handle(ActionEvent event) {

					//implementing the calculation logic and storing it in the array
					double price=0;
					String sPrice;
					
					if(box1.isSelected()) {
						
						price=price+1.50;
						arr[1]="ExtraCheese";
					}
					if(box2.isSelected()) {
						arr[2]="Onion";
						price=price+1.50;
					}
					if(box3.isSelected()) {
						arr[3]="Mushroom";
						price=price+1.50;
					}
					if(box4.isSelected()) {
						arr[4]="Olives";
						price=price+1.50;
					}
					
					if(rb1.isSelected()) {
						arr[0]="Cheese";
						price=price+10.00;
						sPrice=Double.toString(price);
						output.setText("$"+sPrice);
						arr[5]=sPrice;
					}
					
					else if(rb2.isSelected()) {
						arr[0]="Pepperoni";
						price=price+12.00;
						sPrice=Double.toString(price);
						output.setText("$"+sPrice);
						arr[5]=sPrice;
					}
					
					else if(rb3.isSelected()) {
						arr[0]="Veggi";
						price=price+15.00;
						sPrice=Double.toString(price);
						output.setText("$"+sPrice);
						arr[5]=sPrice;
					}
					
					else {
						output.setText("Please select a pizza type!");
					}
					if(rb1.isSelected() || rb2.isSelected() || rb3.isSelected()) {
					Stage stage = (Stage) button3.getScene().getWindow();
					Checkout checkout = new Checkout(arr);
					Scene scene = new Scene(checkout, 600, 300);
					stage.setScene(scene);
					stage.show();
					}
				}
			});
			
			// update the sizes and colors of each variable
			logo.setStyle("-fx-font-weight: bold; -fx-text-fill: #8C1D40");
			logoutButton.setStyle("-fx-background-color: gray");
			logoutButton.setOnAction(new LogoutHandler());

			// Add all components to the order GridPane
			Group g = new Group(label2,label3,button1,button2,button3,box1,box2,box3,box4,rb1,rb2,rb3,output);
			this.add(g, 4, 2);
			label2.setLayoutY(30);
			label2.setLayoutX(160);
			
			rb1.setLayoutY(50);
			rb1.setLayoutX(160);
			rb2.setLayoutY(70);
			rb2.setLayoutX(160);
			rb3.setLayoutY(90);
			rb3.setLayoutX(160);
			
			label3.setLayoutY(30);
			label3.setLayoutX(270);

			box1.setLayoutY(50);
			box1.setLayoutX(270);
			box2.setLayoutY(70);
			box2.setLayoutX(270);
			box3.setLayoutY(90);
			box3.setLayoutX(270);
			box4.setLayoutY(110);
			box4.setLayoutX(270);

			output.setLayoutY(110);
			output.setLayoutX(400);

			button1.setLayoutY(150);
			button1.setLayoutX(200);
			button2.setLayoutY(200);
			button2.setLayoutX(100);
			button3.setLayoutY(200);
			button3.setLayoutX(300);
			
			
			this.add(logo, 4, 0, 1, 1);
			this.add(logoutButton, 5, 3, 1, 1);
			this.setHgap(10);
			this.setVgap(10);
		}
		
		
	/*
	 * LogoutHandler will return to the Login scene
	 */
	private class LogoutHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			Stage stage = (Stage) logoutButton.getScene().getWindow();
			Login login = new Login();
			Scene scene = new Scene(login, 400, 300);
			stage.setScene(scene);
			stage.show();
		}
	}
}