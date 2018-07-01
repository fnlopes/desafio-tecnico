package com.example.demo.resource;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Produto;

@RestController
@RequestMapping("produtos")
public class ProdutoResource {
		
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> salvar(@Valid @RequestBody List<Produto> produtos) {		
		List<Produto> filtro = produtos
				.stream()
				.filter(p -> p.getTitle().contains("Ekul"))
				.collect(Collectors.toList());
		
		//return new ResponseEntity<>(filtro, HttpStatus.OK);
		
		
		Map<String, Map<String, List<Produto>>> produtoEan = produtos
				.stream()
				.sorted(Comparator.comparing(Produto::getPrice))
				.sorted(Comparator.comparing(Produto::getStock).reversed())
				.collect(Collectors.groupingBy(Produto::getEan,
						Collectors.groupingBy(Produto::getBrand)));
		
		return new ResponseEntity<>(produtoEan, HttpStatus.OK);
	}
}
