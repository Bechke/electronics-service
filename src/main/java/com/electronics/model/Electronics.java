package com.electronics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Electronics listing.
 * Sub-categories: MOBILE, LAPTOP, TABLET, TV, CAMERA, AUDIO, GAMING,
 *                 APPLIANCE, ACCESSORIES, OTHER
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "electronics")
public class Electronics {

    @Id
    private String id;

    // ── Classification ────────────────────────────────────────────────────────
    @NotBlank(message = "Category is required")
    @Indexed
    private String category = "ELECTRONICS";

    @NotBlank(message = "Sub-category is required")
    @Indexed
    private String subCategory; // MOBILE | LAPTOP | TABLET | TV | CAMERA | AUDIO | GAMING | APPLIANCE | ACCESSORIES | OTHER

    // ── Core details ──────────────────────────────────────────────────────────
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Brand is required")
    @Indexed
    private String brand;

    private String model;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    @Indexed
    private BigDecimal price;

    private String     currency = "₹";
    private BigDecimal originalPrice;

    // ── Item specs ────────────────────────────────────────────────────────────
    private String  condition;         // NEW | USED | REFURBISHED
    private Integer ageMonths;         // how old the item is (in months)
    private String  storageCapacity;   // e.g. "128GB", "1TB"
    private String  ramCapacity;       // e.g. "8GB"
    private String  processorModel;    // e.g. "Apple M3", "Snapdragon 8 Gen 3"
    private String  screenSize;        // e.g. "6.7 inch"
    private String  batteryCapacity;   // e.g. "5000 mAh"
    private String  os;                // e.g. "Android 14", "iOS 17"
    private String  color;
    private Boolean warrantyAvailable;
    private String  warrantyDetails;   // e.g. "6 months seller warranty"
    private List<String> features;     // e.g. "5G", "Face ID", "OLED Display"

    // ── Media ─────────────────────────────────────────────────────────────────
    private String       featureImage;
    private List<String> imageUrls;

    // ── Location ──────────────────────────────────────────────────────────────
    @Indexed
    private String location;
    private String country;
    private String state;
    private String city;
    private String neighbourhood;

    // ── Ownership ─────────────────────────────────────────────────────────────
    private String ownerId;
    private String organizationId;

    // ── Listing meta ──────────────────────────────────────────────────────────
    @Indexed
    private String  listingStatus;     // PENDING | ACTIVE | SOLD | INACTIVE
    private Long    numberOfViews;
    private Instant createdAt;
    private Instant updatedAt;

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public String getId()                           { return id; }
    public void   setId(String id)                  { this.id = id; }

    public String getCategory()                     { return category; }
    public void   setCategory(String c)             { this.category = c; }

    public String getSubCategory()                  { return subCategory; }
    public void   setSubCategory(String s)          { this.subCategory = s; }

    public String getTitle()                        { return title; }
    public void   setTitle(String title)            { this.title = title; }

    public String getBrand()                        { return brand; }
    public void   setBrand(String brand)            { this.brand = brand; }

    public String getModel()                        { return model; }
    public void   setModel(String model)            { this.model = model; }

    public String getDescription()                  { return description; }
    public void   setDescription(String d)          { this.description = d; }

    public BigDecimal getPrice()                    { return price; }
    public void       setPrice(BigDecimal price)    { this.price = price; }

    public String     getCurrency()                 { return currency; }
    public void       setCurrency(String currency)  { this.currency = currency; }

    public BigDecimal getOriginalPrice()            { return originalPrice; }
    public void       setOriginalPrice(BigDecimal p){ this.originalPrice = p; }

    public String  getCondition()                   { return condition; }
    public void    setCondition(String condition)   { this.condition = condition; }

    public Integer getAgeMonths()                   { return ageMonths; }
    public void    setAgeMonths(Integer a)          { this.ageMonths = a; }

    public String  getStorageCapacity()             { return storageCapacity; }
    public void    setStorageCapacity(String s)     { this.storageCapacity = s; }

    public String  getRamCapacity()                 { return ramCapacity; }
    public void    setRamCapacity(String r)         { this.ramCapacity = r; }

    public String  getProcessorModel()              { return processorModel; }
    public void    setProcessorModel(String p)      { this.processorModel = p; }

    public String  getScreenSize()                  { return screenSize; }
    public void    setScreenSize(String s)          { this.screenSize = s; }

    public String  getBatteryCapacity()             { return batteryCapacity; }
    public void    setBatteryCapacity(String b)     { this.batteryCapacity = b; }

    public String  getOs()                          { return os; }
    public void    setOs(String os)                 { this.os = os; }

    public String  getColor()                       { return color; }
    public void    setColor(String color)           { this.color = color; }

    public Boolean getWarrantyAvailable()           { return warrantyAvailable; }
    public void    setWarrantyAvailable(Boolean w)  { this.warrantyAvailable = w; }

    public String  getWarrantyDetails()             { return warrantyDetails; }
    public void    setWarrantyDetails(String w)     { this.warrantyDetails = w; }

    public List<String> getFeatures()               { return features; }
    public void         setFeatures(List<String> f) { this.features = f; }

    public String       getFeatureImage()           { return featureImage; }
    public void         setFeatureImage(String f)   { this.featureImage = f; }

    public List<String> getImageUrls()              { return imageUrls; }
    public void         setImageUrls(List<String> i){ this.imageUrls = i; }

    public String getLocation()                     { return location; }
    public void   setLocation(String location)      { this.location = location; }

    public String getCountry()                      { return country; }
    public void   setCountry(String country)        { this.country = country; }

    public String getState()                        { return state; }
    public void   setState(String state)            { this.state = state; }

    public String getCity()                         { return city; }
    public void   setCity(String city)              { this.city = city; }

    public String getNeighbourhood()                { return neighbourhood; }
    public void   setNeighbourhood(String n)        { this.neighbourhood = n; }

    public String  getOwnerId()                     { return ownerId; }
    public void    setOwnerId(String ownerId)       { this.ownerId = ownerId; }

    public String  getOrganizationId()              { return organizationId; }
    public void    setOrganizationId(String o)      { this.organizationId = o; }

    public String  getListingStatus()               { return listingStatus; }
    public void    setListingStatus(String s)       { this.listingStatus = s; }

    public Long    getNumberOfViews()               { return numberOfViews; }
    public void    setNumberOfViews(Long n)         { this.numberOfViews = n; }

    public Instant getCreatedAt()                   { return createdAt; }
    public void    setCreatedAt(Instant createdAt)  { this.createdAt = createdAt; }

    public Instant getUpdatedAt()                   { return updatedAt; }
    public void    setUpdatedAt(Instant updatedAt)  { this.updatedAt = updatedAt; }
}
