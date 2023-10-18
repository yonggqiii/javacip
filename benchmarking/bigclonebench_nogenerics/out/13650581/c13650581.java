class c13650581 {

    public static ChannelIF addChannel(String url) throws RuntimeException {
        ChannelIF channel = null;
        try {
            channel = FeedParser.parse(new ChannelBuilder(), url);
            if (channel.getLocation() == null)
                channel.setLocation(new URL(url));
        } catch (ParseRuntimeException e) {
            InputStream is = new URL(url).openStream();
            InputStreamReader reader = new InputStreamReader(is);
            channel = FeedParser.parse(new ChannelBuilder(), reader);
            reader.close();
            is.close();
            if (channel.getLocation() == null)
                channel.setLocation(new URL(url));
        }
        DAOChannel.addChannel(channel);
        DAOFavicon.addFaviconForChannel(channel);
        return channel;
    }
}
