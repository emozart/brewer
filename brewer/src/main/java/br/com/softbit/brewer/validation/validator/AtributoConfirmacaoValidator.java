package br.com.softbit.brewer.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import br.com.softbit.brewer.validation.AtributoConfirmacao;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object>{

	private String atributoPrincipal;
	private String atributoConfirmacao;
	
	@Override
	public void initialize(AtributoConfirmacao constraintAnnotation) {
		this.atributoPrincipal = constraintAnnotation.atributoPrincipal();
		this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
		
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valido = false;
		try {
			Object valorAtributoPrincipal = BeanUtils.getProperty(value, this.atributoPrincipal);
			Object valorAtributoConfirmacao = BeanUtils.getProperty(value, this.atributoConfirmacao);
			
			valido = ambasSaoNull(valorAtributoPrincipal, valorAtributoConfirmacao) || ambasSaoIguais(valorAtributoPrincipal, valorAtributoConfirmacao);
		}catch(Exception e) {
			throw new RuntimeException("Erro recuperando valores dos atributos", e);
		}
		
		if(!valido) {
			context.disableDefaultConstraintViolation();
			String message = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(message);
			violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
		}
		
		return valido;
	}

	private boolean ambasSaoIguais(Object valorAtributoPrincipal, Object valorAtributoConfirmacao) {
		return valorAtributoPrincipal != null && valorAtributoPrincipal.equals(valorAtributoConfirmacao);
	}

	private boolean ambasSaoNull(Object valorAtributoPrincipal, Object valorAtributoConfirmacao) {
		return valorAtributoPrincipal == null && valorAtributoConfirmacao == null;
	}

}
