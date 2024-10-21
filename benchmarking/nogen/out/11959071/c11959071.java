class c11959071 {

    private String download(MacroManagerProgress progress, String fileName, String url) throws RuntimeException {
        URLConnection conn = new URL(url).openConnection();
        progress.setMaximum(Math.max(0, conn.getContentLength()));
        String path = MiscUtilities.constructPath(JavaCIPUnknownScope.installDirectory, fileName);
        if (!JavaCIPUnknownScope.copy(progress, conn.getInputStream(), new FileOutputStream(path), true, true))
            return null;
        if (JavaCIPUnknownScope.archive_zip) {
            Enumeration entries;
            ZipFile zipFile;
            try {
                File tempFile = new File(path);
                zipFile = new ZipFile(tempFile);
                entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (entry.isDirectory()) {
                        String dpath = MiscUtilities.constructPath(JavaCIPUnknownScope.installDirectory, entry.getName());
                        (new File(dpath)).mkdir();
                        continue;
                    }
                    progress.setMaximum((int) entry.getSize());
                    String ePath = MiscUtilities.constructPath(JavaCIPUnknownScope.installDirectory, entry.getName());
                    JavaCIPUnknownScope.copy(progress, zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(ePath)), true, true);
                }
                zipFile.close();
                tempFile.delete();
            } catch (IORuntimeException ioe) {
                ioe.printStackTrace();
            }
        } else if (JavaCIPUnknownScope.archive_gzip) {
            File srce = new File(path);
            GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(srce));
            File temp = File.createTempFile("macro", "mgr");
            progress.setMaximum((int) srce.length());
            JavaCIPUnknownScope.copy(progress, gzis, new BufferedOutputStream(new FileOutputStream(temp)), true, true);
            TarArchive tarc = new TarArchive(new FileInputStream(temp));
            tarc.extractContents(new File(JavaCIPUnknownScope.installDirectory));
            tarc.closeArchive();
            (new File(path)).delete();
        }
        return path;
    }
}
