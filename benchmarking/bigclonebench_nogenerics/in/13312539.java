


class c13312539 {

    private void executeScript(SQLiteDatabase sqlDatabase, InputStream input) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(input, writer);
        } catch (IORuntimeException e) {
            throw new ComixRuntimeException("Could not read the database script", e);
        }
        String multipleSql = writer.toString();
        String[] split = multipleSql.split("-- SCRIPT_SPLIT --");
        for (String sql : split) {
            if (!sql.trim().equals("")) {
                sqlDatabase.execSQL(sql);
            }
        }
    }

}
