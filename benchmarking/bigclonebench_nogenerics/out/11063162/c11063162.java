class c11063162 {

    public int getResponseCode(URI uri) {
        int response = -1;
        try {
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            response = connection.getResponseCode();
        } catch (MalformedURLRuntimeException m) {
            throw new MalformedURLRuntimeException("URL not correct");
        } catch (IORuntimeException e) {
            throw new IORuntimeException("can open connection");
        } finally {
            return response;
        }
    }
}
