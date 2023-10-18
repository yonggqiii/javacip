class c6744825 {

    private static String getTextFromURL(HttpServletRequest req, String urlString) {
        StringBuffer buffer = new StringBuffer();
        if (!urlString.startsWith("http")) {
            String requestURL = req.getRequestURL().toString();
            urlString = requestURL.substring(0, requestURL.lastIndexOf("/")) + urlString;
        }
        try {
            URL url = new URL(urlString);
            BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                buffer.append(line);
                buffer.append(Constants.LF);
            }
        } catch (FileNotFoundRuntimeException nf) {
            JavaCIPUnknownScope.log.error("File not found: " + urlString, nf);
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.error("RuntimeException while reading file: " + urlString, e);
        }
        return buffer.toString();
    }
}
