import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.util.HashMap;

public class RPLS_Client_GUI extends Application {

	Client clientConnection;
	TextField tf1, tf2;
	Button btn1, rock, paper, scissors, lizard, spock, playAgain, quit, refresh, challenge;
	BorderPane bp1, bp2, bp3, bp4;
	Text text1, text2, warningText, winOrLose, clientListTxt, gameText, yourText, opponentText;
	HBox introHBox, tf1Box, tf2Box, gameChoices, twoBtns, listButtons;
	VBox txtFields, winOrLoseElems, rightSide, topText, yourSide, opponentSide;
	ListView listItems, listOfClients;
	Image playedYourself;
	ImageView v0, v1, v2, v3, v4, v5;
	String clientName;

	int picMeasure = 80;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("RPSLS Log In");

		tf1 = new TextField();
		tf2 = new TextField();
		btn1 = new Button("Log In");
		text1 = new Text("Port Number");
		text2 = new Text("IP Address");
		warningText = new Text("");
		tf1Box = new HBox(10, text1, tf1);
		tf1Box.setAlignment(Pos.CENTER_RIGHT);
		tf2Box = new HBox(10, text2, tf2);
		tf2Box.setAlignment(Pos.CENTER_RIGHT);
		txtFields = new VBox(10, tf1Box, tf2Box, warningText);
		txtFields.setAlignment(Pos.CENTER);
		introHBox = new HBox(10, txtFields, btn1);

		// sets first scene
		bp1 = new BorderPane();
		bp1.setCenter(introHBox);
		bp1.setPadding(new Insets(10));

		// client list scene
		listOfClients = new ListView<String>();
		clientListTxt = new Text("");

		playedYourself = new Image("20664707_115910809068836_3483675906501865243_n.jpg");
		v0 = new ImageView(playedYourself);
		v0.setFitHeight(200);
		v0.setFitWidth(200);
		v0.setPreserveRatio(true);

		// Section: Images
		// sets images for buttons
		Image pic = new Image("i500XSw__400x400.jpg");
		v1 = new ImageView(pic);
		v1.setFitHeight(picMeasure);
		v1.setFitWidth(picMeasure);
		v1.setPreserveRatio(true);

		Image pic2 = new Image("ampad-85-in-x-1175-in-canary-50ct.jpg");
		v2 = new ImageView(pic2);
		v2.setFitHeight(picMeasure);
		v2.setFitWidth(picMeasure);
		v2.setPreserveRatio(true);

		Image pic3 = new Image("9291414082611p.jpg");
		v3 = new ImageView(pic3);
		v3.setFitHeight(picMeasure);
		v3.setFitWidth(picMeasure);
		v3.setPreserveRatio(true);

		Image pic4 = new Image("profileIcon_7o98i87a8mn21.jpg");
		v4 = new ImageView(pic4);
		v4.setFitHeight(picMeasure);
		v4.setFitWidth(picMeasure);
		v4.setPreserveRatio(true);

		Image pic5 = new Image("FxE-_STw.jpeg");
		v5 = new ImageView(pic5);
		v5.setFitHeight(picMeasure);
		v5.setFitWidth(picMeasure);
		v5.setPreserveRatio(true);

		rock = new Button();
		rock.setGraphic(v1);
		paper = new Button();
		paper.setGraphic(v2);
		scissors = new Button();
		scissors.setGraphic(v3);
		lizard = new Button();
		lizard.setGraphic(v4);
		spock = new Button();
		spock.setGraphic(v5);

		// makes the refresh and challenge button
		refresh = new Button("Refresh");
		challenge = new Button("Challenge");

		// makes the play again button and quit button
		playAgain = new Button("Play Again");
		quit = new Button("Quit");
		winOrLose = new Text("");

		// makes win or lose scene


		listItems = new ListView<String>();

		// Section: Button Actions
		// what happens what you press a certain picture
		rock.setOnAction(e->{
			clientConnection.gameInfo.setPlayerChoice("Rock");
			clientConnection.gameInfo.setTextCommunication("Game");
			clientConnection.send();

			buttonsOff();
		});

		paper.setOnAction(e->{
			clientConnection.gameInfo.setPlayerChoice("Paper");
			clientConnection.gameInfo.setTextCommunication("Game");
			clientConnection.send();

			buttonsOff();
		});

		scissors.setOnAction(e->{
			clientConnection.gameInfo.setPlayerChoice("Scissors");
			clientConnection.gameInfo.setTextCommunication("Game");
			clientConnection.send();

			buttonsOff();
		});

		lizard.setOnAction(e->{
			clientConnection.gameInfo.setPlayerChoice("Lizard");
			clientConnection.gameInfo.setTextCommunication("Game");
			clientConnection.send();

			buttonsOff();
		});

		spock.setOnAction(e->{
			clientConnection.gameInfo.setPlayerChoice("Spock");
			clientConnection.gameInfo.setTextCommunication("Game");
			clientConnection.send();

			buttonsOff();
		});

		// user wants to play again
		// this section doesn't work
		/*
		playAgain.setOnAction(e2->{
			primaryStage.setScene(createClientListScene());
			primaryStage.setTitle(clientName);

			clientConnection.gameInfo.resetInfo();
			clientConnection.gameInfo.setTextCommunication("Reset");
			clientConnection.send();

			challenge.setOnAction(event->{
				try {
					clientConnection.gameInfo.setTextCommunication(
							listOfClients.getSelectionModel().getSelectedItem().toString());

					clientConnection.send();
				} catch (Exception e1){}
			});

			gameText = new Text("");



			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					Platform.exit();
					System.exit(0);
				}
			});
		});

		 */


		// Section: user quits
		quit.setOnAction(e->{
			Platform.exit();
			System.exit(0);
		});

		// Section: logs in
		this.btn1.setOnAction(e->{
			if(!(tf1.getText().equals("5555") && tf2.getText().equals("127.0.0.1"))){ // if ip and port number are wrong
				tf1.clear();
				tf2.clear();
				warningText.setText("Error: not valid port number or IP address");
			} else { // makes game gui
				clientConnection = new Client(data->{
					Platform.runLater(()->{
						if(primaryStage.getTitle().equals("RPSLS Log In")) {
							clientName = clientConnection.gameInfo.getPlayerCount();
							primaryStage.setTitle(clientName);
						}

						listOfClients.getItems().clear();
						for(int i=0;i<clientConnection.gameInfo.getListOfPlayers().size();i++) {
							listOfClients.getItems().add(clientConnection.gameInfo.getElemOfList(i));
						}

						if(data.toString().equals("You can't play yourself!")){
							v0.setVisible(true);
							clientListTxt.setText("");
						}

						if(data.toString().equals("Other person is already in another game")){
							v0.setVisible(false);
							clientListTxt.setText("Other person is already in another game");
						}

						if(data.toString().equals("Game")){
							v0.setVisible(false);
							primaryStage.setScene(createGameScene());
							primaryStage.setTitle("Rock, Paper, Scissors, Lizard, Spock");
							clientConnection.gameInfo.setTextCommunication("Game");
							clientConnection.send();
						}

						if(data.toString().matches("Client\\s\\d*")){
							clientConnection.gameInfo.setTextCommunication(data.toString());
							clientConnection.send();
						}

						if(data.toString().equals("Waiting")) {
							gameText.setText("Waiting for other player to decide...");
							clientConnection.gameInfo.setTextCommunication("Game");
							clientConnection.send();
						}

						if(data.toString().matches("Win|Lose|Tie")){
							primaryStage.setScene(createWinOrLoseScene(data.toString(),
									clientConnection.gameInfo.getP1Plays(), clientConnection.gameInfo.getP2Plays()));
							primaryStage.setTitle("It's a " + data.toString());
						}

						clientConnection.gameInfo.setTextCommunication("");
					});
				});

				challenge.setOnAction(event->{
					try {
						clientConnection.gameInfo.setTextCommunication(
								listOfClients.getSelectionModel().getSelectedItem().toString());

						clientConnection.send();
					} catch (Exception e1){}
				});

				clientConnection.start();

				gameText = new Text("");

				primaryStage.setScene(createClientListScene());

				primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						Platform.exit();
						System.exit(0);
					}
				});

				// Section: user wants to play again


			}
		});

		Scene scene = new Scene(bp1,330,100);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Scene createClientListScene(){
		v0.setVisible(false);
		listButtons = new HBox(10, challenge);
		rightSide = new VBox(10, v0, clientListTxt, listButtons);
		listButtons.setAlignment(Pos.CENTER);
		rightSide.setAlignment(Pos.CENTER);
		bp3 = new BorderPane();
		bp3.setLeft(listOfClients);
		bp3.setCenter(rightSide);
		rightSide.setAlignment(Pos.CENTER);
		bp3.setPadding(new Insets(10));

		return new Scene(bp3, 500,350);
	}

	public Scene createGameScene(){ // creates game GUI
		gameChoices = new HBox(10, rock, paper, scissors, lizard, spock);
		gameChoices.setAlignment(Pos.CENTER);
		topText = new VBox(10, gameText);
		topText.setAlignment(Pos.CENTER);
		bp2 = new BorderPane();
		bp2.setPadding(new Insets(10));
		bp2.setBottom(gameChoices);
		bp2.setTop(topText);

		return new Scene(bp2, 550,150);
	}

	public Scene createWinOrLoseScene(String input, String you, String opponent){ // creates win or lose screen
		resetImages();

		twoBtns = new HBox(10, playAgain, quit);
		twoBtns.setAlignment(Pos.CENTER);
		yourText = new Text("You played " + you);
		opponentText = new Text("Your opponent played \n" + opponent);

		if(you.equals("Rock")){
			yourSide = new VBox(10, yourText, v1);
		} else if (you.equals("Paper")){
			yourSide = new VBox(10, yourText, v2);
		} else if (you.equals("Scissors")){
			yourSide = new VBox(10, yourText, v3);
		} else if (you.equals("Lizard")){
			yourSide = new VBox(10, yourText, v4);
		} else if (you.equals("Spock")){
			yourSide = new VBox(10, yourText, v5);
		}

		resetImages();
		yourSide.setAlignment(Pos.CENTER_RIGHT);

		if(opponent.equals("Rock")){
			opponentSide = new VBox(10, opponentText, v1);
		} else if (opponent.equals("Paper")){
			opponentSide = new VBox(10, opponentText, v2);
		} else if (opponent.equals("Scissors")){
			opponentSide = new VBox(10, opponentText, v3);
		} else if (opponent.equals("Lizard")){
			opponentSide = new VBox(10, opponentText, v4);
		} else if (opponent.equals("Spock")){
			opponentSide = new VBox(10, opponentText, v5);
		}

		resetImages();
		opponentSide.setAlignment(Pos.CENTER_LEFT);

		winOrLose = new Text(input);
		winOrLoseElems = new VBox(20, winOrLose, twoBtns);
		winOrLoseElems.setAlignment(Pos.CENTER);

		bp4 = new BorderPane();
		bp4.setCenter(winOrLoseElems);
		bp4.setLeft(yourSide);
		bp4.setRight(opponentSide);
		bp4.setPadding(new Insets(20));

		return new Scene(bp4, 600, 500);
	}

	public void buttonsOn(){
		rock.setDisable(false);
		paper.setDisable(false);
		scissors.setDisable(false);
		lizard.setDisable(false);
		spock.setDisable(false);
	}

	public void buttonsOff(){
		rock.setDisable(true);
		paper.setDisable(true);
		scissors.setDisable(true);
		lizard.setDisable(true);
		spock.setDisable(true);
	}

	public void resetImages(){
		Image pic = new Image("i500XSw__400x400.jpg");
		v1 = new ImageView(pic);
		v1.setFitHeight(picMeasure);
		v1.setFitWidth(picMeasure);
		v1.setPreserveRatio(true);

		Image pic2 = new Image("ampad-85-in-x-1175-in-canary-50ct.jpg");
		v2 = new ImageView(pic2);
		v2.setFitHeight(picMeasure);
		v2.setFitWidth(picMeasure);
		v2.setPreserveRatio(true);

		Image pic3 = new Image("9291414082611p.jpg");
		v3 = new ImageView(pic3);
		v3.setFitHeight(picMeasure);
		v3.setFitWidth(picMeasure);
		v3.setPreserveRatio(true);

		Image pic4 = new Image("profileIcon_7o98i87a8mn21.jpg");
		v4 = new ImageView(pic4);
		v4.setFitHeight(picMeasure);
		v4.setFitWidth(picMeasure);
		v4.setPreserveRatio(true);

		Image pic5 = new Image("FxE-_STw.jpeg");
		v5 = new ImageView(pic5);
		v5.setFitHeight(picMeasure);
		v5.setFitWidth(picMeasure);
		v5.setPreserveRatio(true);
	}

}

