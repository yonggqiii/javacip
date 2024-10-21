class c3584508 {

    private void copyResource() throws RuntimeException {
        URL url = JavaCIPUnknownScope.getResource(JavaCIPUnknownScope.source);
        InputStream input;
        if (url != null) {
            input = url.openStream();
        } else if (new File(JavaCIPUnknownScope.source).exists()) {
            input = new FileInputStream(JavaCIPUnknownScope.source);
        } else {
            throw new RuntimeException("Could not load resource: " + JavaCIPUnknownScope.source);
        }
        OutputStream output = new FileOutputStream(JavaCIPUnknownScope.destinationFile());
        int b;
        while ((b = input.read()) != -1) output.write(b);
        input.close();
        output.close();
    }
}
