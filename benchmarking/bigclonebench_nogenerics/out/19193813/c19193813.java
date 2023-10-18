class c19193813 {

    public void writeTo(File f) throws IORuntimeException {
        if (JavaCIPUnknownScope.state != JavaCIPUnknownScope.STATE_OK)
            throw new IllegalStateRuntimeException("Upload failed");
        if (JavaCIPUnknownScope.tempLocation == null)
            throw new IllegalStateRuntimeException("File already saved");
        if (f.isDirectory())
            f = new File(f, JavaCIPUnknownScope.filename);
        FileInputStream fis = new FileInputStream(JavaCIPUnknownScope.tempLocation);
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buf = new byte[JavaCIPUnknownScope.BUFFER_SIZE];
        try {
            int i = 0;
            while ((i = fis.read(buf)) != -1) fos.write(buf, 0, i);
        } finally {
            JavaCIPUnknownScope.deleteTemporaryFile();
            fis.close();
            fos.close();
        }
    }
}
