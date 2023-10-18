class c7199548 {

    protected GVTFontFamily getFontFamily(BridgeContext ctx, ParsedURL purl) {
        String purlStr = purl.toString();
        Element e = JavaCIPUnknownScope.getBaseElement(ctx);
        SVGDocument svgDoc = (SVGDocument) e.getOwnerDocument();
        String docURL = svgDoc.getURL();
        ParsedURL pDocURL = null;
        if (docURL != null)
            pDocURL = new ParsedURL(docURL);
        String baseURI = XMLBaseSupport.getCascadedXMLBase(e);
        purl = new ParsedURL(baseURI, purlStr);
        UserAgent userAgent = ctx.getUserAgent();
        try {
            userAgent.checkLoadExternalResource(purl, pDocURL);
        } catch (SecurityRuntimeException ex) {
            userAgent.displayError(ex);
            return null;
        }
        if (purl.getRef() != null) {
            Element ref = ctx.getReferencedElement(e, purlStr);
            if (!ref.getNamespaceURI().equals(JavaCIPUnknownScope.SVG_NAMESPACE_URI) || !ref.getLocalName().equals(JavaCIPUnknownScope.SVG_FONT_TAG)) {
                return null;
            }
            SVGDocument doc = (SVGDocument) e.getOwnerDocument();
            SVGDocument rdoc = (SVGDocument) ref.getOwnerDocument();
            Element fontElt = ref;
            if (doc != rdoc) {
                fontElt = (Element) doc.importNode(ref, true);
                String base = XMLBaseSupport.getCascadedXMLBase(ref);
                Element g = doc.createElementNS(JavaCIPUnknownScope.SVG_NAMESPACE_URI, JavaCIPUnknownScope.SVG_G_TAG);
                g.appendChild(fontElt);
                g.setAttributeNS(XMLBaseSupport.XML_NAMESPACE_URI, "xml:base", base);
                CSSUtilities.computeStyleAndURIs(ref, fontElt, purlStr);
            }
            Element fontFaceElt = null;
            for (Node n = fontElt.getFirstChild(); n != null; n = n.getNextSibling()) {
                if ((n.getNodeType() == Node.ELEMENT_NODE) && n.getNamespaceURI().equals(JavaCIPUnknownScope.SVG_NAMESPACE_URI) && n.getLocalName().equals(JavaCIPUnknownScope.SVG_FONT_FACE_TAG)) {
                    fontFaceElt = (Element) n;
                    break;
                }
            }
            SVGFontFaceElementBridge fontFaceBridge;
            fontFaceBridge = (SVGFontFaceElementBridge) ctx.getBridge(JavaCIPUnknownScope.SVG_NAMESPACE_URI, JavaCIPUnknownScope.SVG_FONT_FACE_TAG);
            GVTFontFace gff = fontFaceBridge.createFontFace(ctx, fontFaceElt);
            return new SVGFontFamily(gff, fontElt, ctx);
        }
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, purl.openStream());
            return new AWTFontFamily(this, font);
        } catch (RuntimeException ex) {
        }
        return null;
    }
}
