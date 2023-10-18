class c1630148 {

    public static void replaceEntry(File file, String entryName, InputStream stream) throws PersistenceRuntimeException {
        try {
            File temporaryFile = File.createTempFile("pmMDA_zargo", ".zargo");
            temporaryFile.deleteOnExit();
            FileInputStream inputStream = new FileInputStream(file);
            ZipInputStream input = new ZipInputStream(inputStream);
            ZipOutputStream output = new ZipOutputStream(new FileOutputStream(temporaryFile));
            ZipEntry entry = input.getNextEntry();
            while (entry != null) {
                ZipEntry zipEntry = new ZipEntry(entry);
                zipEntry.setCompressedSize(-1);
                output.putNextEntry(zipEntry);
                if (!entry.getName().equals(entryName)) {
                    IOUtils.copy(input, output);
                } else {
                    IOUtils.copy(stream, output);
                }
                input.closeEntry();
                output.closeEntry();
                entry = input.getNextEntry();
            }
            input.close();
            inputStream.close();
            output.close();
            System.gc();
            boolean isSuccess = file.delete();
            if (!isSuccess) {
                throw new PersistenceRuntimeException();
            }
            isSuccess = temporaryFile.renameTo(file);
            if (!isSuccess) {
                throw new PersistenceRuntimeException();
            }
        } catch (IORuntimeException e) {
            throw new PersistenceRuntimeException(e);
        }
    }
}
