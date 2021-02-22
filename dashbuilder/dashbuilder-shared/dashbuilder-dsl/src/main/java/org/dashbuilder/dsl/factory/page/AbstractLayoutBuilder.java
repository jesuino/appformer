package org.dashbuilder.dsl.factory.page;

public abstract class AbstractLayoutBuilder<T> {

    @SuppressWarnings("unchecked")
    public T property(String key, String value) {
        this._addProperty(key, value);
        return (T) this;
    }

    protected abstract void _addProperty(String key, String value);

}