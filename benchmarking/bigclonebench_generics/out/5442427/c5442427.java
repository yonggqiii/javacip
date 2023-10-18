class c5442427 {

    public ISource writeTo(ISource output) throws ResourceException {
        try {
            Document doc = JavaCIPUnknownScope.getParent().getDocument();
            Nodes places = doc.query(JavaCIPUnknownScope.getPosition().getXpath());
            if (places.size() == 0) {
                places = doc.query("//html");
            }
            if (places.size() > 0 && places.get(0) instanceof Element) {
                Element target = (Element) places.get(0);
                List<URL> urls = JavaCIPUnknownScope.getResourceURLs();
                if (JavaCIPUnknownScope.getType() == EType.TEXT) {
                    Element tag = JavaCIPUnknownScope.getHeaderTag();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    UtilIO.writeAllTo(urls, out);
                    String content = out.toString();
                    out.close();
                    tag.appendChild(content);
                    if (JavaCIPUnknownScope.getPosition().getPlace() == EPlace.START) {
                        target.insertChild(tag, 0);
                    } else {
                        target.appendChild(tag);
                    }
                } else {
                    for (URL url : urls) {
                        String file = url.toString();
                        String name = file.substring(file.lastIndexOf("/") + 1) + "_res_" + (JavaCIPUnknownScope.serialNumber++);
                        Element tag = JavaCIPUnknownScope.getHeaderTag(output, name);
                        File resFile = JavaCIPUnknownScope.getFile(output, name);
                        if (!resFile.getParentFile().exists()) {
                            if (!resFile.getParentFile().mkdirs()) {
                                throw new ResourceException("Could not create resource directory '" + resFile.getParent() + "'.");
                            }
                        }
                        UtilIO.writeToClose(url.openStream(), new FileOutputStream(resFile));
                        if (JavaCIPUnknownScope.getPosition().getPlace() == EPlace.START) {
                            target.insertChild(tag, 0);
                        } else {
                            target.appendChild(tag);
                        }
                    }
                }
            } else {
                throw new ResourceException("Head element not found.");
            }
        } catch (IOException e) {
            throw new ResourceException(e);
        } catch (SourceException e) {
            throw new ResourceException(e);
        }
        return output;
    }
}
