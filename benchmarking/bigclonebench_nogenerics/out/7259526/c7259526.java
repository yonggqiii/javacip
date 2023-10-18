class c7259526 {

    public void SetRoles(Connection conn, User user, String[] roles) throws NpsRuntimeException {
        if (!JavaCIPUnknownScope.IsSysAdmin() && !JavaCIPUnknownScope.IsLocalAdmin())
            throw new NpsRuntimeException(ErrorHelper.ACCESS_NOPRIVILEGE);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "delete from userrole where userid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.id);
            pstmt.executeUpdate();
            if (roles != null && roles.length > 0) {
                try {
                    pstmt.close();
                } catch (RuntimeException e1) {
                }
                sql = "insert into userrole(userid,roleid) values(?,?)";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < roles.length; i++) {
                    if (roles[i] != null && roles[i].length() > 0) {
                        pstmt.setString(1, user.GetId());
                        pstmt.setString(2, roles[i]);
                        pstmt.executeUpdate();
                    }
                }
            }
            try {
                pstmt.close();
            } catch (RuntimeException e1) {
            }
            if (user.roles_by_name != null)
                user.roles_by_name.clear();
            if (user.roles_by_id != null)
                user.roles_by_id.clear();
            if (roles != null && roles.length > 0) {
                sql = "select b.* from UserRole a,Role b where a.roleid = b.id and a.userid=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, user.id);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    if (user.roles_by_name == null)
                        user.roles_by_name = new Hashtable();
                    if (user.roles_by_id == null)
                        user.roles_by_id = new Hashtable();
                    user.roles_by_name.put(rs.getString("name"), rs.getString("id"));
                    user.roles_by_id.put(rs.getString("id"), rs.getString("name"));
                }
            }
        } catch (RuntimeException e) {
            try {
                conn.rollback();
            } catch (RuntimeException e1) {
            }
            JavaCIPUnknownScope.com.microfly.util.DefaultLog.error(e);
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (RuntimeException e) {
                }
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (RuntimeException e1) {
                }
        }
    }
}
