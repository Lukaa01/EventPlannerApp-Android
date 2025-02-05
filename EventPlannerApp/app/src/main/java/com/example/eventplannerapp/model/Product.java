package com.example.eventplannerapp.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentId;

import java.util.Arrays;
import java.util.List;
public class Product implements Parcelable {
    @DocumentId
    private String id;
    private String category;
    private String subcategory;
    private String name;
    private String description;
    private int price;
    private int discount;
    private int discountedPrice;
    private List<String> gallery;
    private String eventType;
    private boolean available;
    private boolean visible;
    private boolean favourite;

    // Constructor
    public Product(String id,String category, String subcategory, String name, String description, int price, int discount, int discountedPrice, List<String> gallery, String eventType, boolean available, boolean visible, boolean favourite) {
        this.id = id;
        this.category = category;
        this.subcategory = subcategory;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.discountedPrice = discountedPrice;
        this.gallery = gallery;
        this.eventType = eventType;
        this.available = available;
        this.visible = visible;
        this.favourite = favourite;
    }

    public Product(String category, String subcategory, String name, String description, int price, int discount, int discountedPrice, List<String> gallery, String eventType, boolean available, boolean visible, boolean favourite) {
        this.category = category;
        this.subcategory = subcategory;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.discountedPrice = discountedPrice;
        this.gallery = gallery;
        this.eventType = eventType;
        this.available = available;
        this.visible = visible;
        this.favourite = favourite;
    }

    public Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        category = in.readString();
        subcategory = in.readString();
        eventType = in.readString();
        price = in.readInt();
        discount = in.readInt();
        discountedPrice = in.readInt();
        available = in.readInt() != 0;
        visible = in.readInt() != 0;
        gallery = in.createStringArrayList();
        favourite = in.readInt() != 0;
    }

    public Product() {

    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public String toString() {
        return "Product{" +
                "category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", discountedPrice=" + discountedPrice +
                ", eventType='" + eventType + '\'' +
                ", available=" + available +
                ", visible=" + visible +
                ", favourite=" + favourite +
                '}';
    }
    /*
     * Ova metoda opisuje vrste posebnih objekata koje vaša Parcelable implementacija sadrži.
     * Većinom se vraća 0, osim u slučajevima kada objekat uključuje File Descriptor,
     * u kom slučaju se vraća 1.*/
    @Override
    public int describeContents() {
        return 0;
    }
    /*
     * Metoda koja uzima dva argumenta: Parcel u koji se vaš objekat serijalizuje i
     * flags koje Android koristi za označavanje načina na koji se objekat treba
     * serijalizovati. U ovoj metodi trebate upisati sve potrebne podatke iz vašeg
     * objekta u Parcel.
     * */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(subcategory);
        dest.writeInt(price);
        dest.writeInt(discount);
        dest.writeInt(discountedPrice);
        dest.writeString(eventType);
        dest.writeInt(available ? 1 : 0);
        dest.writeInt(visible ? 1 : 0);
        dest.writeStringList(gallery);
        dest.writeInt(favourite ? 1 : 0);
    }
    /*
     * Da biste omogućili Androidu da regeneriše vaš objekat iz Parcel-a,
     * morate da obezbedite statički CREATOR polje koje implementira Parcelable.Creator
     * interfejs. Ovaj interfejs ima dve metode:
     * - createFromParcel(Parcel source): Stvara i vraća novu instancu vaše klase,
     * popunjavajući je podacima iz Parcel objekta koji je prosleđen kao argument.
     * - newArray(int size): Vraća niz vaše klase, što Android koristi kada se
     * regenerišu nizovi Parcelable objekata.
     * */
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}

