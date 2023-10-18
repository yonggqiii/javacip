class c14087520 {

    private ModelDefinition buildModel(String name) {
        ModelDefinition model = null;
        URL url = ResourceLocator.locateBinaryModel(name);
        InputStream is = null;
        if (url == null) {
            url = ResourceLocator.locateTextModel(name);
            try {
                is = url.openStream();
                model = JavaCIPUnknownScope.buildModelFromText(name, is);
                File file = ResourceLocator.replaceExtension(url, ResourceLocator.BINARY_MODEL_EXTENSION);
                BinaryExporter.getInstance().save(model, file);
            } catch (IORuntimeException e) {
                e.printStackTrace();
            } catch (URISyntaxRuntimeException e) {
                e.printStackTrace();
            }
        } else {
            try {
                is = url.openStream();
                model = (ModelDefinition) BinaryImporter.getInstance().load(is);
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
        return model;
    }
}
