package br.com.softbit.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softbit.brewer.model.Cliente;
import br.com.softbit.brewer.repository.ClientesRepository;
import br.com.softbit.brewer.service.exception.CpfCnpjClienteJaCadastradoException;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClientesRepository clientesRepository;

	@Transactional
	public void salvar(Cliente cliente){
		
		Optional<Cliente> clienteExistente = clientesRepository.findByCpfOuCnpj(cliente.getCpfCnpJSemFormatacao());
		if(clienteExistente.isPresent()) {
			throw new CpfCnpjClienteJaCadastradoException("CPF ou CNPJ já cadastrado.");
		}
		clientesRepository.save(cliente);
	}
}
