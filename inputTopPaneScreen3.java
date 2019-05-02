  /*
  *  Method that creates the objects to be displayed
  *  at the top of screen 3
  */
  private void inputTopPaneScreen3(BorderPane bp) {
    // Create and set up a label to be placed at the top of screen 3
    Label top = new Label("Successfully written " + dataToWrite.size() + " IDs to a file");
    top.setFont((Font.font("Helvetica", FontWeight.BOLD, 36)));
    top.setTextFill(Color.WHITE);

    bp.setTop(top); // add label to top of the border pane
  }