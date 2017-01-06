package Andante.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class DeliveringQuote {
  private static int hc = 0;
  private static DeliveringQuote instance = null;

  public DeliveringQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static DeliveringQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new DeliveringQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof DeliveringQuote;
  }

  public String toString() {

    return "<Delivering>";
  }
}
