class c9423426 {

    private void initialize() {
        if (!JavaCIPUnknownScope.initialized) {
            if (JavaCIPUnknownScope.context.getJavadocLinks() != null) {
                for (String url : JavaCIPUnknownScope.context.getJavadocLinks()) {
                    if (!url.endsWith("/")) {
                        url += "/";
                    }
                    StringWriter writer = new StringWriter();
                    try {
                        IOUtils.copy(new URL(url + "package-list").openStream(), writer);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        continue;
                    }
                    StringTokenizer tokenizer2 = new StringTokenizer(writer.toString());
                    while (tokenizer2.hasMoreTokens()) {
                        JavaCIPUnknownScope.javadocByPackage.put(tokenizer2.nextToken(), url);
                    }
                }
            }
            JavaCIPUnknownScope.initialized = true;
        }
    }
}
