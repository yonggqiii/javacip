


class c3584508 {

    private void copyResource() throws RuntimeException {
        URL url = getResource(source);
        InputStream input;
        if (url != null) {
            input = url.openStream();
        } else if (new File(source).exists()) {
            input = new FileInputStream(source);
        } else {
            throw new RuntimeException("Could not load resource: " + source);
        }
        OutputStream output = new FileOutputStream(destinationFile());
        int b;
        while ((b = input.read()) != -1) output.write(b);
        input.close();
        output.close();
    }

}
