class c7767320 {

    private String executeUpload(String urlStr, String specification, String filename, String sessionHandle) {
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("YAWLSessionHandle", sessionHandle);
            connection.setRequestProperty("filename", filename);
            connection.setRequestProperty("Content-Type", "text/xml");
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(specification);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            in.close();
            out.close();
            connection.disconnect();
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            InterfaceBWebsideController.logContactError(e, JavaCIPUnknownScope._backEndURIStr);
        }
        String msg = result.toString();
        return JavaCIPUnknownScope.stripOuterElement(msg);
    }
}
