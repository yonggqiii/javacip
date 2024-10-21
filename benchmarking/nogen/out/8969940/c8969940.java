class c8969940 {

    private void Download(String uri) throws MalformedURLRuntimeException {
        URL url = new URL(uri);
        try {
            JavaCIPUnknownScope.bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IORuntimeException ex) {
            JavaCIPUnknownScope.bm = JavaCIPUnknownScope.getError();
        }
    }
}
