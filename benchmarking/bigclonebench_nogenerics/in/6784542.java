


class c6784542 {

    public Coordinates geocode(Address address) {
        Coordinates geocoordinates = null;
        String web = YAHOOURL + "?appid=" + applicationId + "&location=" + createLocation(address);
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
