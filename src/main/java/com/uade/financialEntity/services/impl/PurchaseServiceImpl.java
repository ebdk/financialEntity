package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
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
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.uade.financialEntity.models.Purchase.PurchaseType.ORIGINAL;
import static com.uade.financialEntity.models.ShopPayment.PaymentType.DAILY;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseDAO purchaseRepository;

	/*
	@Autowired
	private PurchaseItemDAO purchaseItemRepository;
	 */

	@Autowired
	private ShopDAO shopRepository;

	@Autowired
	private ShopPaymentDAO shopPaymentRepository;

	@Autowired
	private CardDAO cardRepository;

	@Autowired
	private MonthResumeDAO monthResumeRepository;

	@Autowired
	private SystemCacheDAO systemCacheRepository;

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
		Optional<Shop> optionalShop = shopRepository.findByName(request.getShopName());
		List<Card> cards = cardRepository.findAll()
				.stream()
				.filter(card -> card.isCard(request.getCreditNumber(), request.getSecretCode()))
				.collect(Collectors.toList());

		SystemCache systemCache = systemCacheRepository.findAll().get(0);
		Integer monthNumber = systemCache.getMonthNumber();

		if (optionalShop.isPresent() && !cards.isEmpty()) {
			Card card = cards.get(0);
			Shop shop = optionalShop.get();

			Purchase purchase = new Purchase(ORIGINAL);
			purchase.setMonthPays(request.getMonthPays());
			purchase.setMonthsPaid(1);

			/*
			List<PurchaseItem> purchaseItems = request.getPurchaseItems()
					.stream()
					.map(PurchaseItemRequest::toEntity).collect(Collectors.toList());
			purchaseItems.forEach(purchaseItem -> purchaseItem.setPurchase(purchase));
			purchase.setPurchaseItems(purchaseItems);
			 */

			purchase.setOriginalAmount(purchase.calculateTotalAmount(request.getAmount(), request.getMonthPays()));

			Date now = new Date();
			purchase.setDate(now);
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", Locale.US); // the day of the week spelled out completely
			String date = simpleDateformat.format(now);

			ShopPromotion shopPromotion = shop.getPromotion(card.getCardEntityName(), request.getProductWithDiscount(), date);
			Integer amount;
			if (shopPromotion != null) {
				purchase.setDiscount(MathUtils.getPercentage(purchase.getOriginalAmount(), shopPromotion.getPercentageValue()));
				purchase.setShopPromotion(shopPromotion);
				amount = purchase.getOriginalAmount() - purchase.getDiscount();
			} else {
				purchase.setDiscount(0);
				amount = purchase.getOriginalAmount();
			}
			purchase.setTotalAmount(amount);

			MonthResume monthResume;
			monthResume = card.getLastMonthResumeOpen();

			monthResume.addPurchase(purchase);
			purchase.setMonthResume(monthResume);
			//purchase.setShop(shop);

			String description = String.format("con tarjeta %s del dia %s",
					card.getCardEntityName(), now);
			purchase.setDescription(String.format("Compra del negocio %s ",
					shop.getName()) + description);
			monthResume.setAmountToPay(monthResume.calculateTotalAmount());

			ShopPayment shopPayment = new ShopPayment();
			shopPayment.setShop(shop);
			shopPayment.setOriginalAmount(purchase.getTotalAmount());
			shopPayment.setComission(MathUtils.getPercentage(shopPayment.getOriginalAmount(), 15));
			shopPayment.setTotalAmount(shopPayment.getOriginalAmount() - shopPayment.getComission());
			shopPayment.setDate(now);
			shopPayment.setPaymentType(DAILY);
			shopPayment.setDescription("Venta " + description);
			shopPayment.setMonth(monthNumber);
			shopPayment.setPurchase(purchase);

			shopPaymentRepository.save(shopPayment);
			purchaseRepository.save(purchase);
			monthResumeRepository.save(monthResume);
			//purchaseItemRepository.saveAll(purchaseItems);

			return purchase.toFullDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrado la tarjeta o negocio.")).getMapMessage();
		}
	}

	@Override
	public Object delete(Long id) {
		purchaseRepository.deleteById(id);
		return new MessageResponse("Removed Succesfuly").getMapMessage();
	}

	/*
	@Override
	public Object deleteItem(Long id) {
		purchaseItemRepository.deleteById(id);
		return new MessageResponse("Removed Succesfuly").getMapMessage();
	}
	 */

}