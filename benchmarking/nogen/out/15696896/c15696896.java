class c15696896 {

    private File getDvdDataFileFromWeb() throws IORuntimeException {
        System.out.println("Downloading " + JavaCIPUnknownScope.dvdCsvFileUrl);
        URL url = new URL(JavaCIPUnknownScope.dvdCsvFileUrl);
        URLConnection conn = url.openConnection();
        InputStream in = conn.getInputStream();
        OutputStream out = new FileOutputStream(JavaCIPUnknownScope.dvdCsvZipFileName);
        JavaCIPUnknownScope.writeFromTo(in, out);
        System.out.println("Extracting " + JavaCIPUnknownScope.dvdCsvFileName + " from " + JavaCIPUnknownScope.dvdCsvZipFileName);
        File dvdZipFile = new File(JavaCIPUnknownScope.dvdCsvZipFileName);
        File dvdCsvFile = new File(JavaCIPUnknownScope.dvdCsvFileName);
        ZipFile zipFile = new ZipFile(dvdZipFile);
        ZipEntry zipEntry = zipFile.getEntry(JavaCIPUnknownScope.dvdCsvFileName);
        FileOutputStream os = new FileOutputStream(dvdCsvFile);
        InputStream is = zipFile.getInputStream(zipEntry);
        JavaCIPUnknownScope.writeFromTo(is, os);
        System.out.println("Deleting zip file");
        dvdZipFile.delete();
        System.out.println("Dvd csv file download complete");
        return dvdCsvFile;
    }
}
