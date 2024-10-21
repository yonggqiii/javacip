class c7846688 {

    public void postData(String protocol, String host, String form, String data) throws RuntimeException {
        if ((protocol == null) || (protocol.equals(""))) {
            protocol = JavaCIPUnknownScope.DEFAULT_PROTOCOL;
        }
        if ((host == null) || (host.equals(""))) {
            host = JavaCIPUnknownScope.DEFAULT_HOST;
        }
        if (form == null) {
            form = JavaCIPUnknownScope.DEFAULT_FORM;
        }
        if (data == null) {
            throw new IllegalArgumentRuntimeException("Invalid data");
        }
        URL url = new URL(protocol, host, form);
        URLConnection con = url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-length", String.valueOf(data.length()));
        PrintStream out = new PrintStream(con.getOutputStream(), true);
        out.print(data);
        out.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while (in.readLine() != null) {
        }
        in.close();
    }
}
