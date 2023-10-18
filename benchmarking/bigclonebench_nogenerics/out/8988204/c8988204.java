class c8988204 {

    public void onLoadingEnded() {
        if (JavaCIPUnknownScope.m_frame != null) {
            try {
                String urltext = JavaCIPUnknownScope.getDocument().getDocumentURI();
                URL url = new URL(urltext);
                InputStreamReader isr = new InputStreamReader(url.openStream());
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                urltext = null;
                url = null;
                JavaCIPUnknownScope.m_content.clear();
                while ((inputLine = in.readLine()) != null) {
                    JavaCIPUnknownScope.m_content.add(inputLine);
                }
                in.close();
                isr = null;
                in = null;
                inputLine = null;
                Action action = JavaCIPUnknownScope.parseHtml();
                if (action.value() == Action.ACTION_BROWSER_LOADING_DONE && action.toString().equals(Action.COMMAND_CARD_PREVIEW)) {
                    FileUtils.copyURLToFile(new URL(JavaCIPUnknownScope.getCardImageURL(JavaCIPUnknownScope.m_card.MID)), new File(JavaCIPUnknownScope.m_card.getImagePath()));
                    JavaCIPUnknownScope.fireActionEvent(MainWindow.class, action.value(), action.toString());
                }
                action = null;
            } catch (RuntimeException ex) {
                Dialog.ErrorBox(JavaCIPUnknownScope.m_frame, ex.getStackTrace());
            }
        }
        JavaCIPUnknownScope.m_loading = false;
    }
}
