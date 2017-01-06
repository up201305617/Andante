package Andante;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class MaquinaAndante {
  public Number quantidadeCartoes = 0L;
  public Number quantidadeRecibo = 0L;
  public VDMMap stockDinheiro = MapUtil.map();
  public Number quantiaInserida = 0L;
  public CartaoAndante andante = null;
  public Recibo recibo = null;
  public VDMMap troco = MapUtil.map();
  public String codigo = SeqUtil.toStr(SeqUtil.seq());
  public Object status = Andante.quotes.OnFactoryQuote.getInstance();
  public static Zona z2 = new Zona("z2", 120L);
  public static Zona z3 = new Zona("z3", 155L);
  public static Zona z4 = new Zona("z4", 195L);
  public static Zona z5 = new Zona("z5", 235L);
  public static Zona z6 = new Zona("z6", 275L);
  public static Zona z7 = new Zona("z7", 315L);
  public static Zona z8 = new Zona("z8", 355L);
  public static Zona z9 = new Zona("z9", 395L);
  public static Zona z10 = new Zona("z10", 435L);
  public static Zona z11 = new Zona("z11", 475L);
  public static Zona z12 = new Zona("z12", 515L);

  public void cg_init_MaquinaAndante_1() {

    return;
  }

  public MaquinaAndante() {

    cg_init_MaquinaAndante_1();
  }

  public void definirPassword(final String code) {

    codigo = code;
  }

  public void sairFabrica() {

    status = Andante.quotes.IdleQuote.getInstance();
  }

  public void abrirMaquina(final String code) {

    if (Utils.equals(code, codigo)) {
      status = Andante.quotes.OnMaintenanceQuote.getInstance();
    }
  }

  public void carregarMaquina(final VDMMap coins, final Number cartoes, final Number papel) {

    stockDinheiro = Utils.copy(coins);
    quantidadeCartoes = cartoes;
    quantidadeRecibo = papel;
  }

  public void fecharMaquina() {

    status = Andante.quotes.IdleQuote.getInstance();
  }

  public void reset() {

    status = Andante.quotes.IdleQuote.getInstance();
  }

  public void mudarZona(final Zona z, final CartaoAndante c) {

    c.mudarZona(z);
    andante = c;
    status = Andante.quotes.IdleQuote.getInstance();
  }

  public void inserirDinheiro(final Number coin) {

    VDMMap atomicTmp_1 = Dinheiro.add(coin, Utils.copy(stockDinheiro));
    Number atomicTmp_2 = quantiaInserida.longValue() + coin.longValue();
    Object atomicTmp_3 = Andante.quotes.EnteringCoinsQuote.getInstance();
    {
        /* Start of atomic statement */
      stockDinheiro = Utils.copy(atomicTmp_1);
      quantiaInserida = atomicTmp_2;
      status = atomicTmp_3;
    } /* End of atomic statement */
  }

  public VDMMap apanharTroco() {

    final VDMMap r = Utils.copy(troco);
    {
      VDMMap atomicTmp_4 = MapUtil.map();
      Object ternaryIfExp_6 = null;

      if (Utils.equals(andante, null)) {
        ternaryIfExp_6 = Andante.quotes.IdleQuote.getInstance();
      } else {
        ternaryIfExp_6 = Andante.quotes.DeliveringQuote.getInstance();
      }

      Object atomicTmp_5 = ternaryIfExp_6;
      {
          /* Start of atomic statement */
        troco = Utils.copy(atomicTmp_4);
        status = atomicTmp_5;
      } /* End of atomic statement */

      return Utils.copy(r);
    }
  }

  public CartaoAndante apanharAndante() {

    {
      final CartaoAndante r = andante;
      {
        CartaoAndante atomicTmp_6 = null;
        Object ternaryIfExp_7 = null;

        if (Utils.equals(andante, null)) {
          ternaryIfExp_7 = Andante.quotes.IdleQuote.getInstance();
        } else {
          ternaryIfExp_7 = Andante.quotes.DeliveringQuote.getInstance();
        }

        Object atomicTmp_7 = ternaryIfExp_7;
        {
            /* Start of atomic statement */
          andante = atomicTmp_6;
          status = atomicTmp_7;
        } /* End of atomic statement */

        return r;
      }
    }
  }

  public Recibo apanharRecibo() {

    {
      final Recibo r = recibo;
      {
        Recibo atomicTmp_8 = null;
        Object ternaryIfExp_8 = null;

        if (Utils.equals(recibo, null)) {
          ternaryIfExp_8 = Andante.quotes.IdleQuote.getInstance();
        } else {
          ternaryIfExp_8 = Andante.quotes.DeliveringQuote.getInstance();
        }

        Object atomicTmp_9 = ternaryIfExp_8;
        {
            /* Start of atomic statement */
          recibo = atomicTmp_8;
          status = atomicTmp_9;
        } /* End of atomic statement */

        return r;
      }
    }
  }

  public void cancelar() {

    final VDMMap chg = Dinheiro.extract(Utils.copy(stockDinheiro), quantiaInserida);
    VDMMap atomicTmp_10 = Dinheiro.removeAll(Utils.copy(chg), Utils.copy(stockDinheiro));
    Number atomicTmp_11 = 0L;
    VDMMap atomicTmp_12 = Utils.copy(chg);
    Object atomicTmp_13 = Andante.quotes.DeliveringQuote.getInstance();
    {
        /* Start of atomic statement */
      stockDinheiro = Utils.copy(atomicTmp_10);
      quantiaInserida = atomicTmp_11;
      troco = Utils.copy(atomicTmp_12);
      status = atomicTmp_13;
    } /* End of atomic statement */
  }

  public void comprarAndante(final Number q, final Zona z, final Boolean r) {

    final CartaoAndante cartao = new CartaoAndante(q, z);
    final Number preco = cartao.zona.preco.longValue() * cartao.quantidade.longValue() + 60L;
    VDMMap chg =
        Dinheiro.extract(
            Utils.copy(stockDinheiro), quantiaInserida.longValue() - preco.longValue());
    {

    	 VDMMap atomicTmp_14 = null;
    	if(chg != null)
    	{
    		atomicTmp_14 = Dinheiro.removeAll(Utils.copy(chg), Utils.copy(stockDinheiro));
    	}
    	else
    	{
    		chg = new VDMMap();
    		atomicTmp_14 = Dinheiro.removeAll(Utils.copy(chg), Utils.copy(stockDinheiro));
    	}
    	
      Number atomicTmp_15 = quantidadeCartoes.longValue() - 1L;
      Number atomicTmp_16 = 0L;
      VDMMap atomicTmp_17 = Utils.copy(chg);
      CartaoAndante atomicTmp_18 = cartao;
      Object atomicTmp_19 = Andante.quotes.DeliveringQuote.getInstance();
      {
          /* Start of atomic statement */
        stockDinheiro = Utils.copy(atomicTmp_14);
        quantidadeCartoes = atomicTmp_15;
        quantiaInserida = atomicTmp_16;
        troco = Utils.copy(atomicTmp_17);
        andante = atomicTmp_18;
        status = atomicTmp_19;
      } /* End of atomic statement */

      if (r) {
        quantidadeRecibo = quantidadeRecibo.longValue() - 1L;
        recibo = new Recibo(q, z, "Compra");
      }
    }
  }

  public void carregarAndante(final Number q, final CartaoAndante c, final Boolean r) {

    c.carregarAndante(q);
    {
      final Number preco = c.zona.preco.longValue() * q.longValue();
      final VDMMap chg =
          Dinheiro.extract(
              Utils.copy(stockDinheiro), quantiaInserida.longValue() - preco.longValue());
      {
        VDMMap atomicTmp_20 = Dinheiro.removeAll(Utils.copy(chg), Utils.copy(stockDinheiro));
        Number atomicTmp_21 = 0L;
        VDMMap atomicTmp_22 = Utils.copy(chg);
        CartaoAndante atomicTmp_23 = c;
        Object atomicTmp_24 = Andante.quotes.DeliveringQuote.getInstance();
        {
            /* Start of atomic statement */
          stockDinheiro = Utils.copy(atomicTmp_20);
          quantiaInserida = atomicTmp_21;
          troco = Utils.copy(atomicTmp_22);
          andante = atomicTmp_23;
          status = atomicTmp_24;
        } /* End of atomic statement */

        if (r) {
          quantidadeRecibo = quantidadeRecibo.longValue() - 1L;
          recibo = new Recibo(q, c.zona, "Carregamento");
        }
      }
    }
  }

  public String toString() {

    return "MaquinaAndante{"
        + "quantidadeCartoes := "
        + Utils.toString(quantidadeCartoes)
        + ", quantidadeRecibo := "
        + Utils.toString(quantidadeRecibo)
        + ", stockDinheiro := "
        + Utils.toString(stockDinheiro)
        + ", quantiaInserida := "
        + Utils.toString(quantiaInserida)
        + ", andante := "
        + Utils.toString(andante)
        + ", recibo := "
        + Utils.toString(recibo)
        + ", troco := "
        + Utils.toString(troco)
        + ", codigo := "
        + Utils.toString(codigo)
        + ", status := "
        + Utils.toString(status)
        + ", z2 := "
        + Utils.toString(z2)
        + ", z3 := "
        + Utils.toString(z3)
        + ", z4 := "
        + Utils.toString(z4)
        + ", z5 := "
        + Utils.toString(z5)
        + ", z6 := "
        + Utils.toString(z6)
        + ", z7 := "
        + Utils.toString(z7)
        + ", z8 := "
        + Utils.toString(z8)
        + ", z9 := "
        + Utils.toString(z9)
        + ", z10 := "
        + Utils.toString(z10)
        + ", z11 := "
        + Utils.toString(z11)
        + ", z12 := "
        + Utils.toString(z12)
        + "}";
  }
}
