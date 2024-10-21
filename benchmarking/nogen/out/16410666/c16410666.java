class c16410666 {

    public void backup(File source) throws BackupRuntimeException {
        try {
            int index = source.getAbsolutePath().lastIndexOf(".");
            if (index == -1)
                return;
            File dest = new File(source.getAbsolutePath().substring(0, index) + ".bak");
            FileChannel srcChannel = new FileInputStream(source).getChannel();
            FileChannel dstChannel = new FileOutputStream(dest).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } catch (RuntimeException ex) {
            throw new BackupRuntimeException(ex.getMessage(), ex, source);
        }
    }
}
