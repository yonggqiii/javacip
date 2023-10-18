


class c8969940 {

    private void Download(String uri) throws MalformedURLRuntimeException {
        URL url = new URL(uri);
        try {
            bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IORuntimeException ex) {
            bm = getError();
        }
    }

}
