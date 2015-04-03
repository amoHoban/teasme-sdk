package net.netm.apps.libs.teaseMe.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;

public class FilteredScreen {

    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private Map<String, String> properties;
    @Expose
    private String service;
    @Expose
    private String owner;
    @Expose
    private Date createdDate;
    @Expose
    private Date lastModifiedDate;
    @Expose
    private Date accumulatedLastModifiedDate;
    @Expose
    private List<Teaser> teasers = new ArrayList<Teaser>();

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
     * @return The properties
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * @param properties The properties
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    /**
     * @return The service
     */
    public String getService() {
        return service;
    }

    /**
     * @param service The service
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * @return The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
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
     * @return The lastModifiedDate
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * @param lastModifiedDate The lastModifiedDate
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * @return The teasers
     */
    public List<Teaser> getTeasers() {
        return teasers;
    }

    /**
     * @param teasers The teasers
     */
    public void setTeasers(List<Teaser> teasers) {
        this.teasers = teasers;
    }


    public Date getAccumulatedLastModifiedDate() {
        return accumulatedLastModifiedDate;
    }

    public void setAccumulatedLastModifiedDate(Date accumulatedLastModifiedDate) {
        this.accumulatedLastModifiedDate = accumulatedLastModifiedDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(description).append(properties).append(service).append(owner).append(createdDate).append(lastModifiedDate).append(teasers).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FilteredScreen) == false) {
            return false;
        }
        FilteredScreen rhs = ((FilteredScreen) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(description, rhs.description).append(properties, rhs.properties).append(service, rhs.service).append(owner, rhs.owner).append(createdDate, rhs.createdDate).append(lastModifiedDate, rhs.lastModifiedDate).append(teasers, rhs.teasers).isEquals();
    }

}