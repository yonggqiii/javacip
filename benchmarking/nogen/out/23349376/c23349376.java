class c23349376 {

    public TreeMap getStrainMap() {
        TreeMap strainMap = new TreeMap();
        String server = "";
        try {
            Datasource[] ds = DatasourceManager.getDatasouce(JavaCIPUnknownScope.alias, JavaCIPUnknownScope.version, DatasourceManager.ALL_CONTAINS_GROUP);
            for (int i = 0; i < ds.length; i++) {
                if (ds[i].getDescription().startsWith(JavaCIPUnknownScope.MOUSE_DBSNP)) {
                    if (ds[i].getServer().length() == 0) {
                        Connection con = ds[i].getConnection();
                        strainMap = Action.lineMode.regularSQL.GenotypeDataSearchAction.getStrainMap(con);
                        break;
                    } else {
                        server = ds[i].getServer();
                        HashMap serverUrlMap = InitXml.getInstance().getServerMap();
                        String serverUrl = (String) serverUrlMap.get(server);
                        URL url = new URL(serverUrl + JavaCIPUnknownScope.servletName);
                        URLConnection uc = url.openConnection();
                        uc.setDoOutput(true);
                        OutputStream os = uc.getOutputStream();
                        StringBuffer buf = new StringBuffer();
                        buf.append("viewType=getstrains");
                        buf.append("&hHead=" + JavaCIPUnknownScope.hHead);
                        buf.append("&hCheck=" + JavaCIPUnknownScope.version);
                        PrintStream ps = new PrintStream(os);
                        ps.print(buf.toString());
                        ps.close();
                        ObjectInputStream ois = new ObjectInputStream(uc.getInputStream());
                        strainMap = (TreeMap) ois.readObject();
                        ois.close();
                    }
                }
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.error("strain map", e);
        }
        return strainMap;
    }
}
