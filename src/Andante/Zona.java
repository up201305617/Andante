package Andante;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Zona {
  public String nome;
  public Number preco;

  public void cg_init_Zona_1(final String n, final Number p) {

    nome = n;
    preco = p;
    return;
  }

  public Zona(final String n, final Number p) {

    cg_init_Zona_1(n, p);
  }

  public Zona() {}

  public String toString() {

    return "Zona{"
        + "nome := "
        + Utils.toString(nome)
        + ", preco := "
        + Utils.toString(preco)
        + "}";
  }
}
