package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class Main extends Application {
  // hash table stores SourceObjects, indexing starts at 1
  private static Hashtable<Integer, SourceObject> allData;
  // hash table stores SourceObjects to write to a file, indexing starts at 1
  private static Hashtable<Integer, SourceObject> dataToWrite;
  // Scene objects store each scene (page) of GUI
  private static Scene scene1, scene2, scene3, scene4;
  // types stores the type of each SourceObject in the hashtable
  private static ArrayList<String> types;
  // flag to tell if file has been read in yet
  private static boolean fileRead;

  private static int IDsAdded;

  private static String fileToWriteTo;

  private static JSONArray astroRegion;

  private static File newFile;

  /**
   * @param filepath
   * @throws IOException
   * @throws ParseException
   * @throws org.json.simple.parser.ParseException
   * @throws org.json.simple.parser.ParseException
   */
  private static void parseJSON(String filepath)
      throws IOException, ParseException, org.json.simple.parser.ParseException {
    // astroexample1
    FileReader f = new FileReader(filepath);
    // first, parse JSON file given the file path
    Object obj = new JSONParser().parse(f);
    // then create a JSON Object to represent file
    JSONObject jo = (JSONObject) obj;
    // create JSONArray to represent vertices from JSON file
    astroRegion = (JSONArray) jo.get("AstronomicalData");
    // SSA22_Field
    // iterate though all vertices (packages)

    for (int i = 0; i < astroRegion.size(); i++) {
      // create JSONObject to hold given package
      JSONObject jsonPackage = (JSONObject) astroRegion.get(i);
      // look for/ save the name info for given package (vertex name)
      String type = (String) jsonPackage.get("type");
      // System.out.println(type);
      if (type != null) {
        types.add(type.toString());
      }
      // create a JSONArray object to store the dependencies of the given package
      // this is the same as the edges of this given vertex
      JSONArray array = (JSONArray) jsonPackage.get("parameterArray");
      // create a String[] array large enough to store these dependencies
      String[] params = new String[array.size()];
      JSONObject idpar = (JSONObject) array.get(0);
      String id = (String) idpar.get("id");
      int ide = Integer.parseInt(id);
      // System.out.println("ID: " + id);
      // iterate through the JSON array

      for (int j = 0; j < array.size(); j++) {
        // copy the package/ vertex names into String[] array
        params[j] = (String) array.get(j).toString();

        // System.out.println(params[j]);

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

      allData.put(ide, so);
    }
  }

  private Scene screen1Setup(Stage primaryStage) {
    BorderPane mainMenu = new BorderPane();

    Image bg = new Image("file:hs-1996-01-a-large_web.jpg");
    mainMenu.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
    Image power = new Image("file:Power.png");
    ImageView powerView = new ImageView(power);
    powerView.setFitHeight(60);
    powerView.setFitWidth(60);
    Button powerButton = new Button(null, powerView);
    mainMenu.setRight(powerButton);
    Text title = new Text("Astronomical Catalog Data Receiver");
    title.setFont(Font.font("Helvetica", 48));
    title.setFill(Color.WHITE);
    TextFlow titleBox = new TextFlow(title);
    titleBox.setTextAlignment(TextAlignment.CENTER);
    mainMenu.setTop(titleBox);
    Button fileChooser = new Button("Select File");
    fileChooser.setFont(Font.font("Helvetica", 42));
    mainMenu.setCenter(fileChooser);
    Button recentFile = new Button("Read most recent file");
    recentFile.setFont(Font.font("Helvetica", 36));
    Button recentID = new Button("Read all data from most recent ID");
    recentID.setFont(Font.font("Helvetica", 36));
    Button skipLoad = new Button("Skip File Selection");
    skipLoad.setFont(Font.font("Helvetica", 36));
    VBox bottomRight = new VBox(skipLoad, recentFile, recentID);
    bottomRight.setAlignment(Pos.BOTTOM_RIGHT);
    bottomRight.setSpacing(25);
    bottomRight.setTranslateY(-20);
    mainMenu.setBottom(bottomRight);

    FileChooser chooser = new FileChooser();
    fileChooser.setOnAction(e -> {
      File selectedFile = chooser.showOpenDialog(primaryStage);
      System.out.println(selectedFile.getName());
      if (selectedFile.getName()
          .substring(selectedFile.getName().length() - 5, selectedFile.getName().length())
          .equals(".json")) {
        try {
          fileRead = true;
          parseJSON("./" + selectedFile.getName() + "");

          scene2 = screen2Setup(primaryStage);
          primaryStage.setScene(scene2);

        } catch (IOException e1) {

          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (ParseException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (org.json.simple.parser.ParseException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }

      } else {
        Alert invalidFile =
            new Alert(Alert.AlertType.INFORMATION, "Incorrect File Type, Choose a .json file");
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
    skipLoad.setOnAction(e -> {
      primaryStage.setScene(scene2);
    });

    return new Scene(mainMenu, 1200, 800);
  }

  private Scene screen2Setup(Stage primaryStage) {
    BorderPane s2bp = new BorderPane();
    Image bg = new Image("file:hs-1996-01-a-large_web.jpg");
    s2bp.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
    inputIDlist(s2bp);
    inputRightPaneScreen2(s2bp, primaryStage);
    return new Scene(s2bp, 1200, 800);

  }

  private Scene screen3Setup(Stage primaryStage) {
    BorderPane results = new BorderPane();
    Image bg = new Image("file:hs-1996-01-a-large_web.jpg");
    results.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
    inputTopPaneScreen3(results);
    inputBottomPaneScreen3(results, primaryStage);
    return new Scene(results, 1200, 800);
  }

  /**
   * @param s
   * @return
   */
  private Scene screen4Setup(Stage s) {
    BorderPane pane = new BorderPane();
    Text text1 = new Text("Thank you for using \n      our program!");
    text1.setFill(Color.WHITE);
    text1.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
//    Text text2 = new Text("Now exiting...");
//    text2.setFill(Color.WHITE);
//    text2.setFont(Font.font("Helvetica", FontPosture.ITALIC, 20));
    Button done = new Button("Close Program");
    done.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
  //  VBox bottomRight = new VBox(text2);
 //   bottomRight.setAlignment(Pos.BOTTOM_RIGHT);
    pane.setRight(done);
    pane.setCenter(text1);
    pane.setPrefSize(500, 300);
  //  pane.setBottom(bottomRight);

    Image image = new Image("file:hs-1996-01-a-large_web.jpg");

    Scene exitScene = new Scene(pane, 1200, 800);
    BackgroundImage back = new BackgroundImage(image, BackgroundRepeat.REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    pane.setBackground(new Background(back));
    done.setOnAction(e -> {
      s.close();
    });
    return exitScene;
  }

  /**
   * This method adds the list of source IDs to the GUI
   * 
   * @param bp
   */
  private void inputIDlist(BorderPane bp) {
    ArrayList<String> ids = new ArrayList<String>();
    ListView<Text> list = new ListView<Text>();
    Label listId = new Label("Source ID List");
    listId.setFont((Font.font("Helvetica", FontWeight.BOLD, 24)));
    ObservableList<Text> items = FXCollections.observableArrayList();
    if (allData.get(1) != null)
      System.out.println(allData.get(1).getID());
    for (Integer i : allData.keySet()) {
      // add id items
      items.add(new Text(
          "" + (int) allData.get(allData.size() - i + 1).getID() + ", Type: " + types.get(types.size() - i)));
    }
    
    listId.setTextFill(Color.WHITE);
    list.setItems(items);
    // HBox listCount = new HBox();

    bp.setTop(listId);
    for (Text t : list.getItems()) {
      t.setFont((Font.font("Helvetica", FontWeight.BOLD, 20)));
      // (Font.font("Helvetica", FontWeight.BOLD, 24));
    }
    // listCount.getChildren().add(list);
    // VBox counter = new VBox();
    // Label idCount = new Label("IDs saved:");
    // idCount.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
    // counter.getChildren().add(idCount);
    // Text counts = new Text("" + IDsAdded);
    // counter.getChildren().add(idCount);
    // counter.getChildren().add(counts);
    // listCount.getChildren().add(counter);
    // bp.setLeft(listCount);
    bp.setLeft(list);

    // vbox.setBackground(new Background(new BackgroundFill(Color.WHITE,
    // CornerRadii.EMPTY,
    // Insets.EMPTY)));
  }

  private void inputRightPaneScreen2(BorderPane pane, Stage primaryStage) {
    TextField enterID = new TextField();
    enterID.setPromptText("Enter Valid ID");
    Label label1 = new Label("Search ID: ");

    label1.setTextFill(Color.WHITE);
    label1.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
    Label labelParams = new Label("Select Parameters: ");
    labelParams.setTextFill(Color.WHITE);
    labelParams.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
    HBox hb = new HBox(new VBox(label1, new Label(""), labelParams),
        new VBox(enterID, new Label(""), new Label("")));
    hb.setAlignment(Pos.BOTTOM_RIGHT);

    CheckBox ra = new CheckBox("Right Ascension");
    ra.setTextFill(Color.WHITE);
    ra.setFont(Font.font("Helvetica", 24));
    CheckBox dec = new CheckBox("Declination");
    dec.setTextFill(Color.WHITE);
    dec.setFont(Font.font("Helvetica", 24));
    CheckBox hflux = new CheckBox("Hard-Band Flux");
    hflux.setTextFill(Color.WHITE);
    hflux.setFont(Font.font("Helvetica", 24));
    CheckBox sflux = new CheckBox("Soft-Band Flux");
    sflux.setTextFill(Color.WHITE);
    sflux.setFont(Font.font("Helvetica", 24));
    CheckBox z = new CheckBox("Redshift");
    z.setTextFill(Color.WHITE);
    z.setFont(Font.font("Helvetica", 24));
    CheckBox rmag = new CheckBox("R-Band Magnitude");
    rmag.setTextFill(Color.WHITE);
    rmag.setFont(Font.font("Helvetica", 24));
    CheckBox all = new CheckBox("Select All");
    all.setTextFill(Color.WHITE);
    all.setFont(Font.font("Helvetica", 24));

    VBox first = new VBox(ra, dec, hflux);
    first.setSpacing(100);
    VBox sec = new VBox(sflux, z, rmag);
    sec.setSpacing(100);
    HBox boxes = new HBox(first, sec);
    boxes.setSpacing(30);

    VBox moreboxes = new VBox(hb, boxes, all);
    moreboxes.setSpacing(100);
    moreboxes.setTranslateX(-20);
    moreboxes.setTranslateY(10);
    moreboxes.setAlignment(Pos.TOP_CENTER);
    pane.setRight(moreboxes);
    all.setOnAction(e -> checkall(ra, dec, hflux, sflux, z, rmag, all));
    // bottom buttons
    Button addSource = new Button("Add Source");
    addSource.setFont(Font.font("Helvetica", 18));
    Button display = new Button("Display Source Data");
    display.setFont(Font.font("Helvetica", 18));
    Button write = new Button("Write");
    write.setFont(Font.font("Helvetica", 18));

    Button exit = new Button("Exit");
    exit.setFont(Font.font("Helvetica", 18));
    TextField enterFileName = new TextField();
    enterFileName.setPromptText("Enter File Name");
    
    
    Button help = new Button("Help");
    help.setOnAction(e -> 
           try {
              final Stage helpPop = new Stage();
              helpPop.initModality(Modality.APPLICATION_MODAL);
              helpPop.initOwner(primaryStage);   
              Label helpLabel = new Label("Help");
              TextArea helpText = new TextArea("How to operate the program:\n"
              +" There are many options that you have for going through the data\n"
              +"that you read in from the file given.\n"
              +"\n" +
              "To add a new source, click the add button and add the preferred data\n"
              +"to the popup window that appears.\n"
              +"\n"+
              "To display data, check the correct data that is desired from the checkboxes\n"
              +"on the right and then click on the display button. A popup will appear, and\n"
              +"if wanted, the data displayed can be saved to be written to another file by\n"
              +"clicking on the save and exit button.\n"+
              "\n"+
              "To write to another file, click on the write button. If there is a valid ID in\n"
              "the text field, all the data from that specific source is written to a separate json file.\n
              If there is no ID written in the text field, then all the data from the IDs on the \n
              second list will be written to a separate json file.");
           
           } catch (exception e) {
              Alert helpError = new Alert(Alert.AlertType.INFORMATION, "Something went wrong when loading the help popup.");

                    helpError.showAndWait().filter(response -> response == ButtonType.OK);
           });
    exit.setOnAction(e -> {
      primaryStage.setScene(scene4);
    });
    display.setOnAction(

        e -> {
          if (fileRead) {
            try {
              final Stage disp = new Stage();

              disp.initModality(Modality.APPLICATION_MODAL);

              disp.initOwner(primaryStage);
              HBox saveExt = new HBox();
              VBox vbox1 = new VBox();
              int searchID = Integer.parseInt(enterID.getText().trim());
              System.out.println("adding " + searchID + " to hash");
              dataToWrite.put(IDsAdded + 1, allData.get(searchID));
              IDsAdded++;
System.out.println("total IDs: " + IDsAdded);
              Button saveSrc = new Button("Save this source");
              saveSrc.setFont(Font.font("Helvetica", 30));
              Button ext = new Button("Exit without saving");
              ext.setFont(Font.font("Helvetica", 30));
              saveExt.getChildren().add(saveSrc);
              saveExt.getChildren().add(ext);
              if (!allData.containsKey(searchID)) {

                Alert invalidID = new Alert(Alert.AlertType.INFORMATION, "ID not valid.");

                invalidID.showAndWait().filter(response -> response == ButtonType.OK);

              }



              if (ra.isSelected()) {
                Label id_disp =
                    new Label("Displaying ID #" + searchID + ", " + types.get(searchID - 1));
                id_disp.setFont(Font.font("Helvetica", 45));
                Text right = new Text("Right Ascension: " + allData.get(searchID).getRA() + "°");

                right.setFont(Font.font("Helvetica", 30));
                vbox1.getChildren().add(id_disp);
                vbox1.getChildren().add(right);

              } else {
                dataToWrite.get(IDsAdded+1).setRA(-1.0);
              }
              if (dec.isSelected()) {

                Text decl = new Text("Declination: " + allData.get(searchID).getDEC() + "°");

                decl.setFont(Font.font("Helvetica", 30));

                vbox1.getChildren().add(decl);

              } else {
                dataToWrite.get(IDsAdded+1).setDEC(-1.0);
              }

              if (hflux.isSelected()) {

                Text horiz = new Text(
                    "Hard-Band Flux: " + allData.get(searchID).getHflux() + " cm^(-2) sec^(-1)");

                horiz.setFont(Font.font("Helvetica", 30));

                vbox1.getChildren().add(horiz);

              } else {
                dataToWrite.get(IDsAdded+1).setHflux(-1.0);
              }

              if (sflux.isSelected()) {

                Text soft = new Text(
                    "Soft-Band Flux: " + allData.get(searchID).getSflux() + " cm^(-2) sec^(-1)");

                soft.setFont(Font.font("Helvetica", 30));

                vbox1.getChildren().add(soft);

              } else {
                dataToWrite.get(IDsAdded+1).setSflux(-1.0);
              }

              if (z.isSelected()) {

                Text zee = new Text("Redshift: " + allData.get(searchID).getZ());

                zee.setFont(Font.font("Helvetica", 30));

                vbox1.getChildren().add(zee);

              } else {
                dataToWrite.get(IDsAdded+1).setZ(-1.0);
              }

              if (rmag.isSelected()) {

                Text mag = new Text("R-Band Magnitude: " + allData.get(searchID).getRmag());

                mag.setFont(Font.font("Helvetica", 30));

                vbox1.getChildren().add(mag);

              } else {
                dataToWrite.get(IDsAdded+1).setRmag(-1.0);
              }

              vbox1.getChildren().add(saveExt);

              Scene dialogScene = new Scene(vbox1, 800, 500);

              disp.setScene(dialogScene);

              disp.setTitle("Display Source");
              // add a button for closing and a button for saving
              disp.show();

              saveSrc.setOnAction(f -> {

                disp.close();
              });
              ext.setOnAction(g -> {
                System.out.println("removing " + searchID + " to hash");
                dataToWrite.remove(IDsAdded + 1);
                IDsAdded--;

                disp.close();
              });

            } catch (NumberFormatException f) {

              Alert invalidFile = new Alert(Alert.AlertType.INFORMATION, "ID not valid.");

              invalidFile.showAndWait().filter(response -> response == ButtonType.OK);

            }

          }



        });
    FileChooser chooser = new FileChooser();
    addSource.setOnAction(e -> {
      File selectedFile = chooser.showOpenDialog(primaryStage);
      if (selectedFile.getName()
          .substring(selectedFile.getName().length() - 5, selectedFile.getName().length())
          .equals(".json")) {
        primaryStage.setScene(scene2);
      } else {
        Alert invalidFile =
            new Alert(Alert.AlertType.INFORMATION, "Incorrect File Type, Choose a .json file");
        invalidFile.showAndWait().filter(response -> response == ButtonType.OK);
      }
    });
    write.setOnAction(e -> {
      fileToWriteTo = enterFileName.getText().trim();
      try {
        if (!writeToFile(fileToWriteTo) || !fileToWriteTo
            .substring(fileToWriteTo.length() - 5, fileToWriteTo.length()).equals(".json")) {
          Alert invalidFile = new Alert(Alert.AlertType.INFORMATION,
              "Duplicate or invalid file, choose a .json file");
          invalidFile.showAndWait().filter(response -> response == ButtonType.OK);
        } else {
          primaryStage.setScene(scene3);
        }
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }

    });
    HBox botRight = new HBox(write, enterFileName);

    HBox botButtons = new HBox(addSource, display, botRight, exit);
    botButtons.setSpacing(50);
    botButtons.setAlignment(Pos.CENTER);
    botButtons.setTranslateY(-30);
    pane.setBottom(botButtons);
  }

  private void inputTopPaneScreen3(BorderPane bp) {

    Label top = new Label("Successfully written " + IDsAdded + " IDs to a file");
    top.setFont((Font.font("Helvetica", FontWeight.BOLD, 36)));
    top.setTextFill(Color.WHITE);

    bp.setTop(top);
  }

  private boolean writeToFile(String fileName) throws IOException {
    File file = new File("./" + fileName);
    newFile = file;
    JSONObject title = new JSONObject();

    JSONArray allSources = new JSONArray();
    // Create the file
    if (!file.createNewFile()) {
      return false;
      
    } 

    System.out.println("writing file, size of hash is " + dataToWrite.size());
    for (int i = 1; i <= dataToWrite.size(); i++) {
      SourceObject cur = dataToWrite.get(i);
      JSONArray paramArr = new JSONArray();
      // First Employee
      JSONObject id = new JSONObject();
      
      id.put("id", "" + cur.getID());
      JSONObject ra = new JSONObject();
      ra.put("ra", "" + cur.getRA());
      JSONObject dec = new JSONObject();
      dec.put("dec", "" + cur.getDEC());
      JSONObject hflux = new JSONObject();
      hflux.put("hflux", "" + cur.getHflux());
      JSONObject sflux = new JSONObject();
      sflux.put("sflux", "" + cur.getSflux());
      JSONObject z = new JSONObject();
      z.put("z", "" + cur.getZ());
      JSONObject rmag = new JSONObject();
      rmag.put("rmag", "" + cur.getRmag());

      paramArr.add(id);
      paramArr.add(ra);
      paramArr.add(dec);
      paramArr.add(hflux);
      paramArr.add(sflux);
      paramArr.add(z);
      paramArr.add(rmag);
      JSONObject cursrc = new JSONObject();
      cursrc.put("type:", types.get(i - 1));
      cursrc.put("parameterArray", paramArr);

      allSources.add(cursrc);
      // allSources.add(params);

    }


    title.put("ModifiedAstronomicalData", allSources);
    // Write JSON file
    try (FileWriter files = new FileWriter(fileName)) {

      files.write(title.toJSONString());
      files.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  private void inputBottomPaneScreen3(BorderPane bp, Stage primaryStage) {
    Button quit = new Button("Quit");
    quit.setFont(Font.font("Helvetica", 40));
    Button open = new Button("Open New File");
    open.setFont(Font.font("Helvetica", 40));
    Button back = new Button("Back to Input Page");
    back.setFont(Font.font("Helvetica", 40));
    Image home = new Image("file:Home.png");
    ImageView homeView = new ImageView(home);
    homeView.setFitHeight(60);
    homeView.setFitWidth(60);
    Button mainMenu = new Button(null, homeView);

    quit.setOnAction(e -> {
      primaryStage.setScene(scene4);
    });
    // if user wants to open their new file, open it up
    open.setOnAction(e -> {
      try {
        // open up the file
        Desktop.getDesktop().open(newFile);
      } catch (IOException e1) {
        // print stack trace if exception
        e1.printStackTrace();
      }

    });
    back.setOnAction(e -> {
      primaryStage.setScene(scene2);
    });
    mainMenu.setOnAction(e -> {
      primaryStage.setScene(scene1);
    });

    VBox bottom = new VBox(quit, open, back, mainMenu);
    bottom.setSpacing(50);
    bottom.setTranslateX(+30);
    bottom.setTranslateY(-30);
    bp.setBottom(bottom);
  }

  private void checkall(CheckBox ra, CheckBox dec, CheckBox hflux, CheckBox sflux, CheckBox z,
      CheckBox rmag, CheckBox all) {
    if (all.isSelected()) {
      if (!ra.isSelected()) {
        ra.fire();
      }
      if (!dec.isSelected()) {
        dec.fire();
      }
      if (!hflux.isSelected()) {
        hflux.fire();
      }
      if (!sflux.isSelected()) {
        sflux.fire();
      }
      if (!z.isSelected()) {
        z.fire();
      }
      if (!rmag.isSelected()) {
        rmag.fire();
      }
    } else {
      if (ra.isSelected()) {
        ra.fire();
      }
      if (dec.isSelected()) {
        dec.fire();
      }
      if (hflux.isSelected()) {
        hflux.fire();
      }
      if (sflux.isSelected()) {
        sflux.fire();
      }
      if (z.isSelected()) {
        z.fire();
      }
      if (rmag.isSelected()) {
        rmag.fire();
      }
    }
  }

  private void idParameters(BorderPane pane) {

  }

  private void idLookUp(BorderPane pane) {
    TextField enterID = new TextField();
    enterID.setPromptText("Enter ID");
    Label label1 = new Label("ID: ");
    label1.setTextFill(Color.WHITE);
    label1.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
    HBox hb = new HBox(label1, enterID);
    hb.setAlignment(Pos.TOP_RIGHT);
    pane.setRight(hb);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      scene1 = screen1Setup(primaryStage);
      scene2 = screen2Setup(primaryStage);
      scene3 = screen3Setup(primaryStage);
      scene4 = screen4Setup(primaryStage);

      scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      scene4.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

      primaryStage.setScene(scene1);
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws FileNotFoundException, IOException, ParseException,
      org.json.simple.parser.ParseException {
    allData = new Hashtable<Integer, SourceObject>();
    IDsAdded = 0;
    types = new ArrayList<String>();
    dataToWrite = new Hashtable<Integer, SourceObject>();
    fileRead = false;
    IDsAdded = 0;
    System.out.println(System.getProperty("user.dir"));
    // parseJSON("./astroexample1.json");
    // System.out.println("Test: ");
    // System.out.println("Source 2 z is: " + h.get(2).Z + ", should be " + 1.90);
    launch(args);
  }

}
