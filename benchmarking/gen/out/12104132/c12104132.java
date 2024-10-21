class c12104132 {

    public void render(Output output) throws IOException {
        output.setStatus(JavaCIPUnknownScope.statusCode, JavaCIPUnknownScope.statusMessage);
        if (JavaCIPUnknownScope.headersMap != null) {
            Iterator<Entry<String, String>> iterator = JavaCIPUnknownScope.headersMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> header = iterator.next();
                output.addHeader(header.getKey(), header.getValue());
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
