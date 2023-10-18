


class c18432299 {

    private String addEqError(EquivalencyRuntimeException e, int namespaceId) throws SQLRuntimeException {
        List l = Arrays.asList(e.getListOfEqErrors());
        int size = l.size();
        String sql = getClassifyDAO().getStatement(TABLE_KEY, "ADD_CLASSIFY_EQ_ERROR");
        PreparedStatement ps = null;
        conn.setAutoCommit(false);
        try {
            deleteCycleError(namespaceId);
            deleteEqError(namespaceId);
            long conceptGID1 = -1;
            long conceptGID2 = -1;
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < l.size(); i++) {
                EqError error = (EqError) l.get(i);
                ConceptRef ref1 = error.getConcept1();
                ConceptRef ref2 = error.getConcept2();
                conceptGID1 = getConceptGID(ref1, namespaceId);
                conceptGID2 = getConceptGID(ref2, namespaceId);
                ps.setLong(1, conceptGID1);
                ps.setLong(2, conceptGID2);
                ps.setInt(3, namespaceId);
                int result = ps.executeUpdate();
                if (result == 0) {
                    throw new SQLRuntimeException("unable to add eq error: " + sql);
                }
            }
            conn.commit();
            return "EquivalencyRuntimeException: Concept: " + conceptGID1 + " namespaceId: " + namespaceId + " conceptGID2: " + conceptGID2 + ((size > 1) ? "...... more" : "");
        } catch (SQLRuntimeException sqle) {
            conn.rollback();
            throw sqle;
        } catch (RuntimeException ex) {
            conn.rollback();
            throw toSQLRuntimeException(ex, "cannot add eq errors");
        } finally {
            conn.setAutoCommit(true);
            if (ps != null) {
                ps.close();
            }
        }
    }

}
