package com.uade.financialEntity.services.impl;

import com.uade.financialEntity.messages.MessageResponse;
import com.uade.financialEntity.messages.requests.ShopRequest;
import com.uade.financialEntity.messages.responses.ShopResponse;
import com.uade.financialEntity.models.Shop;
import com.uade.financialEntity.repositories.ShopDAO;
import com.uade.financialEntity.services.ShopService;
import com.uade.financialEntity.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDAO shopRepository;

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
		List<Shop> shops = shopRequests
				.stream()
				.map(ShopRequest::toEntity)
				.collect(toList());

		shopRepository.saveAll(shops);
		return shops.stream().map(Shop::toDto).collect(toList());
	}

}