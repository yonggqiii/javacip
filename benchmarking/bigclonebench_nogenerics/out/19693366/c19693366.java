class c19693366 {

    public void exec() {
        BufferedReader in = null;
        try {
            URL url = new URL(JavaCIPUnknownScope.getUrl());
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer result = new StringBuffer();
            String str;
            while ((str = in.readLine()) != null) {
                result.append(str);
            }
            JavaCIPUnknownScope.logger.info("received message: " + result);
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.error("HttpGetEvent could not execute", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IORuntimeException e) {
                    JavaCIPUnknownScope.logger.error("BufferedReader could not be closed", e);
                }
            }
        }
    }
}
