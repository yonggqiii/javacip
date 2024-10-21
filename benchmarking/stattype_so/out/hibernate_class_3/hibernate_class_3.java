public class hibernate_class_3 extends Dialect {

    public hibernate_class_3() {
        super();
        JavaCIPUnknownScope.registerColumnType(Types.BIT, "integer");
        JavaCIPUnknownScope.registerColumnType(Types.TINYINT, "tinyint");
        JavaCIPUnknownScope.registerColumnType(Types.SMALLINT, "smallint");
        JavaCIPUnknownScope.registerColumnType(Types.INTEGER, "integer");
        JavaCIPUnknownScope.registerColumnType(Types.BIGINT, "bigint");
        JavaCIPUnknownScope.registerColumnType(Types.FLOAT, "float");
        JavaCIPUnknownScope.registerColumnType(Types.REAL, "real");
        JavaCIPUnknownScope.registerColumnType(Types.DOUBLE, "double");
        JavaCIPUnknownScope.registerColumnType(Types.NUMERIC, "numeric");
        JavaCIPUnknownScope.registerColumnType(Types.DECIMAL, "decimal");
        JavaCIPUnknownScope.registerColumnType(Types.CHAR, "char");
        JavaCIPUnknownScope.registerColumnType(Types.VARCHAR, "varchar");
        JavaCIPUnknownScope.registerColumnType(Types.LONGVARCHAR, "longvarchar");
        JavaCIPUnknownScope.registerColumnType(Types.DATE, "date");
        JavaCIPUnknownScope.registerColumnType(Types.TIME, "time");
        JavaCIPUnknownScope.registerColumnType(Types.TIMESTAMP, "timestamp");
        JavaCIPUnknownScope.registerColumnType(Types.BINARY, "blob");
        JavaCIPUnknownScope.registerColumnType(Types.VARBINARY, "blob");
        JavaCIPUnknownScope.registerColumnType(Types.LONGVARBINARY, "blob");
        // registerColumnType(Types.NULL, "null");
        JavaCIPUnknownScope.registerColumnType(Types.BLOB, "blob");
        JavaCIPUnknownScope.registerColumnType(Types.CLOB, "clob");
        JavaCIPUnknownScope.registerColumnType(Types.BOOLEAN, "integer");
        JavaCIPUnknownScope.registerFunction("concat", new VarArgsSQLFunction(Hibernate.STRING, "", "||", ""));
        JavaCIPUnknownScope.registerFunction("mod", new SQLFunctionTemplate(Hibernate.INTEGER, "?1 % ?2"));
        JavaCIPUnknownScope.registerFunction("substr", new StandardSQLFunction("substr", Hibernate.STRING));
        JavaCIPUnknownScope.registerFunction("substring", new StandardSQLFunction("substr", Hibernate.STRING));
    }

    public boolean supportsIdentityColumns() {
        return true;
    }

    /*
		 public boolean supportsInsertSelectIdentity() {
		 return true; // As specify in NHibernate dialect
		 }
		 */
    public boolean hasDataTypeInIdentityColumn() {
        // As specify in NHibernate dialect
        return false;
    }

    /*
		 public String appendIdentitySelectToInsert(String insertString) {
		 return new StringBuffer(insertString.length()+30). // As specify in NHibernate dialect
		 append(insertString).
		 append("; ").append(getIdentitySelectString()).
		 toString();
		 }
		 */
    public String getIdentityColumnString() {
        // return "integer primary key autoincrement";
        return "integer";
    }

    public String getIdentitySelectString() {
        return "select last_insert_rowid()";
    }

    public boolean supportsLimit() {
        return true;
    }

    public String getLimitString(String query, boolean hasOffset) {
        return new StringBuffer(query.length() + 20).append(query).append(hasOffset ? " limit ? offset ?" : " limit ?").toString();
    }

    public boolean supportsTemporaryTables() {
        return true;
    }

    public String getCreateTemporaryTableString() {
        return "create temporary table if not exists";
    }

    public boolean dropTemporaryTableAfterUse() {
        return false;
    }

    public boolean supportsCurrentTimestampSelection() {
        return true;
    }

    public boolean isCurrentTimestampSelectStringCallable() {
        return false;
    }

    public String getCurrentTimestampSelectString() {
        return "select current_timestamp";
    }

    public boolean supportsUnionAll() {
        return true;
    }

    public boolean hasAlterTable() {
        // As specify in NHibernate dialect
        return false;
    }

    public boolean dropConstraints() {
        return false;
    }

    public String getAddColumnString() {
        return "add column";
    }

    public String getForUpdateString() {
        return "";
    }

    public boolean supportsOuterJoinForUpdate() {
        return false;
    }

    public String getDropForeignKeyString() {
        throw new UnsupportedOperationException("No drop foreign key syntax supported by hibernate_class_3");
    }

    public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable, String[] primaryKey, boolean referencesPrimaryKey) {
        throw new UnsupportedOperationException("No add foreign key syntax supported by hibernate_class_3");
    }

    public String getAddPrimaryKeyConstraintString(String constraintName) {
        throw new UnsupportedOperationException("No add primary key syntax supported by hibernate_class_3");
    }

    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    public boolean supportsCascadeDelete() {
        return false;
    }
}
