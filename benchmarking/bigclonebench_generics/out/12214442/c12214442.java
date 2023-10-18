class c12214442 {

    public boolean saveTemplate(Template t) {
        try {
            JavaCIPUnknownScope.conn.setAutoCommit(false);
            Statement stmt = JavaCIPUnknownScope.conn.createStatement();
            String query;
            ResultSet rset;
            if (Integer.parseInt(JavaCIPUnknownScope.executeMySQLGet("SELECT COUNT(*) FROM templates WHERE name='" + JavaCIPUnknownScope.escapeCharacters(t.getName()) + "'")) != 0)
                return false;
            query = "select * from templates where templateid = " + t.getID();
            rset = stmt.executeQuery(query);
            if (rset.next()) {
                System.err.println("Updating already saved template is not supported!!!!!!");
                return false;
            } else {
                query = "INSERT INTO templates (name, parentid) VALUES ('" + JavaCIPUnknownScope.escapeCharacters(t.getName()) + "', " + t.getParentID() + ")";
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                    JavaCIPUnknownScope.conn.rollback();
                    JavaCIPUnknownScope.conn.setAutoCommit(true);
                    e.printStackTrace();
                    return false;
                }
                int templateid = Integer.parseInt(JavaCIPUnknownScope.executeMySQLGet("SELECT LAST_INSERT_ID()"));
                t.setID(templateid);
                LinkedList<Field> fields = t.getFields();
                ListIterator<Field> iter = fields.listIterator();
                Field f = null;
                PreparedStatement pstmt = JavaCIPUnknownScope.conn.prepareStatement("INSERT INTO templatefields(fieldtype, name, templateid, defaultvalue)" + "VALUES (?,?,?,?)");
                try {
                    while (iter.hasNext()) {
                        f = iter.next();
                        if (f.getType() == Field.IMAGE) {
                            System.out.println("field is an image.");
                            byte[] data = ((FieldDataImage) f.getDefault()).getDataBytes();
                            pstmt.setBytes(4, data);
                        } else {
                            System.out.println("field is not an image");
                            String deflt = (f.getDefault()).getData();
                            pstmt.setString(4, deflt);
                        }
                        pstmt.setInt(1, f.getType());
                        pstmt.setString(2, f.getName());
                        pstmt.setInt(3, t.getID());
                        pstmt.execute();
                        f.setID(Integer.parseInt(JavaCIPUnknownScope.executeMySQLGet("SELECT LAST_INSERT_ID()")));
                    }
                } catch (SQLException e) {
                    JavaCIPUnknownScope.conn.rollback();
                    JavaCIPUnknownScope.conn.setAutoCommit(true);
                    e.printStackTrace();
                    return false;
                }
            }
            JavaCIPUnknownScope.conn.commit();
            JavaCIPUnknownScope.conn.setAutoCommit(true);
        } catch (SQLException ex) {
            System.err.println("Error saving the Template");
            return false;
        }
        return true;
    }
}
