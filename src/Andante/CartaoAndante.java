package Andante;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class CartaoAndante {
  public Zona zona;
  public Number quantidade;

  public void cg_init_CartaoAndante_1(final Number q, final Zona z) {

    zona = z;
    quantidade = q;
    return;
  }

  public CartaoAndante(final Number q, final Zona z) {

    cg_init_CartaoAndante_1(q, z);
  }

  public void carregarAndante(final Number q) {

    quantidade = quantidade.longValue() + q.longValue();
  }

  public void mudarZona(final Zona z) {

    quantidade = 0L;
    zona = z;
  }

  public CartaoAndante() {}

  public String toString() {

    return "CartaoAndante{"
        + "zona := "
        + Utils.toString(zona)
        + ", quantidade := "
        + Utils.toString(quantidade)
        + "}";
  }
}
