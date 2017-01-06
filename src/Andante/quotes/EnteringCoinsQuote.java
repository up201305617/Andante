package Andante.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class EnteringCoinsQuote {
  private static int hc = 0;
  private static EnteringCoinsQuote instance = null;

  public EnteringCoinsQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static EnteringCoinsQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new EnteringCoinsQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof EnteringCoinsQuote;
  }

  public String toString() {

    return "<EnteringCoins>";
  }
}
