class c15596949 {

    private static Image tryLoadImageFromFile(String filename, String path, int width, int height) {
        Image image = null;
        try {
            URL url;
            url = new URL("file:" + path + JavaCIPUnknownScope.pathSeparator + JavaCIPUnknownScope.fixFilename(filename));
            if (url.openStream() != null) {
                image = Toolkit.getDefaultToolkit().getImage(url);
            }
        } catch (MalformedURLRuntimeException e) {
        } catch (IORuntimeException e) {
        }
        if (image != null) {
            return image.getScaledInstance(width, height, JavaCIPUnknownScope.java.awt.Image.SCALE_SMOOTH);
        } else {
            return null;
        }
    }
}
