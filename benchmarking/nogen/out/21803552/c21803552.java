class c21803552 {

    public void doRender() throws IORuntimeException {
        File file = new File(JavaCIPUnknownScope.fileName);
        if (!file.exists()) {
            JavaCIPUnknownScope.logger.error("Static resource not found: " + JavaCIPUnknownScope.fileName);
            JavaCIPUnknownScope.isNotFound = true;
            return;
        }
        if (JavaCIPUnknownScope.fileName.endsWith("xml") || JavaCIPUnknownScope.fileName.endsWith("asp"))
            JavaCIPUnknownScope.servletResponse.setContentType("text/xml");
        else if (JavaCIPUnknownScope.fileName.endsWith("css"))
            JavaCIPUnknownScope.servletResponse.setContentType("text/css");
        else if (JavaCIPUnknownScope.fileName.endsWith("js"))
            JavaCIPUnknownScope.servletResponse.setContentType("text/javascript");
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            IOUtils.copy(in, JavaCIPUnknownScope.servletResponse.getOutputStream());
            JavaCIPUnknownScope.logger.debug("Static resource rendered: ".concat(JavaCIPUnknownScope.fileName));
        } catch (FileNotFoundRuntimeException e) {
            JavaCIPUnknownScope.logger.error("Static resource not found: " + JavaCIPUnknownScope.fileName);
            JavaCIPUnknownScope.isNotFound = true;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
