package Andante;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Dinheiro {
  public static final VDMSet CoinValues = SetUtil.set(1L, 2L, 5L, 10L, 20L, 50L, 100L, 200L);

  public Dinheiro() {}

  public static Number sum(final VDMMap b) {

    if (Utils.empty(b)) {
      return 0L;

    } else {
      Number letBeStExp_1 = null;
      Number c = null;
      Boolean success_1 = false;
      VDMSet set_1 = MapUtil.dom(Utils.copy(b));
      for (Iterator iterator_1 = set_1.iterator(); iterator_1.hasNext() && !(success_1); ) {
        c = ((Number) iterator_1.next());
        success_1 = true;
      }
      if (!(success_1)) {
        throw new RuntimeException("Let Be St found no applicable bindings");
      }

      letBeStExp_1 =
          ((Number) Utils.get(b, c)).longValue() * c.longValue()
              + sum(MapUtil.domResBy(SetUtil.set(c), Utils.copy(b))).longValue();
      return letBeStExp_1;
    }
  }

  public static VDMMap add(final Number coin, final VDMMap bag) {

    if (SetUtil.inSet(coin, MapUtil.dom(Utils.copy(bag)))) {
      return MapUtil.override(
          Utils.copy(bag),
          MapUtil.map(new Maplet(coin, ((Number) Utils.get(bag, coin)).longValue() + 1L)));

    } else {
      return MapUtil.munion(Utils.copy(bag), MapUtil.map(new Maplet(coin, 1L)));
    }
  }

  public static VDMMap remove(final Number coin, final VDMMap bag) {

    if (Utils.equals(((Number) Utils.get(bag, coin)), 1L)) {
      return MapUtil.domResBy(SetUtil.set(coin), Utils.copy(bag));

    } else {
      return MapUtil.override(
          Utils.copy(bag),
          MapUtil.map(new Maplet(coin, ((Number) Utils.get(bag, coin)).longValue() - 1L)));
    }
  }

  public static VDMMap addAll(final VDMMap bag1, final VDMMap bag2) {

    VDMMap mapCompResult_1 = MapUtil.map();
    VDMSet set_2 = SetUtil.union(MapUtil.dom(Utils.copy(bag1)), MapUtil.dom(Utils.copy(bag2)));
    for (Iterator iterator_2 = set_2.iterator(); iterator_2.hasNext(); ) {
      Number c = ((Number) iterator_2.next());
      Number ternaryIfExp_1 = null;

      if (SetUtil.inSet(c, MapUtil.dom(Utils.copy(bag1)))) {
        ternaryIfExp_1 = ((Number) Utils.get(bag1, c));
      } else {
        ternaryIfExp_1 = 0L;
      }

      Number ternaryIfExp_2 = null;

      if (SetUtil.inSet(c, MapUtil.dom(Utils.copy(bag2)))) {
        ternaryIfExp_2 = ((Number) Utils.get(bag2, c));
      } else {
        ternaryIfExp_2 = 0L;
      }

      MapUtil.mapAdd(
          mapCompResult_1,
          new Maplet(c, (ternaryIfExp_1.longValue()) + (ternaryIfExp_2.longValue())));
    }
    return Utils.copy(mapCompResult_1);
  }

  public static VDMMap removeAll(final VDMMap bag1, final VDMMap bag2) {

    VDMMap mapCompResult_2 = MapUtil.map();
    VDMSet set_3 = MapUtil.dom(Utils.copy(bag2));
    for (Iterator iterator_3 = set_3.iterator(); iterator_3.hasNext(); ) {
      Number c = ((Number) iterator_3.next());
      Boolean andResult_2 = false;

      if (SetUtil.inSet(c, MapUtil.dom(Utils.copy(bag1)))) {
        if (Utils.equals(((Number) Utils.get(bag1, c)), ((Number) Utils.get(bag2, c)))) {
          andResult_2 = true;
        }
      }

      if (!(andResult_2)) {
        Number ternaryIfExp_3 = null;

        if (SetUtil.inSet(c, MapUtil.dom(Utils.copy(bag1)))) {
          ternaryIfExp_3 = ((Number) Utils.get(bag1, c));
        } else {
          ternaryIfExp_3 = 0L;
        }

        MapUtil.mapAdd(
            mapCompResult_2,
            new Maplet(
                c, ((Number) Utils.get(bag2, c)).longValue() - (ternaryIfExp_3.longValue())));
      }
    }
    return Utils.copy(mapCompResult_2);
  }

  public static Boolean isSubBag(final VDMMap b1, final VDMMap b2) {

    Boolean andResult_3 = false;

    if (SetUtil.subset(MapUtil.dom(Utils.copy(b1)), MapUtil.dom(Utils.copy(b2)))) {
      Boolean forAllExpResult_1 = true;
      VDMSet set_4 = MapUtil.dom(Utils.copy(b1));
      for (Iterator iterator_4 = set_4.iterator(); iterator_4.hasNext() && forAllExpResult_1; ) {
        Number c = ((Number) iterator_4.next());
        forAllExpResult_1 =
            ((Number) Utils.get(b1, c)).longValue() <= ((Number) Utils.get(b2, c)).longValue();
      }
      if (forAllExpResult_1) {
        andResult_3 = true;
      }
    }

    return andResult_3;
  }

  public static VDMMap extract(final VDMMap bag, final Number amount) {

    return extractAux(Utils.copy(bag), amount, amount);
  }

  private static VDMMap extractAux(final VDMMap bag, final Number amount, final Number maxCoin) {

    if (Utils.equals(amount, 0L)) {
      return MapUtil.map();

    } else {
      VDMSeq seqCompResult_1 = SeqUtil.seq();
      VDMSet set_5 = MapUtil.dom(Utils.copy(bag));
      for (Iterator iterator_5 = set_5.iterator(); iterator_5.hasNext(); ) {
        Number c = ((Number) iterator_5.next());
        Boolean andResult_4 = false;

        if (c.longValue() <= maxCoin.longValue()) {
          if (c.longValue() <= amount.longValue()) {
            andResult_4 = true;
          }
        }

        if (andResult_4) {
          seqCompResult_1.add(c);
        }
      }
      final VDMSeq coins = SeqUtil.reverse(Utils.copy(seqCompResult_1));

      VDMMap ternaryIfExp_4 = null;

      if (Utils.empty(coins)) {
        ternaryIfExp_4 = null;
      } else {
        final Number c = ((Number) coins.get(0));
        final VDMMap remaining =
            extractAux(remove(c, Utils.copy(bag)), amount.longValue() - c.longValue(), c);

        VDMMap ternaryIfExp_5 = null;

        if (!(Utils.equals(remaining, null))) {
          ternaryIfExp_5 = add(c, Utils.copy(remaining));
        } else {
          ternaryIfExp_5 = extractAux(Utils.copy(bag), amount, c.longValue() - 1L);
        }

        ternaryIfExp_4 = Utils.copy(ternaryIfExp_5);
      }

      return Utils.copy(ternaryIfExp_4);
    }
  }

  public static Boolean canExtract(final VDMMap bag, final Number amount) {

    return !(Utils.equals(extract(Utils.copy(bag), amount), null));
  }

  public String toString() {

    return "Dinheiro{" + "CoinValues = " + Utils.toString(CoinValues) + "}";
  }
}
