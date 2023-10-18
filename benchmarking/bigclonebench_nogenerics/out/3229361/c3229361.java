class c3229361 {

    public void copyFile(String source_name, String dest_name) throws IORuntimeException {
        File source_file = new File(source_name);
        File destination_file = new File(dest_name);
        FileInputStream source = null;
        FileOutputStream destination = null;
        byte[] buffer;
        int bytes_read;
        try {
            if (!source_file.exists() || !source_file.isFile())
                throw new FileCopyRuntimeException(JavaCIPUnknownScope.QZ.PHRASES.getPhrase("25") + " " + source_name);
            if (!source_file.canRead())
                throw new FileCopyRuntimeException(JavaCIPUnknownScope.QZ.PHRASES.getPhrase("26") + " " + JavaCIPUnknownScope.QZ.PHRASES.getPhrase("27") + ": " + source_name);
            if (destination_file.exists()) {
                if (destination_file.isFile()) {
                    DataInputStream in = new DataInputStream(System.in);
                    String response;
                    if (!destination_file.canWrite())
                        throw new FileCopyRuntimeException(JavaCIPUnknownScope.QZ.PHRASES.getPhrase("28") + " " + JavaCIPUnknownScope.QZ.PHRASES.getPhrase("29") + ": " + dest_name);
                    System.out.print(JavaCIPUnknownScope.QZ.PHRASES.getPhrase("19") + dest_name + JavaCIPUnknownScope.QZ.PHRASES.getPhrase("30") + ": ");
                    System.out.flush();
                    response = in.readLine();
                    if (!response.equals("Y") && !response.equals("y"))
                        throw new FileCopyRuntimeException(JavaCIPUnknownScope.QZ.PHRASES.getPhrase("31"));
                } else
                    throw new FileCopyRuntimeException(JavaCIPUnknownScope.QZ.PHRASES.getPhrase("28") + " " + JavaCIPUnknownScope.QZ.PHRASES.getPhrase("32") + ": " + dest_name);
            } else {
                File parentdir = JavaCIPUnknownScope.parent(destination_file);
                if (!parentdir.exists())
                    throw new FileCopyRuntimeException(JavaCIPUnknownScope.QZ.PHRASES.getPhrase("28") + " " + JavaCIPUnknownScope.QZ.PHRASES.getPhrase("33") + ": " + dest_name);
                if (!parentdir.canWrite())
                    throw new FileCopyRuntimeException(JavaCIPUnknownScope.QZ.PHRASES.getPhrase("28") + " " + JavaCIPUnknownScope.QZ.PHRASES.getPhrase("34") + ": " + dest_name);
            }
            source = new FileInputStream(source_file);
            destination = new FileOutputStream(destination_file);
            buffer = new byte[1024];
            while (true) {
                bytes_read = source.read(buffer);
                if (bytes_read == -1)
                    break;
                destination.write(buffer, 0, bytes_read);
            }
        } finally {
            if (source != null)
                try {
                    source.close();
                } catch (IORuntimeException e) {
                    ;
                }
            if (destination != null)
                try {
                    destination.close();
                } catch (IORuntimeException e) {
                    ;
                }
        }
    }
}
