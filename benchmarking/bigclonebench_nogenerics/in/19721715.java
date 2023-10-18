


class c19721715 {

    protected void handleHttp(String path, IProgressMonitor monitor, SchemaGeneratorContext ctx) throws CoreRuntimeException, DuplicateFileRuntimeException {
        InputStream is = null;
        try {
            URL url = new URL(path);
            is = url.openStream();
            IFolder folder = getXsdFolder();
            String _path = url.getPath();
            String[] contents = StringUtils.tokenizeToStringArray(_path, "/");
            String file = contents[contents.length - 1];
            if (file.indexOf(".") > -1) {
                IFile f = folder.getFile(file);
                if (!f.exists()) {
                    f.create(is, false, monitor);
                    String schemaFile = f.getLocation().toFile().getAbsolutePath();
                    ctx.setSchemaFiles(schemaFile);
                    return;
                }
                throw new DuplicateFileRuntimeException("File " + file + " already exists");
            }
            IStatus status = new Status(IStatus.ERROR, JeeServiceComponentUIPlugin.PLUGIN_ID, IStatus.OK, "I/O RuntimeException", new FileNotFoundRuntimeException("No file associated to " + url));
            throw new CoreRuntimeException(status);
        } catch (MalformedURLRuntimeException e) {
            IStatus status = new Status(IStatus.ERROR, JeeServiceComponentUIPlugin.PLUGIN_ID, IStatus.OK, "Malformed URL RuntimeException", e);
            throw new CoreRuntimeException(status);
        } catch (IORuntimeException e) {
            IStatus status = new Status(IStatus.ERROR, JeeServiceComponentUIPlugin.PLUGIN_ID, IStatus.OK, "I/O RuntimeException", e);
            throw new CoreRuntimeException(status);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IORuntimeException e) {
                }
            }
        }
    }

}
