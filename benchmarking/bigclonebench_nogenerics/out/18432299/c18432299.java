class c18432299 {

    private String addEqError(EquivalencyRuntimeException e, int namespaceId) throws SQLRuntimeException {
        List l = Arrays.asList(e.getListOfEqErrors());
        int size = l.size();
        String sql = JavaCIPUnknownScope.getClassifyDAO().getStatement(JavaCIPUnknownScope.TABLE_KEY, "ADD_CLASSIFY_EQ_ERROR");
        PreparedStatement ps = null;
        JavaCIPUnknownScope.conn.setAutoCommit(false);
        try {
            JavaCIPUnknownScope.deleteCycleError(namespaceId);
            JavaCIPUnknownScope.deleteEqError(namespaceId);
            long conceptGID1 = -1;
            long conceptGID2 = -1;
            ps = JavaCIPUnknownScope.conn.prepareStatement(sql);
            for (int i = 0; i < l.size(); i++) {
                EqError error = (EqError) l.get(i);
                ConceptRef ref1 = error.getConcept1();
                ConceptRef ref2 = error.getConcept2();
                conceptGID1 = JavaCIPUnknownScope.getConceptGID(ref1, namespaceId);
                conceptGID2 = JavaCIPUnknownScope.getConceptGID(ref2, namespaceId);
                ps.setLong(1, conceptGID1);
                ps.setLong(2, conceptGID2);
                ps.setInt(3, namespaceId);
                int result = ps.executeUpdate();
                if (result == 0) {
                    throw new SQLRuntimeException("unable to add eq error: " + sql);
                }
            }
            JavaCIPUnknownScope.conn.commit();
            return "EquivalencyRuntimeException: Concept: " + conceptGID1 + " namespaceId: " + namespaceId + " conceptGID2: " + conceptGID2 + ((size > 1) ? "...... more" : "");
        } catch (SQLRuntimeException sqle) {
            JavaCIPUnknownScope.conn.rollback();
            throw sqle;
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.conn.rollback();
            throw JavaCIPUnknownScope.toSQLRuntimeException(ex, "cannot add eq errors");
        } finally {
            JavaCIPUnknownScope.conn.setAutoCommit(true);
            if (ps != null) {
                ps.close();
            }
        }
    }
}
