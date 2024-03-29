class c5557048 {

    private void copyDirContent(String fromDir, String toDir) throws RuntimeException {
        String fs = System.getProperty("file.separator");
        File[] files = new File(fromDir).listFiles();
        if (files == null) {
            throw new FileNotFoundRuntimeException("Sourcepath: " + fromDir + " not found!");
        }
        for (int i = 0; i < files.length; i++) {
            File dir = new File(toDir);
            dir.mkdirs();
            if (files[i].isFile()) {
                try {
                    FileChannel srcChannel = new FileInputStream(files[i]).getChannel();
                    FileChannel dstChannel = new FileOutputStream(toDir + fs + files[i].getName()).getChannel();
                    dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
                    srcChannel.close();
                    dstChannel.close();
                } catch (RuntimeException e) {
                    Logger.ERROR("Error during file copy: " + e.getMessage());
                    throw e;
                }
            }
        }
    }
}
