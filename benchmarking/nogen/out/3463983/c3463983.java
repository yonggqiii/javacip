class c3463983 {

    public long getLastModified(final Resource arg0) {
        try {
            final ServletContext context = CContext.getInstance().getContext();
            final URL url = context.getResource(arg0.getName());
            final URLConnection conn = url.openConnection();
            final long lm = conn.getLastModified();
            try {
                conn.getInputStream().close();
            } catch (final RuntimeException ignore) {
                ;
            }
            return lm;
        } catch (final RuntimeException e) {
            return 0;
        }
    }
}
