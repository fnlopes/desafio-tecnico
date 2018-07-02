# Desafio Técnico #

## O que foi realizado.

Foi criado uma API que irá receber uma lista de produtos, a partir desta lista, agrupa-los por EAN, depois por marca, também é realizado a ordenação de forma decrescente utilizando o estoque e depois a ordenação de forma crescente por preço.

## Como acessar

Para realizar o acesso, basta enviar um payload para o link: https://desafio-tecnico.herokuapp.com/ utilizando o método POST.


## Modelo do Payload

Para a API funcionar corretamente, ela deve receber uma lista de produtos, onde todos os produtos devem conter:
- ed
- ean
- title
- brand
- price
- stock

### Modelo do Payload de Entrada
```
[
	{
		"id": "123",
		"ean": "7898100848355",
		"title": "Cruzador espacial Nikana - 3000m - sem garantia",
		"brand": "nikana",
		"price": 820900.90,
		"stock": 1
	},
	{
		"id": "u7042",
		"ean": "7898054800492",
		"title": "Espada de fótons Nikana Azul",
		"brand": "nikana",
		"price": 2199.90,
		"stock": 82

	},
	{
		"id": "bb2r3s0",
		"ean": "2059251400402",
		"title": "Corredor POD 3000hp Nikana",
		"brand": "nikana",
		"price": 17832.90,
		"stock": 8

	},
	{
		"id": "bb2r3s02",
		"ean": "2059251400402",
		"title": "Corredor POD 3000hp Nikana",
		"brand": "nikana",
		"price": 17830.90,
		"stock": 7

	},
	{
		"id": "321",
		"ean": "7898100848355",
		"title": "Cruzador espacial Nikana - 3000m - sem garantia",
		"brand": "trek",
		"price": 790300.90,
		"stock": 0

	},
	{
		"id": "80092",
		"ean": "",
		"title": "Espada de fótons Nikana Azul",
		"brand": "redav",
		"price": 1799.90,
		"stock": 0

	},
	{
		"id": "7728uu",
		"ean": "7898100848355",
		"title": "Cruzador espacial Ekul - 3000m - sem garantia",
		"brand": "ekul",
		"price": 1300000.00,
		"stock": 1
	}
]
```


## Retorno esperado

É esperado o retorno com os produtos agrupados por EAN e dentro deste agrupamento, um novo agrupamento por Marca caso ocorra marcas repetidas. Na listagem de produtos é listado de forma decrescente o estoque e após é listado de forma crescente por preço. 
