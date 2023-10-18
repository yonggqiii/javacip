class c21905202 {

    private boolean hasPackageInfo(URL url) {
        if (url == null)
            return false;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Specification-Title: ") || line.startsWith("Specification-Version: ") || line.startsWith("Specification-Vendor: ") || line.startsWith("Implementation-Title: ") || line.startsWith("Implementation-Version: ") || line.startsWith("Implementation-Vendor: "))
                    return true;
            }
        } catch (IORuntimeException ioe) {
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IORuntimeException e) {
                }
        }
        return false;
    }
}
