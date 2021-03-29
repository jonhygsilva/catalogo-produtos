# Catálogo de Produtos
Uma API em Java e Spring Framework para gerenciamento de produtos.

A API deve criar, atualizar, deletar consultar todos os produtos, um produto específico ou um produto através de filtros. 

#### Pré requisitos:
* No mínimo Java 11
* Maven

## Testes
Para executar o teste unitário, o comando executado deve ser:

mvn test

### Execução
mvn spring-boot:run -Dspring.profiles.active=dev

Por default, a API está disponível no endereço http://localhost:9999/

#### Formato:

O formato esperado de um produto é o seguinte:
```
  {
    "id": "string",
    "name": "string",
    "description": "string",
    "price": 59.99
  }
```

Durante a criação e alteração, os campos name, description e price são obrigatórios. O campo price o valor deve ser positivo.

A API terá os seguintes endpoints:

#### POST /products: cria uma produto. 

Entrada:

``` 
{ 
    "name": "nome",
    "description": "descrição",
    "price": <preco> 
} 
```

Caso o produto seja cadastrado com sucesso deve ser retornado o codigo 200 com o body:
```

  {
    "id": "id gerado",
    "name": "nome",
    "description": "descrição",
    "price": <preco>
  }
```
Se houver alguma falha tratável de cadastro será retornado o código 400 e o body:
  ```
  {
    "status_code": integer,
    "message": "string"
  }
```

#### PUT /products/{id}: Atualiza um produto
Esse endpoint deve atualizar um produto baseado no {id} passado via path param. Antes de alterar, deve ser consultada a base de dados pelo id, se o produto não for localizado então devolver um HTTP 404 ao cliente. Se localizar o produto, então os campos name, description e price devem ser atualizados conforme recebidos no body da requisição.

Entrada:

```
  {
    "name": "nome",
    "description": "descrição",
    "price": <preco>
  }
  ```
Retorno:

```
  {
    "id": "id atualizado",
    "name": "nome",
    "description": "descrição",
    "price": <preco>
  }
  ```
Antes da atualização as mesmas regras de validação do POST /products são realizadas para garantir consistência, e, se ocorrer erro retornar no mesmo formato:

```
  {
    "status_code": integer,
    "message": "string"
  }
```



#### GET /products/\{id\}

Esse endpoint deve retornar o product localizado na base de dados com um HTTP 200. Em caso de não localização do produto, a API deve retornar um HTTP 404 indicando que o recurso não foi localizado, não há necessidade de retornar um JSON (response body) nesse caso.

Retorno:
```javascript
  {
    "id": "id buscado",
    "name": "nome",
    "description": "descrição",
    "price": <preco>
  }
```

#### GET /products

Nesse endpoint a API deve retornar a lista atual de todos os produtos com HTTP 200. Se não existir produtos, retornar uma lista vazia.

Retorno com produtos:
```javascript
[
  {
    "id": "id produto 1",
    "name": "nome",
    "description": "descrição",
    "price": <preco>
  },
  {
    "id": "id produto 2",
    "name": "nome",
    "description": "descrição",
    "price": <preco>
  }
]
```

Retorno vazio:
```javascript
[]
```

#### GET /products/search

Nesse endpoint a API deve retornar a lista atual de todos os produtos filtrados de acordo com query parameters passados na URL.

Os query parameters aceitos serão: q, min_price e max_price.

**Importante: nenhum deles deverão ser obrigatório na requisição**

Onde:

| Query param |  Ação de filtro     
|-------------|:---------------------------------------------------------------:|
| q           |  deverá bater o valor contra os campos *name* e *description*   |
| min_price   | deverá bater o valor ">=" contra o campo *price*                |
| max_price   | deverá bater o valor "<=" contra o campo *price*                |

**Exemplo: /products/search?min_price=10.5&max_price=50&q=superget**

Retorno com produtos filtrados/buscados:
```javascript
[
  {
    "id": "id produto 1",
    "name": "nome",
    "description": "descrição",
    "price": <preco>
  },
  {
    "id": "id produto 2",
    "name": "nome",
    "description": "descrição",
    "price": <preco>
  }
]

```

Retorno vazio:
```javascript
[]
```

#### DELETE /products/\{id\}

Esse endpoint deve deletar um registro de produto na base de dados. Caso encontre o produto filtrando pelo *id* então deve deletar e retornar um HTTP 200. Se o *id* passado não foi localizado deve retornar um HTTP 404


