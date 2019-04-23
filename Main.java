package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
  private static Hashtable<Integer, SourceObject> h;

  /**
   * @param filepath
   * @throws IOException
   * @throws ParseException
   */
  public static void parseJSON(String filepath) throws IOException, ParseException {
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
      Button powerButton = new Button();
      mainMenu.setRight(powerButton);
      Text title = new Text("Astronomical Catalog Data Receiver");
      TextFlow titleBox = new TextFlow(title);

      mainMenu.setTop(titleBox);
      TextArea filePath = new TextArea();
      mainMenu.setCenter(filePath);
      Button readFile = new Button();

      Scene scene1 = new Scene(mainMenu, 1280, 720);
      scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


      BorderPane input = new BorderPane();
      Scene scene2 = new Scene(input, 1280, 720);
      scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      BorderPane results = new BorderPane();
      Scene scene3 = new Scene(results, 1280, 720);
      scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      BorderPane exit = new BorderPane();
      Scene scene4 = new Scene(exit, 1280, 720);
      scene4.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      primaryStage.setScene(scene1);
      primaryStage.show();



    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
    h = new Hashtable<Integer, SourceObject>();
    String filepath = "astroexample1.json";
    parseJSON(filepath);
    System.out.println("Test: ");
    System.out.println("Source 2 z is: " + h.get(2).Z + ", should be " + 1.90);
    launch(args);
  }
}
