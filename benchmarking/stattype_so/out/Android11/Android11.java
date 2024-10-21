public class Android11 extends MyLocationOverlay {

    public Android11(Context arg0, MapView arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    public void drawMyLocation(Canvas canvas, MapView mapView, Location lastFix, GeoPoint myLocation, long when) {
        super.drawMyLocation(canvas, mapView, lastFix, myLocation, when);
        Location bLocation = new Location("reverseGeocoded");
        // Value = 3.294391E7
        bLocation.setLatitude(FindList.gpslat);
        // Value = -9.6564615E7
        bLocation.setLongitude(FindList.gpslong);
        Location aLocation = new Location("reverseGeocoded");
        // Value = 3.2946164E7
        aLocation.setLatitude(myLocation.getLatitudeE6());
        // Value = -9.6505141E7
        aLocation.setLongitude(myLocation.getLongitudeE6());
        // Don't think I need this
        aLocation.set(aLocation);
        // Don't think I need this either
        bLocation.set(bLocation);
        // Value = 12637795 ???
        int distance = (int) aLocation.distanceTo(bLocation);
        String str = " (" + String.valueOf(distance) + " meters)";
    }
}

class FindList {

    public static int gpslat = 1;

    public static int gpslong = 1;
}
