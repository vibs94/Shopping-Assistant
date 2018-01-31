package com.example.root.shoppingassistance.Model;

/**
 * Created by vibodha on 1/29/18.
 */

public class Attribute {

    private String attributeType;
    private String attributeName;
    private int attributeIndex;

    public Attribute(String attributeType, String attributeName, int attributeIndex) {
        this.attributeType = attributeType;
        this.attributeName = attributeName;
        this.attributeIndex = attributeIndex;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getAttributeIndex() {
        return attributeIndex;
    }

    public void setAttributeIndex(int attributeIndex) {
        this.attributeIndex = attributeIndex;
    }
}
