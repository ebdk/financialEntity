package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.responses.ShopResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Shop")
@Table(name = "shop")
@Getter
@Setter
public class Shop {

	//ATTRIBUTES
	@Id
	@Column(name = "SHOP_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
	private List<Purchase> purchases;

	@OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
	private List<ShopPromotion> shopPromotions;

	private String name;

	//BUILDERS
	public Shop(ShopRequest shopRequest) {
		this.name = shopRequest.getName();
	}

	public Shop() {
	}

	//METHODS
	public ShopResponse toDto() {
		return new ShopResponse(this);
	}

}
