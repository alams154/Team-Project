package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane mainMenu = new BorderPane();
			Button powerButton = new Button();
			mainMenu.setRight(powerButton);
			Text title = new Text("Astronomical Catalog Data Receiver");
			TextFlow titleBox = new TextFlow(title);
			
			mainMenu.setTop(titleBox);
			TextArea filePath = new TextArea();
			mainMenu.setCenter(filePath);
			Button readFile = new Button();
			
			Scene scene1 = new Scene(mainMenu,1280,720);
			scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			BorderPane input = new BorderPane();
			Scene scene2 = new Scene(input,1280,720);
			scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			BorderPane results = new BorderPane();
			Scene scene3 = new Scene(results,1280,720);
			scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			BorderPane exit = new BorderPane();
			Scene scene4 = new Scene(exit,1280,720);
			scene4.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene1);
			primaryStage.show();
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
