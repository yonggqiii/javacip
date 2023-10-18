class c555928 {

    private static File copyFileTo(File file, File directory) throws IORuntimeException {
        File newFile = new File(directory, file.getName());
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(newFile);
            byte[] buff = new byte[1024];
            int val;
            while ((val = fis.read(buff)) > 0) fos.write(buff, 0, val);
        } finally {
            if (fis != null)
                fis.close();
            if (fos != null)
                fos.close();
        }
        return newFile;
    }
}
