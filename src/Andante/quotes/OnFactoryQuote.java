package Andante.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class OnFactoryQuote {
  private static int hc = 0;
  private static OnFactoryQuote instance = null;

  public OnFactoryQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static OnFactoryQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new OnFactoryQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof OnFactoryQuote;
  }

  public String toString() {

    return "<OnFactory>";
  }
}
