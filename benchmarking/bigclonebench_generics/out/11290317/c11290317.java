class c11290317 {

    private void trainSRLParser(byte flag, JarArchiveOutputStream zout) throws RuntimeException {
        AbstractSRLParser labeler = null;
        AbstractDecoder[] decoder = null;
        if (flag == SRLParser.FLAG_TRAIN_LEXICON) {
            System.out.println("\n* Save lexica");
            labeler = new SRLParser(flag, JavaCIPUnknownScope.s_featureXml);
        } else if (flag == SRLParser.FLAG_TRAIN_INSTANCE) {
            System.out.println("\n* Print training instances");
            System.out.println("- loading lexica");
            labeler = new SRLParser(flag, JavaCIPUnknownScope.t_xml, JavaCIPUnknownScope.s_lexiconFiles);
        } else if (flag == SRLParser.FLAG_TRAIN_BOOST) {
            System.out.println("\n* Train boost");
            decoder = new AbstractDecoder[JavaCIPUnknownScope.m_model.length];
            for (int i = 0; i < decoder.length; i++) decoder[i] = new OneVsAllDecoder((OneVsAllModel) JavaCIPUnknownScope.m_model[i]);
            labeler = new SRLParser(flag, JavaCIPUnknownScope.t_xml, JavaCIPUnknownScope.t_map, decoder);
        }
        AbstractReader<DepNode, DepTree> reader = new SRLReader(JavaCIPUnknownScope.s_trainFile, true);
        DepTree tree;
        int n;
        labeler.setLanguage(JavaCIPUnknownScope.s_language);
        reader.setLanguage(JavaCIPUnknownScope.s_language);
        for (n = 0; (tree = reader.nextTree()) != null; n++) {
            labeler.parse(tree);
            if (n % 1000 == 0)
                System.out.printf("\r- parsing: %dK", n / 1000);
        }
        System.out.println("\r- labeling: " + n);
        if (flag == SRLParser.FLAG_TRAIN_LEXICON) {
            System.out.println("- labeling");
            labeler.saveTags(JavaCIPUnknownScope.s_lexiconFiles);
            JavaCIPUnknownScope.t_xml = labeler.getSRLFtrXml();
        } else if (flag == SRLParser.FLAG_TRAIN_INSTANCE || flag == SRLParser.FLAG_TRAIN_BOOST) {
            JavaCIPUnknownScope.a_yx = labeler.a_trans;
            zout.putArchiveEntry(new JarArchiveEntry(JavaCIPUnknownScope.ENTRY_FEATURE));
            IOUtils.copy(new FileInputStream(JavaCIPUnknownScope.s_featureXml), zout);
            zout.closeArchiveEntry();
            for (String lexicaFile : JavaCIPUnknownScope.s_lexiconFiles) {
                zout.putArchiveEntry(new JarArchiveEntry(lexicaFile));
                IOUtils.copy(new FileInputStream(lexicaFile), zout);
                zout.closeArchiveEntry();
            }
            if (flag == SRLParser.FLAG_TRAIN_INSTANCE)
                JavaCIPUnknownScope.t_map = labeler.getSRLFtrMap();
        }
    }
}
