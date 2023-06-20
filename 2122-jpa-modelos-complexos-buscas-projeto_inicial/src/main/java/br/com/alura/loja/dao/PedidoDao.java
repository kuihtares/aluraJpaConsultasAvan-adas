package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}

	public BigDecimal valorTotalVendido(){
		String jpql = "SELECT sum(p.valorTotal) FROM Pedido p";
		return em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();
	}
	public List <RelatorioDeVendasVo> relatorioDeVendas(){
		String jpql = "SELECT new br.com.alura.loja.vo.RelatorioDeVendasVo ("
				 + "produto.nome, "
		         + "SUM(item.quantidade), "
				 + "MAX(pedido.data)) "
				 + "FROM Pedido pedido "
				 + "JOIN pedido.itens item "
				 + "JOIN item.produto produto "
				 + "GROUP BY produto.nome "
				 + "ORDER BY item.quantidade DESC";
		return em.createQuery(jpql, RelatorioDeVendasVo.class)
				.getResultList();
	}
}
