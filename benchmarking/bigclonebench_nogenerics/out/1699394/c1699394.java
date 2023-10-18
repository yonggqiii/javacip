class c1699394 {

    private int getPage(StringBuffer ret) throws IORuntimeException {
        Properties sysProp;
        int ResponseCode = HttpURLConnection.HTTP_OK;
        BufferedReader br = null;
        try {
            URLConnection con = JavaCIPUnknownScope.url.openConnection();
            con.setDefaultUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(false);
            if (con instanceof HttpURLConnection) {
                HttpURLConnection httpcon = (HttpURLConnection) con;
                ResponseCode = httpcon.getResponseCode();
                if (ResponseCode != httpcon.HTTP_OK) {
                    httpcon.disconnect();
                    return (ResponseCode);
                }
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                int NumberOfLines = 0;
                while ((line = br.readLine()) != null) {
                    ret.append(line + "\n");
                    ++NumberOfLines;
                }
                httpcon.disconnect();
            } else {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    ret.append(line + "\n");
                }
            }
        } catch (IORuntimeException e) {
        } finally {
            if (br != null)
                br.close();
        }
        return ResponseCode;
    }
}
