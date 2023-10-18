class c16208627 {

    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletRuntimeException, IORuntimeException {
        final String url = req.getParameter("url");
        if (!JavaCIPUnknownScope.isAllowed(url)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        final HttpClient client = new HttpClient();
        client.getParams().setVersion(HttpVersion.HTTP_1_0);
        final PostMethod method = new PostMethod(url);
        method.getParams().setVersion(HttpVersion.HTTP_1_0);
        method.setFollowRedirects(false);
        final RequestEntity entity = new InputStreamRequestEntity(req.getInputStream());
        method.setRequestEntity(entity);
        try {
            final int statusCode = client.executeMethod(method);
            if (statusCode != -1) {
                InputStream is = null;
                ServletOutputStream os = null;
                try {
                    is = method.getResponseBodyAsStream();
                    try {
                        os = resp.getOutputStream();
                        IOUtils.copy(is, os);
                    } finally {
                        if (os != null) {
                            try {
                                os.flush();
                            } catch (IORuntimeException ignored) {
                            }
                        }
                    }
                } catch (IORuntimeException ioex) {
                    final String message = ioex.getMessage();
                    if (!"chunked stream ended unexpectedly".equals(message)) {
                        throw ioex;
                    }
                } finally {
                    IOUtils.closeQuietly(is);
                }
            }
        } finally {
            method.releaseConnection();
        }
    }
}
