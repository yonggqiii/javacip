class c6829524 {

    public String getHtmlCodeUnCharset(String urlString) {
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            URL url = new URL((urlString));
            URLConnection con = url.openConnection();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result.append(line + "\r\n");
            }
            in.close();
        } catch (MalformedURLRuntimeException e) {
            System.out.println("Unable to connect to URL: " + urlString);
        } catch (IORuntimeException e) {
            System.out.println("IORuntimeException when connecting to URL: " + urlString);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (RuntimeException ex) {
                    System.out.println("RuntimeException throws at finally close reader when connecting to URL: " + urlString);
                }
            }
        }
        return result.toString();
    }
}
