package com.uade.financialEntity.models;

import com.uade.financialEntity.messages.requests.MonthResumeRequest;
import com.uade.financialEntity.messages.responses.MonthResumeResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Month_Resume")
@Table(name = "month_resume")
@Getter
@Setter
public class MonthResume {

	//ATTRIBUTES
	@Id
	@Column(name = "MONTH_RESUME_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer monthNumber;
	private Integer amountToPay;
	private Boolean closed;
	private Integer amountPaid;

	//BUILDERS
	public MonthResume(MonthResumeRequest monthResumeRequest) {
		this.monthNumber = monthResumeRequest.getMonthNumber();
		this.amountToPay = monthResumeRequest.getAmountToPay();
		this.closed = monthResumeRequest.getClosed();
		this.amountPaid = monthResumeRequest.getAmountPaid();
	}

	public MonthResume() {
	}

	//METHODS
	public MonthResumeResponse toDto() {
		return new MonthResumeResponse(this);
	}

}
