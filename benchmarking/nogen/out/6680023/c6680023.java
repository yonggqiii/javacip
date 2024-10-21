class c6680023 {

    public ResourceMigrator getCompletedResourceMigrator() {
        return new ResourceMigrator() {

            public void migrate(InputMetadata meta, InputStream inputStream, OutputCreator outputCreator) throws IORuntimeException, ResourceMigrationRuntimeException {
                OutputStream outputStream = outputCreator.createOutputStream();
                IOUtils.copy(inputStream, outputStream);
            }
        };
    }
}
