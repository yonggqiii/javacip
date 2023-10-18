class c19731419 {

    public void transport(File file) throws TransportRuntimeException {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    transport(file);
                }
            } else if (file.isFile()) {
                try {
                    FileChannel inChannel = new FileInputStream(file).getChannel();
                    FileChannel outChannel = new FileOutputStream(JavaCIPUnknownScope.getOption("destination")).getChannel();
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                } catch (IORuntimeException e) {
                    JavaCIPUnknownScope.log.error("File transfer failed", e);
                }
            }
        }
    }
}
