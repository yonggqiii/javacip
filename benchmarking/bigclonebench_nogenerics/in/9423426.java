


class c9423426 {

    private void initialize() {
        if (!initialized) {
            if (context.getJavadocLinks() != null) {
                for (String url : context.getJavadocLinks()) {
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
                        javadocByPackage.put(tokenizer2.nextToken(), url);
                    }
                }
            }
            initialized = true;
        }
    }

}
