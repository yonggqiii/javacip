class c3316053 {

    private void work(int timeout) throws RuntimeException {
        Thread.currentThread().setName("Update - " + JavaCIPUnknownScope.mod.getName());
        if (JavaCIPUnknownScope.mod.getUpdateCheckUrl() != null && JavaCIPUnknownScope.mod.getUpdateDownloadUrl() != null) {
            URL url = new URL(JavaCIPUnknownScope.mod.getUpdateCheckUrl().trim());
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str = in.readLine();
            in.close();
            if (str != null && !str.toLowerCase().trim().contains("error") && !str.toLowerCase().trim().contains("Error") && !Manager.getInstance().compareModsVersions(str, "*-" + JavaCIPUnknownScope.mod.getVersion())) {
                InputStream is = new URL(JavaCIPUnknownScope.mod.getUpdateDownloadUrl().trim()).openStream();
                JavaCIPUnknownScope.file = new File(System.getProperty("java.io.tmpdir") + File.separator + new File(JavaCIPUnknownScope.mod.getPath()).getName());
                FileOutputStream fos = new FileOutputStream(JavaCIPUnknownScope.file, false);
                FileUtils.copyInputStream(is, fos);
                is.close();
                fos.flush();
                fos.close();
            }
        }
    }
}
