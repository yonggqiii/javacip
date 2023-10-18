class c15102902 {

    public static void translateTableMetaData(String baseDir, String tableName, NameSpaceDefinition nsDefinition) throws RuntimeException {
        JavaCIPUnknownScope.setVosiNS(baseDir, "table", nsDefinition);
        String filename = baseDir + "table.xsl";
        Scanner s = new Scanner(new File(filename));
        PrintWriter fw = new PrintWriter(new File(baseDir + tableName + ".xsl"));
        while (s.hasNextLine()) {
            fw.println(s.nextLine().replaceAll("TABLENAME", tableName));
        }
        s.close();
        fw.close();
        JavaCIPUnknownScope.applyStyle(baseDir + "tables.xml", baseDir + tableName + ".json", baseDir + tableName + ".xsl");
    }
}
