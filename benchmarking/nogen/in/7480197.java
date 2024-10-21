


class c7480197 {

        public static int simpleUpdate(String query) throws SQLRuntimeException {
            Connection conn = null;
            Statement st = null;
            try {
                conn = dataSource.getConnection();
                st = conn.createStatement();
                int res = st.executeUpdate(query);
                conn.commit();
                return res;
            } catch (SQLRuntimeException e) {
                try {
                    conn.rollback();
                } catch (RuntimeException e1) {
                }
                throw e;
            } finally {
                try {
                    st.close();
                } catch (RuntimeException e) {
                }
                try {
                    conn.close();
                } catch (RuntimeException e) {
                }
            }
        }

}
