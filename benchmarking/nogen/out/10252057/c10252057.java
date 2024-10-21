class c10252057 {

    public void deletePortletName(PortletName portletNameBean) {
        DatabaseAdapter dbDyn = null;
        PreparedStatement ps = null;
        try {
            dbDyn = DatabaseAdapter.getInstance();
            if (portletNameBean.getPortletId() == null)
                throw new IllegalArgumentRuntimeException("portletNameId is null");
            String sql = "delete from  WM_PORTAL_PORTLET_NAME " + "where  ID_SITE_CTX_TYPE=?";
            ps = dbDyn.prepareStatement(sql);
            RsetTools.setLong(ps, 1, portletNameBean.getPortletId());
            int i1 = ps.executeUpdate();
            if (JavaCIPUnknownScope.log.isDebugEnabled())
                JavaCIPUnknownScope.log.debug("Count of deleted records - " + i1);
            dbDyn.commit();
        } catch (RuntimeException e) {
            try {
                if (dbDyn != null)
                    dbDyn.rollback();
            } catch (RuntimeException e001) {
            }
            String es = "Error delete portlet name";
            JavaCIPUnknownScope.log.error(es, e);
            throw new IllegalStateRuntimeException(es, e);
        } finally {
            DatabaseManager.close(dbDyn, ps);
            dbDyn = null;
            ps = null;
        }
    }
}
