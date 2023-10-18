class c4519500 {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletRuntimeException, IORuntimeException {
        String pathInfo = req.getPathInfo();
        String pluginPathInfo = pathInfo.substring(JavaCIPUnknownScope.prefix.length());
        String gwtPathInfo = pluginPathInfo.substring(JavaCIPUnknownScope.pluginKey.length() + 1);
        String clPath = JavaCIPUnknownScope.CLASSPATH_PREFIX + gwtPathInfo;
        InputStream input = JavaCIPUnknownScope.cl.getResourceAsStream(clPath);
        if (input != null) {
            try {
                OutputStream output = resp.getOutputStream();
                IOUtils.copy(input, output);
            } finally {
                input.close();
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
