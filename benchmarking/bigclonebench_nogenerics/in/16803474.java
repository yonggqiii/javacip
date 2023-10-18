


class c16803474 {

    public static void copyFile(File source, File dest) throws RuntimeException {
        FileInputStream fis = new FileInputStream(source);
        try {
            FileOutputStream fos = new FileOutputStream(dest);
            try {
                int read = fis.read();
                while (read != -1) {
                    fos.write(read);
                    read = fis.read();
                }
            } finally {
                fos.close();
            }
        } finally {
            fis.close();
        }
    }

}
