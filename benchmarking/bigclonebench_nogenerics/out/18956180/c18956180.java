class c18956180 {

    private void copyMerge(Path[] sources, OutputStream out) throws IORuntimeException {
        Configuration conf = JavaCIPUnknownScope.getConf();
        for (int i = 0; i < sources.length; ++i) {
            FileSystem fs = sources[i].getFileSystem(conf);
            InputStream in = fs.open(sources[i]);
            try {
                IOUtils.copyBytes(in, out, conf, false);
            } finally {
                in.close();
            }
        }
    }
}