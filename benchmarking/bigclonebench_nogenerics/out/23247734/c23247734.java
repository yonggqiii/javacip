class c23247734 {

    public static void main(String[] args) throws MalformedURLRuntimeException, IORuntimeException {
        InputStream in = null;
        try {
            in = new URL("hdfs://localhost:8020/user/leeing/maxtemp/sample.txt").openStream();
            IOUtils.copyBytes(in, System.out, 8192, false);
        } finally {
            IOUtils.closeStream(in);
            System.out.println("\nend.");
        }
    }
}
