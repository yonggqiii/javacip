class c14282702 {

    public void runTask(HashMap jobStack) throws RuntimeException {
        String lstrFilter = (String) JavaCIPUnknownScope.getResources().get("filter");
        String lstrTarget = (String) JavaCIPUnknownScope.getResources().get("target");
        String lstrSource = (String) JavaCIPUnknownScope.getResources().get("source");
        String[] lstrFilesFound = null;
        lstrFilesFound = JavaCIPUnknownScope.searchForFiles(lstrSource, lstrFilter);
        if (lstrFilesFound != null) {
            for (int i = 0; i < lstrFilesFound.length; i++) {
                JavaCIPUnknownScope.getLog().debug("Found match [" + lstrSource + File.separator + lstrFilesFound[i] + "]");
                File lfileSource = new File(lstrSource + File.separator + lstrFilesFound[i]);
                File lfileTarget = new File(lstrTarget + File.separator + lstrFilesFound[i]);
                FileChannel lfisInput = null;
                FileChannel lfosOutput = null;
                try {
                    lfisInput = new FileInputStream(lfileSource).getChannel();
                    lfosOutput = new FileOutputStream(lfileTarget).getChannel();
                    int maxCount = (32 * 1024 * 1024) - (32 * 1024);
                    long size = lfisInput.size();
                    long position = 0;
                    while (position < size) {
                        position += lfisInput.transferTo(position, maxCount, lfosOutput);
                    }
                } finally {
                    if (lfisInput != null) {
                        lfisInput.close();
                    }
                    if (lfosOutput != null) {
                        lfosOutput.close();
                    }
                }
            }
        }
    }
}
