  /*
  *  Method that writes the sources that are stored in the second
  *  global hash table to a separate file that will be given a valid
  *  name as a parameter
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
      
	  // add the values to the json array
      paramArr.add(id);
      paramArr.add(ra);
      paramArr.add(dec);
      paramArr.add(hflux);
      paramArr.add(sflux);
      paramArr.add(z);
      paramArr.add(rmag);
      JSONObject cursrc = new JSONObject();
      cursrc.put("type:", types.get(i - 1));
      cursrc.put("parameterArray", paramArr);

      allSources.add(cursrc);
    }
	
	title.put("ModifiedAstronomicalData", allSources);
    // Write JSON file
    try (FileWriter files = new FileWriter(fileName)) {
      files.write(title.toJSONString());
      files.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }