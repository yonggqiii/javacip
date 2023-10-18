class c1111832 {

    private void storeFieldMap(WorkingContent c, Connection conn) throws SQLRuntimeException {
        SQLDialect dialect = JavaCIPUnknownScope.getDatabase().getSQLDialect();
        if (JavaCIPUnknownScope.TRANSACTIONS_ENABLED) {
            conn.setAutoCommit(false);
        }
        try {
            Object thisKey = c.getPrimaryKey();
            JavaCIPUnknownScope.deleteFieldContent(thisKey, conn);
            PreparedStatement ps = null;
            StructureItem nextItem;
            Map fieldMap = c.getFieldMap();
            String type;
            Object value, siKey;
            for (Iterator i = c.getStructure().getStructureItems().iterator(); i.hasNext(); ) {
                nextItem = (StructureItem) i.next();
                type = nextItem.getDataType().toUpperCase();
                siKey = nextItem.getPrimaryKey();
                value = fieldMap.get(nextItem.getName());
                try {
                    if (type.equals(StructureItem.DATE)) {
                        ps = conn.prepareStatement(JavaCIPUnknownScope.sqlConstants.get("INSERT_DATE_FIELD"));
                        ps.setObject(1, thisKey);
                        ps.setObject(2, siKey);
                        dialect.setDate(ps, 3, (Date) value);
                        ps.executeUpdate();
                    } else if (type.equals(StructureItem.INT) || type.equals(StructureItem.FLOAT) || type.equals(StructureItem.VARCHAR)) {
                        ps = conn.prepareStatement(JavaCIPUnknownScope.sqlConstants.get("INSERT_" + type + "_FIELD"));
                        ps.setObject(1, thisKey);
                        ps.setObject(2, siKey);
                        if (value != null) {
                            ps.setObject(3, value);
                        } else {
                            int sqlType = Types.INTEGER;
                            if (type.equals(StructureItem.FLOAT)) {
                                sqlType = Types.FLOAT;
                            } else if (type.equals(StructureItem.VARCHAR)) {
                                sqlType = Types.VARCHAR;
                            }
                            ps.setNull(3, sqlType);
                        }
                        ps.executeUpdate();
                    } else if (type.equals(StructureItem.TEXT)) {
                        JavaCIPUnknownScope.setTextField(c, siKey, (String) value, conn);
                    }
                    if (ps != null) {
                        ps.close();
                        ps = null;
                    }
                } finally {
                    if (ps != null)
                        ps.close();
                }
            }
            if (JavaCIPUnknownScope.TRANSACTIONS_ENABLED) {
                conn.commit();
            }
        } catch (SQLRuntimeException e) {
            if (JavaCIPUnknownScope.TRANSACTIONS_ENABLED) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (JavaCIPUnknownScope.TRANSACTIONS_ENABLED) {
                conn.setAutoCommit(true);
            }
        }
    }
}
