


class c20789900 {

    public void render(HttpServletRequest request, HttpServletResponse response, File file, final Throwable t, final String contentType, final String encoding) throws RuntimeException {
        if (contentType != null) {
            response.setContentType(contentType);
        }
        if (encoding != null) {
            response.setCharacterEncoding(encoding);
        }
        if (file.length() > Integer.MAX_VALUE) {
            throw new IllegalArgumentRuntimeException("Cannot send file greater than 2GB");
        }
        response.setContentLength((int) file.length());
        IOUtils.copy(new FileInputStream(file), response.getOutputStream());
    }

}
