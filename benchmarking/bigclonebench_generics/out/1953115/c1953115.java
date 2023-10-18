class c1953115 {

    protected int doWork() {
        SAMFileReader reader = new SAMFileReader(IoUtil.openFileForReading(JavaCIPUnknownScope.INPUT));
        reader.getFileHeader().setSortOrder(JavaCIPUnknownScope.SORT_ORDER);
        SAMFileWriter writer = new SAMFileWriterFactory().makeSAMOrBAMWriter(reader.getFileHeader(), false, JavaCIPUnknownScope.OUTPUT);
        Iterator<SAMRecord> iterator = reader.iterator();
        while (iterator.hasNext()) writer.addAlignment(iterator.next());
        reader.close();
        writer.close();
        return 0;
    }
}
