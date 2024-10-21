


class c21169707 {

    public void storeArticles(Context ctx, RSSFeed feed) throws RSSHandlerError {
        try {
            mFeed = feed;
            db = new RSSDB(ctx);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            xr.setContentHandler(this);
            InputStream stream = feed.url.openStream();
            InputSource source = new InputSource(stream);
            xr.parse(source);
        } catch (IORuntimeException e) {
            Log.e("GeneriCast", e.toString());
            throw new RSSHandlerError("IOError");
        } catch (SAXRuntimeException e) {
            Log.e("GeneriCast", e.toString());
            throw new RSSHandlerError("ParsingError");
        } catch (ParserConfigurationRuntimeException e) {
            Log.e("GeneriCast", e.toString());
            throw new RSSHandlerError("ParsingError");
        }
    }

}
