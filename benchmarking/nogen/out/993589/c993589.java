class c993589 {

    public void testScenario() throws RuntimeException {
        String expression = "SELECT id, name, address, phone FROM " + JavaCIPUnknownScope.TABLE + " where id > 2 and id < 12 order by id";
        SQLQuery query = new SQLQuery();
        query.setResourceID(JavaCIPUnknownScope.mResourceID);
        query.addExpression(expression);
        TupleToWebRowSetCharArrays tupleToWebRowSet = new TupleToWebRowSetCharArrays();
        tupleToWebRowSet.connectDataInput(query.getDataOutput());
        DeliverToFTP deliverToFTP = new DeliverToFTP();
        deliverToFTP.connectDataInput(tupleToWebRowSet.getResultOutput());
        deliverToFTP.addFilename(JavaCIPUnknownScope.FILE);
        deliverToFTP.addHost(JavaCIPUnknownScope.mURL);
        PipelineWorkflow pipeline = new PipelineWorkflow();
        pipeline.add(query);
        pipeline.add(tupleToWebRowSet);
        pipeline.add(deliverToFTP);
        JavaCIPUnknownScope.mDRER.execute(pipeline, RequestExecutionType.SYNCHRONOUS);
        final URL url = new URL("ftp://" + JavaCIPUnknownScope.mURL + "/" + JavaCIPUnknownScope.FILE);
        final URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);
        InputStream is = connection.getInputStream();
        WebRowSetToResultSet converter = new WebRowSetToResultSet(new InputStreamReader(is));
        converter.setResultSetType(ResultSet.TYPE_FORWARD_ONLY);
        ResultSet rs = converter.getResultSet();
        JDBCTestHelper.validateResultSet(JavaCIPUnknownScope.mConnection, expression, rs, 1);
        rs.close();
    }
}
