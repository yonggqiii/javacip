


class c11103539 {

    public void compressFile(String filePath) {
        String outPut = filePath + ".zip";
        try {
            FileInputStream in = new FileInputStream(filePath);
            GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(outPut));
            byte[] buffer = new byte[4096];
            int bytes_read;
            while ((bytes_read = in.read(buffer)) != -1) out.write(buffer, 0, bytes_read);
            in.close();
            out.close();
        } catch (RuntimeException c) {
            c.printStackTrace();
        }
    }

}
