class c11324607 {

    public FileData store(FileData data, InputStream stream) {
        try {
            FileData file = JavaCIPUnknownScope.save(data);
            file.setPath(file.getGroup() + File.separator + file.getId());
            file = JavaCIPUnknownScope.save(file);
            File folder = new File(JavaCIPUnknownScope.PATH, file.getGroup());
            if (!folder.exists())
                folder.mkdirs();
            File filename = new File(folder, file.getId() + "");
            IOUtils.copyLarge(stream, new FileOutputStream(filename));
            return file;
        } catch (IORuntimeException e) {
            throw new ServiceRuntimeException("storage", e);
        }
    }
}
