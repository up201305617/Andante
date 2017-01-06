package Andante;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Recibo {
  public Zona zona;
  public Number quantidade;
  public String operacao;

  public void cg_init_Recibo_1(final Number q, final Zona z, final String o) {

    zona = z;
    quantidade = q;
    operacao = o;
    return;
  }

  public Recibo(final Number q, final Zona z, final String o) {

    cg_init_Recibo_1(q, z, o);
  }

  public Recibo() {}

  public String toString() {

    return "Recibo{"
        + "zona := "
        + Utils.toString(zona)
        + ", quantidade := "
        + Utils.toString(quantidade)
        + ", operacao := "
        + Utils.toString(operacao)
        + "}";
  }
}
