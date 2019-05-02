Button help = new Button("Help");
help.setOnAction(e -> 
       try {
	      final Stage helpPop = new Stage();
          helpPop.initModality(Modality.APPLICATION_MODAL);
          helpPop.initOwner(primaryStage);   
		  Label helpLabel = new Label("Help");
	      TextArea helpText = new TextArea("How to operate the program:\n
		  There are many options that you have for going through the data\n
		  that you read in from the file given.\n
		  \n
		  To add a new source, click the add button and add the preferred data\n
		  to the popup window that appears.\n
		  \n
		  To display data, check the correct data that is desired from the checkboxes\n
		  on the right and then click on the display button. A popup will appear, and\n
		  if wanted, the data displayed can be saved to be written to another file by\n
		  clicking on the save and exit button.\n
		  \n
		  To write to another file, click on the write button. If there is a valid ID in\n
		  the text field, all the data from that specific source is written to a separate json file.\n
		  If there is no ID written in the text field, then all the data from the IDs on the \n
		  second list will be written to a separate json file.");
	   
	   } catch (exception e) {
	      Alert helpError = new Alert(Alert.AlertType.INFORMATION, "Something went wrong when loading the help popup.");

                helpError.showAndWait().filter(response -> response == ButtonType.OK);
	   });