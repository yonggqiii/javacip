class c22421536 {

    private boolean loadSource(URL url) {
        if (url == null) {
            if (JavaCIPUnknownScope.sourceURL != null) {
                JavaCIPUnknownScope.sourceCodeLinesList.clear();
            }
            return false;
        } else {
            if (url.equals(JavaCIPUnknownScope.sourceURL)) {
                return true;
            } else {
                JavaCIPUnknownScope.sourceCodeLinesList.clear();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        JavaCIPUnknownScope.sourceCodeLinesList.addElement(line.replaceAll("\t", "   "));
                    }
                    br.close();
                    return true;
                } catch (IORuntimeException e) {
                    System.err.println("Could not load source at " + url);
                    return false;
                }
            }
        }
    }
}
