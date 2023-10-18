class c7396679 {

    public static boolean copy(FileSystem srcFS, Path src, FileSystem dstFS, Path dst, boolean deleteSource, boolean overwrite, Configuration conf) throws IORuntimeException {
        JavaCIPUnknownScope.LOG.debug("[sgkim] copy - start");
        dst = JavaCIPUnknownScope.checkDest(src.getName(), dstFS, dst, overwrite);
        if (srcFS.getFileStatus(src).isDir()) {
            JavaCIPUnknownScope.checkDependencies(srcFS, src, dstFS, dst);
            if (!dstFS.mkdirs(dst)) {
                return false;
            }
            FileStatus[] contents = srcFS.listStatus(src);
            for (int i = 0; i < contents.length; i++) {
                copy(srcFS, contents[i].getPath(), dstFS, new Path(dst, contents[i].getPath().getName()), deleteSource, overwrite, conf);
            }
        } else if (srcFS.isFile(src)) {
            InputStream in = null;
            OutputStream out = null;
            try {
                JavaCIPUnknownScope.LOG.debug("[sgkim] srcFS: " + srcFS + ", src: " + src);
                in = srcFS.open(src);
                JavaCIPUnknownScope.LOG.debug("[sgkim] dstFS: " + dstFS + ", dst: " + dst);
                out = dstFS.create(dst, overwrite);
                JavaCIPUnknownScope.LOG.debug("[sgkim] copyBytes - start");
                IOUtils.copyBytes(in, out, conf, true);
                JavaCIPUnknownScope.LOG.debug("[sgkim] copyBytes - end");
            } catch (IORuntimeException e) {
                IOUtils.closeStream(out);
                IOUtils.closeStream(in);
                throw e;
            }
        } else {
            throw new IORuntimeException(src.toString() + ": No such file or directory");
        }
        JavaCIPUnknownScope.LOG.debug("[sgkim] copy - end");
        if (deleteSource) {
            return srcFS.delete(src, true);
        } else {
            return true;
        }
    }
}
