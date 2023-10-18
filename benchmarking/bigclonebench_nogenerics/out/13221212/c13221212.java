class c13221212 {

    public static void generate(final InputStream input, String format, Point dimension, IPath outputLocation) throws CoreRuntimeException {
        MultiStatus status = new MultiStatus(GraphVizActivator.ID, 0, "Errors occurred while running Graphviz", null);
        File dotInput = null, dotOutput = outputLocation.toFile();
        ByteArrayOutputStream dotContents = new ByteArrayOutputStream();
        try {
            dotInput = File.createTempFile(JavaCIPUnknownScope.TMP_FILE_PREFIX, JavaCIPUnknownScope.DOT_EXTENSION);
            FileOutputStream tmpDotOutputStream = null;
            try {
                IOUtils.copy(input, dotContents);
                tmpDotOutputStream = new FileOutputStream(dotInput);
                IOUtils.copy(new ByteArrayInputStream(dotContents.toByteArray()), tmpDotOutputStream);
            } finally {
                IOUtils.closeQuietly(tmpDotOutputStream);
            }
            IStatus result = JavaCIPUnknownScope.runDot(format, dimension, dotInput, dotOutput);
            if (dotOutput.isFile()) {
                if (!result.isOK() && Platform.inDebugMode())
                    LogUtils.log(status);
                return;
            }
        } catch (IORuntimeException e) {
            status.add(new Status(IStatus.ERROR, GraphVizActivator.ID, "", e));
        } finally {
            dotInput.delete();
            IOUtils.closeQuietly(input);
        }
        throw new CoreRuntimeException(status);
    }
}
