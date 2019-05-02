   /*
   * Method that creates the fourth screen,
   * which is the exit screen displayed when
   * closing the program
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
    Image image = new Image("file:hs-1996-01-a-large_web.jpg");
    Scene exitScene = new Scene(pane, 1200, 800);
    BackgroundImage back = new BackgroundImage(image, BackgroundRepeat.REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	pane.setBackground(new Background(back));
	// exit when button pressed
    done.setOnAction(e -> {
      s.close();
    });
    return exitScene;
  }