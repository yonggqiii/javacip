class c14033995 {

    public void migrate(InputMetadata meta, InputStream input, OutputCreator outputCreator) throws IORuntimeException, ResourceMigrationRuntimeException {
        RestartInputStream restartInput = new RestartInputStream(input);
        Match match = JavaCIPUnknownScope.resourceIdentifier.identifyResource(meta, restartInput);
        restartInput.restart();
        if (match != null) {
            JavaCIPUnknownScope.reporter.reportNotification(JavaCIPUnknownScope.notificationFactory.createLocalizedNotification(NotificationType.INFO, "migration-resource-migrating", new Object[] { meta.getURI(), match.getTypeName(), match.getVersionName() }));
            JavaCIPUnknownScope.processMigrationSteps(match, restartInput, outputCreator);
        } else {
            JavaCIPUnknownScope.reporter.reportNotification(JavaCIPUnknownScope.notificationFactory.createLocalizedNotification(NotificationType.INFO, "migration-resource-copying", new Object[] { meta.getURI() }));
            IOUtils.copyAndClose(restartInput, outputCreator.createOutputStream());
        }
    }
}
