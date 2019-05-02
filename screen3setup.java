  /*
  *  Method that creates the third screen,
  *  which is the screen that displays if the file
  *  was successfully written to
  */
  private Scene screen3Setup(Stage primaryStage) {
    BorderPane results = new BorderPane(); // set up the border pane
    
	// get and set up the background image
	Image bg = new Image("file:hs-1996-01-a-large_web.jpg");
    results.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
		
    inputTopPaneScreen3(results); // helper methods that set up components in the scene
    inputBottomPaneScreen3(results, primaryStage);
    return new Scene(results, 1200, 800);
  }