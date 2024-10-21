


class c14380467 {

    public String readTemplateToString(String fileName) {
        URL url = null;
        url = classLoader.getResource(fileName);
        StringBuffer content = new StringBuffer();
        if (url == null) {
            String error = "Template file could not be found: " + fileName;
            throw new RuntimeRuntimeException(error);
        }
        try {
            BufferedReader breader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String strLine = "";
            while ((strLine = breader.readLine()) != null) {
                content.append(strLine).append("\n");
            }
            breader.close();
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException("Problem while loading file: " + fileName);
        }
        return content.toString();
    }

}
