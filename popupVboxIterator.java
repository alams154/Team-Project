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