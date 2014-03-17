package models.validators;

import models.exceptions.LimiteDeCreditosException;

public class ValidadorMin implements ValidadorDePeriodo{

	public static final int MINIMO_DE_CREDITOS = 12;

	@Override
	public boolean valida(int creditos)	throws LimiteDeCreditosException {
		if (creditos < MINIMO_DE_CREDITOS) {
			throw new LimiteDeCreditosException("Créditos insuficientes");
		}
		return true;
	}

}
