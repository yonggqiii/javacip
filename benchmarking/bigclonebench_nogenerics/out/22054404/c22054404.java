class c22054404 {

    private void copyfile(File srcFile, File dstDir) throws FileNotFoundRuntimeException, IORuntimeException {
        if (srcFile.isDirectory()) {
            File newDestDir = new File(dstDir, srcFile.getName());
            newDestDir.mkdir();
            String[] fileNameList = srcFile.list();
            for (int i = 0; i < fileNameList.length; i++) {
                File newSouceFile = new File(srcFile, fileNameList[i]);
                copyfile(newSouceFile, newDestDir);
            }
        } else {
            File newDestFile = new File(dstDir, srcFile.getName());
            FileInputStream in = new FileInputStream(srcFile);
            FileOutputStream out = new FileOutputStream(newDestFile);
            FileChannel inChannel = in.getChannel();
            FileChannel outChannel = out.getChannel();
            long i;
            Logger.log("copyFile before- copiedSize = " + JavaCIPUnknownScope.copiedSize);
            for (i = 0; i < srcFile.length() - JavaCIPUnknownScope.BLOCK_LENGTH; i += JavaCIPUnknownScope.BLOCK_LENGTH) {
                synchronized (this) {
                    inChannel.transferTo(i, JavaCIPUnknownScope.BLOCK_LENGTH, outChannel);
                    JavaCIPUnknownScope.copiedSize += JavaCIPUnknownScope.BLOCK_LENGTH;
                }
            }
            synchronized (this) {
                inChannel.transferTo(i, srcFile.length() - i, outChannel);
                JavaCIPUnknownScope.copiedSize += srcFile.length() - i;
            }
            Logger.log("copyFile after copy- copiedSize = " + JavaCIPUnknownScope.copiedSize + "srcFile.length = " + srcFile.length() + "diff = " + (JavaCIPUnknownScope.copiedSize - srcFile.length()));
            in.close();
            out.close();
            outChannel = null;
            Logger.log("File copied.");
        }
    }
}
