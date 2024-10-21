public class Android11 extends MyLocationOverlay {

	public Android11(Context arg0, MapView arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawMyLocation(Canvas canvas, MapView mapView, Location lastFix, GeoPoint myLocation, long when) {
		super.drawMyLocation(canvas, mapView, lastFix, myLocation, when);

		Location bLocation = new Location("reverseGeocoded");
		bLocation.setLatitude(FindList.gpslat); // Value = 3.294391E7
		bLocation.setLongitude(FindList.gpslong); // Value = -9.6564615E7
		Location aLocation = new Location("reverseGeocoded");
		aLocation.setLatitude(myLocation.getLatitudeE6()); // Value = 3.2946164E7
		aLocation.setLongitude(myLocation.getLongitudeE6()); // Value = -9.6505141E7
		aLocation.set(aLocation); // Don't think I need this   
		bLocation.set(bLocation); // Don't think I need this either

		int distance = (int) aLocation.distanceTo(bLocation); // Value = 12637795 ???
		String str = " (" + String.valueOf(distance) + " meters)";
	}

}
class FindList {
  public static int gpslat = 1;
  public static int gpslong = 1;
}
