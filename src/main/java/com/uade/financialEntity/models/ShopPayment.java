package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.responses.ShopPaymentResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.uade.financialEntity.models.ShopPayment.DateType.DAILY;

@Entity(name = "ShopPayment")
@Table(name = "shop_payment")
@Getter
@Setter
public class ShopPayment {

	//ATTRIBUTES
	@Id
	@Column(name = "SHOP_PAYMENT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Shop shop;

	@OneToOne(cascade = {CascadeType.ALL})
	private Purchase purchase;

	private Integer month;
	private String description;
	private Integer originalAmount;
	private Integer comission;
	private Integer totalAmount;
	private Date date;
	private DateType dateType;

	public enum DateType {
		DAILY,
		END_MONTH
	}

	//METHODS
	public ShopPaymentResponse toDto() {
		return new ShopPaymentResponse(this);
	}

	public boolean isDailyOfMonth(Integer month) {
		return dateType.equals(DAILY) && month.equals(this.month);
	}

}
