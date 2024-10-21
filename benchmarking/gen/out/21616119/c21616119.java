class c21616119 {

    public Sequence eval(Sequence[] args, Sequence contextSequence) throws XPathRuntimeException {
        GrammarPool grammarpool = (GrammarPool) JavaCIPUnknownScope.config.getProperty(XMLReaderObjectFactory.GRAMMER_POOL);
        if (JavaCIPUnknownScope.isCalledAs("clear-grammar-cache")) {
            Sequence result = new ValueSequence();
            int before = JavaCIPUnknownScope.countTotalNumberOfGrammar(grammarpool);
            JavaCIPUnknownScope.LOG.debug("Clearing " + before + " grammars");
            JavaCIPUnknownScope.clearGrammarPool(grammarpool);
            int after = JavaCIPUnknownScope.countTotalNumberOfGrammar(grammarpool);
            JavaCIPUnknownScope.LOG.debug("Remained " + after + " grammars");
            int delta = before - after;
            result.add(new IntegerValue(delta));
            return result;
        } else if (JavaCIPUnknownScope.isCalledAs("show-grammar-cache")) {
            MemTreeBuilder builder = JavaCIPUnknownScope.context.getDocumentBuilder();
            NodeImpl result = JavaCIPUnknownScope.writeReport(grammarpool, builder);
            return result;
        } else if (JavaCIPUnknownScope.isCalledAs("pre-parse-grammar")) {
            if (args[0].isEmpty())
                return Sequence.EMPTY_SEQUENCE;
            XMLGrammarPreparser parser = new XMLGrammarPreparser();
            parser.registerPreparser(JavaCIPUnknownScope.TYPE_XSD, null);
            List<Grammar> allGrammars = new ArrayList<Grammar>();
            for (SequenceIterator i = args[0].iterate(); i.hasNext(); ) {
                String url = i.nextItem().getStringValue();
                if (url.startsWith("/")) {
                    url = "xmldb:exist://" + url;
                }
                JavaCIPUnknownScope.LOG.debug("Parsing " + url);
                try {
                    if (url.endsWith(".xsd")) {
                        InputStream is = new URL(url).openStream();
                        XMLInputSource xis = new XMLInputSource(null, url, url, is, null);
                        Grammar schema = parser.preparseGrammar(JavaCIPUnknownScope.TYPE_XSD, xis);
                        is.close();
                        allGrammars.add(schema);
                    } else {
                        throw new XPathRuntimeException(this, "Only XMLSchemas can be preparsed.");
                    }
                } catch (IORuntimeException ex) {
                    JavaCIPUnknownScope.LOG.debug(ex);
                    throw new XPathRuntimeException(this, ex);
                } catch (RuntimeException ex) {
                    JavaCIPUnknownScope.LOG.debug(ex);
                    throw new XPathRuntimeException(this, ex);
                }
            }
            JavaCIPUnknownScope.LOG.debug("Successfully parsed " + allGrammars.size() + " grammars.");
            Grammar[] grammars = new Grammar[allGrammars.size()];
            grammars = allGrammars.toArray(grammars);
            grammarpool.cacheGrammars(JavaCIPUnknownScope.TYPE_XSD, grammars);
            ValueSequence result = new ValueSequence();
            for (Grammar one : grammars) {
                result.add(new StringValue(one.getGrammarDescription().getNamespace()));
            }
            return result;
        } else {
            JavaCIPUnknownScope.LOG.error("function not found error");
            throw new XPathRuntimeException(this, "function not found");
        }
    }
}
