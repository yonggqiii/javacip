class c6374428 {

    public String getTags(String content) {
        StringBuffer xml = new StringBuffer();
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        try {
            String reqData = URLEncoder.encode(JavaCIPUnknownScope.paramName, "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");
            URL service = new URL(JavaCIPUnknownScope.cmdUrl);
            URLConnection urlConn = service.openConnection();
            urlConn.setDoOutput(true);
            urlConn.connect();
            osw = new OutputStreamWriter(urlConn.getOutputStream());
            osw.write(reqData);
            osw.flush();
            br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                xml.append(line);
            }
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
        return xml.toString();
    }
}
