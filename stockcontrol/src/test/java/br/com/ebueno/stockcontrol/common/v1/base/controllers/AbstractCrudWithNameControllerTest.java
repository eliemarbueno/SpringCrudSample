package br.com.ebueno.stockcontrol.common.v1.base.controllers;

public class AbstractCrudWithNameControllerTest<E, CreateDTO, UpdateDTO, ResponseDTO, ID>
		extends AbstractCrudControllerTest<E, CreateDTO, UpdateDTO, ResponseDTO, ID> {

	protected String filterNameContaining = "tem";
	protected String filterNameContainingNotFound = "qwertyuiopasdfghjklçzxcvbnm";
	protected String filterNameEquals = "Category 1";
	protected String filterNameEqualsNotFound = "qwertyuiopasdfghjklçzxcvbnm";
	protected String filterNameStartsWith = "Category";
	protected String filterNameStartsWithNotFound = "qwertyuiopasdfghjklçzxcvbnm";
	protected String filterNameEndsWith = "1";
	protected String filterNameEndsWithNotFound = "qwertyuiopasdfghjklçzxcvbnm";
	protected String filterNameIn = "Category 1,Category 2";
	protected String filterNameNotFound = "qwertyuiopasdfghjklçzxcvbnm";
	protected String filterParam = "name";

}
