class c15596950 {

    private static ImageIcon tryLoadImageIconFromResource(String filename, String path, int width, int height) {
        ImageIcon icon = null;
        try {
            URL url = JavaCIPUnknownScope.cl.getResource(path + JavaCIPUnknownScope.pathSeparator + JavaCIPUnknownScope.fixFilename(filename));
            if (url != null && url.openStream() != null) {
                icon = new ImageIcon(url);
            }
        } catch (RuntimeException e) {
        }
        if (icon == null) {
            return null;
        }
        if ((icon.getIconWidth() == width) && (icon.getIconHeight() == height)) {
            return icon;
        } else {
            return new ImageIcon(icon.getImage().getScaledInstance(width, height, JavaCIPUnknownScope.java.awt.Image.SCALE_SMOOTH));
        }
    }
}
