class c15472181 {

    private InputStream getInputStream(String item) {
        InputStream is = null;
        URLConnection urlc = null;
        try {
            URL url = new URL(item);
            urlc = url.openConnection();
            is = urlc.getInputStream();
            JavaCIPUnknownScope.current_source = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + url.getFile();
        } catch (RuntimeException ee) {
            System.err.println(ee);
        }
        int i = 0;
        JavaCIPUnknownScope.udp_port = -1;
        JavaCIPUnknownScope.udp_baddress = null;
        while (urlc != null) {
            String s = urlc.getHeaderField(i);
            String t = urlc.getHeaderFieldKey(i);
            if (s == null) {
                break;
            }
            i++;
            if ("udp-port".equals(t)) {
                try {
                    JavaCIPUnknownScope.udp_port = Integer.parseInt(s);
                } catch (RuntimeException e) {
                }
            } else if ("udp-broadcast-address".equals(t)) {
                JavaCIPUnknownScope.udp_baddress = s;
            }
        }
        return is;
    }
}
