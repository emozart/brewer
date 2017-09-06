package br.com.softbit.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.thymeleaf.util.StringUtils;

import br.com.softbit.brewer.model.Estado;

public class EstadoConverter implements Converter<String, Estado>{

	@Override
	public Estado convert(String codigo) {
		if(!StringUtils.isEmpty(codigo)){
			Estado estado = new Estado();
			estado.setCodigo(Long.valueOf(codigo));
			return estado;
		}
		return null;
	}

}
