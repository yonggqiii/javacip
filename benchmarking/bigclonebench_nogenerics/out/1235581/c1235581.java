class c1235581 {

    public Object process(Atom oAtm) throws IORuntimeException {
        File oFile;
        FileReader oFileRead;
        String sPathHTML;
        char[] cBuffer;
        Object oReplaced;
        final String sSep = System.getProperty("file.separator");
        if (DebugFile.trace) {
            DebugFile.writeln("Begin FileDumper.process([Job:" + JavaCIPUnknownScope.getStringNull(JavaCIPUnknownScope.DB.gu_job, "") + ", Atom:" + String.valueOf(oAtm.getInt(JavaCIPUnknownScope.DB.pg_atom)) + "])");
            DebugFile.incIdent();
        }
        if (JavaCIPUnknownScope.bHasReplacements) {
            sPathHTML = JavaCIPUnknownScope.getProperty("workareasput");
            if (!sPathHTML.endsWith(sSep))
                sPathHTML += sSep;
            sPathHTML += JavaCIPUnknownScope.getParameter("gu_workarea") + sSep + "apps" + sSep + "Mailwire" + sSep + "html" + sSep + JavaCIPUnknownScope.getParameter("gu_pageset") + sSep;
            sPathHTML += JavaCIPUnknownScope.getParameter("nm_pageset").replace(' ', '_') + ".html";
            if (DebugFile.trace)
                DebugFile.writeln("PathHTML = " + sPathHTML);
            oReplaced = JavaCIPUnknownScope.oReplacer.replace(sPathHTML, oAtm.getItemMap());
            JavaCIPUnknownScope.bHasReplacements = (JavaCIPUnknownScope.oReplacer.lastReplacements() > 0);
        } else {
            oReplaced = null;
            if (null != JavaCIPUnknownScope.oFileStr)
                oReplaced = JavaCIPUnknownScope.oFileStr.get();
            if (null == oReplaced) {
                sPathHTML = JavaCIPUnknownScope.getProperty("workareasput");
                if (!sPathHTML.endsWith(sSep))
                    sPathHTML += sSep;
                sPathHTML += JavaCIPUnknownScope.getParameter("gu_workarea") + sSep + "apps" + sSep + "Mailwire" + sSep + "html" + sSep + JavaCIPUnknownScope.getParameter("gu_pageset") + sSep + JavaCIPUnknownScope.getParameter("nm_pageset").replace(' ', '_') + ".html";
                if (DebugFile.trace)
                    DebugFile.writeln("PathHTML = " + sPathHTML);
                oFile = new File(sPathHTML);
                cBuffer = new char[new Long(oFile.length()).intValue()];
                oFileRead = new FileReader(oFile);
                oFileRead.read(cBuffer);
                oFileRead.close();
                if (DebugFile.trace)
                    DebugFile.writeln(String.valueOf(cBuffer.length) + " characters readed");
                oReplaced = new String(cBuffer);
                JavaCIPUnknownScope.oFileStr = new SoftReference(oReplaced);
            }
        }
        String sPathJobDir = JavaCIPUnknownScope.getProperty("storage");
        if (!sPathJobDir.endsWith(sSep))
            sPathJobDir += sSep;
        sPathJobDir += "jobs" + sSep + JavaCIPUnknownScope.getParameter("gu_workarea") + sSep + JavaCIPUnknownScope.getString(JavaCIPUnknownScope.DB.gu_job) + sSep;
        FileWriter oFileWrite = new FileWriter(sPathJobDir + JavaCIPUnknownScope.getString(JavaCIPUnknownScope.DB.gu_job) + "_" + String.valueOf(oAtm.getInt(JavaCIPUnknownScope.DB.pg_atom)) + ".html", true);
        oFileWrite.write((String) oReplaced);
        oFileWrite.close();
        JavaCIPUnknownScope.iPendingAtoms--;
        if (DebugFile.trace) {
            DebugFile.writeln("End FileDumper.process([Job:" + JavaCIPUnknownScope.getStringNull(JavaCIPUnknownScope.DB.gu_job, "") + ", Atom:" + String.valueOf(oAtm.getInt(JavaCIPUnknownScope.DB.pg_atom)) + "])");
            DebugFile.decIdent();
        }
        return oReplaced;
    }
}
