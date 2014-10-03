package com.vaadin.server.widgetsetutils.metadata;

import java.lang.annotation.Annotation;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.SourceWriter;

public abstract class Property implements Comparable<Property> {
    private final String name;
    private final JClassType beanType;
    private final JType propertyType;

    protected Property(String name, JClassType beanType, JType propertyType) {
        this.name = name;
        this.beanType = beanType;
        this.propertyType = propertyType;
    }

    public String getName() {
        return name;
    }

    public JType getPropertyType() {
        return propertyType;
    }

    public String getUnboxedPropertyTypeName() {
        JType propertyType = getPropertyType();
        JPrimitiveType primitive = propertyType.isPrimitive();
        if (primitive != null) {
            return primitive.getQualifiedBoxedSourceName();
        } else {
            return propertyType.getQualifiedSourceName();
        }
    }

    public String boxValue(String codeSnippet) {
        JPrimitiveType primitive = propertyType.isPrimitive();
        if (primitive == null) {
            return codeSnippet;
        } else {
            return String.format("@%s::valueOf(%s)(%s)",
                    primitive.getQualifiedBoxedSourceName(),
                    propertyType.getJNISignature(), codeSnippet);
        }
    }

    public String unboxValue(String codeSnippet) {
        JPrimitiveType primitive = propertyType.isPrimitive();
        if (primitive == null) {
            return codeSnippet;
        } else {
            return String.format("%s.@%s::%sValue()()", codeSnippet,
                    primitive.getQualifiedBoxedSourceName(),
                    primitive.getSimpleSourceName());
        }
    }

    public JClassType getBeanType() {
        return beanType;
    }

    public abstract void writeSetterBody(TreeLogger logger, SourceWriter w,
            String beanVariable, String valueVariable);

    public abstract void writeGetterBody(TreeLogger logger, SourceWriter w,
            String beanVariable);

    public abstract boolean hasAccessorMethods();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Property) {
            Property other = (Property) obj;
            return other.getClass() == getClass()
                    && other.getBeanType().equals(getBeanType())
                    && other.getName().equals(getName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() * 31 ^ 2 + getBeanType().hashCode() * 31
                + getName().hashCode();
    }

    @Override
    public int compareTo(Property o) {
        return getName().compareTo(o.getName());
    }

    public abstract <T extends Annotation> T getAnnotation(
            Class<T> annotationClass);

}
