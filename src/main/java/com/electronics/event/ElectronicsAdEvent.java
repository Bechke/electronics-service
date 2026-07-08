package com.electronics.event;

import java.math.BigDecimal;
import java.time.Instant;

public class ElectronicsAdEvent {

    private String    eventType;
    private String    electronicsId;
    private String    category;
    private String    subCategory;
    private String    title;
    private String    brand;
    private BigDecimal price;
    private String    currency;
    private String    featureImage;
    private String    country;
    private String    state;
    private String    city;
    private String    neighbourhood;
    private String    listingStatus;
    private String    ownerId;
    private String    organizationId;
    private Instant   createdAt;
    private Instant   updatedAt;

    public ElectronicsAdEvent() {}

    public ElectronicsAdEvent(String eventType, String electronicsId, String category,
                              String subCategory, String title, String brand,
                              BigDecimal price, String currency, String featureImage,
                              String country, String state, String city, String neighbourhood,
                              String listingStatus, String ownerId, String organizationId,
                              Instant createdAt, Instant updatedAt) {
        this.eventType      = eventType;
        this.electronicsId  = electronicsId;
        this.category       = category;
        this.subCategory    = subCategory;
        this.title          = title;
        this.brand          = brand;
        this.price          = price;
        this.currency       = currency;
        this.featureImage   = featureImage;
        this.country        = country;
        this.state          = state;
        this.city           = city;
        this.neighbourhood  = neighbourhood;
        this.listingStatus  = listingStatus;
        this.ownerId        = ownerId;
        this.organizationId = organizationId;
        this.createdAt      = createdAt;
        this.updatedAt      = updatedAt;
    }

    public String    getEventType()                        { return eventType; }
    public void      setEventType(String eventType)        { this.eventType = eventType; }
    public String    getElectronicsId()                    { return electronicsId; }
    public void      setElectronicsId(String id)           { this.electronicsId = id; }
    public String    getCategory()                         { return category; }
    public void      setCategory(String category)          { this.category = category; }
    public String    getSubCategory()                      { return subCategory; }
    public void      setSubCategory(String s)              { this.subCategory = s; }
    public String    getTitle()                            { return title; }
    public void      setTitle(String title)                { this.title = title; }
    public String    getBrand()                            { return brand; }
    public void      setBrand(String brand)                { this.brand = brand; }
    public BigDecimal getPrice()                           { return price; }
    public void      setPrice(BigDecimal price)            { this.price = price; }
    public String    getCurrency()                         { return currency; }
    public void      setCurrency(String currency)          { this.currency = currency; }
    public String    getFeatureImage()                     { return featureImage; }
    public void      setFeatureImage(String f)             { this.featureImage = f; }
    public String    getCountry()                          { return country; }
    public void      setCountry(String country)            { this.country = country; }
    public String    getState()                            { return state; }
    public void      setState(String state)                { this.state = state; }
    public String    getCity()                             { return city; }
    public void      setCity(String city)                  { this.city = city; }
    public String    getNeighbourhood()                    { return neighbourhood; }
    public void      setNeighbourhood(String n)            { this.neighbourhood = n; }
    public String    getListingStatus()                    { return listingStatus; }
    public void      setListingStatus(String s)            { this.listingStatus = s; }
    public String    getOwnerId()                          { return ownerId; }
    public void      setOwnerId(String ownerId)            { this.ownerId = ownerId; }
    public String    getOrganizationId()                   { return organizationId; }
    public void      setOrganizationId(String o)           { this.organizationId = o; }
    public Instant   getCreatedAt()                        { return createdAt; }
    public void      setCreatedAt(Instant createdAt)       { this.createdAt = createdAt; }
    public Instant   getUpdatedAt()                        { return updatedAt; }
    public void      setUpdatedAt(Instant updatedAt)       { this.updatedAt = updatedAt; }
}
