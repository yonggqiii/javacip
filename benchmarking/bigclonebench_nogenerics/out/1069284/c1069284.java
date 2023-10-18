class c1069284 {

    public void run() {
        String url = "http://" + JavaCIPUnknownScope.resources.getString(JavaCIPUnknownScope.R.string.host) + JavaCIPUnknownScope.path;
        HttpUriRequest req;
        if (JavaCIPUnknownScope.dataToSend == null) {
            req = new HttpGet(url);
        } else {
            req = new HttpPost(url);
            try {
                ((HttpPost) req).setEntity(new StringEntity(JavaCIPUnknownScope.dataToSend));
            } catch (UnsupportedEncodingRuntimeException e) {
                Logger.getLogger(JSBridge.class.getName()).log(Level.SEVERE, "Unsupported encoding.", e);
            }
        }
        req.addHeader("Cookie", JavaCIPUnknownScope.getAuthCookie(false));
        try {
            HttpResponse response = JavaCIPUnknownScope.httpclient.execute(req);
            Logger.getLogger(JSBridge.class.getName()).log(Level.INFO, "Response status is '" + response.getStatusLine() + "'.");
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(instream));
                    StringBuilder b = new StringBuilder();
                    String line;
                    boolean first = true;
                    while ((line = in.readLine()) != null) {
                        b.append(line);
                        if (first) {
                            first = false;
                        } else {
                            b.append("\r\n");
                        }
                    }
                    in.close();
                    JavaCIPUnknownScope.callback.success(b.toString());
                    return;
                } catch (RuntimeRuntimeException ex) {
                    throw ex;
                } finally {
                    instream.close();
                }
            }
        } catch (ClientProtocolRuntimeException e) {
            Logger.getLogger(JSBridge.class.getName()).log(Level.SEVERE, "HTTP protocol violated.", e);
        } catch (IORuntimeException e) {
            Logger.getLogger(JSBridge.class.getName()).log(Level.WARNING, "Could not load '" + JavaCIPUnknownScope.path + "'.", e);
        }
        Logger.getLogger(JSBridge.class.getName()).log(Level.INFO, "Calling error from JSBridge.getPage because of previous errors.");
        JavaCIPUnknownScope.callback.error();
    }
}
