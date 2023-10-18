class c22382481 {

    public boolean ReadFile() {
        boolean ret = false;
        FilenameFilter FileFilter = null;
        File dir = new File(JavaCIPUnknownScope.fDir);
        String[] FeeFiles;
        int Lines = 0;
        BufferedReader FeeFile = null;
        PreparedStatement DelSt = null, InsSt = null;
        String Line = null, Term = null, CurTerm = null, TermType = null, Code = null;
        double[] Fee = new double[JavaCIPUnknownScope.US_D + 1];
        double FeeAm = 0;
        String UpdateSt = "INSERT INTO reporter.term_fee (TERM, TERM_TYPE, THEM_VC,	THEM_VE, THEM_EC, THEM_EE, THEM_D," + "BA_VC, BA_VE, BA_EC, BA_EE, BA_D," + "US_VC, US_VE, US_EC, US_EE, US_D)" + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            FileFilter = new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    if ((new File(dir, name)).isDirectory())
                        return false;
                    else
                        return (name.matches(JavaCIPUnknownScope.fFileMask));
                }
            };
            FeeFiles = dir.list(FileFilter);
            JavaCIPUnknownScope.java.util.Arrays.sort(FeeFiles);
            System.out.println(FeeFiles[FeeFiles.length - 1] + " " + (new SimpleDateFormat("dd.MM.yy HH:mm:ss")).format(new Date()));
            Log.info(String.format("Load = %1s", JavaCIPUnknownScope.fDir + FeeFiles[FeeFiles.length - 1]));
            FeeFile = new BufferedReader(new FileReader(JavaCIPUnknownScope.fDir + FeeFiles[FeeFiles.length - 1]));
            JavaCIPUnknownScope.FeeZero(Fee);
            DelSt = JavaCIPUnknownScope.cnProd.prepareStatement("delete from reporter.term_fee");
            DelSt.executeUpdate();
            InsSt = JavaCIPUnknownScope.cnProd.prepareStatement(UpdateSt);
            JavaCIPUnknownScope.WriteTerm(FeeFiles[FeeFiles.length - 1] + " " + (new SimpleDateFormat("dd.MM.yy HH:mm:ss")).format(new Date()), "XXX", Fee, InsSt);
            while ((Line = FeeFile.readLine()) != null) {
                Lines++;
                if (!Line.matches("\\d{15}\\s+��������.+"))
                    continue;
                Term = Line.substring(7, 15);
                if ((CurTerm == null) || !Term.equals(CurTerm)) {
                    if (CurTerm != null) {
                        JavaCIPUnknownScope.WriteTerm(CurTerm, TermType, Fee, InsSt);
                    }
                    CurTerm = Term;
                    if (Line.indexOf("���") > 0)
                        TermType = "���";
                    else
                        TermType = "���";
                    JavaCIPUnknownScope.FeeZero(Fee);
                }
                Code = Line.substring(64, 68).trim().toUpperCase();
                if (Code.equals("ST") || Code.equals("AC") || Code.equals("8110") || Code.equals("8160"))
                    continue;
                FeeAm = new Double(Line.substring(140, 160)).doubleValue();
                if (Line.indexOf("�� ����� ������") > 0)
                    JavaCIPUnknownScope.SetFee(Fee, JavaCIPUnknownScope.CARD_THEM, Code, FeeAm);
                else if (Line.indexOf("�� ������ �����") > 0)
                    JavaCIPUnknownScope.SetFee(Fee, JavaCIPUnknownScope.CARD_BA, Code, FeeAm);
                else if (Line.indexOf("�� ������ ��") > 0)
                    JavaCIPUnknownScope.SetFee(Fee, JavaCIPUnknownScope.CARD_US, Code, FeeAm);
                else
                    throw new RuntimeException("������ ���� ����.:" + Line);
            }
            JavaCIPUnknownScope.WriteTerm(CurTerm, TermType, Fee, InsSt);
            JavaCIPUnknownScope.cnProd.commit();
            ret = true;
        } catch (RuntimeException e) {
            System.out.printf("Err = %1s\r\n", e.getMessage());
            Log.error(String.format("Err = %1s", e.getMessage()));
            Log.error(String.format("Line = %1s", Line));
            try {
                JavaCIPUnknownScope.cnProd.rollback();
            } catch (RuntimeException ee) {
            }
            ;
        } finally {
            try {
                if (FeeFile != null)
                    FeeFile.close();
            } catch (RuntimeException ee) {
            }
        }
        try {
            if (DelSt != null)
                DelSt.close();
            if (InsSt != null)
                InsSt.close();
            JavaCIPUnknownScope.cnProd.setAutoCommit(true);
        } catch (RuntimeException ee) {
        }
        Log.info(String.format("Lines = %1d", Lines));
        return (ret);
    }
}
