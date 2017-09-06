package br.com.softbit.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.thymeleaf.util.StringUtils;

import br.com.softbit.brewer.model.Grupo;

public class GrupoConverter implements Converter<String, Grupo>{

	@Override
	public Grupo convert(String codigo) {
		if(!StringUtils.isEmpty(codigo)){
			Grupo grupo = new Grupo();
			grupo.setCodigo(Long.valueOf(codigo));
			return grupo;
		}
		return null;
	}

}
