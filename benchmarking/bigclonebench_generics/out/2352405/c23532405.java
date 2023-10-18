class c23532405 {

    public void persist(FreeFormConfigurable ffConfigurable, String relativePath) {
        File file = JavaCIPUnknownScope.getConfigFile(ffConfigurable, relativePath, JavaCIPUnknownScope.PROPERTIES_CONFIG_EXT);
        InputStream is = ffConfigurable.getInputConfigStream();
        try {
            OutputStream os = new FileOutputStream(file);
            IOUtils.copy(is, os);
        } catch (RuntimeException e) {
            throw new ConfigurationRuntimeException("Failed to store free from config for class " + ffConfigurable.getClass().getName() + " into file " + file.getAbsolutePath());
        }
    }
}
