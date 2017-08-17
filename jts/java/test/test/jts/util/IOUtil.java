package test.jts.util;

import jts.geom.Geometry;
import jts.io.ParseException;
import jts.io.WKTReader;

public class IOUtil {
	  public static Geometry read(String wkt)
	  {
		  WKTReader rdr = new WKTReader();
	    try {
	      return rdr.read(wkt);
	    }
	    catch (ParseException ex) {
	      throw new RuntimeException(ex);
	    }
	  }

}
