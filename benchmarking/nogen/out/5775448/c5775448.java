class c5775448 {

    private void writeData(IBaseType dataType, Writer writer) throws XMLStreamRuntimeException {
        InputStream isData;
        DataType data = (DataType) JavaCIPUnknownScope.baseType;
        if (data.isSetInputStream()) {
            isData = data.getInputStream();
            try {
                IOUtils.copy(isData, writer);
            } catch (IORuntimeException e) {
                throw new XMLStreamRuntimeException("DataType fail writing streaming data ", e);
            }
        } else if (data.isSetOutputStream()) {
            throw new XMLStreamRuntimeException("DataType only can write streaming input, its an output stream (only for reading) ");
        } else {
            new CharactersEventImpl(JavaCIPUnknownScope.startElement.getLocation(), String.valueOf(JavaCIPUnknownScope.baseType.asData()), false).writeAsEncodedUnicode(writer);
        }
    }
}
