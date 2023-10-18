class c22321272 {

    public static void copyFile(File from, File to) throws RuntimeException {
        if (!from.exists())
            return;
        FileInputStream in = new FileInputStream(from);
        FileOutputStream out = new FileOutputStream(to);
        byte[] buffer = new byte[JavaCIPUnknownScope.BUFFER_SIZE];
        int bytes_read;
        while (true) {
            bytes_read = in.read(buffer);
            if (bytes_read == -1)
                break;
            out.write(buffer, 0, bytes_read);
        }
        out.flush();
        out.close();
        in.close();
    }
}
