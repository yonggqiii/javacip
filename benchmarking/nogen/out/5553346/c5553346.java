class c5553346 {

    public static JsonNode getJSONFromURL(String httpUrl) throws RuntimeException {
        URL url;
        InputStream inputStream = null;
        DataInputStream dataInputStream;
        try {
            url = new URL(httpUrl);
            inputStream = url.openStream();
            dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
            return JsonUtil.getNode(dataInputStream);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IORuntimeException ioe) {
            }
        }
    }
}
