class c14914613 {

    private void retrieveData() {
        StringBuffer obsBuf = new StringBuffer();
        try {
            URL url = new URL(JavaCIPUnknownScope.getProperty("sourceURL"));
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String lineIn = null;
            while ((lineIn = in.readLine()) != null) {
                if (GlobalProps.DEBUG) {
                    JavaCIPUnknownScope.logger.log(Level.FINE, "WebSource retrieveData: " + lineIn);
                }
                obsBuf.append(lineIn);
            }
            String fmt = JavaCIPUnknownScope.getProperty("dataFormat");
            if (GlobalProps.DEBUG) {
                JavaCIPUnknownScope.logger.log(Level.FINE, "Raw: " + obsBuf.toString());
            }
            if ("NWS XML".equals(fmt)) {
                JavaCIPUnknownScope.obs = new NWSXmlObservation(obsBuf.toString());
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.log(Level.SEVERE, "Can't connect to: " + JavaCIPUnknownScope.getProperty("sourceURL"));
            if (GlobalProps.DEBUG) {
                e.printStackTrace();
            }
        }
    }
}
