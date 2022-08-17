package eu.interopehrate.mr2dsm.model;

public class RequestedAuthenticationContext {
    private String comparison;
    private ContextClass contextClass;

    public RequestedAuthenticationContext(String comparison, ContextClass contextClass) {
        this.comparison = comparison;
        this.contextClass = contextClass;
    }

    public RequestedAuthenticationContext(String comparison) {
        this.comparison = comparison;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public ContextClass getContextClass() {
        return contextClass;
    }

    public void setContextClass(ContextClass contextClass) {
        this.contextClass = contextClass;
    }
}
