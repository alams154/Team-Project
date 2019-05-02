private void inputIDlist(BorderPane bp) {

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

    ListView<Text> list2 = new ListView<Text>();
    Label savedList = new Label("Saved ID List");
    savedList.setFont((Font.font("Helvetica", FontWeight.BOLD, 24)));
    ObservableList<Text> savedItems = FXCollections.observableArrayList();
    if (dataToWrite.get(1) != null)
      System.out.println(dataToWrite.get(1).getID());
    for (Integer i : dataToWrite.keySet()) {
      // add id items
      savedItems.add(new Text(
          "" + (int) dataToWrite.get(dataToWrite.size() - i + 1).getID() + ", Type: " + types.get(types.size() - i)));
    }
    
    savedList.setTextFill(Color.WHITE);
    list2.setItems(savedItems);
    // HBox listCount = new HBox();

    bp.setTop(savedList);
    for (Text t : list2.getItems()) {
      t.setFont((Font.font("Helvetica", FontWeight.BOLD, 20)));
      // (Font.font("Helvetica", FontWeight.BOLD, 24));
    }
    
    VBox leftBox = new VBox(listId, list);
    VBox rightBox = new VBox(savedList, list2);
    HBox mainBox = new HBox(leftBox, rightBox);
    
    bp.setLeft(mainBox);;

    // vbox.setBackground(new Background(new BackgroundFill(Color.WHITE,
    // CornerRadii.EMPTY,
    // Insets.EMPTY)));
  }