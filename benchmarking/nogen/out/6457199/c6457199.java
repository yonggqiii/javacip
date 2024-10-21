class c6457199 {

    protected boolean doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletRuntimeException, IORuntimeException {
        String path = request.getPathInfo();
        if (!path.startsWith(JavaCIPUnknownScope.alias)) {
            throw new ServletRuntimeException("Path '" + path + "' does not start with registered alias '" + JavaCIPUnknownScope.alias + "'");
        }
        String internal;
        if (JavaCIPUnknownScope.alias.equals("/")) {
            internal = JavaCIPUnknownScope.name + path;
        } else {
            internal = JavaCIPUnknownScope.name + path.substring(JavaCIPUnknownScope.alias.length(), path.length());
        }
        URL resource = JavaCIPUnknownScope.httpContext.getResource(internal);
        if (resource == null) {
            return false;
        }
        String mimeType = JavaCIPUnknownScope.servletContext.getMimeType(internal);
        if (mimeType != null) {
            response.setContentType(mimeType);
        }
        InputStream is = resource.openStream();
        OutputStream os = response.getOutputStream();
        IOUtils.copyAndClose(is, os);
        return true;
    }
}
