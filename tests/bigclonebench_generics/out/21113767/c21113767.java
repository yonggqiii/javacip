class c21113767 {

    public void render(Output output) throws IOException {
        output.setStatus(JavaCIPUnknownScope.headersFile.getStatusCode(), JavaCIPUnknownScope.headersFile.getStatusMessage());
        for (Entry<String, Set<String>> header : JavaCIPUnknownScope.headersFile.getHeadersMap().entrySet()) {
            Set<String> values = header.getValue();
            for (String value : values) {
                output.addHeader(header.getKey(), value);
            }
        }
        if (JavaCIPUnknownScope.file != null) {
            InputStream inputStream = new FileInputStream(JavaCIPUnknownScope.file);
            try {
                output.open();
                OutputStream out = output.getOutputStream();
                IOUtils.copy(inputStream, out);
            } finally {
                inputStream.close();
                output.close();
            }
        }
    }
}
