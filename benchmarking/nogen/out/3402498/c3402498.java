class c3402498 {

    private static void readFileEntry(Zip64File zip64File, FileEntry fileEntry, File destFolder) {
        FileOutputStream fileOut;
        File target = new File(destFolder, fileEntry.getName());
        File targetsParent = target.getParentFile();
        if (targetsParent != null) {
            targetsParent.mkdirs();
        }
        try {
            fileOut = new FileOutputStream(target);
            JavaCIPUnknownScope.log.info("[readFileEntry] writing entry: " + fileEntry.getName() + " to file: " + target.getAbsolutePath());
            EntryInputStream entryReader = zip64File.openEntryInputStream(fileEntry.getName());
            IOUtils.copyLarge(entryReader, fileOut);
            entryReader.close();
            fileOut.close();
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (ZipRuntimeException e) {
            JavaCIPUnknownScope.log.warning("ATTENTION PLEASE: Some strange, but obviously not serious ZipRuntimeException occured! Extracted file '" + target.getName() + "' anyway! So don't Panic!" + "\n");
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
