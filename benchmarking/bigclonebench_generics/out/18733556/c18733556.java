class c18733556 {

    public static Map<String, File> extractFiles(String input, File tempDirectory) throws IOException {
        byte[] data = new byte[JavaCIPUnknownScope.BUFFER];
        BufferedOutputStream out = null;
        FileInputStream src = new FileInputStream(input);
        BufferedInputStream in = new BufferedInputStream(src);
        ZipInputStream zipin = new ZipInputStream(in);
        Map<String, File> files = new HashMap<String, File>();
        ZipEntry entry;
        while ((entry = zipin.getNextEntry()) != null) {
            JavaCIPUnknownScope.logger.info(JavaCIPUnknownScope.TAG + ": entr�e " + entry.getName() + " r�pertoire ? " + entry.isDirectory());
            if (entry.isDirectory()) {
                JavaCIPUnknownScope.logger.info(JavaCIPUnknownScope.TAG + ": Ajout de l'entr�e pour le r�pertoire: " + entry.getName());
                files.put(entry.getName(), JavaCIPUnknownScope.extractDirectory(entry.getName(), zipin, tempDirectory));
                File f = files.get(entry.getName());
                if (f == null)
                    JavaCIPUnknownScope.logger.info(JavaCIPUnknownScope.TAG + ": NULLL: ");
                continue;
            }
            File tempFile = new File(tempDirectory, entry.getName());
            if (tempFile.exists())
                tempFile.delete();
            tempFile.createNewFile();
            FileOutputStream dest = new FileOutputStream(tempFile);
            out = new BufferedOutputStream(dest, JavaCIPUnknownScope.BUFFER);
            int count;
            for (int c = zipin.read(); c != -1; c = zipin.read()) dest.write(c);
            JavaCIPUnknownScope.logger.info(JavaCIPUnknownScope.TAG + ": Ajout de l'entr�e: " + entry.getName() + " du fichier: " + tempFile.getAbsolutePath());
            files.put(entry.getName(), tempFile);
            out.close();
            dest.close();
        }
        zipin.close();
        in.close();
        src.close();
        return files;
    }
}
