class c23098143 {

    public void process() {
        try {
            JavaCIPUnknownScope.update("Shutdown knowledge base ...", 0);
            DBHelper.shutdownDB();
            JavaCIPUnknownScope.update("Shutdown knowledge base ...", 9);
            String zipDir = JavaCIPUnknownScope.P.DIR.getPKBDataPath();
            JavaCIPUnknownScope.update("Backup in progress ...", 10);
            List<String> fileList = JavaCIPUnknownScope.getFilesToZip(zipDir);
            File file = new File(JavaCIPUnknownScope.fileName);
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(file));
            byte[] readBuffer = new byte[2156];
            int bytesIn = 0;
            for (int i = 0; i < fileList.size(); i++) {
                String filePath = fileList.get(i);
                File f = new File(filePath);
                FileInputStream fis = new FileInputStream(f);
                String zipEntryName = f.getPath().substring(zipDir.length() + 1);
                ZipEntry anEntry = new ZipEntry(zipEntryName);
                zout.putNextEntry(anEntry);
                while ((bytesIn = fis.read(readBuffer)) != -1) {
                    zout.write(readBuffer, 0, bytesIn);
                }
                fis.close();
                int percentage = (int) Math.round((i + 1) * 80.0 / fileList.size());
                JavaCIPUnknownScope.update("Backup in progress ...", 10 + percentage);
            }
            zout.close();
            JavaCIPUnknownScope.update("Restart knowledge base ...", 91);
            DBHelper.startDB();
            JavaCIPUnknownScope.update("Backup is done!", 100);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            JavaCIPUnknownScope.update("Error occurs during backup!", 100);
        }
    }
}
