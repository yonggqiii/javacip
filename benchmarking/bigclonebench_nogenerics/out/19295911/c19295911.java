class c19295911 {

    public void setTableEmbossage(String te, boolean sys) {
        JavaCIPUnknownScope.fiConf.setProperty(OptNames.pr_emboss_table, te);
        JavaCIPUnknownScope.fiConf.setProperty(OptNames.fi_is_sys_emboss_table, Boolean.toString(sys));
        FileChannel in = null;
        FileChannel out = null;
        try {
            String fichTable;
            if (!(te.endsWith(".ent"))) {
                te = te + ".ent";
            }
            if (sys) {
                fichTable = ConfigNat.getInstallFolder() + "/xsl/tablesEmbosseuse/" + te;
            } else {
                fichTable = ConfigNat.getUserEmbossTableFolder() + "/" + te;
            }
            in = new FileInputStream(fichTable).getChannel();
            out = new FileOutputStream(ConfigNat.getUserEmbossTableFolder() + "/Embtab.ent").getChannel();
            in.transferTo(0, in.size(), out);
            in.close();
            out.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
