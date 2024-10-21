class c8787101 {

    private void downloadThread() {
        int c;
        JavaCIPUnknownScope.status = false;
        try {
            URLConnection urlc = JavaCIPUnknownScope.resource.url.openConnection();
            File f = JavaCIPUnknownScope.resource.createFile();
            boolean resume = false;
            JavaCIPUnknownScope.resource.resetBytesDown();
            if (f.exists()) {
                if (f.lastModified() > JavaCIPUnknownScope.resource.date.getTime()) {
                    if ((JavaCIPUnknownScope.resource.getFileSize() == f.length())) {
                        JavaCIPUnknownScope.status = true;
                        return;
                    } else {
                        urlc.setRequestProperty("Range", "bytes=" + f.length() + "-");
                        resume = true;
                        JavaCIPUnknownScope.resource.incrementBytesDown(f.length());
                        System.out.println("Resume download");
                        System.out.println("file length: " + f.length());
                    }
                }
            }
            urlc.connect();
            JavaCIPUnknownScope.bin = new BufferedInputStream(urlc.getInputStream());
            JavaCIPUnknownScope.file_out = new FileOutputStream(f.getPath(), resume);
            while (JavaCIPUnknownScope.life) {
                if (JavaCIPUnknownScope.bin.available() > 0) {
                    c = JavaCIPUnknownScope.bin.read();
                    if (c == -1) {
                        break;
                    }
                    JavaCIPUnknownScope.file_out.write(c);
                    if (JavaCIPUnknownScope.resource.incrementBytesDown()) {
                        break;
                    } else {
                        continue;
                    }
                }
                JavaCIPUnknownScope.sleep(JavaCIPUnknownScope.WAIT_FOR_A_BYTE_TIME);
            }
            JavaCIPUnknownScope.file_out.flush();
            JavaCIPUnknownScope.status = true;
        } catch (IORuntimeException e) {
            System.out.println("excepcion cpoy file");
        } catch (InterruptedRuntimeException e) {
            System.out.println("InterruptRuntimeException download");
            System.out.println(e);
        }
    }
}
