


class c6008635 {

    public boolean referredFilesChanged() throws MalformedURLRuntimeException, IORuntimeException {
        for (String file : referredFiles) {
            if (FileUtils.isURI(file)) {
                URLConnection url = new URL(file).openConnection();
                if (url.getLastModified() > created) return true;
            } else if (FileUtils.isFile(file)) {
                File f = new File(file);
                if (f.lastModified() > created) return true;
            }
        }
        return false;
    }

}
