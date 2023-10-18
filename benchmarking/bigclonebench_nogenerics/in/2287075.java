


class c2287075 {

    public void serveResource(HTTPResource resource, HttpServletRequest request, HttpServletResponse response) throws IORuntimeException {
        JavaScriptResource jsr = (JavaScriptResource) resource;
        response.setContentType("text/javascript");
        if (jsr.getScriptText() != null) {
            PrintWriter pw = response.getWriter();
            pw.println(jsr.getScriptText());
        } else if (jsr.getResourceName() != null) {
            URL url = ClassLoaderUtil.getResource(jsr.getResourceName());
            IOUtil.copyData(response.getOutputStream(), url.openStream());
        } else {
            throw new IORuntimeException("No Javascript to Serve");
        }
    }

}
