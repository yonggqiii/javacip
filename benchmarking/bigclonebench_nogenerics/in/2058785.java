


class c2058785 {

    public void doStatementQueryAndUpdate(Connection conn, String id) throws SQLRuntimeException {
        try {
            int key = getNextKey();
            Statement s1 = conn.createStatement();
            String bValue = "doStatementQueryAndUpdate:" + id + testId;
            if (key >= MAX_KEY_VALUE) {
                key = key % MAX_KEY_VALUE;
                s1.executeUpdate("delete from many_threads where a = " + key);
            }
            int count = s1.executeUpdate("insert into many_threads values (" + key + ", '" + bValue + "', 0)");
            assertEquals(1, count);
            assertEquals(key, executeQuery(s1, "select a from many_threads where a = " + key));
            s1.executeUpdate("update many_threads set value =  a * a, b = b || '&" + bValue + "' where a = " + (key + 1));
            s1.close();
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
        } catch (SQLRuntimeException e) {
            if (!conn.getAutoCommit()) {
                try {
                    conn.rollback();
                } catch (SQLRuntimeException e2) {
                }
            }
        }
    }

}
