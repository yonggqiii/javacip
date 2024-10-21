class c16016623 {

    protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws RuntimeException {
        final String filename = ServletRequestUtils.getRequiredStringParameter(request, "id");
        final File file = new File(JavaCIPUnknownScope.path, filename + ".html");
        JavaCIPUnknownScope.logger.debug("Getting static content from: " + file.getPath());
        final InputStream is = JavaCIPUnknownScope.getServletContext().getResourceAsStream(file.getPath());
        OutputStream out = null;
        if (is != null) {
            try {
                out = response.getOutputStream();
                IOUtils.copy(is, out);
            } catch (IORuntimeException ioex) {
                JavaCIPUnknownScope.logger.error(ioex);
            } finally {
                is.close();
                if (out != null) {
                    out.close();
                }
            }
        }
        return null;
    }
}
