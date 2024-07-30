package com.api.sportyShoes.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@ToString
public class PurchaseReport {



    public PurchaseReport(int id, String purchasedBy, String category, Date dop, String orderList) {
        super();
        this.id = id;
        this.purchasedBy = purchasedBy;
        this.category = category;
        this.dop = dop;
        this.orderList = orderList;
    }

    @Id
    @GeneratedValue
    private int id;
    private String purchasedBy; // This can be extended to utilize one to one relation with User Table [Future Implemetations]
    private String category;

    @Temporal(TemporalType.DATE)
    private Date dop;

    /**
     * This can be used for storing orderlist as <Qty, Shoe>
     * Here implementation is made simple by using shoeId instead
     * of shoe in string format.
     */
//	@ManyToMany(cascade = CascadeType.ALL)
//	Map<Integer,Shoe> orderList = new HashMap<Integer,Shoe>();
//								OR
//	Map<Integer,Integer> orderList = new HashMap<Integer,Integer>();

    String orderList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getDop() {
		return dop;
	}

	public void setDop(Date dop) {
		this.dop = dop;
	}

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

}
