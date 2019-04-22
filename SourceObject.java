package application;

/**
 * This object initializes an astronomical source object and stores all of its associated parameters
 * 
 * @author Brandon Radzom, Sameer Alam, Jacob Hoeg, Dayton Lindsay
 *
 */
public class SourceObject {
  // initialize parameters
  final int ID;
  // all parameters in cgs units:
  // RA, DEC in degrees
  final double RA;
  final double DEC;
  // HARD_FLUX, SOFT_FLUX in erg s^-1 cm^-2
  final double HARD_FLUX;
  final double SOFT_FLUX;
  // Z, unitless
  final double Z;
  // RMAG, unitless
  final double RMAG;

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
 * Given the redshift of the source, returns its distance in cm
 * @return the distance of the object, in cm
 */
public double getDistance() {
  return 1.303*Math.pow(10, 28)*Z;

}


}
