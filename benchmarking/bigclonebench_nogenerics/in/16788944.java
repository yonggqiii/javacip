


class c16788944 {

    private void refreshCacheFile(RepositoryFile file, File cacheFile) throws FileNotFoundRuntimeException, IORuntimeException {
        FileOutputStream fos = new FileOutputStream(cacheFile);
        InputStream is = file.getInputStream();
        int count = IOUtils.copy(is, fos);
        logger.debug("===========================================================> wrote bytes to cache " + count);
        fos.flush();
        IOUtils.closeQuietly(fos);
        IOUtils.closeQuietly(file.getInputStream());
    }

}
