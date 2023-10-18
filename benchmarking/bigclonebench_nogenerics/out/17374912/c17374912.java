class c17374912 {

    public void copyTo(File folder) {
        if (!JavaCIPUnknownScope.isNewFile()) {
            return;
        }
        if (!folder.exists()) {
            folder.mkdir();
        }
        File dest = new File(folder, JavaCIPUnknownScope.name);
        try {
            FileInputStream in = new FileInputStream(JavaCIPUnknownScope.currentPath);
            FileOutputStream out = new FileOutputStream(dest);
            byte[] readBuf = new byte[1024 * 512];
            int readLength;
            long totalCopiedSize = 0;
            boolean canceled = false;
            while ((readLength = in.read(readBuf)) != -1) {
                out.write(readBuf, 0, readLength);
            }
            in.close();
            out.close();
            if (canceled) {
                dest.delete();
            } else {
                JavaCIPUnknownScope.currentPath = dest;
                JavaCIPUnknownScope.newFile = false;
            }
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
