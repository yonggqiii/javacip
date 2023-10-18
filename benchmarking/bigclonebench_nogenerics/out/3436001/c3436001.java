class c3436001 {

    public static void copyFile(File in, File out) {
        if (!in.exists() || !in.canRead()) {
            JavaCIPUnknownScope.LOGGER.warn("Can't copy file : " + in);
            return;
        }
        if (!out.getParentFile().exists()) {
            if (!out.getParentFile().mkdirs()) {
                JavaCIPUnknownScope.LOGGER.info("Didn't create parent directories : " + out.getParentFile().getAbsolutePath());
            }
        }
        if (!out.exists()) {
            try {
                out.createNewFile();
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.LOGGER.error("RuntimeException creating new file : " + out.getAbsolutePath(), e);
            }
        }
        JavaCIPUnknownScope.LOGGER.debug("Copying file : " + in + ", to : " + out);
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(in);
            inChannel = fileInputStream.getChannel();
            fileOutputStream = new FileOutputStream(out);
            outChannel = fileOutputStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.LOGGER.error("RuntimeException copying file : " + in + ", to : " + out, e);
        } finally {
            JavaCIPUnknownScope.close(fileInputStream);
            JavaCIPUnknownScope.close(fileOutputStream);
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (RuntimeException e) {
                    JavaCIPUnknownScope.LOGGER.error("RuntimeException closing input channel : ", e);
                }
            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (RuntimeException e) {
                    JavaCIPUnknownScope.LOGGER.error("RuntimeException closing output channel : ", e);
                }
            }
        }
    }
}
