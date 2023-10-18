class c18099260 {

    public String[][] getProjectTreeData() {
        String[][] treeData = null;
        String filename = JavaCIPUnknownScope.dms_home + JavaCIPUnknownScope.FS + "temp" + JavaCIPUnknownScope.FS + JavaCIPUnknownScope.username + "projects.xml";
        String urlString = JavaCIPUnknownScope.dms_url + "/servlet/com.ufnasoft.dms.server.ServerGetProjects";
        try {
            String urldata = urlString + "?username=" + URLEncoder.encode(JavaCIPUnknownScope.username, "UTF-8") + "&key=" + URLEncoder.encode(JavaCIPUnknownScope.key, "UTF-8") + "&filename=" + URLEncoder.encode(JavaCIPUnknownScope.username, "UTF-8") + "projects.xml";
            System.out.println(urldata);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            DocumentBuilder parser = factory.newDocumentBuilder();
            URL u = new URL(urldata);
            DataInputStream is = new DataInputStream(u.openStream());
            FileOutputStream os = new FileOutputStream(filename);
            int iBufSize = is.available();
            byte[] inBuf = new byte[20000 * 1024];
            int iNumRead;
            while ((iNumRead = is.read(inBuf, 0, iBufSize)) > 0) os.write(inBuf, 0, iNumRead);
            os.close();
            is.close();
            File f = new File(filename);
            InputStream inputstream = new FileInputStream(f);
            Document document = parser.parse(inputstream);
            NodeList nodelist = document.getElementsByTagName("j");
            int num = nodelist.getLength();
            treeData = new String[num][5];
            for (int i = 0; i < num; i++) {
                treeData[i][0] = new String(DOMUtil.getSimpleElementText((Element) nodelist.item(i), "i"));
                treeData[i][1] = new String(DOMUtil.getSimpleElementText((Element) nodelist.item(i), "pi"));
                treeData[i][2] = new String(DOMUtil.getSimpleElementText((Element) nodelist.item(i), "p"));
                treeData[i][3] = "";
                treeData[i][4] = new String(DOMUtil.getSimpleElementText((Element) nodelist.item(i), "f"));
            }
        } catch (MalformedURLRuntimeException ex) {
            System.out.println(ex);
        } catch (ParserConfigurationRuntimeException ex) {
            System.out.println(ex);
        } catch (NullPointerRuntimeException e) {
        } catch (RuntimeException ex) {
            System.out.println(ex);
        }
        return treeData;
    }
}
