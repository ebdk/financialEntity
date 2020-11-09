package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.PurchaseItemRequest;
import com.uade.financialEntity.messages.requests.custom.PurchaseCustomRequest;
import com.uade.financialEntity.messages.responses.PurchaseResponse;
import com.uade.financialEntity.models.*;
import com.uade.financialEntity.repositories.*;
import com.uade.financialEntity.services.PurchaseService;
import com.uade.financialEntity.utils.MathUtils;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.uade.financialEntity.models.Purchase.PurchaseType.ORIGINAL;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseDAO purchaseRepository;

	@Autowired
	private PurchaseItemDAO purchaseItemRepository;

	@Autowired
	private ShopDAO shopRepository;

	@Autowired
	private CardDAO cardRepository;

	@Autowired
	private MonthResumeDAO monthResumeRepository;

	@Override
	public List<PurchaseResponse> getAllPurchases() {
		List<Purchase> shops = purchaseRepository.findAll();
		return shops.stream().map(Purchase::toDto).collect(Collectors.toList());
	}


	@Override
	public Object get(Long id) {
		Optional<Purchase> purchase = purchaseRepository.findById(id);
		return purchase.isPresent() ?
				new PurchaseResponse(purchase.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrado el negocio con id " + id)).getMapMessage();
	}

	@Override
	public Object purchase(PurchaseCustomRequest request) {
		Optional<Shop> optionalShop = shopRepository.findById(request.getShopId());
		Optional<Card> optionalCard = cardRepository.findById(request.getCardId());

		if (optionalShop.isPresent() && optionalCard.isPresent()) {
			Card card = optionalCard.get();
			Shop shop = optionalShop.get();

			Purchase purchase = new Purchase();
			purchase.setMonthPays(request.getMonthPays());
			purchase.setPurchaseType(ORIGINAL);

			List<PurchaseItem> purchaseItems = request.getPurchaseItems()
					.stream()
					.map(PurchaseItemRequest::toEntity).collect(Collectors.toList());
			purchase.setPurchaseItems(purchaseItems);
			purchase.setOriginalAmount(purchase.calculateTotalAmount());

			Date now = new Date();
			purchase.setDate(now);
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
			String date = simpleDateformat.format(now);

			ShopPromotion shopPromotion = shop.getPromotion(card.getCardEntityName(), purchase.getPurchaseProductTypes(), date);
			Integer amount;
			if (shopPromotion != null) {
				purchase.setDiscount(MathUtils.getPercentage(purchase.getOriginalAmount(), shopPromotion.getPercentageValue()));
				purchase.setShopPromotion(shopPromotion);
				amount = purchase.getOriginalAmount() - purchase.getDiscount();
				purchase.setTotalAmount(purchase.getOriginalAmount() - purchase.getDiscount());
			} else {
				amount = purchase.getOriginalAmount();
			}
			purchase.setTotalAmount(amount);

			MonthResume monthResume;
			if (card.getLastMonthResumeOpen() != null) {
				monthResume = card.getLastMonthResumeOpen();
			} else {
				monthResume = new MonthResume(1);
				monthResume.setCard(card);
			}
			monthResume.addPurchase(purchase);
			monthResume.setAmountToPay(monthResume.calculateTotalAmount());

			purchaseItemRepository.saveAll(purchaseItems);
			purchaseRepository.save(purchase);
			monthResumeRepository.save(monthResume);

			return purchase.toDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrado la tarjeta o negocio.")).getMapMessage();
		}
	}

}