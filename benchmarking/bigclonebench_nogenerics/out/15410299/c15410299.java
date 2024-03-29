class c15410299 {

    public boolean isValid(WizardContext context) {
        if (JavaCIPUnknownScope.serviceSelection < 0) {
            return false;
        }
        ServiceReference selection = (ServiceReference) JavaCIPUnknownScope.serviceList.getElementAt(JavaCIPUnknownScope.serviceSelection);
        if (selection == null) {
            return false;
        }
        String function = (String) context.getAttribute(ServiceWizard.ATTRIBUTE_FUNCTION);
        context.setAttribute(ServiceWizard.ATTRIBUTE_SERVICE_REFERENCE, selection);
        URL url = selection.getResourceURL();
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            InputSource inputSource = new InputSource(inputStream);
            JdbcService service = ServiceDigester.parseService(inputSource, IsqlToolkit.getSharedEntityResolver());
            context.setAttribute(ServiceWizard.ATTRIBUTE_SERVICE, service);
            return true;
        } catch (IORuntimeException error) {
            if (!ServiceWizard.FUNCTION_DELETE.equals(function)) {
                String loc = url.toExternalForm();
                String message = JavaCIPUnknownScope.messages.format("SelectServiceStep.failed_to_load_service_from_url", loc);
                context.showErrorDialog(error, message);
            } else {
                return true;
            }
        } catch (RuntimeException error) {
            String message = JavaCIPUnknownScope.messages.format("SelectServiceStep.service_load_error", url.toExternalForm());
            context.showErrorDialog(error, message);
        }
        return false;
    }
}
