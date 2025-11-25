import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
//import javafx.animation.RotateTransition;
//import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javafx.util.Duration;

public class JavaFXTemplate extends Application {
	BorderPane welcomePane, gamePane, drawingPane;
	GridPane betCard;
	Button rules, winningOdds, exit, start, newLook, spot1, spot4, spot8, 
			spot10, drawing1, drawing2, drawing3, drawing4, rand, gameStart,
			gameRules, gameWO, gameExit, drawingRules, drawingWO, drawingExit, 
			drawingNL, nextDrawing;
	Button[][] betCardButtons = new Button[8][10];
	EventHandler<ActionEvent> rHandler, woHandler, eHandler, nlHandler;
	TextArea welcomeTF, description, menu, gameMenu, drawingMenu, drawingNum, 
			resultBox, drawingLabel, chooseSpot, chooseDrawing, pickSpots;
	HBox topBP, buttons, choicesBox2;
	VBox centerBox, bottomBox, choicesBox;
	String font = "Trebuchet MS";
	String bgColor = "LightSteelBlue";
	String selectedColor = "#6e6a6a";
	String textColor = "Azure";
	int height = 800;
	int width = 500;
	boolean isCurrDrawing = false;
	
	HashMap<String, Scene> sceneMap;
	
	GameController game;
	
	PauseTransition pause = new PauseTransition(Duration.seconds(1));

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	/*@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to JavaFX");
		
		 Rectangle rect = new Rectangle (100, 40, 100, 100);
	     rect.setArcHeight(50);
	     rect.setArcWidth(50);
	     rect.setFill(Color.VIOLET);

	     RotateTransition rt = new RotateTransition(Duration.millis(5000), rect);
	     rt.setByAngle(270);
	     rt.setCycleCount(4);
	     rt.setAutoReverse(true);
	     SequentialTransition seqTransition = new SequentialTransition (
	         new PauseTransition(Duration.millis(500)),
	         rt
	     );
	     seqTransition.play();
	     
	     FadeTransition ft = new FadeTransition(Duration.millis(5000), rect);
	     ft.setFromValue(1.0);
	     ft.setToValue(0.3);
	     ft.setCycleCount(4);
	     ft.setAutoReverse(true);

	     ft.play();
	     BorderPane root = new BorderPane();
	     root.setCenter(rect);
	     
	     Scene scene = new Scene(root, 700,700);
			primaryStage.setScene(scene);
			primaryStage.show();
		
				
		
	}*/
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Keno Game");
		
		game = new GameController();
		
		createButtons();
		
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("Welcome", createWelcome());
		sceneMap.put("Game", createGame());
		sceneMap.put("Drawing", createDrawing());
		
		// Making the button handlers
		rHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// Changing center text
				welcomeTF.setText("RULES:");
				description.setText("\t1. Choose the number of spots you want to play"
						+ "\n\t2. Choose the amount of drawings you would like"
						+ "\n\t3. Choose your spots or allow your spots to be"
						+ "\n\t    randomly chosen"
						+ "\n\t\t- No duplicates are allowed"
						+ "\n\t\t- You are only allowed to choose the amount"
						+ "\n\t\t   of spots you previously chose");
				rules.setDisable(true);	// Disabling the rules button 
				
				// Setting all other buttons to not disabled
				winningOdds.setDisable(false); 
				exit.setDisable(false);
				
				primaryStage.setScene(sceneMap.get("Welcome"));
			}
		};
		rules.setOnAction(rHandler);
		gameRules.setOnAction(rHandler);
		drawingRules.setOnAction(rHandler);
		
		woHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// Changing center text
				welcomeTF.setText("ODDS OF WINNING:");
				description.setText("\t1 Spot Odds: 1 in 4.00"
						+ "\n\t4 Spot Odds: 1 in 3.86"
						+ "\n\t8 Spot Odds: 1 in 9.77"
						+ "\n\t10 Spot Odds: 1 in 9.05");
				winningOdds.setDisable(true);	// Disabling the winning odds button
				
				// Setting all other buttons to not disabled
				rules.setDisable(false); 
				exit.setDisable(false);
				
				primaryStage.setScene(sceneMap.get("Welcome"));
			}
		};
		winningOdds.setOnAction(woHandler);
		gameWO.setOnAction(woHandler);
		drawingWO.setOnAction(woHandler);
		
		eHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// Changing center text
				welcomeTF.setText("WELCOME!!");
				description.setText("\tThis is Keno, a simple lottery game with great cash"
						+ "\nprizes! You are allowed to choose how many spots"
						+ "\n(numbers) you want to play. Once all spots are chosen,"
						+ "\nthe drawings will begin. Per drawing, 20 out of 80 random"
						+ "\nnumbers will be selected as the winning numbers. These"
						+ "\nnumbers will be matched to your spots and used to select"
						+ "\n your prize.");
				
				exit.setDisable(false);
				
				// Setting all other buttons to not disabled
				rules.setDisable(false); 
				winningOdds.setDisable(false); 
				
				exitAction();
				
				nextDrawing.setText("Next Drawing");
				
				isCurrDrawing = false; 	// Setting the var to false since we are starting a new round
				
				primaryStage.setScene(sceneMap.get("Welcome"));
			}
		};
		exit.setOnAction(eHandler);
		gameExit.setOnAction(eHandler);
		drawingExit.setOnAction(eHandler);
		
		
		start.setOnAction(e-> {
			// Changing scenes
			if (isCurrDrawing) {
				// CHanging to the Drawing Screen if there is currently a drawing going on
				primaryStage.setScene(sceneMap.get("Drawing"));
			}
			else {
				// Changing to the Game Screen if there is not a drawing going on
				primaryStage.setScene(sceneMap.get("Game"));
			}
		});
		
		spot1.setOnAction(e-> {
			spotAction();	// Enabling and disabling buttons
			spot1.setStyle("-fx-background-radius: 10; -fx-background-color: " + selectedColor + ";");	//  Changing the color of the button to show their choice
			game.chooseSpots(1);	// Setting the amount of spots to 1
		});
		
		spot4.setOnAction(e-> {
			spotAction();	// Enabling and disabling buttons
			spot4.setStyle("-fx-background-radius: 10; -fx-background-color: " + selectedColor + ";");	//  Changing the color of the button to show their choice
			game.chooseSpots(4);	// Setting the amount of spots to 4
		});
		
		spot8.setOnAction(e-> {
			spotAction();	// Enabling and disabling buttons
			spot8.setStyle("-fx-background-radius: 10; -fx-background-color: " + selectedColor + ";");	//  Changing the color of the button to show their choice
			game.chooseSpots(8);	// Setting the amount of spots to 8
		});
		
		spot10.setOnAction(e-> {
			spotAction();	// Enabling and disabling buttons
			spot10.setStyle("-fx-background-radius: 10; -fx-background-color: " + selectedColor + ";");	//  Changing the color of the button to show their choice
			game.chooseSpots(10);	// Setting the amount of spots to 10
		});
		
		drawing1.setOnAction(e-> {
			drawingsAction();	// Enabling and disabling buttons
			drawing1.setStyle("-fx-background-radius: 10; -fx-background-color: " + selectedColor + ";");	//  Changing color of button to show their choice
			game.chooseDrawings(1);	// Setting the amount of spots to 1
		});
		
		drawing2.setOnAction(e-> {
			drawingsAction();	// Enabling and disabling buttons
			drawing2.setStyle("-fx-background-radius: 10; -fx-background-color: " + selectedColor + ";");	//  Changing color of button to show their choice
			game.chooseDrawings(2);	// Setting the amount of spots to 2
		});
		
		drawing3.setOnAction(e-> {
			drawingsAction();	// Enabling and disabling buttons
			drawing3.setStyle("-fx-background-radius: 10; -fx-background-color: " + selectedColor + ";");	//  Changing color of button to show their choice
			game.chooseDrawings(3);	// Setting the amount of spots to 3
		});
		
		drawing4.setOnAction(e-> {
			drawingsAction();	// Enabling and disabling buttons
			drawing4.setStyle("-fx-background-radius: 10; -fx-background-color: " + selectedColor + ";");	//  Changing color of button to show their choice
			game.chooseDrawings(4);	// Setting the amount of spots to 4
		});
		
		rand.setOnAction(e-> {
			randAction();
			rand.setStyle("-fx-background-radius: 10; -fx-background-color: " + selectedColor + ";");	//  Changing color of button to show their choice
		});
		
		gameStart.setOnAction(e-> {
			// Changing to the Drawing Screen
			primaryStage.setScene(sceneMap.get("Drawing"));
			drawing();	// Drawing for the first time
		});
		
		nlHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(bgColor == "LightSteelBlue") {
					bgColor = "LightSalmon";
					textColor = "NavajoWhite";
				} else {
					bgColor = "LightSteelBlue";
					textColor = "Azure";
				}
				
				// Changing the color of the panes
				welcomePane.setStyle("-fx-background-color: " + bgColor + ";");
				gamePane.setStyle("-fx-background-color: " + bgColor + ";");
				drawingPane.setStyle("-fx-background-color: " + bgColor + ";");
				
				// Changing the color of the menu and text boxes
				gameMenu.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + bgColor + ";");
				menu.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + bgColor + ";");
				drawingMenu.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + bgColor + ";");
				welcomeTF.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
				description.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
				resultBox.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
				drawingLabel.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
				drawingNum.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
				chooseSpot.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
				chooseDrawing.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
				pickSpots.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
			}
		};
		newLook.setOnAction(nlHandler);
		drawingNL.setOnAction(nlHandler);
		
		// Setting the scene and making it show
		primaryStage.setScene(sceneMap.get("Welcome"));
		primaryStage.show();
	}
	
	private void createButtons() {
		// Welcome Screen Buttons
		// Creating the Menu buttons
		rules = new Button("Rules");
		winningOdds = new Button("Odds of Winning");
		exit = new Button("Exit");
		start = new Button("START");
		
		// Changing the style of the buttons
		rules.setFont(Font.font(font, 20));
		rules.setStyle("-fx-background-radius: 10;");
		winningOdds.setFont(Font.font(font, 20));
		winningOdds.setStyle("-fx-background-radius: 10;");
		exit.setFont(Font.font(font, 20));
		exit.setStyle("-fx-background-radius: 10;");
		start.setFont(Font.font(font, 30));
		start.setStyle("-fx-background-radius: 10;");
		
		// Game Screen Buttons
		// Creating the Menu buttons
		gameRules = new Button("Rules");
		gameWO = new Button("Odds of Winning");
		gameExit = new Button("Exit");
		
		// Changing the style of the buttons
		gameRules.setFont(Font.font(font, 20));
		gameRules.setStyle("-fx-background-radius: 10;");
		gameWO.setFont(Font.font(font, 20));
		gameWO.setStyle("-fx-background-radius: 10;");
		gameExit.setFont(Font.font(font, 20));
		gameExit.setStyle("-fx-background-radius: 10;");
		
		// Drawing Screen Buttons
		// Creating the Menu buttons
		drawingRules = new Button("Rules");
		drawingWO = new Button("Odds of Winning");
		drawingExit = new Button("Exit");
		drawingNL = new Button("New Look!");
		
		// Changing the style of the buttons
		drawingRules.setFont(Font.font(font, 20));
		drawingRules.setStyle("-fx-background-radius: 10;");
		drawingWO.setFont(Font.font(font, 20));
		drawingWO.setStyle("-fx-background-radius: 10;");
		drawingExit.setFont(Font.font(font, 20));
		drawingExit.setStyle("-fx-background-radius: 10;");
		drawingNL.setFont(Font.font(font, 20));
		drawingNL.setStyle("-fx-background-radius: 10;");
		
		// Game Screen Buttons
		newLook = new Button("New Look!");
		spot1 = new Button("1");
		spot4 = new Button("4");
		spot8 = new Button("8");
		spot10 = new Button("10");
		drawing1 = new Button("1");
		drawing2 = new Button("2");
		drawing3 = new Button("3");
		drawing4 = new Button("4");
		rand = new Button("Random Spots");
		gameStart = new Button("START");
		
		// Changing the Style of the buttons
		newLook.setFont(Font.font(font, 20));
		newLook.setStyle("-fx-background-radius: 10;");
		spot1.setFont(Font.font(font, 20));
		spot1.setStyle("-fx-background-radius: 10;");
		spot4.setFont(Font.font(font, 20));
		spot4.setStyle("-fx-background-radius: 10;");
		spot8.setFont(Font.font(font, 20));
		spot8.setStyle("-fx-background-radius: 10;");
		spot10.setFont(Font.font(font, 20));
		spot10.setStyle("-fx-background-radius: 10;");
		drawing1.setFont(Font.font(font, 20));
		drawing1.setStyle("-fx-background-radius: 10;");
		drawing2.setFont(Font.font(font, 20));
		drawing2.setStyle("-fx-background-radius: 10;");
		drawing3.setFont(Font.font(font, 20));
		drawing3.setStyle("-fx-background-radius: 10;");
		drawing4.setFont(Font.font(font, 20));
		drawing4.setStyle("-fx-background-radius: 10;");
		rand.setFont(Font.font(font, 20));
		rand.setStyle("-fx-background-radius: 10;");
		gameStart.setFont(Font.font(font, 20));
		gameStart.setStyle("-fx-background-radius: 10;");
		
		// Defaulting the buttons to disabled for when we first click the start button
		drawing1.setDisable(true);
		drawing2.setDisable(true);
		drawing3.setDisable(true);
		drawing4.setDisable(true);
		rand.setDisable(true);
		gameStart.setDisable(true);
	}
	
	private void exitAction() {
		game.resetAll();
		resetButtons();
		gamePane.setCenter(betCard);
		
		// Enabling all the spot buttons
		spot1.setDisable(false);
		spot4.setDisable(false);
		spot8.setDisable(false);
		spot10.setDisable(false);
		
		// Disabling the start button
		gameStart.setDisable(true);
		
		// Turning them all the buttons back into white
		spot1.setStyle("-fx-background-radius: 10; -fx-background-color: White;");
		spot4.setStyle("-fx-background-radius: 10; -fx-background-color: White;");
		spot8.setStyle("-fx-background-radius: 10; -fx-background-color: White;");
		spot10.setStyle("-fx-background-radius: 10; -fx-background-color: White;");
		
		drawing1.setStyle("-fx-background-radius: 10; -fx-background-color: White;");
		drawing2.setStyle("-fx-background-radius: 10; -fx-background-color: White;");
		drawing3.setStyle("-fx-background-radius: 10; -fx-background-color: White;");
		drawing4.setStyle("-fx-background-radius: 10; -fx-background-color: White;");
		
		rand.setStyle("-fx-background-radius: 10; -fx-background-color: White;");
	}
	
	private void spotAction() {
		// Disabling all the spot buttons
		spot1.setDisable(true);
		spot4.setDisable(true);
		spot8.setDisable(true);
		spot10.setDisable(true);
		
		// Enabling the drawings buttons
		drawing1.setDisable(false);
		drawing2.setDisable(false);
		drawing3.setDisable(false);
		drawing4.setDisable(false);
	}
	
	private void drawingsAction() {
		// Disabling the drawings buttons
		drawing1.setDisable(true);
		drawing2.setDisable(true);
		drawing3.setDisable(true);
		drawing4.setDisable(true);
		
		// Enabling the 1-80 spots and rand buttons
		rand.setDisable(false);
		disableButtons(false);
	}
	
	private void randAction() {
		rand.setDisable(true);	// Disabling the rand button
		gameStart.setDisable(false);	// Enabling the start button
		resetButtons();
		disableButtons(true);
		
		Set<Integer> randSpots = game.autoQuickPick();
		
		for(int spot : randSpots) {
			int r = (spot - 1) / 10;	// Subtracting by 1 since rows are 0-7 and dividing by 10 to get the tens place
			int c = (spot - 1) % 10;	// Subtracting by 1 since cols are 0-9 and modulus by 10 to get the ones
			
			Button currSpot = betCardButtons[r][c];
			currSpot.setStyle("-fx-background-color: " + selectedColor + "; -fx-background-radius: 10;");
		}
	}
	
	private void resetButtons() {
		for(Button[] r : betCardButtons) {
			for(Button spot : r) {
				spot.setStyle("-fx-background-color: White; -fx-background-radius: 10;");
				spot.setDisable(true);
			}
		}
	}
	
	private void disableButtons(boolean isDisabled) {
		for(Button[] r : betCardButtons) {
			for(Button spot : r) {
				spot.setDisable(isDisabled);
			}
		}
	}
	
	private void setWelcomeText() {
		// Creating the text boxes for the welcome part
		welcomeTF = new TextArea("WELCOME!!");
		welcomeTF.setEditable(false);	// Shouldn't be editable
		welcomeTF.setPrefRowCount(0);
		welcomeTF.setPrefWidth(300);
		welcomeTF.setFont(Font.font(font, 35));
		
		description = new TextArea("\tThis is Keno, a simple lottery game with great cash"
				+ "\nprizes! You are allowed to choose how many spots"
				+ "\n(numbers) you want to play. Once all spots are chosen,"
				+ "\nthe drawings will begin. Per drawing, 20 out of 80 random"
				+ "\nnumbers will be selected as the winning numbers. These"
				+ "\nnumbers will be matched to your spots and used to select"
				+ "\n your prize.");
		description.setEditable(false);	// Shouldn't be editable
		description.setPrefWidth(300);
		description.setFont(Font.font(font, 25));
		
		// Making the TextAreas have transparent boxes
		welcomeTF.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
		description.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
	}
	
	public Scene createWelcome() {
		welcomePane = new BorderPane();
		
		exit.setDisable(true);	// Exit brings you to this screen so it should be disabled
		
		setWelcomeText();
		
		// Creating box for the menu
		menu = new TextArea("Menu:");
		menu.setEditable(false);	// Shouldn't be editable
		menu.setPrefRowCount(0);
		menu.setPrefColumnCount(2);
		menu.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + bgColor + ";");
		menu.setFont(Font.font(font, 20));
		
		// Putting the welcome and description text boxes together
		centerBox = new VBox(welcomeTF, description);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setPrefWidth(300);
		
		// Putting the menu and buttons in an HBOX
		Region r1 = new Region();	// Creating a region to separate the buttons so they are not right next to each other
		r1.setMinWidth(20);	// Setting the height of the region to separate the buttons
		Region r2 = new Region();
		r2.setMinWidth(20);
		Region r3 = new Region();	
		r3.setMinWidth(20);	
		buttons = new HBox(r1, rules, r2, winningOdds, r3, exit);
		topBP = new HBox(menu, buttons);
		
		// Putting the start button in a vbox to shift it up
		Region r4 = new Region();	
		r4.setMinHeight(40);
		bottomBox = new VBox(start, r4);
		bottomBox.setAlignment(Pos.CENTER);
		
		// Making regions to put on the left and right of the center boxes
		Region r = new Region();
		r.setMinWidth(50);
		Region r5 = new Region();
		r5.setMinWidth(50);
		
		// Putting everything in the border pane
		welcomePane.setTop(topBP);
		welcomePane.setCenter(centerBox);
		welcomePane.setBottom(bottomBox);
		welcomePane.setLeft(r);
		welcomePane.setRight(r5);
		welcomePane.setStyle("-fx-background-color: " + bgColor + ";");
		
		return new Scene(welcomePane, height, width);
	}
	
	public Scene createGame() {
		gamePane = new BorderPane();
		betCard = new GridPane();
		betCard.setHgap(8);
		betCard.setVgap(15);
		
		int num = 1;
		
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 10; c++) {
				int currNum = num;
				Button spot = new Button(String.valueOf(currNum));
				spot.setFont(Font.font(font, 20));
				spot.setStyle("-fx-background-radius: 10; -fx-background-color: White;");
				
				spot.setOnAction(e -> {
					if(game.containsSpot(currNum)) {	// Will be changed to: if(selectedSpots.contains(num))
						// When the spot is selected again, unselect it by changing the color
						spot.setStyle("-fx-background-color: White; -fx-background-radius: 10;");
						game.removeSpot(currNum);	// Unselecting the spot
					} else {
						if(game.numSpotsChosen() < game.getNumSpots()) {	// Only adds spots if there are any spots left
							// When the spot is selected for the first time, turn it gray
							spot.setStyle("-fx-background-color: " + selectedColor + "; -fx-background-radius: 10;");
							game.addSpot(currNum);	// Selecting the spot
						}
						else if(game.getNumSpots() == 0){
							gameStart.setDisable(true);	// For whenever a spot is unselected
						}
					}
					
					// Game can start if correct amount is selected
					if (game.numSpotsChosen() == game.getNumSpots() && game.getNumSpots() != 0) {
						// Only enabling the button if there is a number of spots chosen
						gameStart.setDisable(false);
					}
					else{
						gameStart.setDisable(true);	// For whenever a spot is unselected
					}
				});
				
				betCard.add(spot, c, r);	// Adding it to the specific row and column
				betCardButtons[r][c] = spot;	// Adding it to my array of buttons
				num++;		// Increasing the number
			}
		}
		
		// Disabling all the buttons for when the start button is first clicked
		
		disableButtons(true);
		//-----------
		// Right side of screen choices boxes
		chooseSpot = new TextArea("Choose Spots:");
		chooseSpot.setEditable(false);
		chooseSpot.setPrefHeight(0);
		chooseSpot.setPrefWidth(225);
		chooseSpot.setFont(Font.font(font, 20));
		chooseSpot.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
		
		chooseDrawing = new TextArea("Choose drawings:");
		chooseDrawing.setEditable(false);
		chooseDrawing.setPrefHeight(0);
		chooseDrawing.setPrefWidth(225);
		chooseDrawing.setFont(Font.font(font, 20));
		chooseDrawing.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
		
		pickSpots = new TextArea("Pick Spots or\nSelect Random");
		pickSpots.setEditable(false);
		pickSpots.setPrefHeight(65);
		pickSpots.setPrefWidth(225);
		pickSpots.setFont(Font.font(font, 20));
		pickSpots.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
		
		// buttons to choose how many spots
		HBox spotBttns = new HBox(spot1, spot4, spot8, spot10);
		spotBttns.setSpacing(10);
		HBox drawBttns = new HBox(drawing1, drawing2, drawing3, drawing4);
		drawBttns.setSpacing(10);
		VBox randBttn = new VBox(rand, gameStart);
		randBttn.setSpacing(15);
		//------------------
		
		// Creating the vbox for the choices
		choicesBox =  new VBox(chooseSpot, spotBttns, chooseDrawing, drawBttns, pickSpots, randBttn);
		choicesBox.setSpacing(10);
		
		// Creating another region to put a space in between the spots and choicesBox
		Region r10 = new Region();
		r10.setMinWidth(20);
		choicesBox2 = new HBox(r10, choicesBox);
		
		
		// Creating regions for in between the buttons
		Region r1 = new Region();	// Creating a region to separate the buttons so they are not right next to each other
		r1.setMinWidth(20);	// Setting the height of the region to separate the buttons
		Region r2 = new Region();
		r2.setMinWidth(20);
		Region r3 = new Region();	
		r3.setMinWidth(20);	
		Region r4 = new Region();	
		r4.setMinWidth(20);	
		
		// Creating box for the menu
		gameMenu = new TextArea("Menu:");
		gameMenu.setEditable(false);	// Shouldn't be editable
		gameMenu.setPrefRowCount(0);
		gameMenu.setPrefColumnCount(2);
		gameMenu.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: LightSteelBlue;");
		gameMenu.setFont(Font.font(font, 20));
		HBox btns = new HBox(r1, gameRules, r2, gameWO, r3, gameExit, r4, newLook);
		HBox topBox = new HBox(gameMenu, btns);
		
		// Putting everything in the border pane
		gamePane.setTop(topBox);
		gamePane.setCenter(betCard);
		gamePane.setRight(choicesBox2);
		
		
		gamePane.setStyle("-fx-background-color: " + bgColor + ";");
		
		return new Scene(gamePane, height, width);
	}
	
	public Scene createDrawing() {
		drawingPane = new BorderPane();
		
		// Creating regions for in between the buttons
		Region r1 = new Region();	// Creating a region to separate the buttons so they are not right next to each other
		r1.setMinWidth(20);	// Setting the height of the region to separate the buttons
		Region r2 = new Region();
		r2.setMinWidth(20);
		Region r3 = new Region();	
		r3.setMinWidth(20);	
		Region r4 = new Region();	
		r4.setMinWidth(20);	
		
		// Creating box for the menu
		drawingMenu = new TextArea("Menu:");
		drawingMenu.setEditable(false);	// Shouldn't be editable
		drawingMenu.setPrefRowCount(0);
		drawingMenu.setPrefColumnCount(2);
		drawingMenu.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + bgColor + ";");
		drawingMenu.setFont(Font.font(font, 20));
		HBox btns = new HBox(r1, drawingRules, r2, drawingWO, r3, drawingExit, r4, drawingNL);
		HBox topBox = new HBox(drawingMenu, btns);
		
		// Putting everything in the border pane
		drawingPane.setTop(topBox);
		
		// Left box that has current drawing number
		drawingLabel = new TextArea("DRAWING: ");
		drawingLabel.setEditable(false);
		drawingLabel.setPrefHeight(30);
		drawingLabel.setPrefWidth(180);
		drawingLabel.setFont(Font.font(font, 35));
		drawingLabel.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
		
		drawingNum = new TextArea("number");
		drawingNum.setEditable(false);
		drawingNum.setPrefHeight(70);
		drawingNum.setPrefWidth(180);
		drawingNum.setFont(Font.font(font, 40));
		drawingNum.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
		VBox leftBox = new VBox(drawingLabel, drawingNum);
		leftBox.setSpacing(10);
		leftBox.setAlignment(Pos.CENTER);
		leftBox.setPrefWidth(250);
		
		// Right box that has results info
		resultBox = new TextArea();
		resultBox.setEditable(false);
		resultBox.setWrapText(true);
		resultBox.setPrefWidth(450);
		resultBox.setFont(Font.font(font, 19));
		resultBox.setWrapText(true);
		resultBox.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-control-inner-background: " + textColor + ";");
		
		HBox centerBox = new HBox(leftBox, resultBox);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setSpacing(20);
		drawingPane.setCenter(centerBox);
		
		// Bottom button
		nextDrawing = new Button("Next Drawing");
		nextDrawing.setFont(Font.font(font, 20));
		nextDrawing.setStyle("-fx-background-radius: 10");
		Region rTop = new Region();
		rTop.setMinHeight(20);
		Region rBottom = new Region();
		rBottom.setMinHeight(20);
		VBox bottomBox = new VBox(nextDrawing);
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setSpacing(5);
		VBox bottom = new VBox(rTop, bottomBox, rBottom);
		drawingPane.setBottom(bottom);
		
		// Background
		drawingPane.setStyle("-fx-background-color: " + bgColor + ";");
		
		// Logic for when Drawing is more than 1
		nextDrawing.setOnAction(e -> {
			drawing();
		});
		
		return new Scene(drawingPane, height, width);
	}
	
	private void showNum(Set<Integer> nums, int indx) {
		if(indx >= nums.size()) {
			// Adding the game information to the text area
			resultBox.setText("DRAWING FINISHED: \n\n" +
					"  Amount matched: " + game.getMatchedCount() + "\n\n" +
					"  Numbers matched: " + game.getMatchedNumbers() + "\n\n" +
					"  Amount won in this drawing: $" + game.getAmountWonThisDrawing() + "0\n\n" +
					"  Total amount of winnings: $" + game.getTotalWinnings() + "0");
			// Enable the buttons once all numbers were drawn
			nextDrawing.setDisable(false);
			drawingRules.setDisable(false);
			drawingWO.setDisable(false);
			return;	// Just return since all numbers were shown
		}
		List<Integer> drawings = new ArrayList<>(nums);	// Making the set of drawings into a list so we can access each number
		int num = drawings.get(indx);
		drawingNum.setText("#" + (indx + 1) + ": " + String.valueOf(num));
		
		pause.setOnFinished(e -> showNum(nums, indx + 1));	// Once the pause is finished, show the next number
		pause.play();
	}
	
	private void drawing() {
		if (game.hasNextDrawing()) {
			isCurrDrawing = true;	// Setting the var to true since we are currently drawing
			nextDrawing.setDisable(true);
			Set<Integer> draw = game.playNextDrawing();
			resultBox.setText("DATA LOADING...\nPayout Info:\n" + game.payoutAsString());
			drawingLabel.setText("DRAWING " + String.valueOf(game.getCurrentDrawingNumber()) + ": ");
			if(game.isLastDrawing()) {
				nextDrawing.setText("DONE");
			}
			drawingRules.setDisable(true);
			drawingWO.setDisable(true);
			showNum(draw, 0);
		}
		else {
			nextDrawing.setDisable(true);
			
			String matchedString = game.res.matchedNumString();
			int numMatches = game.res.matchesSinceStart();
			int numDrawings = game.res.getDrawingsSinceStart();
			
			drawingNum.setText("Finished!");
			resultBox.setText("ALL DRAWINGS FINISHED: \n\n"
					+ "Totals since start of program:\n\n" 
					+ "\tTotal amount of winnings: $" + game.getTotalWinnings() + "0\n\n"
					+ "\tAll spots matched: " + matchedString + "\n\n"
					+ "\tTotal matches made: " + numMatches + "\n\n"
					+ "\tTotal drawings: " + numDrawings);
		}
	}
}