class c21495755 {

    protected Object doExecute() throws RuntimeException {
        if (JavaCIPUnknownScope.args.size() == 1 && "-".equals(JavaCIPUnknownScope.args.get(0))) {
            JavaCIPUnknownScope.log.info("Printing STDIN");
            JavaCIPUnknownScope.cat(new BufferedReader(JavaCIPUnknownScope.io.in), JavaCIPUnknownScope.io);
        } else {
            for (String filename : JavaCIPUnknownScope.args) {
                BufferedReader reader;
                try {
                    URL url = new URL(filename);
                    JavaCIPUnknownScope.log.info("Printing URL: " + url);
                    reader = new BufferedReader(new InputStreamReader(url.openStream()));
                } catch (MalformedURLRuntimeException ignore) {
                    File file = new File(filename);
                    JavaCIPUnknownScope.log.info("Printing file: " + file);
                    reader = new BufferedReader(new FileReader(file));
                }
                try {
                    JavaCIPUnknownScope.cat(reader, JavaCIPUnknownScope.io);
                } finally {
                    IOUtil.close(reader);
                }
            }
        }
        return JavaCIPUnknownScope.SUCCESS;
    }
}
