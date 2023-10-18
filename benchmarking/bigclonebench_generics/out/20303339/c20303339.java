class c20303339 {

    private void trainDepParser(byte flag, JarArchiveOutputStream zout) throws RuntimeException {
        AbstractDepParser parser = null;
        OneVsAllDecoder decoder = null;
        if (flag == ShiftPopParser.FLAG_TRAIN_LEXICON) {
            System.out.println("\n* Save lexica");
            if (JavaCIPUnknownScope.s_depParser.equals(AbstractDepParser.ALG_SHIFT_EAGER))
                parser = new ShiftEagerParser(flag, JavaCIPUnknownScope.s_featureXml);
            else if (JavaCIPUnknownScope.s_depParser.equals(AbstractDepParser.ALG_SHIFT_POP))
                parser = new ShiftPopParser(flag, JavaCIPUnknownScope.s_featureXml);
        } else if (flag == ShiftPopParser.FLAG_TRAIN_INSTANCE) {
            System.out.println("\n* Print training instances");
            System.out.println("- loading lexica");
            if (JavaCIPUnknownScope.s_depParser.equals(AbstractDepParser.ALG_SHIFT_EAGER))
                parser = new ShiftEagerParser(flag, JavaCIPUnknownScope.t_xml, JavaCIPUnknownScope.ENTRY_LEXICA);
            else if (JavaCIPUnknownScope.s_depParser.equals(AbstractDepParser.ALG_SHIFT_POP))
                parser = new ShiftPopParser(flag, JavaCIPUnknownScope.t_xml, JavaCIPUnknownScope.ENTRY_LEXICA);
        } else if (flag == ShiftPopParser.FLAG_TRAIN_BOOST) {
            System.out.println("\n* Train conditional");
            decoder = new OneVsAllDecoder(JavaCIPUnknownScope.m_model);
            if (JavaCIPUnknownScope.s_depParser.equals(AbstractDepParser.ALG_SHIFT_EAGER))
                parser = new ShiftEagerParser(flag, JavaCIPUnknownScope.t_xml, JavaCIPUnknownScope.t_map, decoder);
            else if (JavaCIPUnknownScope.s_depParser.equals(AbstractDepParser.ALG_SHIFT_POP))
                parser = new ShiftPopParser(flag, JavaCIPUnknownScope.t_xml, JavaCIPUnknownScope.t_map, decoder);
        }
        AbstractReader<DepNode, DepTree> reader = null;
        DepTree tree;
        int n;
        if (JavaCIPUnknownScope.s_format.equals(AbstractReader.FORMAT_DEP))
            reader = new DepReader(JavaCIPUnknownScope.s_trainFile, true);
        else if (JavaCIPUnknownScope.s_format.equals(AbstractReader.FORMAT_CONLLX))
            reader = new CoNLLXReader(JavaCIPUnknownScope.s_trainFile, true);
        parser.setLanguage(JavaCIPUnknownScope.s_language);
        reader.setLanguage(JavaCIPUnknownScope.s_language);
        for (n = 0; (tree = reader.nextTree()) != null; n++) {
            parser.parse(tree);
            if (n % 1000 == 0)
                System.out.printf("\r- parsing: %dK", n / 1000);
        }
        System.out.println("\r- parsing: " + n);
        if (flag == ShiftPopParser.FLAG_TRAIN_LEXICON) {
            System.out.println("- saving");
            parser.saveTags(JavaCIPUnknownScope.ENTRY_LEXICA);
            JavaCIPUnknownScope.t_xml = parser.getDepFtrXml();
        } else if (flag == ShiftPopParser.FLAG_TRAIN_INSTANCE || flag == ShiftPopParser.FLAG_TRAIN_BOOST) {
            JavaCIPUnknownScope.a_yx = parser.a_trans;
            zout.putArchiveEntry(new JarArchiveEntry(JavaCIPUnknownScope.ENTRY_PARSER));
            PrintStream fout = new PrintStream(zout);
            fout.print(JavaCIPUnknownScope.s_depParser);
            fout.flush();
            zout.closeArchiveEntry();
            zout.putArchiveEntry(new JarArchiveEntry(JavaCIPUnknownScope.ENTRY_FEATURE));
            IOUtils.copy(new FileInputStream(JavaCIPUnknownScope.s_featureXml), zout);
            zout.closeArchiveEntry();
            zout.putArchiveEntry(new JarArchiveEntry(JavaCIPUnknownScope.ENTRY_LEXICA));
            IOUtils.copy(new FileInputStream(JavaCIPUnknownScope.ENTRY_LEXICA), zout);
            zout.closeArchiveEntry();
            if (flag == ShiftPopParser.FLAG_TRAIN_INSTANCE)
                JavaCIPUnknownScope.t_map = parser.getDepFtrMap();
        }
    }
}
