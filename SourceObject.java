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
   * Constructor initializes parameters of the given source, in cgs units
   * Negative values indicate no/null data.
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
   * Given the redshift of the source, returns its distance in cm
   * 
   * @return the distance of the object, in cm
   */
  public double getDistance() {
    return 1.303 * Math.pow(10, 28) * Z;

  }
  public double getID() {
    return this.ID;
  }
  public double getRA() {
    if (RA != 0.0) {
    return this.RA;
    } else {
      return -1.0;
    }
  }
  public double getDEC() {
    if (DEC != 0.0) {
    return this.DEC;
    } else {
      return -1.0;
    }
  }
  public double getHflux() {
    if (HARD_FLUX != 0.0) {
    return this.HARD_FLUX;
    } else {
      return -1.0;
    }
  }
  public double getSflux() {
    if (SOFT_FLUX != 0.0) {
    return this.SOFT_FLUX;
    } else {
      return -1.0;
    }
  }
    public double getZ() {
      if (Z != 0.0) {
      return this.Z;
      } else {
        return -1.0;
      }
  }
    public double getRmag() {
      if (RMAG != 0.0) {
      return this.RMAG;
      } else {
        return -1.0;
      }
  }
    public void setRA(double ra) {
      this.RA = ra;
    }
    public void setDEC(double dec) {
      this.DEC = dec;
    }
    public void setHflux(double h) {
      this.HARD_FLUX = h;
    }
    public void setSflux(double s) {
      this.SOFT_FLUX = s;
    }
      public void setZ(double z) {
        this.Z = z;
    }
      public void setRmag(double r) {
        this.RMAG = r;
    }

}
