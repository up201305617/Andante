package Andante;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class TestMaquinaAndante extends MyTestCase {
  public MaquinaAndante ConfigurarMaquina(final String codigo) {

    MaquinaAndante ma = new MaquinaAndante();
    ma.definirPassword(codigo);
    ma.sairFabrica();
    return ma;
  }

  public void CarregarMaquina(
      final MaquinaAndante maq,
      final String code,
      final VDMMap saco,
      final Number qc,
      final Number qr) {

    Number quantidadeInicialCartao = maq.quantidadeCartoes;
    Number quantidadeInicialPapel = maq.quantidadeRecibo;
    maq.abrirMaquina(code);
    maq.carregarMaquina(Utils.copy(saco), qc, qr);
    maq.fecharMaquina();
    assertTrue(
        Utils.equals(maq.quantidadeCartoes, quantidadeInicialCartao.longValue() + qc.longValue()));
    assertTrue(
        Utils.equals(maq.quantidadeRecibo, quantidadeInicialPapel.longValue() + qr.longValue()));
  }

  public Tuple ComprarAndanteRecibo(
      final MaquinaAndante maq,
      final Zona zon,
      final Number qua,
      final VDMMap inserido,
      final Boolean querRecibo) {

    Number quantidadeInicialCartao = maq.quantidadeCartoes;
    Number quantidadeInicialPapel = maq.quantidadeRecibo;
    VDMMap stockInicialDinheiro = maq.stockDinheiro;
    Number dinheiroInserido = 0L;
    VDMMap troco = MapUtil.map();
    CartaoAndante cartao = null;
    Recibo recibo = null;
    Tuple result = null;
    for (Iterator iterator_6 = MapUtil.dom(Utils.copy(inserido)).iterator();
        iterator_6.hasNext();
        ) {
      Number c = (Number) iterator_6.next();
      for (Iterator iterator_7 = SetUtil.range(1L, ((Number) Utils.get(inserido, c))).iterator();
          iterator_7.hasNext();
          ) {
        Number ignorePattern_1 = (Number) iterator_7.next();
        maq.inserirDinheiro(c);
        dinheiroInserido = dinheiroInserido.longValue() + c.longValue();
        assertEqual(dinheiroInserido, maq.quantiaInserida);
      }
    }
    maq.comprarAndante(qua, zon, querRecibo);
    if (Dinheiro.sum(Utils.copy(inserido)).longValue() > zon.preco.longValue() * qua.longValue()) {
      troco = maq.apanharTroco();
    }

    cartao = maq.apanharAndante();
    recibo = maq.apanharRecibo();
    result = Tuple.mk_(cartao, Utils.copy(troco), recibo);
    assertTrue(Utils.equals(maq.quantidadeCartoes, quantidadeInicialCartao.longValue() - 1L));
    assertTrue(Utils.equals(maq.quantidadeRecibo, quantidadeInicialPapel.longValue() - 1L));
    assertTrue(
        Utils.equals(
            Dinheiro.addAll(maq.stockDinheiro, Utils.copy(troco)),
            Dinheiro.addAll(Utils.copy(stockInicialDinheiro), Utils.copy(inserido))));
    return Utils.copy(result);
  }

  public Tuple CarregarAndanteRecibo(
      final MaquinaAndante maq,
      final Number qua,
      final VDMMap inserido,
      final CartaoAndante car,
      final Boolean quer) {

    Number quantidadeInicialPapel = maq.quantidadeRecibo;
    VDMMap stockInicialDinheiro = maq.stockDinheiro;
    Number dinheiroInserido = 0L;
    VDMMap troco = MapUtil.map();
    Number quantidadeInicial = car.quantidade;
    CartaoAndante cartaoNovo = car;
    Recibo recibo = null;
    Tuple result = null;
    for (Iterator iterator_8 = MapUtil.dom(Utils.copy(inserido)).iterator();
        iterator_8.hasNext();
        ) {
      Number c = (Number) iterator_8.next();
      for (Iterator iterator_9 = SetUtil.range(1L, ((Number) Utils.get(inserido, c))).iterator();
          iterator_9.hasNext();
          ) {
        Number ignorePattern_2 = (Number) iterator_9.next();
        maq.inserirDinheiro(c);
        dinheiroInserido = dinheiroInserido.longValue() + c.longValue();
        assertEqual(dinheiroInserido, maq.quantiaInserida);
      }
    }
    maq.carregarAndante(qua, cartaoNovo, quer);
    if (Dinheiro.sum(Utils.copy(inserido)).longValue()
        > cartaoNovo.zona.preco.longValue() * qua.longValue()) {
      troco = maq.apanharTroco();
    }

    cartaoNovo = maq.apanharAndante();
    recibo = maq.apanharRecibo();
    result = Tuple.mk_(cartaoNovo, Utils.copy(troco), recibo);
    assertTrue(Utils.equals(maq.quantidadeRecibo, quantidadeInicialPapel.longValue() - 1L));
    assertTrue(
        Utils.equals(
            Dinheiro.addAll(maq.stockDinheiro, Utils.copy(troco)),
            Dinheiro.addAll(Utils.copy(stockInicialDinheiro), Utils.copy(inserido))));
    assertTrue(
        Utils.equals(cartaoNovo.quantidade, quantidadeInicial.longValue() + qua.longValue()));
    return Utils.copy(result);
  }

  public VDMMap CancelarCompra(final MaquinaAndante maq, final VDMMap coins) {

    VDMMap stockInicialDinheiro = maq.stockDinheiro;
    Number inserido = 0L;
    VDMMap result = null;
    for (Iterator iterator_10 = MapUtil.dom(Utils.copy(coins)).iterator();
        iterator_10.hasNext();
        ) {
      Number c = (Number) iterator_10.next();
      for (Iterator iterator_11 = SetUtil.range(1L, ((Number) Utils.get(coins, c))).iterator();
          iterator_11.hasNext();
          ) {
        Number ignorePattern_3 = (Number) iterator_11.next();
        maq.inserirDinheiro(c);
        inserido = inserido.longValue() + c.longValue();
        assertEqual(inserido, maq.quantiaInserida);
      }
    }
    maq.cancelar();
    result = maq.apanharTroco();
    assertTrue(
        Utils.equals(
            Dinheiro.addAll(Utils.copy(stockInicialDinheiro), Utils.copy(coins)),
            Dinheiro.addAll(maq.stockDinheiro, Utils.copy(result))));
    return Utils.copy(result);
  }

  public CartaoAndante MudarZona(
      final MaquinaAndante maq, final CartaoAndante cartao, final Zona zon) {

    CartaoAndante result = null;
    maq.mudarZona(zon, cartao);
    result = maq.apanharAndante();
    assertTrue(Utils.equals(zon.nome, result.zona.nome));
    assertTrue(Utils.equals(0L, result.quantidade));
    return result;
  }

  public void testarCompraCartao() {

    MaquinaAndante maquina = ConfigurarMaquina("password");
    CarregarMaquina(
        maquina,
        "password",
        MapUtil.map(
            new Maplet(1L, 5L),
            new Maplet(2L, 5L),
            new Maplet(5L, 5L),
            new Maplet(10L, 5L),
            new Maplet(20L, 5L),
            new Maplet(50L, 5L),
            new Maplet(100L, 5L),
            new Maplet(200L, 5L)),
        5L,
        5L);
    {
      final Tuple tuplePattern_1 =
          ComprarAndanteRecibo(maquina, maquina.z2, 1L, MapUtil.map(new Maplet(200L, 1L)), true);
      Boolean success_2 =
          tuplePattern_1.compatible(CartaoAndante.class, VDMMap.class, Recibo.class);
      VDMMap troco = null;
      if (success_2) {
        troco = Utils.copy(((VDMMap) tuplePattern_1.get(1)));
      }

      if (!(success_2)) {
        throw new RuntimeException("Tuple pattern match failed");
      }

      assertEqual(MapUtil.map(new Maplet(20L, 1L)), Utils.copy(troco));
    }
  }

  public CartaoAndante testarCompraCartao(
      final Zona zon, final Number q, final VDMMap bag, final Boolean quer) {

    MaquinaAndante maquina = ConfigurarMaquina("password");
    CarregarMaquina(
        maquina,
        "password",
        MapUtil.map(
            new Maplet(1L, 5L),
            new Maplet(2L, 5L),
            new Maplet(5L, 5L),
            new Maplet(10L, 5L),
            new Maplet(20L, 5L),
            new Maplet(50L, 5L),
            new Maplet(100L, 5L),
            new Maplet(200L, 5L)),
        5L,
        5L);
    {
      final Tuple tuplePattern_2 = ComprarAndanteRecibo(maquina, zon, q, Utils.copy(bag), quer);
      Boolean success_3 =
          tuplePattern_2.compatible(CartaoAndante.class, VDMMap.class, Recibo.class);
      CartaoAndante cartao = null;
      if (success_3) {
        cartao = ((CartaoAndante) tuplePattern_2.get(0));
      }

      if (!(success_3)) {
        throw new RuntimeException("Tuple pattern match failed");
      }

      {
        assertEqual(cartao.zona, zon);
        return cartao;
      }
    }
  }

  public void testarCarregarCartao() {

    MaquinaAndante maquina = ConfigurarMaquina("password");
    CartaoAndante cartao = new CartaoAndante(2L, maquina.z2);
    CarregarMaquina(
        maquina,
        "password",
        MapUtil.map(
            new Maplet(1L, 5L),
            new Maplet(2L, 5L),
            new Maplet(5L, 5L),
            new Maplet(10L, 5L),
            new Maplet(20L, 5L),
            new Maplet(50L, 5L),
            new Maplet(100L, 5L),
            new Maplet(200L, 5L)),
        5L,
        5L);
    {
      final Tuple tuplePattern_3 =
          CarregarAndanteRecibo(
              maquina, 1L, MapUtil.map(new Maplet(100L, 1L), new Maplet(200L, 1L)), cartao, true);
      Boolean success_4 =
          tuplePattern_3.compatible(CartaoAndante.class, VDMMap.class, Recibo.class);
      VDMMap troco = null;
      if (success_4) {
        troco = Utils.copy(((VDMMap) tuplePattern_3.get(1)));
      }

      if (!(success_4)) {
        throw new RuntimeException("Tuple pattern match failed");
      }

      assertEqual(
          MapUtil.map(
              new Maplet(10L, 1L), new Maplet(20L, 1L), new Maplet(50L, 1L), new Maplet(100L, 1L)),
          Utils.copy(troco));
    }
  }

  public CartaoAndante testarCarregarCartao(
      final Number q, final CartaoAndante car, final VDMMap bag, final Boolean quer) {

    MaquinaAndante maquina = ConfigurarMaquina("password");
    Number numeroViagens = car.quantidade;
    CarregarMaquina(
        maquina,
        "password",
        MapUtil.map(
            new Maplet(1L, 5L),
            new Maplet(2L, 5L),
            new Maplet(5L, 5L),
            new Maplet(10L, 5L),
            new Maplet(20L, 5L),
            new Maplet(50L, 5L),
            new Maplet(100L, 5L),
            new Maplet(200L, 5L)),
        5L,
        5L);
    {
      final Tuple tuplePattern_4 = CarregarAndanteRecibo(maquina, q, Utils.copy(bag), car, quer);
      Boolean success_5 =
          tuplePattern_4.compatible(CartaoAndante.class, VDMMap.class, Recibo.class);
      CartaoAndante cartao = null;
      if (success_5) {
        cartao = ((CartaoAndante) tuplePattern_4.get(0));
      }

      if (!(success_5)) {
        throw new RuntimeException("Tuple pattern match failed");
      }

      {
        assertEqual(cartao.quantidade, q.longValue() + numeroViagens.longValue());
        return cartao;
      }
    }
  }

  public void testarCancelar() {

    {
      final MaquinaAndante maquina = ConfigurarMaquina("password");
      final VDMMap coins = MapUtil.map(new Maplet(100L, 1L), new Maplet(200L, 1L));
      {
        CarregarMaquina(
            maquina,
            "password",
            MapUtil.map(
                new Maplet(1L, 5L),
                new Maplet(2L, 5L),
                new Maplet(5L, 5L),
                new Maplet(10L, 5L),
                new Maplet(20L, 5L),
                new Maplet(50L, 5L),
                new Maplet(100L, 5L),
                new Maplet(200L, 5L)),
            5L,
            5L);
        assertEqual(Utils.copy(coins), CancelarCompra(maquina, Utils.copy(coins)));
      }
    }
  }

  public void testarMudarZona() {

    MaquinaAndante maquina = ConfigurarMaquina("password");
    CartaoAndante cartao = new CartaoAndante(2L, maquina.z2);
    Zona zona = maquina.z5;
    CartaoAndante cartaoNovo = MudarZona(maquina, cartao, zona);
    assertEqual(zona.nome, cartaoNovo.zona.nome);
  }

  public CartaoAndante testarMudarZona(final Zona z, final CartaoAndante c) {

    MaquinaAndante maquina = ConfigurarMaquina("password");
    CartaoAndante cartaoNovo = MudarZona(maquina, c, z);
    assertEqual(z.nome, cartaoNovo.zona.nome);
    return cartaoNovo;
  }

  public void main() {

    testarCompraCartao();
    testarCarregarCartao();
    testarCancelar();
    testarMudarZona();
  }

  public TestMaquinaAndante() {}

  public String toString() {

    return "TestMaquinaAndante{}";
  }
}
