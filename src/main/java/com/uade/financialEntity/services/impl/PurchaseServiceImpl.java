package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.custom.PurchaseCustomRequest;
import com.uade.financialEntity.messages.responses.PurchaseResponse;
import com.uade.financialEntity.models.*;
import com.uade.financialEntity.models.Purchase.PurchaseType;
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

import static com.uade.financialEntity.models.Purchase.PurchaseType.MONTHLY_PAYMENT_ORIGINAL_AMOUNT;
import static com.uade.financialEntity.models.Purchase.PurchaseType.ONE_PAY;
import static com.uade.financialEntity.models.Purchase.calculateTotalAmount;
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

			PurchaseType purchaseType = request.getMonthPays() <= 1 ? ONE_PAY : MONTHLY_PAYMENT_ORIGINAL_AMOUNT;
			Purchase purchase = new Purchase(purchaseType);
			purchase.setOriginalAmount(calculateTotalAmount(request.getAmount(), request.getMonthPays()));
			purchase.setMonthPays(request.getMonthPays());

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

			MonthResume monthResume = card.getLastMonthResumeOpen();

			monthResume.addPurchase(purchase);
			purchase.setMonthResume(monthResume);

			String description = String.format("con tarjeta %s del dia %s",
					card.getCardEntityName(), now);
			purchase.setDescription(String.format("Compra en %s ",
					shop.getName()) + description);


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

			if (purchase.getPurchaseType().equals(MONTHLY_PAYMENT_ORIGINAL_AMOUNT)) {
				Integer cuotasRemaining = card.getPurchasesRemainingMonthPay().stream()
						.filter(Purchase::itOriginalMonthlyPay)
						.mapToInt(Purchase::getTotalAmount)
						.sum();
				if (purchase.getTotalAmount() + cuotasRemaining > card.getCardEntity().getLimitMonthlyPay()) {
					return new MessageResponse(new Pair("error", "Error, Limite de compra en cuotas.")).getMapMessage();
				}

				Purchase purchaseMonthlyPayOne = purchase.cloneNew();
				monthResume.addPurchase(purchaseMonthlyPayOne);
				purchaseMonthlyPayOne.setMonthResume(monthResume);
				purchaseRepository.save(purchaseMonthlyPayOne);

				card.setAmountUntilMonthlyPayLimit(card.getCardEntity().getLimitMonthlyPay() - (purchase.getTotalAmount() + cuotasRemaining));

			} else {
				Integer amountFromMonth = card.getLastMonthResumeOpen().getAmountToPay();
				if (amountFromMonth + purchase.getTotalAmount() > card.getCardEntity().getLimitPay()) {
					return new MessageResponse(new Pair("error", "Error, Limite de compra.")).getMapMessage();
				}

				card.setAmountUntilOnePayLimit(card.getCardEntity().getLimitPay() - (purchase.getTotalAmount() + amountFromMonth));
			}
			purchaseRepository.save(purchase);

			monthResume.setAmountToPay(monthResume.calculateTotalAmount());

			shopPaymentRepository.save(shopPayment);
			monthResumeRepository.save(monthResume);
			cardRepository.save(card);

			return shopPayment.toDto();
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