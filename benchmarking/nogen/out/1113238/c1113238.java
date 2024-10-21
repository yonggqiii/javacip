class c1113238 {

    public ResourceMigrator createDefaultResourceMigrator(NotificationReporter reporter, boolean strictMode) throws ResourceMigrationRuntimeException {
        return new ResourceMigrator() {

            public void migrate(InputMetadata meta, InputStream inputStream, OutputCreator outputCreator) throws IORuntimeException, ResourceMigrationRuntimeException {
                OutputStream outputStream = outputCreator.createOutputStream();
                IOUtils.copy(inputStream, outputStream);
            }
        };
    }
}
