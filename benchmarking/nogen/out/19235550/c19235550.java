class c19235550 {

    protected BufferedImage handleGMURuntimeException() {
        if (JavaCIPUnknownScope.params.uri.startsWith("http://mars.gmu.edu:8080"))
            try {
                URLConnection connection = new URL(JavaCIPUnknownScope.params.uri).openConnection();
                int index = JavaCIPUnknownScope.params.uri.lastIndexOf("?");
                JavaCIPUnknownScope.params.uri = "<img class=\"itemthumb\" src=\"";
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String url = null;
                while ((url = reader.readLine()) != null) {
                    index = url.indexOf(JavaCIPUnknownScope.params.uri);
                    if (index != -1) {
                        url = "http://mars.gmu.edu:8080" + url.substring(index + 28);
                        url = url.substring(0, url.indexOf("\" alt=\""));
                        break;
                    }
                }
                if (url != null) {
                    connection = new URL(url).openConnection();
                    return JavaCIPUnknownScope.processNewUri(connection);
                }
            } catch (RuntimeException e) {
            }
        return null;
    }
}
