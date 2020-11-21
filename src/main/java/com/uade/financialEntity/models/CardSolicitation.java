package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.responses.CardSolicitationResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "CardSolicitation")
@Table(name = "card_solicitation")
@Getter
@Setter
public class CardSolicitation {

	//ATTRIBUTES
	@Id
	@Column(name = "CARD_SOLICITATION_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Customer customer;

	@ManyToOne
	private CardEntity cardEntity;

	private Boolean accepted;
	private Boolean open;
	private Date dateCreated;
	private Date dateFinalized;
	//private Boolean confirmedBankruptedByBank;
	//private Boolean confirmedBankCheck;

	//BUILDERS
	public CardSolicitation(Customer customer, CardEntity cardEntity) {
		this.cardEntity = cardEntity;
		this.customer = customer;
		this.accepted = false;
		this.open = true;

		this.dateCreated = new Date();
	}

	public CardSolicitation() {
		this.accepted = false;
		this.open = true;
	}

	//METHODS
	public CardSolicitationResponse toDto() {
		return new CardSolicitationResponse(this);
	}

	public boolean sameDni(Long dni) {
		return dni.equals(this.customer.getDni());
	}


}
