class c19295736 {

    public void setTableBraille(String tableBraille, boolean sys) {
        JavaCIPUnknownScope.fiConf.setProperty(OptNames.fi_braille_table, tableBraille);
        JavaCIPUnknownScope.fiConf.setProperty(OptNames.fi_is_sys_braille_table, Boolean.toString(sys));
        FileChannel in = null;
        FileChannel out = null;
        try {
            String fichTable;
            if (!(tableBraille.endsWith(".ent"))) {
                tableBraille = tableBraille + ".ent";
            }
            if (sys) {
                fichTable = ConfigNat.getInstallFolder() + "xsl/tablesBraille/" + tableBraille;
            } else {
                fichTable = ConfigNat.getUserBrailleTableFolder() + tableBraille;
            }
            in = new FileInputStream(fichTable).getChannel();
            out = new FileOutputStream(JavaCIPUnknownScope.getUserBrailleTableFolder() + "Brltab.ent").getChannel();
            in.transferTo(0, in.size(), out);
            in.close();
            out.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        try {
            String fichTable;
            if (sys) {
                fichTable = ConfigNat.getInstallFolder() + "/xsl/tablesEmbosseuse/" + tableBraille;
            } else {
                fichTable = ConfigNat.getUserEmbossTableFolder() + "/" + tableBraille;
            }
            in = new FileInputStream(fichTable).getChannel();
            out = new FileOutputStream(ConfigNat.getUserTempFolder() + "Table_pour_chaines.ent").getChannel();
            in.transferTo(0, in.size(), out);
            in.close();
            out.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
