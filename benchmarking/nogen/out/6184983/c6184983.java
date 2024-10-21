class c6184983 {

    public boolean copyFile(String srcRootPath, String srcDir, String srcFileName, String destRootPath, String destDir, String destFileName) {
        File srcPath = new File(srcRootPath + JavaCIPUnknownScope.separator() + Database.getDomainName() + JavaCIPUnknownScope.separator() + srcDir);
        if (!srcPath.exists()) {
            try {
                srcPath.mkdirs();
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.logger.error("Can't create directory...:" + srcPath);
                return false;
            }
        }
        File destPath = new File(destRootPath + JavaCIPUnknownScope.separator() + Database.getDomainName() + JavaCIPUnknownScope.separator() + destDir);
        if (!destPath.exists()) {
            try {
                destPath.mkdirs();
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.logger.error("Can't create directory...:" + destPath);
                return false;
            }
        }
        File from = new File(srcPath + JavaCIPUnknownScope.separator() + srcFileName);
        File to = new File(destPath + JavaCIPUnknownScope.separator() + destFileName);
        boolean res = true;
        FileChannel srcChannel = null;
        FileChannel destChannel = null;
        try {
            srcChannel = new FileInputStream(from).getChannel();
            destChannel = new FileOutputStream(to).getChannel();
            destChannel.transferFrom(srcChannel, 0, srcChannel.size());
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.logger.error("RuntimeException", ex);
            res = false;
        } finally {
            if (destChannel != null) {
                try {
                    destChannel.close();
                } catch (IORuntimeException ex) {
                    JavaCIPUnknownScope.logger.error("RuntimeException", ex);
                    res = false;
                }
            }
            if (srcChannel != null) {
                try {
                    srcChannel.close();
                } catch (IORuntimeException ex) {
                    JavaCIPUnknownScope.logger.error("RuntimeException", ex);
                    res = false;
                }
            }
        }
        return res;
    }
}
