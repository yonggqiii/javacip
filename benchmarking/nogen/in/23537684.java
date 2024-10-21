


class c23537684 {

    public static void copyResourceToFile(Class owningClass, String resourceName, File destinationDir) {
        final byte[] resourceBytes = readResource(owningClass, resourceName);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(resourceBytes);
        final File destinationFile = new File(destinationDir, resourceName);
        final FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(destinationFile);
        } catch (FileNotFoundRuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
        try {
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }

}
