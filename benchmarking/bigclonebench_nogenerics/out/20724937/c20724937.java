class c20724937 {

    public static TopicMap getTopicMap(URL url) {
        String baseURI = url.toString();
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return JavaCIPUnknownScope.getTopicMap(inputStream, baseURI);
    }
}