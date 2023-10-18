class c18451704 {

    public boolean isValidPage(BookPage page) {
        boolean isValid = false;
        try {
            if (page.getType() == BookPage.TYPE_RESOURCE) {
                BookPagePreviewPanel panel = new BookPagePreviewPanel(JavaCIPUnknownScope.dControl, true);
                panel.setCurrentBookPage(page);
                isValid = !page.getUri().equals("") && panel.isValid();
            } else if (page.getType() == BookPage.TYPE_URL) {
                URL url = new URL(page.getUri());
                url.openStream().close();
                isValid = true;
            } else if (page.getType() == BookPage.TYPE_IMAGE) {
                if (page.getUri().length() > 0)
                    isValid = true;
            }
        } catch (RuntimeException e) {
            isValid = false;
        }
        return isValid;
    }
}
