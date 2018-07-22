package com.example.demo.resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Produto;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource  {	
	// Função para receber o payload e fazer o agrupamento e ordenação padrão.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> tratarProdutos(@RequestBody List<Produto> prod) {
		
		System.out.println("Bloco 01");
		
		// Bloco para validação do payload recebido.
		ArrayList<String> erros = new ArrayList<>();
		for (Produto produto : prod) {
			String error = isValid(produto);
			if ( !error.isEmpty() )
				erros.add("Entrada da Posição: " + prod.indexOf(produto) + " com o erro: " + error);
		}
		if ( !erros.isEmpty() )
			return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
		
		// Função que realiza a ordenação e agrupamento.		
		Map<String, Map<String, List<Produto>>> produtos = prod
				.stream()
				.sorted(Comparator.comparing(Produto::getPrice))
				.sorted(Comparator.comparing(Produto::getStock).reversed())
				.collect(Collectors.groupingBy(Produto::getEan,
						Collectors.groupingBy(Produto::getBrand)));
		
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}
	
	
	// Função para receber o payload e fazer a ordenação e filtro baseado
	// no que foi passado como parametros
	@RequestMapping(method = RequestMethod.POST, params = {"filter", "order_by"})
	public ResponseEntity<?> filtrarOrdenarProdutos(@RequestBody List<Produto> prod,
			@RequestParam (value = "filter", defaultValue = "") String filter,
			@RequestParam (value = "order_by", defaultValue = "") String order,
			@RequestParam (value = "page", defaultValue = "0") int page,
			@RequestParam (value = "size", defaultValue = "10") int size) {
			
		// Bloco para validação do payload recebido.
		ArrayList<String> erros = new ArrayList<>();
		for (Produto produto : prod) {
			String error = isValid(produto);
			if ( !error.isEmpty() )
				erros.add("Entrada da Posição: " + prod.indexOf(produto) + " com o erro: " + error);
		}
		if ( !erros.isEmpty() )
			return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
		
		String filtro [];
		String ordenacao [];
		List<Produto> produtos = prod;
		
		
		try {
			filtro = filter.split(":");
			ordenacao = order.split(":");
						
			// Bloco para verificar qual o filtro realizar.
			if (filtro[0].toLowerCase().equals("id"))
				produtos = prod.stream().filter(p -> p.getId().equals(filtro[1])).collect(Collectors.toList());
			else
				if (filtro[0].toLowerCase().equals("ean"))
					produtos = prod.stream().filter(p -> p.getEan().equals(filtro[1])).collect(Collectors.toList());
				else
					if (filtro[0].toLowerCase().equals("title"))
						produtos = prod.stream().filter(p -> p.getTitle().equals(filtro[1])).collect(Collectors.toList());
					else
						if (filtro[0].toLowerCase().equals("brand"))
							produtos = prod.stream().filter(p -> p.getBrand().equals(filtro[1])).collect(Collectors.toList());
						else
							if (filtro[0].toLowerCase().equals("price"))
								produtos = prod.stream().filter(p -> p.getPrice() == Float.parseFloat(filtro[1])).collect(Collectors.toList());
							else
								if (filtro[0].toLowerCase().equals("stock"))
									produtos = prod.stream().filter(p -> p.getStock() == Integer.parseInt(filtro[1])).collect(Collectors.toList());
								else
									return new ResponseEntity<>("Campo para realizar o filtro invalido, favor verificar", HttpStatus.BAD_REQUEST);
			
			
			// Bloco para verificar qual o campo realizar a ordenação e qual a orientação
			if (ordenacao[0].toLowerCase().equals("id"))
				if (ordenacao[1].toLowerCase().equals("asc"))
					produtos = produtos.stream().sorted(Comparator.comparing(Produto::getId)).collect(Collectors.toList());
				else
					produtos = produtos.stream().sorted(Comparator.comparing(Produto::getId).reversed()).collect(Collectors.toList());
			else
				if (ordenacao[0].toLowerCase().equals("ean"))
					if (ordenacao[1].toLowerCase().equals("asc"))
						produtos = produtos.stream().sorted(Comparator.comparing(Produto::getEan)).collect(Collectors.toList());
					else
						produtos = produtos.stream().sorted(Comparator.comparing(Produto::getEan).reversed()).collect(Collectors.toList());
				else
					if (ordenacao[0].toLowerCase().equals("title"))
						if (ordenacao[1].toLowerCase().equals("asc"))
							produtos = produtos.stream().sorted(Comparator.comparing(Produto::getTitle)).collect(Collectors.toList());
						else
							produtos = produtos.stream().sorted(Comparator.comparing(Produto::getTitle).reversed()).collect(Collectors.toList());
					else
						if (ordenacao[0].toLowerCase().equals("brand"))
							if (ordenacao[1].toLowerCase().equals("asc"))
								produtos = produtos.stream().sorted(Comparator.comparing(Produto::getBrand)).collect(Collectors.toList());
							else
								produtos = produtos.stream().sorted(Comparator.comparing(Produto::getBrand).reversed()).collect(Collectors.toList());
						else
							if (ordenacao[0].toLowerCase().equals("price"))
								if (ordenacao[1].toLowerCase().equals("asc"))
									produtos = produtos.stream().sorted(Comparator.comparing(Produto::getPrice)).collect(Collectors.toList());
								else
									produtos = produtos.stream().sorted(Comparator.comparing(Produto::getPrice).reversed()).collect(Collectors.toList());
							else
								if (ordenacao[0].toLowerCase().equals("stock"))
									if (ordenacao[1].toLowerCase().equals("asc"))
										produtos = produtos.stream().sorted(Comparator.comparing(Produto::getStock)).collect(Collectors.toList());
									else
										produtos = produtos.stream().sorted(Comparator.comparing(Produto::getStock).reversed()).collect(Collectors.toList());
								else
									return new ResponseEntity<>("Campo para realizar a ordenacao invalido, favor verificar", HttpStatus.BAD_REQUEST);
	
			
		}
		catch (Exception e) {
			return new ResponseEntity<>("O filto ou ordenação não está correto.", HttpStatus.BAD_REQUEST);
		}
		
		produtos = paginacao(produtos, page, size);		
		
		
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}
	
	
	// Função para receber o payload e fazer o filtro baseado
	// no que foi passado como parametros
	@RequestMapping(method = RequestMethod.POST, params = "filter")
	public ResponseEntity<?> filtrarProdutos(@RequestBody List<Produto> prod,
			@RequestParam (value = "filter", defaultValue = "") String filter,
			@RequestParam (value = "page", defaultValue = "0") int page,
			@RequestParam (value = "size", defaultValue = "10") int size) {
			
		// Bloco para validação do payload recebido.
		ArrayList<String> erros = new ArrayList<>();
		for (Produto produto : prod) {
			String error = isValid(produto);
			if ( !error.isEmpty() )
				erros.add("Entrada da Posição: " + prod.indexOf(produto) + " com o erro: " + error);
		}
		if ( !erros.isEmpty() )
			return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
		
		String filtro [];
		List<Produto> produtos = prod;
		
		
		try {
			filtro = filter.split(":");
						
			// Bloco para verificar qual o filtro realizar.
			if (filtro[0].toLowerCase().equals("id"))
				produtos = prod.stream().filter(p -> p.getId().equals(filtro[1])).collect(Collectors.toList());
			else
				if (filtro[0].toLowerCase().equals("ean"))
					produtos = prod.stream().filter(p -> p.getEan().equals(filtro[1])).collect(Collectors.toList());
				else
					if (filtro[0].toLowerCase().equals("title"))
						produtos = prod.stream().filter(p -> p.getTitle().equals(filtro[1])).collect(Collectors.toList());
					else
						if (filtro[0].toLowerCase().equals("brand"))
							produtos = prod.stream().filter(p -> p.getBrand().equals(filtro[1])).collect(Collectors.toList());
						else
							if (filtro[0].toLowerCase().equals("price"))
								produtos = prod.stream().filter(p -> p.getPrice() == Float.parseFloat(filtro[1])).collect(Collectors.toList());
							else
								if (filtro[0].toLowerCase().equals("stock"))
									produtos = prod.stream().filter(p -> p.getStock() == Integer.parseInt(filtro[1])).collect(Collectors.toList());
								else
									return new ResponseEntity<>("Campo para realizar o filtro invalido, favor verificar", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>("O filto não está correto.", HttpStatus.BAD_REQUEST);
		}
		
		produtos = paginacao(produtos, page, size);		
		
		
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}
		
	
	// Função para receber o payload e fazer a ordenação baseado
	// no que foi passado como parametros
	@RequestMapping(method = RequestMethod.POST, params = "order_by")
	public ResponseEntity<?> OrdenarProdutos(@RequestBody List<Produto> prod,
			@RequestParam (value = "order_by", defaultValue = "") String order,
			@RequestParam (value = "page", defaultValue = "0") int page,
			@RequestParam (value = "size", defaultValue = "10") int size) {
			
		// Bloco para validação do payload recebido.
		ArrayList<String> erros = new ArrayList<>();
		for (Produto produto : prod) {
			String error = isValid(produto);
			if ( !error.isEmpty() )
				erros.add("Entrada da Posição: " + prod.indexOf(produto) + " com o erro: " + error);
		}
		if ( !erros.isEmpty() )
			return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
		
		String ordenacao [];
		List<Produto> produtos = prod;
		
		
		try {
			ordenacao = order.split(":");
					
			// Bloco para verificar qual o campo realizar a ordenação e qual a orientação
			if (ordenacao[0].toLowerCase().equals("id"))
				if (ordenacao[1].toLowerCase().equals("asc"))
					produtos = produtos.stream().sorted(Comparator.comparing(Produto::getId)).collect(Collectors.toList());
				else
					produtos = produtos.stream().sorted(Comparator.comparing(Produto::getId).reversed()).collect(Collectors.toList());
			else
				if (ordenacao[0].toLowerCase().equals("ean"))
					if (ordenacao[1].toLowerCase().equals("asc"))
						produtos = produtos.stream().sorted(Comparator.comparing(Produto::getEan)).collect(Collectors.toList());
					else
						produtos = produtos.stream().sorted(Comparator.comparing(Produto::getEan).reversed()).collect(Collectors.toList());
				else
					if (ordenacao[0].toLowerCase().equals("title"))
						if (ordenacao[1].toLowerCase().equals("asc"))
							produtos = produtos.stream().sorted(Comparator.comparing(Produto::getTitle)).collect(Collectors.toList());
						else
							produtos = produtos.stream().sorted(Comparator.comparing(Produto::getTitle).reversed()).collect(Collectors.toList());
					else
						if (ordenacao[0].toLowerCase().equals("brand"))
							if (ordenacao[1].toLowerCase().equals("asc"))
								produtos = produtos.stream().sorted(Comparator.comparing(Produto::getBrand)).collect(Collectors.toList());
							else
								produtos = produtos.stream().sorted(Comparator.comparing(Produto::getBrand).reversed()).collect(Collectors.toList());
						else
							if (ordenacao[0].toLowerCase().equals("price"))
								if (ordenacao[1].toLowerCase().equals("asc"))
									produtos = produtos.stream().sorted(Comparator.comparing(Produto::getPrice)).collect(Collectors.toList());
								else
									produtos = produtos.stream().sorted(Comparator.comparing(Produto::getPrice).reversed()).collect(Collectors.toList());
							else
								if (ordenacao[0].toLowerCase().equals("stock"))
									if (ordenacao[1].toLowerCase().equals("asc"))
										produtos = produtos.stream().sorted(Comparator.comparing(Produto::getStock)).collect(Collectors.toList());
									else
										produtos = produtos.stream().sorted(Comparator.comparing(Produto::getStock).reversed()).collect(Collectors.toList());
								else
									return new ResponseEntity<>("Campo para realizar a ordenacao invalido, favor verificar", HttpStatus.BAD_REQUEST);
	
			
		}
		catch (Exception e) {
			return new ResponseEntity<>("O filto ou ordenação não está correto.", HttpStatus.BAD_REQUEST);
		}
		
		produtos = paginacao(produtos, page, size);		
		
		
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}
	
	
	// Função para realizar a valicação dos campos.
	private String isValid(Produto prod) {
		String erros = "";
		
		if ( prod.getBrand() == null || prod.getBrand().isEmpty() )
			erros += "O campo Brand não pode ser nulo ou vazio. ";
		if ( prod.getEan() == null || prod.getEan().isEmpty() )
			erros += "O campo Ean não pode ser nulo ou vazio. ";
		if ( prod.getId() == null || prod.getId().isEmpty() )
			erros += "O campo Id não pode ser nulo ou vazio. ";
		if ( prod.getPrice() <= 0 )
			erros += "O campo Price não pode ser 0 ou menor. ";
		if ( prod.getStock() < 0 )
			erros += "O campo Stock não pode ser menor que 0. ";
		if ( prod.getTitle() == null || prod.getTitle().isEmpty() )
			erros += "O campo Title não pode ser nulo ou vazio. ";
		
		return (erros);
	}
	
	
	// Função para realizar a paginação.
	private List<Produto> paginacao(List<Produto> produtos, int page, int size) {
		
		int originalSize = produtos.size();
		int start = Math.min(produtos.size(), Math.abs(page * size));
		produtos.subList(0, start).clear();;
		
		int tamanho = produtos.size();
		int end = Math.min(size, tamanho);
		produtos.subList(end, tamanho).clear();
		
		boolean hasNext = (end < tamanho);
		boolean hasPrevious = (start > 0);
		
		
		System.out.println("Tamanho Original: " + originalSize);
		System.out.println("Inicio da Página: " + start);
		System.out.println("Tamanho: " + tamanho);
		System.out.println("Final da Página: " + end);
		System.out.println("Tem Proximo: " + hasNext);
		System.out.println("Tem Anterior: " + hasPrevious);
		
		
		return produtos;
	}
		
}
