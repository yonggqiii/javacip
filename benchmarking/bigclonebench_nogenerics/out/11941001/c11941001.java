class c11941001 {

    private static void insertFiles(Connection con, File file) throws IORuntimeException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = bf.readLine();
        while (line != null) {
            if (!line.startsWith(" ") && !line.startsWith("#")) {
                try {
                    System.out.println("Exec: " + line);
                    PreparedStatement prep = con.prepareStatement(line);
                    prep.executeUpdate();
                    prep.close();
                    con.commit();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    try {
                        con.rollback();
                    } catch (SQLRuntimeException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            line = bf.readLine();
        }
        bf.close();
    }
}
