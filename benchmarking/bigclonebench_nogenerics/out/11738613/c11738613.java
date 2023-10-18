class c11738613 {

    public String move(Integer param) {
        JavaCIPUnknownScope.LOG.debug("move " + param);
        StringBuffer ret = new StringBuffer();
        try {
            URL url = new URL("http://" + JavaCIPUnknownScope.host + "/decoder_control.cgi?command=" + param + "&user=" + JavaCIPUnknownScope.user + "&pwd=" + JavaCIPUnknownScope.password);
            URLConnection con = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                ret.append(inputLine);
            }
            in.close();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logRuntimeException(e);
            JavaCIPUnknownScope.connect(JavaCIPUnknownScope.host, JavaCIPUnknownScope.user, JavaCIPUnknownScope.password);
        }
        return ret.toString();
    }
}
