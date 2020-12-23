package br.com.alura.livraria.tx;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import br.com.alura.alura_lib.tx.Transacionado;

@Alternative
@Priority(value = Interceptor.Priority.APPLICATION)
@Typed(Transacionado.class)
public class MeuGerenciadorDeTransacao implements Transacionado {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@Override
	public Object executaComTransacao(InvocationContext context) {

		System.out.println("Abrindo uma transação");
		em.getTransaction().begin();

		try {
			System.out.println("Antes de executar o método interceptado");
			Object result = context.proceed();

			System.out.println("Antes de commitar a transação");
			em.getTransaction().commit();

			return result;
		} catch (Exception e) {
			System.out.println("Anter de efetuar o roolback da transação");
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}
}
