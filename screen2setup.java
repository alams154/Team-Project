  /*
  *  Method that creates and supports the second screen,
  *  which is in charge of adding, displaying, and writing data
  */
  private Scene screen2Setup(Stage primaryStage) {
    BorderPane s2bp = new BorderPane(); // set up the boarderpane
    
	// get and set up the background image
	Image bg = new Image("file:hs-1996-01-a-large_web.jpg"); 
    s2bp.setBackground(new Background(new BackgroundImage(bg, BackgroundRepeat.REPEAT,
        BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
		
    inputIDlist(s2bp);  // helper methods that set up the left and right sides of the screen
    inputRightPaneScreen2(s2bp, primaryStage);
	// return the scene
    return new Scene(s2bp, 1200, 800);
  }