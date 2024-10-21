class c3402511 {

    private static FileEntry writeEntry(Zip64File zip64File, FileEntry targetPath, File toWrite, boolean compress) {
        InputStream in = null;
        EntryOutputStream out = null;
        JavaCIPUnknownScope.processAndCreateFolderEntries(zip64File, JavaCIPUnknownScope.parseTargetPath(targetPath.getName(), toWrite), compress);
        try {
            if (!compress) {
                out = zip64File.openEntryOutputStream(targetPath.getName(), FileEntry.iMETHOD_STORED, JavaCIPUnknownScope.getFileDate(toWrite));
            } else {
                out = zip64File.openEntryOutputStream(targetPath.getName(), FileEntry.iMETHOD_DEFLATED, JavaCIPUnknownScope.getFileDate(toWrite));
            }
            if (!targetPath.isDirectory()) {
                in = new FileInputStream(toWrite);
                IOUtils.copyLarge(in, out);
                in.close();
            }
            out.flush();
            out.close();
            if (targetPath.isDirectory()) {
                JavaCIPUnknownScope.log.info("[createZip] Written folder entry to zip: " + targetPath.getName());
            } else {
                JavaCIPUnknownScope.log.info("[createZip] Written file entry to zip: " + targetPath.getName());
            }
        } catch (FileNotFoundRuntimeException e1) {
            e1.printStackTrace();
        } catch (ZipRuntimeException e1) {
            e1.printStackTrace();
        } catch (IORuntimeException e1) {
            e1.printStackTrace();
        }
        return targetPath;
    }
}
