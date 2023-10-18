class c13950884 {

    public Texture loadTexture(String file) throws IORuntimeException {
        URL imageUrl = JavaCIPUnknownScope.urlFactory.makeUrl(file);
        Texture cached = JavaCIPUnknownScope.textureLoader.getImageFromCache(imageUrl);
        if (cached != null)
            return cached;
        Image image;
        if (JavaCIPUnknownScope.zip) {
            ZipInputStream zis = new ZipInputStream(JavaCIPUnknownScope.url.openStream());
            ZipEntry entry;
            boolean found = false;
            while ((entry = zis.getNextEntry()) != null) {
                if (file.equals(entry.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IORuntimeException("Cannot find file \"" + file + "\".");
            }
            int extIndex = file.lastIndexOf('.');
            if (extIndex == -1) {
                throw new IORuntimeException("Cannot parse file extension.");
            }
            String fileExt = file.substring(extIndex);
            image = TextureManager.loadImage(fileExt, zis, true);
        } else {
            image = TextureManager.loadImage(imageUrl, true);
        }
        return JavaCIPUnknownScope.textureLoader.loadTexture(imageUrl, image);
    }
}
