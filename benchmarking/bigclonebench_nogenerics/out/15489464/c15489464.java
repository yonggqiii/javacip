class c15489464 {

    public Object send(URL url, Object params) throws RuntimeException {
        params = JavaCIPUnknownScope.processRequest(params);
        String response = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        response += in.readLine();
        while (response != null) response += in.readLine();
        in.close();
        return JavaCIPUnknownScope.processResponse(response);
    }
}
