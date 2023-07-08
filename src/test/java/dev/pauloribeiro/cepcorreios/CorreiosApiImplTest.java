package dev.pauloribeiro.cepcorreios;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class CorreiosApiImplTest {
	
	private final CorreiosApiImpl correiosApi = new CorreiosApiImpl();
	
	@Test
	void testCepCorreios() throws InterruptedException {
		
		CepCorreio dtoResponse = new CepCorreio("01046010","SP","São Paulo","República","Avenida Ipiranga","- até 399/400","","");
		
		List<CepCorreio> response = correiosApi.getCep("01046010");
		
		assertThat(response.get(0).getCep()).isEqualTo(dtoResponse.getCep());
		assertThat(response.get(0).getBairro()).isEqualTo(dtoResponse.getBairro());
		assertThat(response.get(0).getCidade()).isEqualTo(dtoResponse.getCidade());
		assertThat(response.get(0).getComplemento()).isEqualTo(dtoResponse.getComplemento());
		assertThat(response.get(0).getEstado()).isEqualTo(dtoResponse.getEstado());
		
	}

}
