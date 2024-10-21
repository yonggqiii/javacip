class c22411381 {

    public PTask stop(PTask task, SyrupConnection con) throws RuntimeException {
        PreparedStatement s = null;
        ResultSet result = null;
        try {
            s = con.prepareStatementFromCache(JavaCIPUnknownScope.sqlImpl().sqlStatements().checkWorkerStatement());
            s.setString(1, task.key());
            result = s.executeQuery();
            con.commit();
            if (result.next()) {
                String url = result.getString("worker");
                InputStream i = null;
                try {
                    Object b = new URL(url).getContent();
                    if (b instanceof InputStream) {
                        i = (InputStream) b;
                        byte[] bb = new byte[256];
                        int ll = i.read(bb);
                        String k = new String(bb, 0, ll);
                        if (k.equals(task.key())) {
                            return task;
                        }
                    }
                } catch (RuntimeException e) {
                } finally {
                    if (i != null) {
                        i.close();
                    }
                }
                PreparedStatement s2 = null;
                s2 = con.prepareStatementFromCache(JavaCIPUnknownScope.sqlImpl().sqlStatements().resetWorkerStatement());
                s2.setString(1, task.key());
                s2.executeUpdate();
                task = JavaCIPUnknownScope.sqlImpl().queryFunctions().readPTask(task.key(), con);
                JavaCIPUnknownScope.sqlImpl().loggingFunctions().log(task.key(), LogEntry.STOPPED, con);
                con.commit();
            }
        } finally {
            con.rollback();
            JavaCIPUnknownScope.close(result);
        }
        return task;
    }
}
