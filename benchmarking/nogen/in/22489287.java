


class c22489287 {

    public void load(URL url) throws IORuntimeException {
        try {
            oggBitStream_ = new BufferedInputStream(url.openStream());
        } catch (RuntimeException ex) {
            System.err.println("ogg file " + url + " could not be loaded");
        }
        load();
    }

}
