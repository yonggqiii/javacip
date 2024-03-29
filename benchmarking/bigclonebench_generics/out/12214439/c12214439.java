class c12214439 {

    public boolean saveNote(NoteData n) {
        String query;
        try {
            JavaCIPUnknownScope.conn.setAutoCommit(false);
            Statement stmt = null;
            ResultSet rset = null;
            stmt = JavaCIPUnknownScope.conn.createStatement();
            query = "select * from notes where noteid = " + n.getID();
            rset = stmt.executeQuery(query);
            if (rset.next()) {
                query = "UPDATE notes SET title = '" + JavaCIPUnknownScope.escapeCharacters(n.getTitle()) + "', keywords = '" + JavaCIPUnknownScope.escapeCharacters(n.getKeywords()) + "' WHERE noteid = " + n.getID();
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JavaCIPUnknownScope.conn.rollback();
                    JavaCIPUnknownScope.conn.setAutoCommit(true);
                    return false;
                }
                LinkedList<FieldData> fields = n.getFields();
                ListIterator<FieldData> iter = fields.listIterator(0);
                FieldData f = null;
                PreparedStatement pstmt = JavaCIPUnknownScope.conn.prepareStatement("UPDATE fielddata SET data = ? WHERE noteid = ? AND fieldid = ?");
                try {
                    while (iter.hasNext()) {
                        f = iter.next();
                        if (f instanceof FieldDataImage) {
                            System.out.println("field is an image.");
                            pstmt.setBytes(1, ((FieldDataImage) f).getDataBytes());
                        } else {
                            System.out.println("field is not an image");
                            pstmt.setString(1, f.getData());
                        }
                        pstmt.setInt(2, n.getID());
                        pstmt.setInt(3, f.getID());
                        pstmt.execute();
                    }
                } catch (SQLException e) {
                    JavaCIPUnknownScope.conn.rollback();
                    JavaCIPUnknownScope.conn.setAutoCommit(true);
                    e.printStackTrace();
                    return false;
                }
                query = "DELETE FROM links WHERE (note1id = " + n.getID() + " OR note2id = " + n.getID() + ")";
                try {
                    stmt.execute(query);
                } catch (SQLException e) {
                    JavaCIPUnknownScope.conn.rollback();
                    JavaCIPUnknownScope.conn.setAutoCommit(true);
                    e.printStackTrace();
                    return false;
                }
                Vector<Link> associations = n.getAssociations();
                ListIterator<Link> itr = associations.listIterator();
                Link association = null;
                pstmt = JavaCIPUnknownScope.conn.prepareStatement("INSERT INTO links (note1id, note2id) VALUES (?, ?)");
                try {
                    while (itr.hasNext()) {
                        association = itr.next();
                        pstmt.setInt(1, n.getID());
                        pstmt.setInt(2, association.getID());
                        pstmt.execute();
                    }
                } catch (SQLException e) {
                    JavaCIPUnknownScope.conn.rollback();
                    JavaCIPUnknownScope.conn.setAutoCommit(true);
                    e.printStackTrace();
                    return false;
                }
            } else {
                query = "INSERT INTO notes (templateid, title, keywords) VALUES (" + n.getTemplate().getID() + ", '" + JavaCIPUnknownScope.escapeCharacters(n.getTitle()) + "', '" + JavaCIPUnknownScope.escapeCharacters(n.getKeywords()) + "')";
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JavaCIPUnknownScope.conn.rollback();
                    JavaCIPUnknownScope.conn.setAutoCommit(true);
                    return false;
                }
                LinkedList<FieldData> fields = n.getFields();
                ListIterator<FieldData> iter = fields.listIterator(0);
                FieldData f = null;
                n.setID(Integer.parseInt(JavaCIPUnknownScope.executeMySQLGet("SELECT LAST_INSERT_ID()")));
                PreparedStatement pstmt;
                try {
                    pstmt = JavaCIPUnknownScope.conn.prepareStatement("INSERT INTO fielddata (noteid, fieldid, data) VALUES (?,?,?)");
                    while (iter.hasNext()) {
                        f = iter.next();
                        if (f instanceof FieldDataImage) {
                            System.out.println("field is an image.");
                            pstmt.setBytes(3, ((FieldDataImage) f).getDataBytes());
                        } else {
                            System.out.println("field is not an image");
                            pstmt.setString(3, f.getData());
                        }
                        pstmt.setInt(1, n.getID());
                        pstmt.setInt(2, f.getID());
                        pstmt.execute();
                    }
                } catch (SQLException e) {
                    JavaCIPUnknownScope.conn.rollback();
                    JavaCIPUnknownScope.conn.setAutoCommit(true);
                    e.printStackTrace();
                    return false;
                }
                Vector<Link> assoc = n.getAssociations();
                Iterator<Link> itr = assoc.listIterator();
                Link l = null;
                pstmt = JavaCIPUnknownScope.conn.prepareStatement("INSERT INTO links (note1id, note2id) VALUES (?,?)");
                try {
                    while (itr.hasNext()) {
                        l = itr.next();
                        pstmt.setInt(1, n.getID());
                        pstmt.setInt(2, l.getID());
                        pstmt.execute();
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
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
