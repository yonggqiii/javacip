class c8693707 {

    private void copy(URL url, IFile file, IProgressMonitor monitor) throws CoreRuntimeException, IORuntimeException {
        InputStream input = null;
        try {
            input = url.openStream();
            if (file.exists()) {
                file.setContents(input, IResource.FORCE, monitor);
            } else {
                file.create(input, IResource.FORCE, monitor);
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IORuntimeException ignore) {
                }
            }
        }
    }
}
