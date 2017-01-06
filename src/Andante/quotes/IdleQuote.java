package Andante.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class IdleQuote {
  private static int hc = 0;
  private static IdleQuote instance = null;

  public IdleQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static IdleQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new IdleQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof IdleQuote;
  }

  public String toString() {

    return "<Idle>";
  }
}
