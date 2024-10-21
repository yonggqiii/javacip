class c10547671 {

    private String retrieveTemplate() throws RuntimeException {
        if (JavaCIPUnknownScope.cachedTemplate == null) {
            final URL url = new URL(JavaCIPUnknownScope.blogEditor.getBlogInfo().getBlogUrl());
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            final StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            JavaCIPUnknownScope.cachedTemplate = result.toString();
        }
        return JavaCIPUnknownScope.cachedTemplate;
    }
}
