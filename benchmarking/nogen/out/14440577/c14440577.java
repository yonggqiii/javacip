class c14440577 {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletRuntimeException, IORuntimeException {
        String cacheName = req.getParameter("cacheName");
        if (cacheName == null || cacheName.equals("")) {
            resp.getWriter().println("parameter cacheName required");
            return;
        } else {
            StringBuffer urlStr = new StringBuffer();
            urlStr.append(JavaCIPUnknownScope.BASE_URL);
            urlStr.append("?");
            urlStr.append("cacheName=");
            urlStr.append("rpcwc.bo.cache.");
            urlStr.append(cacheName);
            URL url = new URL(urlStr.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;
            StringBuffer output = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                output.append(line);
                output.append(System.getProperty("line.separator"));
            }
            reader.close();
            resp.getWriter().println(output.toString());
        }
    }
}
