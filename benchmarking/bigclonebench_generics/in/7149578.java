


class c7149578 {

    @Override
    protected void parseContent(StreamLimiter streamLimiter, LanguageEnum forcedLang) throws IOException {
        String charset = null;
        IndexDocument sourceDocument = getSourceDocument();
        if (sourceDocument != null && urlItemFieldEnum != null) {
            String name = urlItemFieldEnum.contentTypeCharset.getName();
            FieldValueItem fieldValueItem = sourceDocument.getFieldValue(name, 0);
            if (fieldValueItem != null) charset = fieldValueItem.getValue();
            if (charset == null) {
                name = urlItemFieldEnum.contentEncoding.getName();
                fieldValueItem = sourceDocument.getFieldValue(name, 0);
                if (fieldValueItem != null) charset = fieldValueItem.getValue();
            }
        }
        boolean charsetWasNull = charset == null;
        if (charsetWasNull) charset = getProperty(ClassPropertyEnum.DEFAULT_CHARSET).getValue();
        StringWriter writer = new StringWriter();
        IOUtils.copy(streamLimiter.getNewInputStream(), writer, charset);
        addField(ParserFieldEnum.htmlSource, writer.toString());
        writer.close();
        HtmlDocumentProvider htmlProvider = findBestProvider(charset, streamLimiter);
        if (htmlProvider == null) return;
        addField(ParserFieldEnum.htmlProvider, htmlProvider.getName());
        String contentType = htmlProvider.getMetaHttpEquiv("content-type");
        String contentTypeCharset = null;
        if (contentType != null) {
            contentTypeCharset = MimeUtils.extractContentTypeCharset(contentType);
            if (contentTypeCharset != null && !contentTypeCharset.equals(charset)) charsetWasNull = true;
        }
        if (charsetWasNull) {
            if (contentTypeCharset != null) charset = contentTypeCharset; else charset = htmlProvider.getMetaCharset();
            if (charset != null) htmlProvider = findBestProvider(charset, streamLimiter);
        }
        HtmlNodeAbstract<?> rootNode = htmlProvider.getRootNode();
        if (rootNode == null) return;
        for (HtmlNodeAbstract<?> metaNode : htmlProvider.getMetas()) {
            String metaName = metaNode.getAttributeText("name");
            if (metaName != null && metaName.startsWith(OPENSEARCHSERVER_FIELD)) {
                String field = metaName.substring(OPENSEARCHSERVER_FIELD_LENGTH);
                String[] fields = field.split("\\.");
                if (fields != null) {
                    String content = metaNode.getAttributeText("content");
                    addDirectFields(fields, content);
                }
            }
        }
        addField(ParserFieldEnum.charset, charset);
        addFieldTitle(htmlProvider.getTitle());
        String metaRobots = null;
        String metaDcLanguage = null;
        String metaContentLanguage = null;
        for (HtmlNodeAbstract<?> node : htmlProvider.getMetas()) {
            String attr_name = node.getAttributeText("name");
            String attr_http_equiv = node.getAttributeText("http-equiv");
            String metaContent = HtmlDocumentProvider.getMetaContent(node);
            if ("keywords".equalsIgnoreCase(attr_name)) addField(ParserFieldEnum.meta_keywords, metaContent); else if ("description".equalsIgnoreCase(attr_name)) addField(ParserFieldEnum.meta_description, metaContent); else if ("robots".equalsIgnoreCase(attr_name)) metaRobots = metaContent; else if ("dc.language".equalsIgnoreCase(attr_name)) metaDcLanguage = metaContent; else if ("content-language".equalsIgnoreCase(attr_http_equiv)) metaContentLanguage = metaContent;
        }
        boolean metaRobotsFollow = true;
        boolean metaRobotsNoIndex = false;
        if (metaRobots != null) {
            metaRobots = metaRobots.toLowerCase();
            if (metaRobots.contains("noindex")) {
                metaRobotsNoIndex = true;
                addField(ParserFieldEnum.meta_robots, "noindex");
            }
            if (metaRobots.contains("nofollow")) {
                metaRobotsFollow = false;
                addField(ParserFieldEnum.meta_robots, "nofollow");
            }
        }
        UrlFilterItem[] urlFilterList = getUrlFilterList();
        List<HtmlNodeAbstract<?>> nodes = rootNode.getAllNodes("a", "frame");
        IndexDocument srcDoc = getSourceDocument();
        if (srcDoc != null && nodes != null && metaRobotsFollow) {
            URL currentURL = htmlProvider.getBaseHref();
            if (currentURL == null && urlItemFieldEnum != null) {
                FieldValueItem fvi = srcDoc.getFieldValue(urlItemFieldEnum.url.getName(), 0);
                if (fvi != null) currentURL = new URL(fvi.getValue());
            }
            for (HtmlNodeAbstract<?> node : nodes) {
                String href = null;
                String rel = null;
                String nodeName = node.getNodeName();
                if ("a".equals(nodeName)) {
                    href = node.getAttributeText("href");
                    rel = node.getAttributeText("rel");
                } else if ("frame".equals(nodeName)) {
                    href = node.getAttributeText("src");
                }
                boolean follow = true;
                if (rel != null) if (rel.contains("nofollow")) follow = false;
                URL newUrl = null;
                if (href != null) if (!href.startsWith("javascript:")) if (currentURL != null) newUrl = LinkUtils.getLink(currentURL, href, urlFilterList);
                if (newUrl != null) {
                    ParserFieldEnum field = null;
                    if (newUrl.getHost().equalsIgnoreCase(currentURL.getHost())) {
                        if (follow) field = ParserFieldEnum.internal_link; else field = ParserFieldEnum.internal_link_nofollow;
                    } else {
                        if (follow) field = ParserFieldEnum.external_link; else field = ParserFieldEnum.external_link_nofollow;
                    }
                    addField(field, newUrl.toExternalForm());
                }
            }
        }
        if (!metaRobotsNoIndex) {
            nodes = rootNode.getNodes("html", "body");
            if (nodes == null || nodes.size() == 0) nodes = rootNode.getNodes("html");
            if (nodes != null && nodes.size() > 0) {
                StringBuffer sb = new StringBuffer();
                HtmlNodeAbstract<?> x = nodes.get(0);
                getBodyTextContent(sb, x, true, null);
                addField(ParserFieldEnum.body, sb);
            }
        }
        Locale lang = null;
        String langMethod = null;
        String[] pathHtml = { "html" };
        nodes = rootNode.getNodes(pathHtml);
        if (nodes != null && nodes.size() > 0) {
            langMethod = "html lang attribute";
            HtmlNodeAbstract<?> x = nodes.get(0);
            String l = x.getAttributeText("lang");
            if (l != null) lang = Lang.findLocaleISO639(l);
        }
        if (lang == null && metaContentLanguage != null) {
            langMethod = "meta http-equiv content-language";
            lang = Lang.findLocaleISO639(metaContentLanguage);
        }
        if (lang == null && metaDcLanguage != null) {
            langMethod = "meta dc.language";
            lang = Lang.findLocaleISO639(metaDcLanguage);
        }
        if (lang != null) {
            String ll = lang.getLanguage();
            addField(ParserFieldEnum.lang, ll);
            addField(ParserFieldEnum.lang_method, langMethod);
        } else if (!metaRobotsNoIndex) lang = langDetection(10000, ParserFieldEnum.body);
    }

}
