class c12909291 {

    public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        response.setContentType(JavaCIPUnknownScope.s_contentType);
        response.setHeader("Cache-control", "no-cache");
        InputStream graphStream = JavaCIPUnknownScope.getGraphStream(request);
        OutputStream out = JavaCIPUnknownScope.getOutputStream(response);
        IOUtils.copy(graphStream, out);
        out.flush();
    }
}
