# Cep-Correios
Biblioteca desenvolvida em Java para pesquisa de Ceps diretamente da Base dos Correios.

## Como funciona
A biblioteca consulta a Base de Dados dos Correios pesquisando pelo Cep informado. Para que a pesquisa seja mais rápida é recomendável que sejam informados todos os "oito" digitos do CEP. O retorno sempre será uma lista.

## Instalando via Maven
Insira essa configuração dentro do seu arquivo **pom.xml**
```xml
<dependency>
  <groupId>dev.pauloribeiro.cepcorreios</groupId>
  <artifactId>cepcorreios</artifactId>
  <version>1.0.5</version>
</dependency>
```

## Exemplo
```java
package demo;

import java.util.List;

import dev.pauloribeiro.cepcorreios.CepCorreio;
import dev.pauloribeiro.cepcorreios.CorreiosApiFactory;

public class ExemploCepCorreios {

	public static void main(String[] args) throws InterruptedException {
		
		List<CepCorreio> ceps = CorreiosApiFactory.get().getCep("01046");
		
		System.out.println(ceps.get(0).toString());
		
	}
}
```
### Resultado
```html
CepCorreio [cep=01046010, estado=SP, cidade=São Paulo, bairro=República, logradouro=Avenida Ipiranga, complemento=- até 399/400, nomeUnidade=]
```
