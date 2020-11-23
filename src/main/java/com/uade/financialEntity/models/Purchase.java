package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.responses.PurchaseFullResponse;
import com.uade.financialEntity.messages.responses.PurchaseResponse;
import com.uade.financialEntity.utils.MathUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.uade.financialEntity.models.Purchase.PurchaseType.MONTHLY_PAYMENT;
import static com.uade.financialEntity.models.Purchase.PurchaseType.MONTHLY_PAYMENT_ORIGINAL_AMOUNT;

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

	private String description;
	private Integer originalAmount;
	private Integer discount;
	private Integer totalAmount;
	private Date date;
	private PurchaseType purchaseType;
	private Integer monthPays;
	private Integer monthsPaid;

	public enum PurchaseType {
		ONE_PAY,
		MONTHLY_PAYMENT,
		MONTHLY_PAYMENT_ORIGINAL_AMOUNT
	}

	//BUILDERS
	public Purchase cloneNew() {
		monthsPaid++;
		Purchase newPurchase = new Purchase(MONTHLY_PAYMENT);
		newPurchase.setDate(date);
		newPurchase.setOriginalAmount(totalAmount);
		newPurchase.setTotalAmount(totalAmount / monthPays);
		newPurchase.setDiscount(newPurchase.getOriginalAmount() - newPurchase.getTotalAmount());
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
		monthsPaid = 1;
	}

	public Purchase(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
		originalAmount = 0;
		discount = 0;
		totalAmount = 0;
		date = new Date();
		monthsPaid = purchaseType.equals(MONTHLY_PAYMENT_ORIGINAL_AMOUNT) ? 0 : 1;
		monthPays = 1;
	}

	//METHODS
	public PurchaseResponse toDto() {
		return new PurchaseResponse(this);
	}

	public PurchaseFullResponse toFullDto() {
		return new PurchaseFullResponse(this);
	}

	public static Integer calculateTotalAmount(Integer amount, Integer monthPays) {
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

	boolean isOriginalAndNotFullyPaid() {
		boolean isNotFullyPaid = monthsPaid < monthPays;
		boolean isOriginal = purchaseType.equals(MONTHLY_PAYMENT_ORIGINAL_AMOUNT);

		return isNotFullyPaid && isOriginal;
	}

	public boolean isNotOriginalMonthlyPay() {
		return !this.purchaseType.equals(MONTHLY_PAYMENT_ORIGINAL_AMOUNT);
	}

	public boolean itOriginalMonthlyPay() {
		return this.purchaseType.equals(MONTHLY_PAYMENT_ORIGINAL_AMOUNT);
	}


}
