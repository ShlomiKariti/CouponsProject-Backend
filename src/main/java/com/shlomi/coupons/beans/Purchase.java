package com.shlomi.coupons.beans;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "purchases")
public class Purchase {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "customer_id", unique = false, nullable = false)
	private long customerId;
	
	@Column(name = "Amount", unique = false, nullable = false)
	private int amount;
	
	@Column(name = "purchase_date", nullable = false)
	private Date purchaseDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", insertable = false, updatable = false)
	@JsonIgnore
	private Coupon coupon;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", customerId=" + customerId + ", amount=" + amount + ", purchaseDate="
				+ purchaseDate + ", coupon=" + coupon + "]";
	}


	
	
}
