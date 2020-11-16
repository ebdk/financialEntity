package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.responses.ShopPaymentResponse;
import com.uade.financialEntity.messages.responses.ShopResponse;
import com.uade.financialEntity.models.Shop;
import com.uade.financialEntity.models.ShopPayment;
import com.uade.financialEntity.models.User;
import com.uade.financialEntity.repositories.ShopDAO;
import com.uade.financialEntity.repositories.ShopPaymentDAO;
import com.uade.financialEntity.repositories.UserDAO;
import com.uade.financialEntity.services.ShopService;
import com.uade.financialEntity.utils.Pair;
import com.uade.financialEntity.utils.PairObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.uade.financialEntity.models.ShopPayment.DateType.END_MONTH;
import static java.util.stream.Collectors.toList;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDAO shopRepository;

	@Autowired
	private ShopPaymentDAO shopPaymentRepository;

	@Autowired
	private UserDAO userRepository;

	@Override
	public List<ShopResponse> getAllShops() {
		List<Shop> shops = shopRepository.findAll();
		return shops.stream().map(Shop::toDto).collect(Collectors.toList());
	}


	@Override
	public Object get(Long id) {
		Optional<Shop> shop = shopRepository.findById(id);
		return shop.isPresent() ?
				new ShopResponse(shop.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrado el negocio con id " + id)).getMapMessage();
	}

	@Override
	public Object getByName(String name) {
		Optional<Shop> shop = shopRepository.findByName(name);
		return shop.isPresent() ?
				new ShopResponse(shop.get()) :
				new MessageResponse(new Pair("error", "Error, no pudo ser encontrado el negocio con nombre " + name)).getMapMessage();
	}

	@Override
	public Object createShops(List<ShopRequest> shopRequests) {
		List<Shop> shops = new ArrayList<>();
		shopRequests.forEach(shopRequest -> {
			Shop customer = new Shop(shopRequest);

			Long idUser = shopRequest.getUserId();
			Optional<User> optionalUser = userRepository.findById(idUser);
			optionalUser.ifPresent(customer::setUser);

			shops.add(customer);
		});

		shopRepository.saveAll(shops);
		return shops.stream().map(Shop::toDto).collect(toList());
	}

	@Override
	public Object delete(Long id) {
		shopRepository.deleteById(id);
		return new MessageResponse("Removed Succesfuly").getMapMessage();
	}

	@Override
	public Object modify(Long id, ShopRequest request) {
		Optional<Shop> optionalShop = shopRepository.findById(id);
		if (optionalShop.isPresent()) {
			Shop shop = optionalShop.get();
			shop.modify(request);
			shopRepository.save(shop);
			return shop.toDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada el negocio con id " + id)).getMapMessage();
		}
	}

	@Override
	public Object existsName(String name) {
		Shop shop = new Shop(name);
		boolean exists = shopRepository.exists(Example.of(shop));
		return new MessageResponse(new PairObject("exists", exists)).getMapObject();
	}

	@Override
	public Object closeMonth(Long id) {
		Optional<Shop> optionalShop = shopRepository.findById(id);

		if (optionalShop.isPresent()) {
			return closeMonth(optionalShop.get(), 1).toDto();
		} else {
			return new MessageResponse(new Pair("error", "Error, no pudo ser encontrada la tarjeta con numero " + id)).getMapMessage();
		}
	}

	@Override
	public Object closeMonths() {
		List<Shop> shops = shopRepository.findAll();

		List<ShopPayment> shopPayments = shops
				.stream()
				.map(shop -> closeMonth(shop, 1))
				.collect(toList());

		return shopPayments
				.stream()
				.map(ShopPayment::toDto)
				.collect(toList());
	}

	@Override
	public List<ShopPaymentResponse> closeMonths(Integer month) {

		List<Shop> shops = shopRepository.findAll();

		List<ShopPayment> shopPayments = shops
				.stream()
				.map(shop -> closeMonth(shop, month))
				.collect(toList());

		return shopPayments
				.stream()
				.map(ShopPayment::toDto)
				.collect(toList());
	}

	private ShopPayment closeMonth(Shop shop, Integer month) {

		Date now = new Date();

		List<ShopPayment> shopPayments = shop.getPurchases()
				.stream()
				.filter(shopPayment -> shopPayment.isDailyOfMonth(month))
				.collect(toList());


		ShopPayment endMonthPurchase = new ShopPayment();
		endMonthPurchase.setMonth(month);
		endMonthPurchase.setDescription("Resume of Month " + month);
		endMonthPurchase.setDateType(END_MONTH);
		endMonthPurchase.setDate(now);

		endMonthPurchase.setOriginalAmount(shopPayments
				.stream()
				.mapToInt(ShopPayment::getOriginalAmount)
				.sum());
		endMonthPurchase.setComission(shopPayments
				.stream()
				.mapToInt(ShopPayment::getComission)
				.sum());
		endMonthPurchase.setTotalAmount(shopPayments
				.stream()
				.mapToInt(ShopPayment::getTotalAmount)
				.sum());

		shopPaymentRepository.save(endMonthPurchase);
		return endMonthPurchase;
	}

}