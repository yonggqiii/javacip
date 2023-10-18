class c1008326 {

    public long copyDirAllFilesToDirectoryRecursive(String baseDirStr, String destDirStr, boolean copyOutputsRtIDsDirs) throws RuntimeException {
        long plussQuotaSize = 0;
        if (baseDirStr.endsWith(JavaCIPUnknownScope.sep)) {
            baseDirStr = baseDirStr.substring(0, baseDirStr.length() - 1);
        }
        if (destDirStr.endsWith(JavaCIPUnknownScope.sep)) {
            destDirStr = destDirStr.substring(0, destDirStr.length() - 1);
        }
        FileUtils.getInstance().createDirectory(destDirStr);
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        byte[] dataBuff = new byte[JavaCIPUnknownScope.bufferSize];
        File baseDir = new File(baseDirStr);
        baseDir.mkdirs();
        if (!baseDir.exists()) {
            JavaCIPUnknownScope.createDirectory(baseDirStr);
        }
        if ((baseDir.exists()) && (baseDir.isDirectory())) {
            String[] entryList = baseDir.list();
            if (entryList.length > 0) {
                for (int pos = 0; pos < entryList.length; pos++) {
                    String entryName = entryList[pos];
                    String oldPathFileName = baseDirStr + JavaCIPUnknownScope.sep + entryName;
                    File entryFile = new File(oldPathFileName);
                    if (entryFile.isFile()) {
                        String newPathFileName = destDirStr + JavaCIPUnknownScope.sep + entryName;
                        File newFile = new File(newPathFileName);
                        if (newFile.exists()) {
                            plussQuotaSize -= newFile.length();
                            newFile.delete();
                        }
                        in = new BufferedInputStream(new FileInputStream(oldPathFileName), JavaCIPUnknownScope.bufferSize);
                        out = new BufferedOutputStream(new FileOutputStream(newPathFileName), JavaCIPUnknownScope.bufferSize);
                        int readLen;
                        while ((readLen = in.read(dataBuff)) > 0) {
                            out.write(dataBuff, 0, readLen);
                            plussQuotaSize += readLen;
                        }
                        out.flush();
                        in.close();
                        out.close();
                    }
                    if (entryFile.isDirectory()) {
                        boolean enableCopyDir = false;
                        if (copyOutputsRtIDsDirs) {
                            enableCopyDir = true;
                        } else {
                            if (entryFile.getParentFile().getName().equals("outputs")) {
                                enableCopyDir = false;
                            } else {
                                enableCopyDir = true;
                            }
                        }
                        if (enableCopyDir) {
                            plussQuotaSize += this.copyDirAllFilesToDirectoryRecursive(baseDirStr + JavaCIPUnknownScope.sep + entryName, destDirStr + JavaCIPUnknownScope.sep + entryName, copyOutputsRtIDsDirs);
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException("Base dir not exist ! baseDirStr = (" + baseDirStr + ")");
        }
        return plussQuotaSize;
    }
}
