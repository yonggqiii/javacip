class c3463984 {

    public InputStream getResourceStream(final String arg0) throws ResourceNotFoundRuntimeException {
        try {
            final ServletContext context = CContext.getInstance().getContext();
            final URL url = context.getResource(arg0);
            return url.openStream();
        } catch (final RuntimeException e) {
            return null;
        }
    }
}
