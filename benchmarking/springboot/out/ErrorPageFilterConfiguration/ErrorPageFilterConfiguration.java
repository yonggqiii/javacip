class ErrorPageFilterConfiguration {

    ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    FilterRegistrationBean<ErrorPageFilter> errorPageFilterRegistration(ErrorPageFilter filter) {
        FilterRegistrationBean<ErrorPageFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(filter.getOrder());
        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        return registration;
    }
}
