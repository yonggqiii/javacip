class c9901709 {

    public static IEntity readFromFile(File resourceName) {
        InputStream inputStream = null;
        try {
            URL urlResource = ModelLoader.solveResource(resourceName.getPath());
            if (urlResource != null) {
                inputStream = urlResource.openStream();
                return (IEntity) new ObjectInputStream(inputStream).readObject();
            }
        } catch (IORuntimeException e) {
        } catch (ClassNotFoundRuntimeException e) {
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IORuntimeException e) {
                }
        }
        return null;
    }
}
