//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.19 at 02:54:38 PM BRT 
//


package br.eti.rslemos.tigerapi.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for featureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="featureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="value" type="{}featurevalueType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{}featurenameType" />
 *       &lt;attribute name="domain" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="T"/>
 *             &lt;enumeration value="NT"/>
 *             &lt;enumeration value="FREC"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "featureType", propOrder = {
    "value"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2009-04-19T02:54:38-03:00", comments = "JAXB RI vJAXB 2.1.3 in JDK")
public class FeatureType {

    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2009-04-19T02:54:38-03:00", comments = "JAXB RI vJAXB 2.1.3 in JDK")
    protected List<FeaturevalueType> value;
    @XmlAttribute(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2009-04-19T02:54:38-03:00", comments = "JAXB RI vJAXB 2.1.3 in JDK")
    protected String name;
    @XmlAttribute(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2009-04-19T02:54:38-03:00", comments = "JAXB RI vJAXB 2.1.3 in JDK")
    protected String domain;

    /**
     * Gets the value of the value property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the value property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FeaturevalueType }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2009-04-19T02:54:38-03:00", comments = "JAXB RI vJAXB 2.1.3 in JDK")
    public List<FeaturevalueType> getValue() {
        if (value == null) {
            value = new ArrayList<FeaturevalueType>();
        }
        return this.value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2009-04-19T02:54:38-03:00", comments = "JAXB RI vJAXB 2.1.3 in JDK")
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2009-04-19T02:54:38-03:00", comments = "JAXB RI vJAXB 2.1.3 in JDK")
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2009-04-19T02:54:38-03:00", comments = "JAXB RI vJAXB 2.1.3 in JDK")
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2009-04-19T02:54:38-03:00", comments = "JAXB RI vJAXB 2.1.3 in JDK")
    public void setDomain(String value) {
        this.domain = value;
    }

}
