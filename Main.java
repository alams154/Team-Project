package application;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class Main extends Application {

	private Scene screen1Setup(Stage primaryStage, Scene scene1, Scene scene2, Scene scene3, Scene scene4) {
		BorderPane mainMenu = new BorderPane();
		
		Image bg = new Image("file:hs-1996-01-a-large_web.jpg");
		mainMenu.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
		Image power = new Image("file:Power.png");
		ImageView powerView = new ImageView(power);
		powerView.setFitHeight(40);
		powerView.setFitWidth(40);
		Button powerButton = new Button(null, powerView);
		mainMenu.setRight(powerButton);
		Text title = new Text("Astronomical Catalog Data Receiver");
		title.setFont(Font.font(40));
		title.setFill(Color.WHITE);
		TextFlow titleBox = new TextFlow(title);
		titleBox.setTextAlignment(TextAlignment.CENTER);
		mainMenu.setTop(titleBox);
		Button fileChooser = new Button("Select File");
		fileChooser.setFont(Font.font(20));
		mainMenu.setCenter(fileChooser);
		Button recentFile = new Button("Read most recent file");
		recentFile.setFont(Font.font(20));
		Button recentID = new Button("Read all data from most recent ID");
		recentID.setFont(Font.font(20));
		VBox bottomRight = new VBox(recentFile, recentID);
		bottomRight.setAlignment(Pos.BOTTOM_RIGHT);
		mainMenu.setBottom(bottomRight);
		
		FileChooser chooser = new FileChooser();
		fileChooser.setOnAction(e -> {
			File selectedFile = chooser.showOpenDialog(primaryStage);
			if(selectedFile.getName().substring(selectedFile.getName().length() - 5, selectedFile.getName().length()).equals(".json")) {
				primaryStage.setScene(scene2);
			}
			else {
				Alert invalidFile = new Alert (Alert.AlertType.INFORMATION, "Incorrect File Type, Choose a .json file");
				invalidFile.showAndWait().filter(response -> response == ButtonType.OK);
			}
		});
		recentFile.setOnAction(e -> {
			primaryStage.setScene(scene3);
		});
		recentID.setOnAction(e -> {
			primaryStage.setScene(scene3);
		});
		powerButton.setOnAction(e -> {
			primaryStage.setScene(scene4);
		});
		
		return new Scene(mainMenu, 780, 800);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene1 = new Scene(new BorderPane());
			
			// Layout Setup
			BorderPane input = new BorderPane();
			Scene scene2 = new Scene(input, 1280, 720);
			scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			BorderPane results = new BorderPane();
			Scene scene3 = new Scene(results, 1280, 720);
			scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			BorderPane exit = new BorderPane();
			Scene scene4 = new Scene(exit, 1280, 720);
			scene4.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			scene1 = screen1Setup(primaryStage, scene1, scene2, scene3, scene4);
			// Layout Setup

			scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());



			primaryStage.setScene(scene1);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
