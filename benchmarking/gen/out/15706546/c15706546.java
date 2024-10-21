class c15706546 {

    public boolean ImportData() {
        if (JavaCIPUnknownScope.fileData == null) {
            return false;
        }
        String line = new String();
        BufferedReader br;
        BufferedWriter bw;
        String tableName = new String();
        List<String> columns = new ArrayList<String>();
        long recordsNumber;
        String sql = new String();
        File tempDataFile;
        String filePath = new String();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(JavaCIPUnknownScope.fileData)));
            if (br.ready()) {
                if ((line = br.readLine()) != null) {
                    do {
                        tableName = JavaCIPUnknownScope.siteName + "_" + JavaCIPUnknownScope.getTableName(line);
                        columns = JavaCIPUnknownScope.getTableColumns(line);
                        tempDataFile = new File("./Data/" + tableName + ".txt");
                        tempDataFile.createNewFile();
                        filePath = tempDataFile.getCanonicalPath();
                        sql = JavaCIPUnknownScope.generateSQL(tableName, columns, filePath);
                        recordsNumber = JavaCIPUnknownScope.getRecordNumber(line);
                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempDataFile)));
                        for (long i = 0; i < recordsNumber; i++) {
                            bw.write(br.readLine() + "\r\n");
                        }
                        bw.close();
                        if (JavaCIPUnknownScope.sqlConnector != null) {
                            JavaCIPUnknownScope.sqlConnector.executeSQL(sql);
                        } else {
                            return false;
                        }
                    } while ((line = br.readLine()) != null);
                }
                br.close();
            }
        } catch (RuntimeException e) {
            RuntimeExceptionHandler.handleExcptin(e);
        }
        return true;
    }
}
