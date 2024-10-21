class c4411141 {

    private void addDocToDB(String action, DataSource database) {
        String typeOfDoc = JavaCIPUnknownScope.findTypeOfDoc(action).trim().toLowerCase();
        Connection con = null;
        try {
            con = database.getConnection();
            con.setAutoCommit(false);
            JavaCIPUnknownScope.checkDupDoc(typeOfDoc, con);
            String add = "insert into " + typeOfDoc + " values( ?, ?, ?, ?, ?, ?, ? )";
            PreparedStatement prepStatement = con.prepareStatement(add);
            prepStatement.setString(1, JavaCIPUnknownScope.selectedCourse.getCourseId());
            prepStatement.setString(2, JavaCIPUnknownScope.selectedCourse.getAdmin());
            prepStatement.setTimestamp(3, JavaCIPUnknownScope.getTimeStamp());
            prepStatement.setString(4, JavaCIPUnknownScope.getLink());
            prepStatement.setString(5, JavaCIPUnknownScope.homePage.getUser());
            prepStatement.setString(6, JavaCIPUnknownScope.getText());
            prepStatement.setString(7, JavaCIPUnknownScope.getTitle());
            prepStatement.executeUpdate();
            prepStatement.close();
            con.commit();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.sqlError = true;
            e.printStackTrace();
            if (con != null)
                try {
                    con.rollback();
                } catch (RuntimeException logOrIgnore) {
                }
            try {
                throw e;
            } catch (RuntimeException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (RuntimeException logOrIgnore) {
                }
        }
    }
}
