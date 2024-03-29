


class c18354823 {

    private String storeEditionFile(InputStream in) throws IORuntimeException {
        String datadir = getCqPropertiesBeanSpring().getDatadir() + File.separator + "attachments" + File.separator;
        File attachmentsDir = new File(datadir);
        attachmentsDir.mkdirs();
        File storedEditionFile = File.createTempFile("edition_import_", ".tmp", attachmentsDir);
        FileOutputStream out = new FileOutputStream(storedEditionFile);
        IOUtils.copyLarge(in, out);
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(in);
        return storedEditionFile.getAbsolutePath();
    }

}
