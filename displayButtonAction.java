/*
*
*
*
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