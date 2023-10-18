class c2525684 {

    private void add(Hashtable applicantInfo) throws RuntimeException {
        String mode = "".equals(JavaCIPUnknownScope.getParam("applicant_id_gen").trim()) ? "update" : "insert";
        String applicant_id = JavaCIPUnknownScope.getParam("applicant_id");
        String password = JavaCIPUnknownScope.getParam("password");
        if ("".equals(applicant_id))
            applicant_id = JavaCIPUnknownScope.getParam("applicant_id_gen");
        if ("".equals(JavaCIPUnknownScope.getParam("applicant_name")))
            throw new RuntimeException("Can not have empty fields!");
        applicantInfo.put("id", applicant_id);
        applicantInfo.put("password", password);
        applicantInfo.put("name", JavaCIPUnknownScope.getParam("applicant_name"));
        applicantInfo.put("address1", JavaCIPUnknownScope.getParam("address1"));
        applicantInfo.put("address2", JavaCIPUnknownScope.getParam("address2"));
        applicantInfo.put("address3", JavaCIPUnknownScope.getParam("address3"));
        applicantInfo.put("city", JavaCIPUnknownScope.getParam("city"));
        applicantInfo.put("state", JavaCIPUnknownScope.getParam("state"));
        applicantInfo.put("poscode", JavaCIPUnknownScope.getParam("poscode"));
        applicantInfo.put("country_code", JavaCIPUnknownScope.getParam("country_list"));
        applicantInfo.put("email", JavaCIPUnknownScope.getParam("email"));
        applicantInfo.put("phone", JavaCIPUnknownScope.getParam("phone"));
        String birth_year = JavaCIPUnknownScope.getParam("birth_year");
        String birth_month = JavaCIPUnknownScope.getParam("birth_month");
        String birth_day = JavaCIPUnknownScope.getParam("birth_day");
        applicantInfo.put("birth_year", birth_year);
        applicantInfo.put("birth_month", birth_month);
        applicantInfo.put("birth_day", birth_day);
        applicantInfo.put("gender", JavaCIPUnknownScope.getParam("gender"));
        String birth_date = birth_year + "-" + JavaCIPUnknownScope.fmt(birth_month) + "-" + JavaCIPUnknownScope.fmt(birth_day);
        applicantInfo.put("birth_date", birth_date);
        Db db = null;
        String sql = "";
        Connection conn = null;
        try {
            db = new Db();
            conn = db.getConnection();
            conn.setAutoCommit(false);
            Statement stmt = db.getStatement();
            SQLRenderer r = new SQLRenderer();
            boolean found = false;
            {
                r.add("applicant_id");
                r.add("applicant_id", (String) applicantInfo.get("id"));
                sql = r.getSQLSelect("adm_applicant");
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next())
                    found = true;
                else
                    found = false;
            }
            if (found && !"update".equals(mode))
                throw new RuntimeException("Applicant Id was invalid!");
            {
                r.clear();
                r.add("password", (String) applicantInfo.get("password"));
                r.add("applicant_name", (String) applicantInfo.get("name"));
                r.add("address1", (String) applicantInfo.get("address1"));
                r.add("address2", (String) applicantInfo.get("address2"));
                r.add("address3", (String) applicantInfo.get("address3"));
                r.add("city", (String) applicantInfo.get("city"));
                r.add("state", (String) applicantInfo.get("state"));
                r.add("poscode", (String) applicantInfo.get("poscode"));
                r.add("country_code", (String) applicantInfo.get("country_code"));
                r.add("phone", (String) applicantInfo.get("phone"));
                r.add("birth_date", (String) applicantInfo.get("birth_date"));
                r.add("gender", (String) applicantInfo.get("gender"));
            }
            if (!found) {
                r.add("applicant_id", (String) applicantInfo.get("id"));
                sql = r.getSQLInsert("adm_applicant");
                stmt.executeUpdate(sql);
            } else {
                r.update("applicant_id", (String) applicantInfo.get("id"));
                sql = r.getSQLUpdate("adm_applicant");
                stmt.executeUpdate(sql);
            }
            conn.commit();
        } catch (DbRuntimeException dbex) {
            throw dbex;
        } catch (SQLRuntimeException sqlex) {
            try {
                conn.rollback();
            } catch (SQLRuntimeException rollex) {
            }
            throw sqlex;
        } finally {
            if (db != null)
                db.close();
        }
    }
}
