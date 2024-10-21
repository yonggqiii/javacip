


class c10158738 {

    public void add(String language, String tag, String root, String surface) throws FidoDatabaseRuntimeException, MorphologyTagNotFoundRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            try {
                conn = FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                if (containsTag(stmt, tag) == false) throw new MorphologyTagNotFoundRuntimeException(tag);
                if (isRuleUnique(stmt, language, tag, root, surface) == false) return;
                int row;
                if (root.equals("*") == true) row = getAppendRowForTag(stmt, language, tag); else if (root.indexOf('*') == -1) row = getFirstRowForTag(stmt, language, tag); else row = getFirstRegularFormForTag(stmt, language, tag);
                boolean use = determineRecognitionUse(root, surface);
                bumpAllRowsDown(stmt, language, tag, row);
                String sql = "insert into LanguageMorphologies (LanguageName, Rank, Root, Surface, MorphologyTag, Used) " + "values ('" + language + "', " + row + ", '" + root + "', '" + surface + "', '" + tag + "', ";
                if (use == true) sql = sql + "TRUE)"; else sql = sql + "FALSE)";
                stmt.executeUpdate(sql);
                conn.commit();
            } catch (SQLRuntimeException e) {
                if (conn != null) conn.rollback();
                throw e;
            } finally {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            }
        } catch (SQLRuntimeException e) {
            throw new FidoDatabaseRuntimeException(e);
        }
    }

}
