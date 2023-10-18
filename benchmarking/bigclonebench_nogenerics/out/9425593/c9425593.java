class c9425593 {

    public ContentPackage resolveItem() {
        URL url;
        try {
            url = new URL(JavaCIPUnknownScope.itembankURL + "/" + JavaCIPUnknownScope.deposit.get("http://www.caret.cam.ac.uk/minibix/metadata/ticket"));
            return new ContentPackage(url.openStream());
        } catch (MalformedURLRuntimeException e1) {
            e1.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
