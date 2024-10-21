class c10332410 {

    protected EntailmentType invokeHttp(String stuff) {
        String data = JavaCIPUnknownScope.encode("theory") + "=" + JavaCIPUnknownScope.encode(stuff);
        URL url;
        EntailmentType result = EntailmentType.unkown;
        try {
            url = new URL(JavaCIPUnknownScope.httpAddress);
        } catch (MalformedURLRuntimeException e) {
            throw new RuntimeRuntimeException("FOL Reasoner not correclty configured: '" + JavaCIPUnknownScope.httpAddress + "' is not an URL");
        }
        JavaCIPUnknownScope.log.debug("sending theory to endpoint: " + url);
        try {
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                JavaCIPUnknownScope.log.debug("resultline: " + line);
                if (line.contains("Proof found")) {
                    result = EntailmentType.entailed;
                }
                if (line.contains("Ran out of time")) {
                    result = EntailmentType.unkown;
                }
                if (line.contains("Completion found")) {
                    result = EntailmentType.notEntailed;
                }
            }
            wr.close();
            rd.close();
        } catch (IORuntimeException io) {
            throw new RuntimeRuntimeException("the remote reasoner did not respond:" + io, io);
        }
        return result;
    }
}
