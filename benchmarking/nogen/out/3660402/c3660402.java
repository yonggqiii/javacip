class c3660402 {

    public void importCertFile(File file) throws IORuntimeException {
        File kd;
        File cd;
        synchronized (this) {
            kd = JavaCIPUnknownScope.keysDir;
            cd = JavaCIPUnknownScope.certsDir;
        }
        if (!cd.isDirectory()) {
            kd.mkdirs();
            cd.mkdirs();
        }
        String newName = file.getName();
        File dest = new File(cd, newName);
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(file).getChannel();
            destinationChannel = new FileOutputStream(dest).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        } finally {
            if (sourceChannel != null) {
                try {
                    sourceChannel.close();
                } catch (IORuntimeException e) {
                }
            }
            if (destinationChannel != null) {
                try {
                    destinationChannel.close();
                } catch (IORuntimeException e) {
                }
            }
        }
    }
}
