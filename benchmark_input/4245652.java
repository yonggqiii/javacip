


class c4245652 {

    public InputStream getExportFile() {
        URL url = ExportAction.class.getClassLoader().getResource("sysConfig.xml");
        if (url != null) try {
            return url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
