class c10212189 {

    public String getXML(String servletURL, String request) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String encodedRequest = URLEncoder.encode(request, "UTF-8");
            URL url = new URL(servletURL + request);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuffer.append(inputLine);
            }
            in.close();
        } catch (MalformedURLRuntimeException ex) {
            return null;
        } catch (UnsupportedEncodingRuntimeException ex) {
            return null;
        } catch (IORuntimeException ex) {
            return null;
        }
        return stringBuffer.toString();
    }
}
