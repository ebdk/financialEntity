package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.responses.PurchaseFullResponse;
import com.uade.financialEntity.messages.responses.PurchaseResponse;
import com.uade.financialEntity.utils.MathUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.uade.financialEntity.models.Purchase.PurchaseType.MONTHLY_PAYMENT;
import static com.uade.financialEntity.models.Purchase.PurchaseType.ORIGINAL;

@Entity(name = "Purchase")
@Table(name = "purchase")
@Getter
@Setter
public class Purchase {

	//ATTRIBUTES
	@Id
	@Column(name = "PURCHASE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private ShopPromotion shopPromotion;

	@OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL)
	private ShopPayment shopPayment;

	@ManyToOne
	private MonthResume monthResume;

	/*
	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
	private List<PurchaseItem> purchaseItems;
	 */

	private String description;
	private Integer originalAmount;
	private Integer discount;
	private Integer totalAmount;
	private Date date;
	private PurchaseType purchaseType;
	private Integer monthPays;
	private Integer monthsPaid;

	public enum PurchaseType {
		ORIGINAL,
		MONTHLY_PAYMENT
	}

	//BUILDERS
	Purchase cloneNew() {
		//monthsPaid++;
		Purchase newPurchase = new Purchase(MONTHLY_PAYMENT);
		newPurchase.setDate(date);
		newPurchase.setTotalAmount(totalAmount);
		newPurchase.setMonthPays(monthPays);
		newPurchase.setMonthsPaid(monthsPaid);
		newPurchase.setDescription(description + String.format(" - Pago de la cuota %s / %s", monthsPaid, monthPays));
		return newPurchase;
	}

	public Purchase() {
		originalAmount = 0;
		discount = 0;
		totalAmount = 0;
		date = new Date();
		monthPays = 1;
		monthsPaid = 0;
	}

	public Purchase(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
		originalAmount = 0;
		discount = 0;
		totalAmount = 0;
		date = new Date();
		monthPays = 1;
		monthsPaid = 0;
	}

	//METHODS
	public PurchaseResponse toDto() {
		return new PurchaseResponse(this);
	}

	public PurchaseFullResponse toFullDto() {
		return new PurchaseFullResponse(this);
	}

	/*
	public Integer calculateTotalAmount() {
		return purchaseItems.stream().mapToInt(PurchaseItem::calculateTotalAmount).sum();
	}
	 */

	public Integer calculateTotalAmount(Integer amount, Integer monthPays) {
		//Integer amount = purchaseItems.stream().mapToInt(PurchaseItem::calculateTotalAmount).sum();
		if (monthPays.equals(1)) {
			return amount;
		} else {
			if ((1 < monthPays) && (monthPays <= 3)) {
				amount = amount + MathUtils.getPercentage(amount, 10);
			}
			if ((3 < monthPays) && (monthPays <= 6)) {
				amount = amount + MathUtils.getPercentage(amount, 20);
			}
			if ((6 < monthPays) && (monthPays <= 12)) {
				amount = amount + MathUtils.getPercentage(amount, 30);
			}
			return amount;
		}
	}

	/*
	public List<ProductType> getPurchaseProductTypes() {
		List<ProductType> types = new ArrayList<>();
		purchaseItems.forEach(purchaseItem -> types.add(purchaseItem.getTypeOfProduct()));
		return types;
	}
	 */

	private boolean isOriginal() {
		return purchaseType.equals(ORIGINAL);
	}


	private boolean isFullyPaid() {
		return monthsPaid >= monthPays;
	}

	private boolean isNotFullyPaid() {
		return !isFullyPaid();
	}

	boolean isOriginalAndNotFullyPaid() {
		return isNotFullyPaid() && isOriginal();
	}

	public void increasePayMonth() {
		this.monthsPaid = monthsPaid + 1;
	}


}
