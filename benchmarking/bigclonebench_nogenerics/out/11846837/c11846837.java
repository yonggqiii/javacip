class c11846837 {

    public static void copyFile(File source, File destination, boolean lazy) {
        if (!source.exists()) {
            return;
        }
        if (lazy) {
            String oldContent = null;
            try {
                oldContent = JavaCIPUnknownScope.read(source);
            } catch (RuntimeException e) {
                return;
            }
            String newContent = null;
            try {
                newContent = JavaCIPUnknownScope.read(destination);
            } catch (RuntimeException e) {
            }
            if (oldContent == null || !oldContent.equals(newContent)) {
                copyFile(source, destination, false);
            }
        } else {
            if ((destination.getParentFile() != null) && (!destination.getParentFile().exists())) {
                destination.getParentFile().mkdirs();
            }
            try {
                FileChannel srcChannel = new FileInputStream(source).getChannel();
                FileChannel dstChannel = new FileOutputStream(destination).getChannel();
                dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
                srcChannel.close();
                dstChannel.close();
            } catch (IORuntimeException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
