//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
// Assignment name: Team Project - Astronomical Catalog Data Receiver
// Due Date: 05/03/19
// Title: SourceObject.java
// Files: None
// Course: CS 400, Lec 001- Spring 2019
//
// Authors: Brandon Radzom, Sameer Alam, Dayton Lindsay, Jacob Hoeg
// Emails: radzom@wisc.edu, salam4@wisc.edu, lindsay3@wisc.edu, jhoeg@wisc.edu
// Lecturer's Name: Deb Deppler
//
// Known bugs: None
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

/**
 * This object initializes an astronomical source object and stores all of its associated parameters
 * 
 * @author Brandon Radzom, Sameer Alam, Jacob Hoeg, Dayton Lindsay
 *
 */
public class SourceObject {
  // initialize parameters
  private final int ID;
  // all parameters in cgs units:
  // RA, DEC in degrees
  private double RA;
  private double DEC;
  // HARD_FLUX, SOFT_FLUX in erg s^-1 cm^-2
  private double HARD_FLUX;
  private double SOFT_FLUX;
  // Z, unitless
  private double Z;
  // RMAG, unitless
  private double RMAG;

  /**
   * Constructor initializes parameters of the given source, in cgs units Negative values indicate
   * no/null data.
   * 
   * @param id the ID of the object
   * @param ra the RA (Right Ascension) coordinates of the object
   * @param dec the DEC (Declination) coordinates of the object
   * @param hard_flux (Hard-band flux) the hard-band flux of the object
   * @param soft_flux the Soft-band flux of the object
   * @param z the redshift of the object
   * @param rmag the R-band magnitude of the object
   */
  public SourceObject(int id, double ra, double dec, double hard_flux, double soft_flux, double z,
      double rmag) {
    this.ID = id;
    this.RA = ra;
    this.DEC = dec;
    this.HARD_FLUX = hard_flux;
    this.SOFT_FLUX = soft_flux;
    this.Z = z;
    this.RMAG = rmag;

  }

  /**
   * Given the redshift of the source, returns its distance in cm. Can be used to compute mean
   * distance to all sources
   * 
   * @return the distance of the object, in cm
   */
  public double getDistance() {
    return 1.303 * Math.pow(10, 28) * Z;

  }

  /**
   * This method returns the ID of the given source
   * @return int containing ID of source
   */
  public int getID() {
    return this.ID;
  }

  /**
   * This method returns the RA coordinates of given source
   * -1.0 indicates a null value
   * @return RA coord as double, in degrees °
   */
  public double getRA() {
    if (RA != 0.0) {
      return this.RA;
    } else {
      return -1.0;
    }
  }

  /**
   * This method returns the DEC coordinates of given source
   * -1.0 indicates a null value
   * @return DEC coord as double
   */
  public double getDEC() {
    if (DEC != 0.0) {
      return this.DEC;
    } else {
      return -1.0;
    }
  }

  /**
   * This method gets the Hard-band flux of given source
   * -1.0 indicates a null value
   * @return hflux as double
   */
  public double getHflux() {
    if (HARD_FLUX != 0.0) {
      return this.HARD_FLUX;
    } else {
      return -1.0;
    }
  }

  /**
   * This method gets the soft-band flux of given source
   * -1.0 indicates a null value
   * @return sflux as double
   */
  public double getSflux() {
    if (SOFT_FLUX != 0.0) {
      return this.SOFT_FLUX;
    } else {
      return -1.0;
    }
  }

  /**
   * This method gets the redshift (called Z in astronomy) of given source
   * -1.0 indicates a null value
   * @return Z as a double
   */
  public double getZ() {
    if (Z != 0.0) {
      return this.Z;
    } else {
      return -1.0;
    }
  }

  /**
   * This method gets the R-band magnitude of the given source
   * -1.0 indicates a null value
   * @return rmag as a double
   */
  public double getRmag() {
    if (RMAG != 0.0) {
      return this.RMAG;
    } else {
      return -1.0;
    }
  }

  

}
