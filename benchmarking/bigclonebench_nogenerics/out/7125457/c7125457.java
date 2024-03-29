class c7125457 {

    void readFileHeader(DmgConfigDMO config, ConfigLocation sl) throws IORuntimeException {
        JavaCIPUnknownScope.fileHeader = "";
        if (config.getGeneratedFileHeader() != null) {
            StringBuffer sb = new StringBuffer();
            if (sl.getJarFilename() != null) {
                URL url = new URL("jar:file:" + sl.getJarFilename() + "!/" + sl.getJarDirectory() + "/" + config.getGeneratedFileHeader());
                LineNumberReader in = new LineNumberReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    sb.append(str + "\n");
                }
                in.close();
            } else {
                LineNumberReader in = new LineNumberReader(new FileReader(sl.getDirectory() + File.separator + config.getGeneratedFileHeader()));
                String str;
                while ((str = in.readLine()) != null) {
                    sb.append(str + "\n");
                }
                in.close();
            }
            JavaCIPUnknownScope.fileHeader = sb.toString();
        }
    }
}
