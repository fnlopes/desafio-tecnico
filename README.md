# Desafio Técnico #

## O que foi realizado.

Foi criado uma API que irá receber uma lista de produtos, a partir desta lista, agrupa-los por EAN, depois por marca, também será realizada a ordenação de forma decrescente utilizando o estoque e depois a ordenação de forma crescente utilizando o campo preço.

## Como acessar

Para realizar o acesso, basta enviar um payload para o link: https://desafio-tecnico.herokuapp.com/produtos utilizando o método POST.
- Utilizar a Autenticação Básica para realizar a chamada.
- Usuário: admin
- Password: admin


## Modelo do Payload

Para a API funcionar corretamente, ela deve receber uma lista de produtos, onde todos os produtos devem conter:
- ed
- ean
- title
- brand
- price
- stock

Obs.: Todos os campos são obrigatórios.

## Realizando filtro ou ordenação

Para realizar filtros ou ordenar, bastar informar no momento da chamada.

### Exemplo da chamada

Para realizar um filtro
```
http://desafio-tecnico.herokuapp.com/produtos?filter=ean:123
```

Para realizar uma ordenação
```
http://desafio-tecnico.herokuapp.com/produtos?order_by=ean:asc
```

Para realizar uma filtro com ordenação
```
http://desafio-tecnico.herokuapp.com/produtos?order_by=stock:desc&filter=id:bb2r3s0
```

## Paginação

Para que o payload não fique tão grande, pode ser adicionado paginação ao filtro ou ordenação.
Por padrão, o resultado virá sempre na primeira página e com um limite de 10 resultados, parametros estes que podem ser alterados pelo usuário.

### Exemplo da Chamada.

Exemplo para alterar as configurações de paginação e navegar por elas.
```
http://desafio-tecnico.herokuapp.com/produtos?order_by=ean:asc&page=0&size=3
```

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

É esperado o retorno HTTP Status 200 (OK), com os produtos agrupados por EAN e dentro deste agrupamento, um novo agrupamento por Marca caso ocorra marcas repetidas. Na listagem de produtos são listados de forma decrescente o estoque e após são listados de forma crescente por preço.

### Modelo esperado para retorno do Payload
```
{
    "": {
        "redav": [
            {
                "id": "80092",
                "ean": "",
                "title": "Espada de fótons Nikana Azul",
                "brand": "redav",
                "price": 1799.9,
                "stock": 0
            }
        ]
    },
    "2059251400402": {
        "nikana": [
            {
                "id": "bb2r3s0",
                "ean": "2059251400402",
                "title": "Corredor POD 3000hp Nikana",
                "brand": "nikana",
                "price": 17832.9,
                "stock": 8
            },
            {
                "id": "bb2r3s02",
                "ean": "2059251400402",
                "title": "Corredor POD 3000hp Nikana",
                "brand": "nikana",
                "price": 17830.9,
                "stock": 7
            }
        ]
    },
    "7898054800492": {
        "nikana": [
            {
                "id": "u7042",
                "ean": "7898054800492",
                "title": "Espada de fótons Nikana Azul",
                "brand": "nikana",
                "price": 2199.9,
                "stock": 82
            }
        ]
    },
    "7898100848355": {
        "trek": [
            {
                "id": "321",
                "ean": "7898100848355",
                "title": "Cruzador espacial Nikana - 3000m - sem garantia",
                "brand": "trek",
                "price": 790300.9,
                "stock": 0
            }
        ],
        "ekul": [
            {
                "id": "7728uu",
                "ean": "7898100848355",
                "title": "Cruzador espacial Ekul - 3000m - sem garantia",
                "brand": "ekul",
                "price": 1300000,
                "stock": 1
            }
        ],
        "nikana": [
            {
                "id": "123",
                "ean": "7898100848355",
                "title": "Cruzador espacial Nikana - 3000m - sem garantia",
                "brand": "nikana",
                "price": 820900.9,
                "stock": 1
            }
        ]
    }
}
```

#  Próximos passos
- Implementação de testes automatizados (TDD), para garantir o correto funcionamento da API.
- Melhorar a forma de autenticação.
