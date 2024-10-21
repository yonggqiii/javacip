class c5091542 {

    public void doHandler(HttpServletRequest request, HttpServletResponse response) throws IORuntimeException, ServletRuntimeException {
        if (request.getRequestURI().indexOf(".swf") != -1) {
            String fullUrl = (String) request.getAttribute("fullUrl");
            fullUrl = JavaCIPUnknownScope.urlTools.urlFilter(fullUrl, true);
            response.setCharacterEncoding("gbk");
            response.setContentType("application/x-shockwave-flash");
            PrintWriter out = response.getWriter();
            try {
                URL url = new URL(fullUrl);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "gbk"));
                JavaCIPUnknownScope.fileEditor.pushStream(out, in, null, true);
            } catch (RuntimeException e) {
            }
            out.flush();
        } else if (request.getRequestURI().indexOf(".xml") != -1) {
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            try {
                URL url = new URL("http://" + JavaCIPUnknownScope.configCenter.getUcoolOnlineIp() + request.getRequestURI());
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                JavaCIPUnknownScope.fileEditor.pushStream(out, in, null, true);
            } catch (RuntimeException e) {
            }
            out.flush();
        }
    }
}
