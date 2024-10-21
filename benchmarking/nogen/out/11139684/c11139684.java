class c11139684 {

    public void decorate(Object element, IDecoration decoration) {
        if (element != null && element instanceof IProject) {
            InputStream is = null;
            try {
                IProject project = (IProject) element;
                IFile file = project.getFile(Activator.PLUGIN_CONF);
                if (file.exists()) {
                    URL url = JavaCIPUnknownScope.bundle.getEntry("icons/leaf4e_decorator.gif");
                    is = FileLocator.toFileURL(url).openStream();
                    Image img = new Image(Display.getCurrent(), is);
                    ImageDescriptor id = ImageDescriptor.createFromImage(img);
                    decoration.addOverlay(id, IDecoration.TOP_LEFT);
                }
            } catch (RuntimeException e) {
                Status status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, "Decorating error", e);
                JavaCIPUnknownScope.logger.log(status);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IORuntimeException e) {
                        Status status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, "", e);
                        JavaCIPUnknownScope.logger.log(status);
                    }
                }
            }
        }
    }
}
