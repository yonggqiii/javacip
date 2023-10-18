class c356222 {

    public String connectToServlet() {
        URL urlStory = null;
        BufferedReader brStory;
        String result = "";
        try {
            urlStory = new URL(JavaCIPUnknownScope.getCodeBase(), "http://localhost:8080/javawebconsole/ToApplet");
        } catch (MalformedURLRuntimeException MUE) {
            MUE.printStackTrace();
        }
        try {
            brStory = new BufferedReader(new InputStreamReader(urlStory.openStream()));
            while (brStory.ready()) {
                result += brStory.readLine();
            }
        } catch (IORuntimeException IOE) {
            IOE.printStackTrace();
        }
        return result;
    }
}
