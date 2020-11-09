package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.PurchaseRequest;
import com.uade.financialEntity.messages.responses.PurchaseResponse;
import com.uade.financialEntity.models.PurchaseItem.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private Integer monthPays;
	private Integer monthsPaid;

	//BUILDERS
	public Purchase(PurchaseRequest purchaseRequest) throws ParseException {
		this.description = purchaseRequest.getDescription();
		this.totalAmount = purchaseRequest.getTotalAmount();
		this.date = new SimpleDateFormat("dd/MM/yyyy").parse(purchaseRequest.getDate());
		this.monthPays = purchaseRequest.getMonthPays();
		this.monthsPaid = purchaseRequest.getMonthsPaid();
	}

	public Purchase() {
	}

	//METHODS
	public PurchaseResponse toDto() {
		return new PurchaseResponse(this);
	}

	public Integer calculateTotalAmount() {
		return purchaseItems.stream().mapToInt(PurchaseItem::calculateTotalAmount).sum();
	}

	public List<ProductType> getPurchaseProductTypes() {
		List<ProductType> types = new ArrayList<>();
		purchaseItems.forEach(purchaseItem -> types.add(purchaseItem.getTypeOfProduct()));
		return types;
	}

}
