package com.epam.jwd.core_final.exception;

public class UnknownEntityException extends RuntimeException {

    private final String entityName;
    private final Object[] args;

    public UnknownEntityException(String entityName) {
        super();
        this.entityName = entityName;
        this.args = null;
    }

    public UnknownEntityException(String entityName, Object[] args) {
        super();
        this.entityName = entityName;
        this.args = args;
    }

    @Override
    public String getMessage() {
        // todo
        // you should use entityName, args (if necessary)
        StringBuilder result = new StringBuilder();
        result.append("Unknown entity: ").append(this.entityName).append(", ");
        if (this.args != null)
            for (Object obj : this.args)
                result.append(obj.toString()).append(", ");
        return result.toString();
    }
}
