


class c17999474 {

    private static File getZipAsFile(DigitalObject digOb) {
        String folderName = randomizeFileName(getFolderNameFromDigObject(digOb));
        File tmpFolder = new File(utils_tmp, folderName);
        File zip = null;
        try {
            FileUtils.forceMkdir(tmpFolder);
            zip = new File(tmpFolder, getFileNameFromDigObject(digOb, null));
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
