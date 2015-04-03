package net.netm.apps.libs.teaseme.models;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;

public class Teaser {

    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private String teaserType;
    @Expose
    private Map<String, String> properties;
    @Expose
    private String content;
    @Expose
    private String image;
    @Expose
    private String imageAlt;
    @Expose
    private Double imageAspectRatio;
    @Expose
    private String actionValue;
    @Expose
    private String actionType;
    @Expose
    private Date createdDate;
    @Expose
    private Date lastModfiedDate;
    @Expose
    private Long imageId;
    @Expose
    private Boolean imageTeaser;


    /**
     * @return The id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The teaserType
     */
    public String getTeaserType() {
        return teaserType;
    }

    /**
     * @param teaserType The teaserType
     */
    public void setTeaserType(String teaserType) {
        this.teaserType = teaserType;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The imageAlt
     */
    public String getImageAlt() {
        return imageAlt;
    }

    /**
     * @param imageAlt The imageAlt
     */
    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    /**
     * @return The actionValue
     */
    public String getActionValue() {
        return actionValue;
    }

    /**
     * @param actionValue The actionValue
     */
    public void setActionValue(String actionValue) {
        this.actionValue = actionValue;
    }

    /**
     * @return The actionType
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * @param actionType The actionType
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /**
     * @return The createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate The createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return The lastModfiedDate
     */
    public Date getLastModfiedDate() {
        return lastModfiedDate;
    }

    /**
     * @param lastModfiedDate The lastModfiedDate
     */
    public void setLastModfiedDate(Date lastModfiedDate) {
        this.lastModfiedDate = lastModfiedDate;
    }

    /**
     * @return The imageId
     */
    public Long getImageId() {
        return imageId;
    }

    /**
     * @param imageId The imageId
     */
    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    /**
     * @return The imageTeaser
     */
    public Boolean getImageTeaser() {
        return imageTeaser;
    }

    /**
     * @param imageTeaser The imageTeaser
     */
    public void setImageTeaser(Boolean imageTeaser) {
        this.imageTeaser = imageTeaser;
    }

    public Double getImageAspectRatio() {
        return imageAspectRatio;
    }

    public void setImageAspectRatio(Double imageAspectRatio) {
        this.imageAspectRatio = imageAspectRatio;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(description).append(teaserType).append(properties).append(content).append(image).append(imageAlt).append(actionValue).append(actionType).append(createdDate).append(lastModfiedDate).append(imageId).append(imageTeaser).toHashCode();
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Teaser) == false) {
            return false;
        }
        Teaser rhs = ((Teaser) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(description, rhs.description).append(teaserType, rhs.teaserType).append(properties, rhs.properties).append(content, rhs.content).append(image, rhs.image).append(imageAlt, rhs.imageAlt).append(actionValue, rhs.actionValue).append(actionType, rhs.actionType).append(createdDate, rhs.createdDate).append(lastModfiedDate, rhs.lastModfiedDate).append(imageId, rhs.imageId).append(imageTeaser, rhs.imageTeaser).isEquals();
    }


}