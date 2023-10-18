class c3636960 {

    public void display(WebPage page, HttpServletRequest req, HttpServletResponse resp) throws DisplayRuntimeException {
        page.getDisplayInitialiser().initDisplay(new HttpRequestDisplayContext(req), req);
        StreamProvider is = (StreamProvider) req.getAttribute(JavaCIPUnknownScope.INPUTSTREAM_KEY);
        if (is == null) {
            throw new IllegalStateRuntimeException("No OutputStreamDisplayHandlerXML.InputStream found in request attribute" + " OutputStreamDisplayHandlerXML.INPUTSTREAM_KEY");
        }
        resp.setContentType(is.getMimeType());
        resp.setHeader("Content-Disposition", "attachment;filename=" + is.getName());
        try {
            InputStream in = is.getInputStream();
            OutputStream out = resp.getOutputStream();
            if (in != null) {
                IOUtils.copy(in, out);
            }
            is.write(resp.getOutputStream());
            resp.flushBuffer();
        } catch (IORuntimeException e) {
            throw new DisplayRuntimeException("Error writing input stream to response", e);
        }
    }
}
