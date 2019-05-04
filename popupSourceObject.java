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