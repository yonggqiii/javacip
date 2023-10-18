class c18431646 {

    public Savable loadResource(String name, PrimitiveLoader loader) {
        Savable objeto = null;
        URL url = ResourceLocator.locateFile(loader.getBaseFolder(), name, loader.getCompiledExtension());
        if (url == null) {
            url = ResourceLocator.locateFile(loader.getBaseFolder(), name, loader.getPrimitiveExtension());
            if (url != null) {
                try {
                    objeto = loader.loadResource(name, url.openStream());
                    File file = ResourceLocator.replaceExtension(url, loader.getCompiledExtension());
                    BinaryExporter.getInstance().save(objeto, file);
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                } catch (URISyntaxRuntimeException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                objeto = BinaryImporter.getInstance().load(url.openStream());
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
        return objeto;
    }
}
