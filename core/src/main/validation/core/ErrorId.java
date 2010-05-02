package validation.core;

class ErrorId {

    private String fieldName;

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorId errorId = (ErrorId) o;

        if (fieldName != null ? !fieldName.equals(errorId.fieldName) : errorId.fieldName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fieldName != null ? fieldName.hashCode() : 0;
    }
}
