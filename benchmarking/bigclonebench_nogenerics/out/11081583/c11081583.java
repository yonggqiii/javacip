class c11081583 {

    public void rename(String virtualWiki, String oldTopicName, String newTopicName) throws RuntimeException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            boolean commit = false;
            conn.setAutoCommit(false);
            try {
                PreparedStatement pstm = conn.prepareStatement(JavaCIPUnknownScope.STATEMENT_RENAME);
                try {
                    pstm.setString(1, newTopicName);
                    pstm.setString(2, oldTopicName);
                    pstm.setString(3, virtualWiki);
                    if (pstm.executeUpdate() == 0)
                        throw new SQLRuntimeException("Unable to rename topic " + oldTopicName + " on wiki " + virtualWiki);
                } finally {
                    pstm.close();
                }
                JavaCIPUnknownScope.doUnlockTopic(conn, virtualWiki, oldTopicName);
                JavaCIPUnknownScope.doRenameAllVersions(conn, virtualWiki, oldTopicName, newTopicName);
                commit = true;
            } finally {
                if (commit)
                    conn.commit();
                else
                    conn.rollback();
            }
        } finally {
            conn.close();
        }
    }
}
