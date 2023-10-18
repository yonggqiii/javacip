class c6784542 {

    public Coordinates geocode(Address address) {
        Coordinates geocoordinates = null;
        String web = JavaCIPUnknownScope.YAHOOURL + "?appid=" + JavaCIPUnknownScope.applicationId + "&location=" + JavaCIPUnknownScope.createLocation(address);
        URL url;
        try {
            url = new URL(web);
            InputStream in = url.openStream();
            geocoordinates = YahooXmlReader.readConfig(in);
            in.close();
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return geocoordinates;
    }
}
