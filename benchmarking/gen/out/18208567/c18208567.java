class c18208567 {

    public void createPdf(final String eventURI) throws IOException, DocumentException {
        JavaCIPUnknownScope.createSections(eventURI);
        JavaCIPUnknownScope.even = false;
        final Document document = new Document(Dimensions.getDimension(JavaCIPUnknownScope.even, Dimension.MEDIABOX));
        final PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(MyProperties.getOutput()));
        writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnRight);
        writer.setCropBoxSize(Dimensions.getDimension(JavaCIPUnknownScope.even, Dimension.CROPBOX));
        writer.setBoxSize("trim", Dimensions.getDimension(JavaCIPUnknownScope.even, Dimension.TRIMBOX));
        writer.setBoxSize("bleed", Dimensions.getDimension(JavaCIPUnknownScope.even, Dimension.BLEEDBOX));
        final EventBackgroundAndPageNumbers event = new EventBackgroundAndPageNumbers();
        writer.setPageEvent(event);
        document.open();
        final PdfContentByte content = writer.getDirectContent();
        event.setTabs(Index.INFO.getTab());
        JavaCIPUnknownScope.importPages(document, content, new PdfReader(Index.INFO.getOutput()), Index.INFO.getTitle());
        JavaCIPUnknownScope.importPages(document, content, MyProperties.getBefore(), event);
        JavaCIPUnknownScope.addAdPage(document, content);
        PdfReader reader = new PdfReader(Presentations.INFO.getOutput());
        String[] titles = { "", "" };
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            titles = JavaCIPUnknownScope.index.getSubtitle(titles, i);
            event.setTabs(titles[0].toLowerCase());
            JavaCIPUnknownScope.addTitleLeft(content, Dimensions.getTitleArea(JavaCIPUnknownScope.even), titles[0], MyFonts.TITLE);
            JavaCIPUnknownScope.addTitleRight(content, Dimensions.getTitleArea(JavaCIPUnknownScope.even), titles[1], MyFonts.DATE);
            content.addTemplate(writer.getImportedPage(reader, i), Dimensions.getOffsetX(JavaCIPUnknownScope.even), Dimensions.getOffsetY(JavaCIPUnknownScope.even));
            document.newPage();
            JavaCIPUnknownScope.even = !JavaCIPUnknownScope.even;
        }
        JavaCIPUnknownScope.addAdPage(document, content);
        JavaCIPUnknownScope.importPages(document, content, MyProperties.getAfter(), event);
        int total = writer.getPageNumber() - 1;
        event.setNoMorePageNumbers();
        event.setTabs(Schedules.INFO.getTab());
        reader = new PdfReader(Schedules.INFO.getOutput());
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            JavaCIPUnknownScope.addTitleLeft(content, Dimensions.getTitleArea(JavaCIPUnknownScope.even), Schedules.INFO.getTitle(), MyFonts.TITLE);
            content.addTemplate(writer.getImportedPage(reader, i), Dimensions.getOffsetX(JavaCIPUnknownScope.even), Dimensions.getOffsetY(JavaCIPUnknownScope.even));
            document.newPage();
            JavaCIPUnknownScope.even = !JavaCIPUnknownScope.even;
        }
        document.close();
        final File file = new File(MyProperties.getOutput());
        final byte[] original = new byte[(int) file.length()];
        final FileInputStream f = new FileInputStream(file);
        f.read(original);
        reader = new PdfReader(original);
        final List<Integer> ranges = new ArrayList<Integer>();
        for (int i = 1; i <= total; i++) {
            ranges.add(i);
            if (i == total / 2) {
                for (int j = total + 1; j <= reader.getNumberOfPages(); j++) {
                    ranges.add(j);
                }
            }
        }
        reader.selectPages(ranges);
        final PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(MyProperties.getOutput()));
        stamper.close();
    }
}
