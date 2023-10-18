class c6677391 {

    protected void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletRuntimeException, IORuntimeException {
        String reqPath = req.getPathInfo();
        if (reqPath.startsWith("/"))
            reqPath = reqPath.substring(1);
        ZipEntry entry = JavaCIPUnknownScope.zipInfo.get(reqPath);
        if (entry == null) {
            JavaCIPUnknownScope.logger.debug(Utils.join("Requested path not found: [", reqPath, "]"));
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        JavaCIPUnknownScope.logger.debug(Utils.join("Requested path: [", reqPath, "]"));
        ServletUtils.establishContentType(reqPath, resp);
        InputStream in = null;
        try {
            in = new BufferedInputStream(JavaCIPUnknownScope.zipFile.getInputStream(entry));
            IOUtils.copy(in, resp.getOutputStream());
            JavaCIPUnknownScope.logger.debug("Rendered: " + reqPath);
        } catch (FileNotFoundRuntimeException e) {
            JavaCIPUnknownScope.logger.error("zipped resource not found: " + reqPath);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}