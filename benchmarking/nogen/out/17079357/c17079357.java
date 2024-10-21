class c17079357 {

    public static void copyFile(File file, String destDir) throws IORuntimeException {
        if (!JavaCIPUnknownScope.isCanReadFile(file))
            throw new RuntimeRuntimeException("The File can't read:" + file.getPath());
        if (!JavaCIPUnknownScope.isCanWriteDirectory(destDir))
            throw new RuntimeRuntimeException("The Directory can't write:" + destDir);
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;
        try {
            srcChannel = new FileInputStream(file).getChannel();
            dstChannel = new FileOutputStream(destDir + "/" + file.getName()).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            if (srcChannel != null)
                try {
                    srcChannel.close();
                } catch (IORuntimeException e) {
                }
            if (dstChannel != null)
                try {
                    dstChannel.close();
                } catch (IORuntimeException e) {
                }
        }
    }
}
