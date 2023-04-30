class c19090290 {

    public static Channel getChannelFromXML(String channelURL) throws SAXException, IOException {
        JavaCIPUnknownScope.channel = new Channel(channelURL);
        JavaCIPUnknownScope.downloadedItems = new LinkedList<Item>();
        URL url = new URL(channelURL);
        XMLReader xr = XMLReaderFactory.createXMLReader();
        ChannelFactory handler = new ChannelFactory();
        xr.setContentHandler(handler);
        xr.setErrorHandler(handler);
        xr.parse(new InputSource(url.openStream()));
        JavaCIPUnknownScope.channel.setUnreadItemsCount(JavaCIPUnknownScope.downloadedItems.size());
        return JavaCIPUnknownScope.channel;
    }
}
