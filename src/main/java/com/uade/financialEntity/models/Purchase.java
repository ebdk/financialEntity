package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.responses.PurchaseFullResponse;
import com.uade.financialEntity.messages.responses.PurchaseResponse;
import com.uade.financialEntity.models.PurchaseItem.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	@ManyToOne
	private Shop shop;

	@ManyToOne
	private MonthResume monthResume;

	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
	private List<PurchaseItem> purchaseItems;

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
		Purchase newPurchase = new Purchase();
		newPurchase.setDate(date);
		newPurchase.setTotalAmount(totalAmount);
		newPurchase.setMonthPays(monthPays);
		newPurchase.setMonthsPaid(monthsPaid);
		newPurchase.setDescription(description + String.format(" - Pago de la cuota %s / %s", monthsPaid, monthPays));
		newPurchase.setPurchaseType(MONTHLY_PAYMENT);
		return newPurchase;
	}


	public Purchase() {
		monthPays = 0;
		monthsPaid = 0;
	}

	//METHODS
	public PurchaseResponse toDto() {
		return new PurchaseResponse(this);
	}

	public PurchaseFullResponse toFullDto() {
		return new PurchaseFullResponse(this);
	}

	public Integer calculateTotalAmount() {
		return purchaseItems.stream().mapToInt(PurchaseItem::calculateTotalAmount).sum();
	}

	public List<ProductType> getPurchaseProductTypes() {
		List<ProductType> types = new ArrayList<>();
		purchaseItems.forEach(purchaseItem -> types.add(purchaseItem.getTypeOfProduct()));
		return types;
	}

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
