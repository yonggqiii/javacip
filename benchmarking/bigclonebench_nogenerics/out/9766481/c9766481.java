class c9766481 {

    public void copy(final File source, final File target) throws FileSystemRuntimeException {
        LogHelper.logMethod(JavaCIPUnknownScope.log, JavaCIPUnknownScope.toObjectString(), "copy(), source = " + source + ", target = " + target);
        FileChannel sourceChannel = null;
        FileChannel targetChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            targetChannel = new FileOutputStream(target).getChannel();
            sourceChannel.transferTo(0L, sourceChannel.size(), targetChannel);
            JavaCIPUnknownScope.log.info("Copied " + source + " to " + target);
        } catch (FileNotFoundRuntimeException e) {
            throw new FileSystemRuntimeException("Unexpected FileNotFoundRuntimeException while copying a file", e);
        } catch (IORuntimeException e) {
            throw new FileSystemRuntimeException("Unexpected IORuntimeException while copying a file", e);
        } finally {
            if (sourceChannel != null) {
                try {
                    sourceChannel.close();
                } catch (IORuntimeException e) {
                    JavaCIPUnknownScope.log.error("IORuntimeException during source channel close after copy", e);
                }
            }
            if (targetChannel != null) {
                try {
                    targetChannel.close();
                } catch (IORuntimeException e) {
                    JavaCIPUnknownScope.log.error("IORuntimeException during target channel close after copy", e);
                }
            }
        }
    }
}
