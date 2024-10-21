class c17999474 {

    private static File getZipAsFile(DigitalObject digOb) {
        String folderName = JavaCIPUnknownScope.randomizeFileName(JavaCIPUnknownScope.getFolderNameFromDigObject(digOb));
        File tmpFolder = new File(JavaCIPUnknownScope.utils_tmp, folderName);
        File zip = null;
        try {
            FileUtils.forceMkdir(tmpFolder);
            zip = new File(tmpFolder, JavaCIPUnknownScope.getFileNameFromDigObject(digOb, null));
            FileOutputStream out = new FileOutputStream(zip);
            IOUtils.copyLarge(digOb.getContent().getInputStream(), out);
            out.close();
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return zip;
    }
}
