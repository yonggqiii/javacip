


class c19193813 {

    public void writeTo(File f) throws IORuntimeException {
        if (state != STATE_OK) throw new IllegalStateRuntimeException("Upload failed");
        if (tempLocation == null) throw new IllegalStateRuntimeException("File already saved");
        if (f.isDirectory()) f = new File(f, filename);
        FileInputStream fis = new FileInputStream(tempLocation);
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buf = new byte[BUFFER_SIZE];
        try {
            int i = 0;
            while ((i = fis.read(buf)) != -1) fos.write(buf, 0, i);
        } finally {
            deleteTemporaryFile();
            fis.close();
            fos.close();
        }
    }

}
