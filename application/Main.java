//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
// Assignment name: Team Project - Astronomical Catalog Data Receiver
// Due Date: 05/03/19
// Title: Main.java
// Files: application.css, SourceObject.java, astroexample1.json
// Course: CS 400, Lec 001- Spring 2019
//
// Authors: Brandon Radzom, Sameer Alam, Dayton Lindsay, Jacob Hoeg
// Emails: radzom@wisc.edu, salam4@wisc.edu, lindsay3@wisc.edu, jhoeg@wisc.edu
// Lecturer's Name: Deb Deppler
//
// Known bugs: None
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
  // int counter to keep track of the number of IDs added
  private static int IDsAdded;
  // String representing the file name to write into
  private static String fileToWriteTo;
  // JSONArray object to contain all data in source file
  private static JSONArray astroRegion;
  // File object to hold reference of newly created file after writing
  private static File newFile;
  // String array that contains the names of each source parameter
  final private static String[] params = new String[] {"ID", "Right Ascension", "Declination",
      "Hard-Band Flux", "Soft-Band Flux", "Redshift", "R-Band Magnitude"};

  /**
   * This method takes in a path to a json file and parses it by saving its data to an internal
   * hashTable structure
   * 
   * @param filepath
   * @throws IOException
   * @throws ParseException
   * @throws org.json.simple.parser.ParseException
   * @throws org.json.simple.parser.ParseException
   */
  private static void parseJSON(String filepath)
      throws IOException, ParseException, org.json.simple.parser.ParseException {
    // creates a file reader for the specified file
    FileReader f = new FileReader(filepath);
    // first, parse JSON file given the file path
    Object obj = new JSONParser().parse(f);
    // then create a JSON Object to represent file
    JSONObject jo = (JSONObject) obj;
    // create JSONArray to represent vertices from JSON file
    astroRegion = (JSONArray) jo.get("AstronomicalData");
    // iterate though all JSONArray objects
    for (int i = 0; i < astroRegion.size(); i++) {
      // create JSONObject to hold given source
      JSONObject jsonSrc = (JSONObject) astroRegion.get(i);
      // save the type of given source in String
      String type = (String) jsonSrc.get("type");
      // store String in types array
      if (type != null) {
        types.add(type.toString());
      }
      // create a JSONArray object to store all parameters of given source
      JSONArray array = (JSONArray) jsonSrc.get("parameterArray");
      // create a String[] array large enough to store these parameters
      String[] params = new String[array.size()];
      // save the id of the source
      JSONObject idpar = (JSONObject) array.get(0);
      String id = (String) idpar.get("id");
      // convert id to Integer
      int ide = Integer.parseInt(id);
      // iterate through the JSON parameter array
      for (int j = 0; j < array.size(); j++) {
        // save all the parameters
        params[j] = (String) array.get(j).toString();

      }
      // now go through and save each individual parameter

      // save RA coordinates as a double
      double rightasc = 0;
      // index 1 always has RA
      JSONObject ras = (JSONObject) array.get(1);
      String right = (String) ras.get("ra");
      // save RA to double variable
      if (right.length() > 0) {
        rightasc = Double.parseDouble((String) right);
      }

      // save DEC coordinates as a double
      double decl = 0;
      // index 2 always has DEC
      JSONObject decc = (JSONObject) array.get(2);
      String dec = (String) decc.get("dec");
      // save DEC to double
      if (dec.length() > 0) {
        decl = Double.parseDouble((String) dec);
      }
      // save hard-band flux of source to double
      double hard = 0;
      // H-flux always in index 3
      JSONObject ha = (JSONObject) array.get(3);
      String hardf = (String) ha.get("hflux");
      if (hardf.length() > 0) {
        hard = Double.parseDouble(hardf);
      }
      // save soft-band flux of source to double
      double soft = 0;
      // s-flux always in 4th index
      JSONObject sof = (JSONObject) array.get(4);
      String softf = (String) sof.get("sflux");
      if (softf.length() > 0) {
        soft = Double.parseDouble((String) softf);
      }
      // save redshift of source to double
      double z = 0;
      // redshift in index 5
      JSONObject redshift = (JSONObject) array.get(5);
      String redz = (String) redshift.get("z");
      if (redz.length() > 0) {
        z = Double.parseDouble(redz);
      }
      // save r-band magnitude of source to double
      double rmagnit = 0;
      // rmag always in index 6
      JSONObject rmag = (JSONObject) array.get(6);
      String rmagn = (String) rmag.get("rmag");
      if (rmagn.length() > 0) {
        rmagnit = Double.parseDouble((String) rmagn);
      }
      // finally create new SourceObject with these given parameters
      SourceObject so = new SourceObject(ide, rightasc, decl, hard, soft, z, rmagnit);
      // and put this source object in hashtable containing all data from file
      allData.put(ide, so);
    }
  }

  /*
   * Method that creates and supports the first screen, which is in charge of choosing a file,
   * exiting, or proceeding to the second screen
   */
  private Scene screen1Setup(Stage primaryStage) {
    BorderPane mainMenu = new BorderPane();
    backgroundSetup(mainMenu);
    // call helper methods that set up the right, top, center, and bottom sides of
    // the screen
    inputRightPaneScreen1(mainMenu, primaryStage);
    inputTopPaneScreen1(mainMenu, primaryStage);
    inputCenterPaneScreen1(mainMenu, primaryStage);
    inputBottomPaneScreen1(mainMenu, primaryStage);
    // return the scene
    return new Scene(mainMenu, 1200, 800);
  }

  /*
   * Method that creates and supports the second screen, which is in charge of adding, displaying,
   * and writing data
   */
  private Scene screen2Setup(Stage primaryStage) {
    BorderPane s2bp = new BorderPane(); // set up the boarderpane

    // get and set up the background image
    backgroundSetup(s2bp);
    // call helper methods that set up the left and right sides of the screen
    inputIDlist(s2bp);
    inputRightPaneScreen2(s2bp, primaryStage);
    inputBottomPaneScreen2(s2bp, primaryStage);
    // return the scene
    return new Scene(s2bp, 1200, 800);
  }

  /*
   * Method that creates the third screen, which is the screen that displays if the file was
   * successfully written to
   */
  private Scene screen3Setup(Stage primaryStage) {
    BorderPane results = new BorderPane(); // set up the border pane

    // get and set up the background image
    backgroundSetup(results);
    // helper methods that set up components in the scene
    // set top border pane
    inputTopPaneScreen3(results);
    // set bottom border pane
    inputBottomPaneScreen3(results, primaryStage);
    // return a newly created scene
    return new Scene(results, 1200, 800);
  }

  /*
   * Method that creates the fourth screen, which is the exit screen displayed when closing the
   * program
   */
  private Scene screen4Setup(Stage s) {
    BorderPane pane = new BorderPane(); // set up the border pane

    // set up and edit the font of the text to appear on the screen
    Text text1 = new Text("Thank you for using \n      our program!");
    text1.setFill(Color.WHITE);
    text1.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));

    // set up the exit button
    Button done = new Button("Close Program");
    done.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));

    // place the button and text on the screen
    pane.setRight(done);
    pane.setCenter(text1);
    pane.setPrefSize(500, 300);

    // get and set up the background image
    backgroundSetup(pane);
    Scene exitScene = new Scene(pane, 1200, 800);
    // exit when button pressed
    done.setOnAction(e -> {
      s.close();
    });
    // return the exit scene
    return exitScene;
  }

  /*
   * Method that creates the background for a specific screen (Used to setup background for all
   * screens)
   */
  private void backgroundSetup(BorderPane pane) {
    // load in space background image
    Image bg = new Image("file:hs-1996-01-a-large_web.jpg");
    // set background
    pane.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
  }

  /**
   * This method handles setting up the power button (quit) for screen 1
   * 
   * @param pane is border pane object to work from
   * @param primaryStage is stage that is set (screen 1)
   */
  private void inputRightPaneScreen1(BorderPane pane, Stage primaryStage) {
    // load in new file
    Image power = new Image("file:Power.png");
    ImageView powerView = new ImageView(power);
    // set proper dimensions
    powerView.setFitHeight(60);
    powerView.setFitWidth(60);
    // create a new button using image
    Button powerButton = new Button(null, powerView);
    // put button on top right
    pane.setRight(powerButton);
    // link it to exit screen
    powerButton.setOnAction(e -> {
      primaryStage.setScene(scene4);
    });
  }

  /**
   * This method sets up the title bar for screen 1
   * 
   * @param pane is border pane to work from
   * @param primaryStage is Stage to work from
   */
  private void inputTopPaneScreen1(BorderPane pane, Stage primaryStage) {
    // set title of program to be big, white and centered
    Text title = new Text("Astronomical Catalog Data Processor");
    title.setFont(Font.font("Helvetica", 48));
    title.setFill(Color.WHITE);
    TextFlow titleBox = new TextFlow(title);
    titleBox.setTextAlignment(TextAlignment.CENTER);
    // put it inn top border pane
    pane.setTop(titleBox);
  }

  /**
   * This method handles the select file button and functionality for screen 1
   * 
   * @param pane is the border pane to work from
   * @param primaryStage is the stage to work from
   */
  private void inputCenterPaneScreen1(BorderPane pane, Stage primaryStage) {
    // create button that allows user to use GUI to select their file
    Button fileChooser = new Button("Select File");
    // create file chooser object for GUI file explorer option
    fileChooser.setFont(Font.font("Helvetica", 42));
    // put in center of screen
    pane.setCenter(fileChooser);
    // if they select button, let them choose a file
    FileChooser chooser = new FileChooser();
    fileChooser.setOnAction(e -> {
      File selectedFile = chooser.showOpenDialog(primaryStage);
      // if the file is not .json, alert the user
      if (selectedFile.getName()
          .substring(selectedFile.getName().length() - 5, selectedFile.getName().length())
          .equals(".json")) {
        try {
          // else, we can read from the file
          fileRead = true;
          // thus we can parse it and save its data
          parseJSON("./" + selectedFile.getName() + "");
          // now proceed to screen 2
          scene2 = screen2Setup(primaryStage);
          primaryStage.setScene(scene2);
          // catch any unexpected exceptions
        } catch (IOException e1) {

          // print stack trace to help user debug
          e1.printStackTrace();
          // catch any parsing exceptions
        } catch (ParseException e1) {
          // print stack trace to help user debug
          e1.printStackTrace();
          // catch exception
        } catch (org.json.simple.parser.ParseException e1) {
          // print stack trace to help user debug
          e1.printStackTrace();
        }
        // else, if the file is not .json, alert the user and remind them of valid file types
      } else {
        Alert invalidFile =
            new Alert(Alert.AlertType.INFORMATION, "Incorrect File Type, Choose a .json file");
        invalidFile.showAndWait().filter(response -> response == ButtonType.OK);
      }
    });
  }

  /**
   * This method inputs functionality on first page that allows user to skip loading in a file so
   * they can just see what the GUI looks like
   * 
   * @param pane is border pane to work from
   * @param primaryStage is screen 1 object
   */
  private void inputBottomPaneScreen1(BorderPane pane, Stage primaryStage) {
    // create button that allows skipping to screen 2
    Button skipLoad = new Button("Skip File Selection");
    skipLoad.setFont(Font.font("Helvetica", 36));// put button on bottom right
    VBox bottomRight = new VBox(skipLoad);
    bottomRight.setAlignment(Pos.BOTTOM_RIGHT);
    bottomRight.setSpacing(25);
    bottomRight.setTranslateY(-20);
    // set it to bottom right border pane
    pane.setBottom(bottomRight);
    // if they click it, go to second page
    skipLoad.setOnAction(e -> {
      primaryStage.setScene(scene2);
    });
  }

  /**
   * This method adds the list of selected saved source IDs to the GUI
   * 
   * @param bp is BorderPane object to work from
   */
  private void inputIDlist(BorderPane bp) {
    // create a listView object thtat shows all sources that were read in from file
    ListView<Text> list = new ListView<Text>();
    Label listId = new Label("Source ID List");
    listId.setFont((Font.font("Helvetica", FontWeight.BOLD, 24)));
    ObservableList<Text> items = FXCollections.observableArrayList();
    // make sure data isn't null
    if (allData.get(1) != null)
      // add all sources stored in hash table to this observable list
      for (Integer i : allData.keySet()) {
        // add id items
        items.add(new Text("" + (int) allData.get(allData.size() - i + 1).getID() + ", Type: "
            + types.get(types.size() - i)));
      }
    // set the list up in visible way
    listId.setTextFill(Color.WHITE);
    list.setItems(items);
    // put the ID list to the top left corner
    bp.setTop(listId);
    // set the font to be consistent
    for (Text t : list.getItems()) {
      t.setFont((Font.font("Helvetica", FontWeight.BOLD, 20)));
    }
    // now create a list for saved IDs
    ListView<Text> savedIDs = new ListView<Text>();
    Label savedList = new Label("Saved ID List");
    savedList.setFont((Font.font("Helvetica", FontWeight.BOLD, 24)));
    ObservableList<Text> savedItems = FXCollections.observableArrayList();
    // look through the "saved data" hash table- contains sources they want to save
    // if data is not null
    if (dataToWrite.get(1) != null)
      // add its contents to the list
      for (Integer i : dataToWrite.keySet()) {
        // add id items
        savedItems.add(new Text("" + (int) dataToWrite.get(dataToWrite.size() - i + 1).getID()));
      }
    // format the list
    savedList.setTextFill(Color.WHITE);
    savedIDs.setItems(savedItems);
    // set the list to be right next to original one

    bp.setTop(savedList);
    // make font consistent
    for (Text t : savedIDs.getItems()) {
      t.setFont((Font.font("Helvetica", FontWeight.BOLD, 20)));
      // (Font.font("Helvetica", FontWeight.BOLD, 24));
    }
    // now add all lists to their Vboxes
    VBox leftBox = new VBox(listId, list);
    VBox rightBox = new VBox(savedList, savedIDs);
    HBox mainBox = new HBox(leftBox, rightBox);
    // add in the main Vbox that holds all ID lists in it
    bp.setLeft(mainBox);

  }

  private void inputRightPaneScreen2(BorderPane pane, Stage primaryStage) {
		//Creates search field and label for parameter boxs
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

		//checkboxes for all the paramters to be viewed
		CheckBox ra = new CheckBox(params[1]);
		ra.setTextFill(Color.WHITE);
		ra.setFont(Font.font("Helvetica", 24));
		CheckBox dec = new CheckBox(params[2]);
		dec.setTextFill(Color.WHITE);
		dec.setFont(Font.font("Helvetica", 24));
		CheckBox hflux = new CheckBox(params[3]);
		hflux.setTextFill(Color.WHITE);
		hflux.setFont(Font.font("Helvetica", 24));
		CheckBox sflux = new CheckBox(params[4]);
		sflux.setTextFill(Color.WHITE);
		sflux.setFont(Font.font("Helvetica", 24));
		CheckBox z = new CheckBox(params[5]);
		z.setTextFill(Color.WHITE);
		z.setFont(Font.font("Helvetica", 24));
		CheckBox rmag = new CheckBox(params[6]);
		rmag.setTextFill(Color.WHITE);
		rmag.setFont(Font.font("Helvetica", 24));

		//select all checkbox
		CheckBox all = new CheckBox("Select All");
		all.setTextFill(Color.WHITE);
		all.setFont(Font.font("Helvetica", 24));

		//organize checkboxes
		VBox first = new VBox(ra, dec, hflux);
		first.setSpacing(100);
		VBox sec = new VBox(sflux, z, rmag);
		sec.setSpacing(100);
		HBox boxes = new HBox(first, sec);
		boxes.setSpacing(30);

		//vbox to contain all the boxes
		VBox moreboxes = new VBox(hb, boxes, all);
		moreboxes.setSpacing(100);
		moreboxes.setTranslateX(-20);
		moreboxes.setTranslateY(10);
		moreboxes.setAlignment(Pos.TOP_CENTER);
		pane.setRight(moreboxes);
		all.setOnAction(e -> checkall(ra, dec, hflux, sflux, z, rmag, all));
		// bottom buttons
	}

  private void inputBottomPaneScreen2(BorderPane pane, Stage primaryStage) {
		//creates buttons for bottom of the screen
		Button addSource = new Button("Add Source");
		addSource.setFont(Font.font("Helvetica", 18));
		Button display = new Button("Display/Save Source Data");
		display.setFont(Font.font("Helvetica", 18));
		Button write = new Button("Write");
		write.setFont(Font.font("Helvetica", 18));
		Button exit = new Button("Exit");
		exit.setFont(Font.font("Helvetica", 18));
		TextField enterFileName = new TextField();
		enterFileName.setPromptText("Enter File Name");
		Button average = new Button("Compute mean distance");
		average.setFont(Font.font("Helvetica", 18));
		Button help = new Button("Help");
		help.setFont(Font.font("Helvetica", 18));
		HBox botRight = new HBox(write, enterFileName);
		HBox botButtons = new HBox(help, addSource, display, botRight, average, exit);
		//spaces buttons, then adds to pane
		botButtons.setSpacing(50);
		botButtons.setAlignment(Pos.CENTER);
		botButtons.setTranslateY(-30);
		pane.setBottom(botButtons);

		display.setOnAction(e -> {
			displayButtonAction(primaryStage, pane);
		});
		
		//button to compute the mean distance from the source data
		average.setOnAction(e -> {
			String meanD = "" + getMeanDistance();
			Alert meanDistance = new Alert(Alert.AlertType.INFORMATION, "Mean Source Distance: " + meanD.substring(0, 6)
					+ meanD.substring(meanD.length() - 3, meanD.length()) + " cm");
			meanDistance.showAndWait().filter(response -> response == ButtonType.OK);
		});

		//creates a popup containing helpful information to use the program
		help.setOnAction(e -> {
			Alert invalidFile = new Alert(Alert.AlertType.INFORMATION,
					"How to operate the program:\nThere are many options that you have for going through the"
							+ " data\nthat you read in from the file given. There are 3 analysis options"
							+ " as well as an option to exit.\n\n To add a new source, click the add"
							+ " button and add the preferred data\nto the popup window that appears.\n\n"
							+ " To display data, check the correct data that is desired from the "
							+ "checkboxes\non the right and then click on the display/save button. "
							+ "A popup will appear, and\nif wanted, the data displayed can be saved to"
							+ " be written to another json file by\nclicking on the save and exit"
							+ " button.\n\n To write to another file, click on the write button, "
							+ "and add a valid file name.\nInclude the .json at the end of the name "
							+ "in order for the file to be created successfully. \nOnce clicked, all "
							+ "sources (requested to save) from the second list will be written to a "
							+ "separate json file saved to the current directory.");
			invalidFile.showAndWait().filter(response -> response == ButtonType.OK);

		});
		//closes scene, moving to exit scene
		exit.setOnAction(e -> {
			primaryStage.setScene(scene4);
		});
		//calls method for creating add source popup
		addSource.setOnAction(e -> {
			popupAddScreen2(primaryStage);
		});
		//writes to a json file the information that was chose by user
		write.setOnAction(e -> {
			fileToWriteTo = enterFileName.getText().trim();
			try {
				if (!writeToFile(fileToWriteTo) || !fileToWriteTo
						.substring(fileToWriteTo.length() - 5, fileToWriteTo.length()).equals(".json")) {
					Alert invalidFile = new Alert(Alert.AlertType.INFORMATION,
							"Duplicate or invalid file, choose a .json file");
					invalidFile.showAndWait().filter(response -> response == ButtonType.OK);
				} else {
					scene3 = screen3Setup(primaryStage);
					primaryStage.setScene(scene3);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

	}

  /*
   * Method that creates a new window that is displayed with the data that corresponds
   * to the ID typed into the ID search bar, and displays the selected data from
   * the check boxes
   */
   private void displayButtonAction(Stage primaryStage, BorderPane pane) {
    if (fileRead) {
      try {
		// create the popup for the display button
        final Stage disp = new Stage();
        TextField enterID =
            (TextField) ((VBox) ((HBox) ((VBox) pane.getRight()).getChildren().get(0)).getChildren()
                .get(1)).getChildren().get(0);
        CheckBox ra = (CheckBox) ((VBox) ((HBox) ((VBox) pane.getRight()).getChildren().get(1))
            .getChildren().get(0)).getChildren().get(0);
        CheckBox dec = (CheckBox) ((VBox) ((HBox) ((VBox) pane.getRight()).getChildren().get(1))
            .getChildren().get(0)).getChildren().get(1);
        CheckBox hflux = (CheckBox) ((VBox) ((HBox) ((VBox) pane.getRight()).getChildren().get(1))
            .getChildren().get(0)).getChildren().get(2);
        CheckBox sflux = (CheckBox) ((VBox) ((HBox) ((VBox) pane.getRight()).getChildren().get(1))
            .getChildren().get(1)).getChildren().get(0);
        CheckBox z = (CheckBox) ((VBox) ((HBox) ((VBox) pane.getRight()).getChildren().get(1))
            .getChildren().get(1)).getChildren().get(1);
        CheckBox rmag = (CheckBox) ((VBox) ((HBox) ((VBox) pane.getRight()).getChildren().get(1))
            .getChildren().get(1)).getChildren().get(2);

        disp.initModality(Modality.APPLICATION_MODAL);
        disp.initOwner(primaryStage);
		// create the boxes
        HBox saveExt = new HBox();
        VBox vbox1 = new VBox();
		// add the data stored in the ID search and the subsequent
		// data stored at that index in the hash table
        int searchID = Integer.parseInt(enterID.getText().trim());
        dataToWrite.put(IDsAdded + 1, allData.get(searchID));
        IDsAdded++;
        // create the buttons and add them to the Hbox
        Button saveSrc = new Button("Save this source");
        saveSrc.setFont(Font.font("Helvetica", 30));
        Button ext = new Button("Exit without saving");
        ext.setFont(Font.font("Helvetica", 30));
        saveExt.getChildren().add(saveSrc);
        saveExt.getChildren().add(ext);
        // send an alert if the hash table does not contain the ID found at the ID search
        if (!allData.containsKey(searchID)) {
          Alert invalidID = new Alert(Alert.AlertType.INFORMATION, "ID not valid.");
          invalidID.showAndWait().filter(response -> response == ButtonType.OK);
        }
		// check the checkbox ra to see if it should be displayed in this popup
        if (ra.isSelected()) {
          Label id_disp = new Label("Displaying ID #" + searchID + ", " + types.get(searchID - 1));
          id_disp.setFont(Font.font("Helvetica", 45));
          Text right;
          if (allData.get(searchID).getRA() != -1.0) {
            right = new Text(params[1] + ": " + allData.get(searchID).getRA() + "°");
          } else {
            right = new Text(params[1] + ": Parameter not given");

          }
          right.setFont(Font.font("Helvetica", 30));
          vbox1.getChildren().add(id_disp);
          vbox1.getChildren().add(right);
        }
		// check the checkbox dec to see if it should be displayed in this popup
		// and add the text to the vbox of the popup
        if (dec.isSelected()) {
          Text decl;
          if (allData.get(searchID).getDEC() != -1.0) {
            decl = new Text(params[2] + ": " + allData.get(searchID).getDEC() + "°");
          } else {
            decl = new Text(params[2] + ": Parameter not given");
          }
          decl.setFont(Font.font("Helvetica", 30));
          vbox1.getChildren().add(decl);
        }
		// check the checkbox hflux to see if it should be displayed in this popup
		// and add the text to the vbox of the popup
        if (hflux.isSelected()) {
          Text horiz;
          if (allData.get(searchID).getHflux() != -1.0) {
            horiz =
                new Text(params[3] + ": " + allData.get(searchID).getHflux() + " cm^(-2) sec^(-1)");
          } else {
            horiz = new Text(params[3] + ": Parameter not given");
          }
          horiz.setFont(Font.font("Helvetica", 30));
          vbox1.getChildren().add(horiz);
        }
		// check the checkbox sflux to see if it should be displayed in this popup
		// and add the text to the vbox of the popup
        if (sflux.isSelected()) {
          Text soft;
          if (allData.get(searchID).getSflux() != -1.0) {
            soft =
                new Text(params[4] + ": " + allData.get(searchID).getSflux() + " cm^(-2) sec^(-1)");
          } else {
            soft = new Text(params[4] + ": Parameter not given");
          }
          soft.setFont(Font.font("Helvetica", 30));
          vbox1.getChildren().add(soft);
        }
		// check the checkbox z to see if it should be displayed in this popup
		// and add the text to the vbox of the popup
        if (z.isSelected()) {
          Text zee;
          if (allData.get(searchID).getZ() != -1.0) {
            zee = new Text(params[5] + ": " + allData.get(searchID).getZ());
          } else {
            zee = new Text(params[5] + ": Parameter not given");
          }
          zee.setFont(Font.font("Helvetica", 30));
          vbox1.getChildren().add(zee);
        }
		// check the checkbox rmag to see if it should be displayed in this popup
		// and add the text to the vbox of the popup
        if (rmag.isSelected()) {
          Text mag;
          if (allData.get(searchID).getRmag() != -1.0) {
            mag = new Text(params[6] + ": " + allData.get(searchID).getRmag());
          } else {
            mag = new Text(params[6] + ": Parameter not given");
          }
          mag.setFont(Font.font("Helvetica", 30));

          vbox1.getChildren().add(mag);
        }
		
		// add the button hbox to the bottom of the vbox,
		// and then set the scene
        vbox1.getChildren().add(saveExt);
        Scene dialogScene = new Scene(vbox1, 800, 500);
        disp.setScene(dialogScene);
        disp.setTitle("Display Source");
        // add a button for closing and a button for saving
        disp.show();
        // if the save and exit button is clicked, close the screen
        saveSrc.setOnAction(f -> {
          scene2 = screen2Setup(primaryStage);
          primaryStage.setScene(scene2);
          disp.close();
        });
		// if the exit button is clicked, clear the data that was written 
		// to the second hash table and decrement the global IDsAdded
		// and then close the screen
        ext.setOnAction(g -> {
          dataToWrite.remove(IDsAdded + 1);
          IDsAdded--;
          disp.close();
        });
		// if there is an error, catch it and send an alert
      } catch (NumberFormatException f) {
        Alert invalidFile = new Alert(Alert.AlertType.INFORMATION, "ID not valid.");
        invalidFile.showAndWait().filter(response -> response == ButtonType.OK);
      }
    }
  }

  private void popupAddScreen2(Stage primaryStage) {
		primaryStage.setTitle("Creating popup");

		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.initOwner(primaryStage);

		//create vbox for type box and parameters
		VBox leftVbox = new VBox(20);
		Label type = new Label("Select Type:");
		type.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		ObservableList<String> options = FXCollections
				.observableArrayList(types.stream().distinct().collect(Collectors.toList()));
		ComboBox<String> c = new ComboBox<String>(options);
		VBox typeBox = new VBox(5);
		typeBox.getChildren().addAll(type, c);
		leftVbox.getChildren().add(typeBox);
		for (int i = 1; i < params.length - 3; i++) {
			VBox temp = new VBox(10);
			TextField tf = new TextField();
			tf.setPromptText(params[i]);
			Label l = new Label(params[i] + ":");
			l.setTextFill(Color.BLACK);
			l.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
			temp.getChildren().addAll(l, tf);
			leftVbox.getChildren().add(temp);
		}
		leftVbox.setTranslateX(10);
		//creates vbox to hold other parameter text fields
		VBox centerVbox = new VBox(20);
		for (int i = params.length - 3; i < params.length; i++) {
			VBox temp = new VBox(10);
			TextField tf = new TextField();
			tf.setPromptText(params[i]);
			tf.setMaxWidth(157);
			Label l = new Label(params[i] + ":");
			l.setTextFill(Color.BLACK);
			l.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
			temp.getChildren().addAll(l, tf);
			centerVbox.getChildren().add(temp);
		}
		centerVbox.setAlignment(Pos.BASELINE_CENTER);
		centerVbox.setTranslateY(37);
		centerVbox.setTranslateX(100);
			
		//hbox to contain the buttons to control the popup
		HBox bottomHbox = new HBox(20);
		Button addSource = new Button("Add Source");
		addSource.setFont(Font.font("Helvetica", 20));
		Button addAndExit = new Button("Add Source and Exit");
		addAndExit.setFont(Font.font("Helvetica", 20));
		Button exit = new Button("Exit");
		exit.setFont(Font.font("Helvetica", 20));
		bottomHbox.getChildren().addAll(addSource, addAndExit, exit);
		bottomHbox.setAlignment(Pos.BOTTOM_CENTER);
		bottomHbox.setTranslateY(-10);

		//whole scene border pane
		BorderPane bp = new BorderPane();
		bp.setLeft(leftVbox);
		bp.setCenter(centerVbox);
		bp.setBottom(bottomHbox);
		Scene popupScene = new Scene(bp, 600, 400);
		popup.setScene(popupScene);
		popup.show();

		//adds the new source created with the given parameters
		addSource.setOnAction(e -> {
			String[][] pVals = new String[2][4];

			pVals[0] = popupVboxIterator(leftVbox);
			pVals[1] = popupVboxIterator(centerVbox);

			SourceObject s = null;
			try {
				s = popupSourceObject(pVals);
				allData.put(types.size() + 1, s);
				if (pVals[0][0].equals("null")) {
					throw new NullPointerException();
				}
				types.add(pVals[0][0]);
				scene2 = screen2Setup(primaryStage);
				primaryStage.setScene(scene2);
			} catch (NullPointerException np) {
				allData.remove(types.size() + 1, s);
				Alert invalid = new Alert(Alert.AlertType.INFORMATION, "Please select a type");
				invalid.showAndWait().filter(response -> response == ButtonType.OK);
			} catch (NumberFormatException nf) {
				Alert invalid = new Alert(Alert.AlertType.INFORMATION, "Please enter a number in all fields");
				invalid.showAndWait().filter(response -> response == ButtonType.OK);
			}
		});

		//adds source but exits popup when its done
		addAndExit.setOnAction(e -> {
			String[][] pVals = new String[2][4];

			pVals[0] = popupVboxIterator(leftVbox);
			pVals[1] = popupVboxIterator(centerVbox);

			boolean exception = false;
			SourceObject s = null;
			try {
				s = popupSourceObject(pVals);
				allData.put(types.size() + 1, s);
				if (pVals[0][0].equals("null")) {
					throw new NullPointerException();
				}
				types.add(pVals[0][0]);
				scene2 = screen2Setup(primaryStage);
				primaryStage.setScene(scene2);
			} catch (NullPointerException np) {
				allData.remove(types.size() + 1, s);
				Alert invalid = new Alert(Alert.AlertType.INFORMATION, "Please select a type");
				invalid.showAndWait().filter(response -> response == ButtonType.OK);
				exception = true;
			} catch (NumberFormatException nf) {
				Alert invalid = new Alert(Alert.AlertType.INFORMATION, "Please enter a number in all fields");
				invalid.showAndWait().filter(response -> response == ButtonType.OK);
				exception = true;
			}

			if (!exception) {
				popup.close();
			}
		});

		//closes popup
		exit.setOnAction(e -> {
			popup.close();
		});

	}

  /*
   * Method that collects all the data added to the top box
   * of the popup screen that adds a new source to the
   * list of sources
   */
   private String[] popupVboxIterator(VBox top) {
    // create an array that will hold the data to be returned
	String[] arr = new String[4];
    int i = 1;
	// loops through the checkbox and text fields to
	// grab the text stored in these areas
    for (Node v : top.getChildren()) {
      if (v instanceof VBox) {
        VBox vb = (VBox) v;
        for (Node n : vb.getChildren()) {
          if (n instanceof ComboBox) {
			  // if the combobox is detected, store the data
			  // in the first index of the array
            ComboBox<String> cb = (ComboBox<String>) n;
            arr[0] = cb.getValue();
          } else if (n instanceof TextField) {
			  // if its a text field, iterate through the
			  // other indexes and store the data
            TextField t = (TextField) n;
            arr[i] = t.getText();
            i++;
          }
        }
      }
    }
    return arr;
  }

  /*
   * Method that parses a 2D string array into a 2D double array,
   * and adds the values to a new source object
   */
   private SourceObject popupSourceObject(String[][] arr) throws NumberFormatException {
	// create the new 2D double array and parse
	// the values from the given array into it
    double[][] pVals = new double[2][4];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 1; j < arr.length; j++) {
        pVals[i][j] = Double.parseDouble(arr[i][j]);
      }
	}
    
	// create a new source object and add all the values from the new array to it
    SourceObject so = new SourceObject(types.size() + 1, pVals[0][1], pVals[0][2], pVals[0][3],
        pVals[1][1], pVals[1][2], pVals[1][3]);

    return so;
  }

  /**
   * This method is used to select all parameters of interest
   * 
   * @param ra is CheckBox option for Right ascension
   * @param dec is checkbox option for declination
   * @param hflux is checkbox option for hard-band flux
   * @param sflux is checkbox option for soft-band flux
   * @param z is checkbox option for redshift
   * @param rmag is checkbox option for R-band magnitude
   * @param all is option to select all params
   */
  private void checkall(CheckBox ra, CheckBox dec, CheckBox hflux, CheckBox sflux, CheckBox z,
      CheckBox rmag, CheckBox all) {
    // check if each parameter is selected or not
    // if select all is checked, then open all of them
    if (all.isSelected()) {
      // check right ascension
      if (!ra.isSelected()) {
        ra.fire();
      }
      // check declination
      if (!dec.isSelected()) {
        dec.fire();
      }
      // check hard-flux
      if (!hflux.isSelected()) {
        hflux.fire();
      }
      // check soft-flux
      if (!sflux.isSelected()) {
        sflux.fire();
      }
      // check redshift
      if (!z.isSelected()) {
        z.fire();
      }
      // check R-mag
      if (!rmag.isSelected()) {
        rmag.fire();
      }
    } // else we go one by one and check each parameter
    else {
      // check right ascension
      if (ra.isSelected()) {
        ra.fire();
      }
      // check declination
      if (dec.isSelected()) {
        dec.fire();
      }
      // check hard-flux
      if (hflux.isSelected()) {
        hflux.fire();
      }
      // check soft-flux
      if (sflux.isSelected()) {
        sflux.fire();
      }
      // check redshift
      if (z.isSelected()) {
        z.fire();
      }
      // check R-mag
      if (rmag.isSelected()) {
        rmag.fire();
      }
    }
  }

  /*
   * Method that creates the objects to be displayed at the top of screen 3
   */
  private void inputTopPaneScreen3(BorderPane bp) {
    // Create and set up a label to be placed at the top of screen 3
    Label top = new Label("Successfully written " + dataToWrite.size() + " IDs to a file");
    top.setFont((Font.font("Helvetica", FontWeight.BOLD, 36)));
    top.setTextFill(Color.WHITE);

    bp.setTop(top); // add label to top of the border pane
  }

  /**
   * This method sets up all buttons and functionality seen in the third screen, allows user to open
   * their new .json file, return to the home screen, and quit the program
   * 
   * @param bp is the border pane object to add buttons to
   * @param primaryStage is the stage to set border pane object to
   */
  private void inputBottomPaneScreen3(BorderPane bp, Stage primaryStage) {
    // create quit button
    Button quit = new Button("Quit");
    quit.setFont(Font.font("Helvetica", 40));
    // create button that allows user to open new file
    Button open = new Button("Open New File");
    open.setFont(Font.font("Helvetica", 40));
    // create back button
    Button back = new Button("Back to Input Page");
    back.setFont(Font.font("Helvetica", 40));
    // load in image to return to home screen and make button for it
    Image home = new Image("file:Home.png");
    ImageView homeView = new ImageView(home);
    homeView.setFitHeight(60);
    homeView.setFitWidth(60);
    Button mainMenu = new Button(null, homeView);
    // go to exit scene if they press quit
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
    // if the user wants to return to input screen, go back to screen 2
    back.setOnAction(e -> {
      primaryStage.setScene(scene2);
    });
    // else if the user wants to return to home screen, go back to screen 1
    mainMenu.setOnAction(e -> {
      primaryStage.setScene(scene1);
    });
    // encapsulate all buttons in Vbox, make it look nice
    VBox bottom = new VBox(quit, open, back, mainMenu);
    bottom.setSpacing(50);
    bottom.setTranslateX(+30);
    bottom.setTranslateY(-30);
    // put Vbox in borderpane
    bp.setBottom(bottom);
  }

  /*
   * Method that writes the sources that are stored in the second global hash table to a separate
   * file that will be given a valid name as a parameter
   */
  private boolean writeToFile(String fileName) throws IOException {
    // Set up the file to be written to
    File file = new File("./" + fileName);
    newFile = file;

    // creates the json object and array to be used
    // to write to the new file
    JSONObject title = new JSONObject();
    JSONArray allSources = new JSONArray();

    // Create the file
    if (!file.createNewFile()) {
      return false;

    }

    // loops through the second hash table to grab the
    // data and transfer it to json data to be written
    // to the new file created
    for (int i = 1; i <= dataToWrite.size(); i++) {
      SourceObject cur = dataToWrite.get(i);
      JSONArray paramArr = new JSONArray();
      // First Employee
      JSONObject id = new JSONObject();

      // set up the json file to correct syntax
      // add all relevant source parameters
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

      // add the parameter JSONObjects to the JSONArray
      paramArr.add(id);
      paramArr.add(ra);
      paramArr.add(dec);
      paramArr.add(hflux);
      paramArr.add(sflux);
      paramArr.add(z);
      paramArr.add(rmag);
      // create a new JSONObject to hold the source you're looking at
      JSONObject cursrc = new JSONObject();
      // add the type JSONObject and source parameter array JSONArray
      cursrc.put("type:", types.get(i - 1));
      cursrc.put("parameterArray", paramArr);
      // finally, add this source data to JSONOArray containing all sources
      allSources.add(cursrc);
    }

    // set the title of the final JSONArray
    title.put("AstronomicalData", allSources);
    // create a new file
    try (FileWriter files = new FileWriter(fileName)) {
      // write the JSONObjects into it
      files.write(title.toJSONString());
      files.flush();
      // catch any unexpected exceptions
    } catch (IOException e) {
      e.printStackTrace();
    }
    // return true, since file has been written
    return true;
  }

  /**
   * This method computes and returns the average distance to each source read in from the given
   * file using their getDistance() methods
   * 
   * @return a double containing the average distance
   */
  private double getMeanDistance() {
    // create double to hold the total distance
    double avg = 0;
    // sum up distances of all sources
    for (int i = 0; i < allData.size(); i++) {
      avg = avg + allData.get(i + 1).getDistance();
    }
    // return the true average
    return avg / (allData.size());
  }

  /*
   * This method is the heart of the GUI generation. It initializes all main screens and scenes and
   * sets the stage once the program is launched
   * 
   * @see javafx.application.Application#start(javafx.stage.Stage) for more
   */
  @Override
  public void start(Stage primaryStage) {
    try {
      // set up all 4 main screens that will be linked
      scene1 = screen1Setup(primaryStage);
      scene2 = screen2Setup(primaryStage);
      scene3 = screen3Setup(primaryStage);
      scene4 = screen4Setup(primaryStage);
      // get any styling options from .css document
      scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      scene4.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      // open up the first page when launched
      primaryStage.setScene(scene1);
      primaryStage.show();
      // catch any unexpected exceptions
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * The main method initializes the data structure back ends, and several misc. fields, and then
   * runs the GUI
   * 
   * @param args
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParseException
   * @throws org.json.simple.parser.ParseException
   */
  public static void main(String[] args) throws FileNotFoundException, IOException, ParseException,
      org.json.simple.parser.ParseException {
    // initialize fields
    allData = new Hashtable<Integer, SourceObject>();
    IDsAdded = 0;
    types = new ArrayList<String>();
    dataToWrite = new Hashtable<Integer, SourceObject>();
    fileRead = false;

    System.out.println(System.getProperty("user.dir"));
    // launch GUI
    launch(args);
  }

}
