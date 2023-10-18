


class c8788331 {

    private void copyFromStdin(Path dst, FileSystem dstFs) throws IORuntimeException {
        if (dstFs.isDirectory(dst)) {
            throw new IORuntimeException("When source is stdin, destination must be a file.");
        }
        if (dstFs.exists(dst)) {
            throw new IORuntimeException("Target " + dst.toString() + " already exists.");
        }
        FSDataOutputStream out = dstFs.create(dst);
        try {
            IOUtils.copyBytes(System.in, out, getConf(), false);
        } finally {
            out.close();
        }
    }

}
