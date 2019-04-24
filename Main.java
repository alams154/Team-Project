package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Hashtable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
  private static Hashtable<Integer, SourceObject> h;

  /**
   * @param filepath
   * @throws IOException
   * @throws ParseException
   * @throws org.json.simple.parser.ParseException
   */
  public static void parseJSON(String filepath)
      throws IOException, ParseException, org.json.simple.parser.ParseException {
    // astroexample1
    FileReader f = new FileReader(filepath);
    // first, parse JSON file given the file path
    Object obj = new JSONParser().parse(f);
    // then create a JSON Object to represent file
    JSONObject jo = (JSONObject) obj;
    // create JSONArray to represent vertices from JSON file
    JSONArray packages = (JSONArray) jo.get("SSA22Field");
    // SSA22_Field
    // iterate though all vertices (packages)

    for (int i = 0; i < packages.size(); i++) {
      // create JSONObject to hold given package
      JSONObject jsonPackage = (JSONObject) packages.get(i);
      // look for/ save the name info for given package (vertex name)
      String type = (String) jsonPackage.get("type");
      // create a JSONArray object to store the dependencies of the given package
      // this is the same as the edges of this given vertex
      JSONArray array = (JSONArray) jsonPackage.get("parameterArray");
      // create a String[] array large enough to store these dependencies
      String[] params = new String[array.size()];
      JSONObject idpar = (JSONObject) array.get(0);
      String id = (String) idpar.get("id");
      int ide = Integer.parseInt(id);
      System.out.println("ID: " + id);
      // iterate through the JSON array

      for (int j = 0; j < array.size(); j++) {
        // copy the package/ vertex names into String[] array
        params[j] = (String) array.get(j).toString();


        System.out.println(params[j]);

      }
      double rightasc = 0;
      JSONObject ras = (JSONObject) array.get(1);
      String right = (String) ras.get("ra");
      if (right.length() > 0) {
        rightasc = Double.parseDouble((String) right);
      }

      double decl = 0;
      JSONObject decc = (JSONObject) array.get(2);
      String dec = (String) decc.get("dec");
      if (dec.length() > 0) {
        decl = Double.parseDouble((String) dec);
      }

      double hard = 0;
      JSONObject ha = (JSONObject) array.get(3);
      String hardf = (String) ha.get("hflux");
      if (hardf.length() > 0) {
        hard = Double.parseDouble(hardf);
      }

      double soft = 0;
      JSONObject sof = (JSONObject) array.get(4);
      String softf = (String) sof.get("sflux");
      if (softf.length() > 0) {
        soft = Double.parseDouble((String) softf);
      }

      double z = 0;
      JSONObject redshift = (JSONObject) array.get(5);
      String redz = (String) redshift.get("z");
      if (redz.length() > 0) {
        z = Double.parseDouble(redz);
      }

      double rmagnit = 0;
      JSONObject rmag = (JSONObject) array.get(6);
      String rmagn = (String) rmag.get("rmag");
      if (rmagn.length() > 0) {
        rmagnit = Double.parseDouble((String) rmagn);
      }

      SourceObject so = new SourceObject(ide, rightasc, decl, hard, soft, z, rmagnit);

      h.put(ide, so);
    }
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      BorderPane mainMenu = new BorderPane();

      // Layout Setup
      Image bg = new Image("file:hs-1996-01-a-large_web.jpg");
      mainMenu.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT,
          BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(
              BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
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

      Scene scene1 = new Scene(mainMenu, 780, 800);
      scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


      BorderPane input = new BorderPane();

      // Layout Setup


      Scene scene2 = new Scene(input, 1280, 720);
      scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      BorderPane results = new BorderPane();
      Scene scene3 = new Scene(results, 1280, 720);
      scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      BorderPane exit = new BorderPane();
      Scene scene4 = new Scene(exit, 1280, 720);
      scene4.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      // Action Setup
      FileChooser chooser = new FileChooser();
      fileChooser.setOnAction(e -> {
        File selectedFile = chooser.showOpenDialog(primaryStage);
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

      // Action Setup

      primaryStage.setScene(scene1);
      primaryStage.show();



    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws FileNotFoundException, IOException, ParseException,
      org.json.simple.parser.ParseException {
    h = new Hashtable<Integer, SourceObject>();
    String filepath = "astroexample1.json";
    parseJSON(filepath);
    System.out.println("Test: ");
    System.out.println("Source 2 z is: " + h.get(2).Z + ", should be " + 1.90);
    launch(args);
  }
}
