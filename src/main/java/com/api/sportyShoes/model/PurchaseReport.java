package com.api.sportyShoes.model;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "purchase_report")
@Getter
@Setter
@ToString
public class PurchaseReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    private Shoe shoe;  // Added Shoe reference

    private String purchasedBy; 
    private String category;

    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    private int quantity;

    private double totalPrice;
    
    public PurchaseReport() {
    }
    
    public PurchaseReport(int id, Shoe shoe, String purchasedBy, String category, Date purchaseDate, double totalPrice, int quantity) {
        this.id = id;
        this.shoe = shoe;
        this.purchasedBy = purchasedBy;
        this.category = category;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Shoe getShoe() {
		return shoe;
	}

	public void setShoe(Shoe shoe) {
		this.shoe = shoe;
	}

	public String getPurchasedBy() {
		return purchasedBy;
	}

	public void setPurchasedBy(String purchasedBy) {
		this.purchasedBy = purchasedBy;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
