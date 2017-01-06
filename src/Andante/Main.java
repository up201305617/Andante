package Andante;

import java.io.IOException;
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Main {

	public static CartaoAndante cartao = null;
	public static Recibo recibo = null;

	// "dirty-clear" of console
	public static void clearConsole() {
		for (int i = 0; i < 10; i++)
			System.out.println("");
	}

	// display the main menu
	public static void mainMenu() {

		if (cartao != null) {
			System.out.println("************************************");
			System.out.println("**         MAQUINA ANDANTE        **");
			System.out.println("************************************");
			System.out.println("** \"" + cartao.zona.nome + "\" " + cartao.quantidade + "                         **");
			System.out.println("**                                **");
			System.out.println("** 1) COMPRAR Cartão Andante      **");
			System.out.println("** 2) CARREGAR Cartão Andante     **");
			System.out.println("** 3) MUDAR ZONA Cartão Andante   **");
			System.out.println("************************************");
			System.out.print("Escolha operação : ");
		} else {
			System.out.println("************************************");
			System.out.println("**         MAQUINA ANDANTE        **");
			System.out.println("************************************");
			System.out.println("**                                **");
			System.out.println("**                                **");
			System.out.println("** 1) COMPRAR Cartão Andante      **");
			System.out.println("**                                **");
			System.out.println("**                                **");
			System.out.println("************************************");
			System.out.print("Escolha operação : ");
		}
	}

	// display zone selector menu
	public static void seletorZona() {
		System.out.println("************************************");
		System.out.println("**        MAQUINA ANDANTE         **");
		System.out.println("************************************");
		System.out.println("** 01) Z2                  Z7 (06 **");
		System.out.println("** 02) Z3                  Z8 (07 **");
		System.out.println("** 03) Z4                  Z9 (08 **");
		System.out.println("** 04) Z5                 Z10 (09 **");
		System.out.println("** 05) Z6                 Z11 (10 **");
		System.out.println("************************************");
		System.out.print("Escolha zona : ");
	}

	// display confirmation menu
	public static void precoMenu(Zona zona, int quantidade, int quantiaInserida) {
		System.out.println("************************************");
		System.out.println("**         MAQUINA ANDANTE        **");
		System.out.println("************************************");
		System.out.println("**                                **");
		System.out.println("** Zona: \"" + zona.nome + "\"                     **");
		System.out.println("** Quantidade: " + quantidade + "                  **");
		System.out.println("** Preço: " + (zona.preco.intValue() * quantidade) + "                     **");
		System.out.println("**                 Inserido: " + quantiaInserida + "  **");
		System.out.println("************************************");
	}

	// return the zone based on the option number
	public static Zona zona(int option) {
		switch (option) {
		case 1:
			return MaquinaAndante.z2;
		case 2:
			return MaquinaAndante.z3;
		case 3:
			return MaquinaAndante.z4;
		case 4:
			return MaquinaAndante.z5;
		case 5:
			return MaquinaAndante.z6;
		case 6:
			return MaquinaAndante.z7;
		case 7:
			return MaquinaAndante.z8;
		case 8:
			return MaquinaAndante.z9;
		case 9:
			return MaquinaAndante.z10;
		case 10:
			return MaquinaAndante.z11;
		default:
			System.out.println("Something went really bad with zone option!");
			return null;
		}
	}

	// tell if a String ban be converted into an integer
	public static boolean isNumeric(String line) {
		try {
			int number = Integer.parseInt(line);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// read command number
	public static int readOption() {
		Scanner reader = new Scanner(System.in);
		String option;
		do {
			option = reader.nextLine();
		} while (!isNumeric(option));
		return Integer.parseInt(option);
	}

	// prompt for a positive quantity
	public static int readQuantity() {
		Scanner reader = new Scanner(System.in);
		int quantity;
		do {
			quantity = readOption();
		} while (quantity <= 0);
		return quantity;
	}

	// prompt for valid money entries
	public static int readMoneyQuantities() {
		Scanner reader = new Scanner(System.in);
		int money;
		do {
			money = readOption();
		} while(money != 0 && money != 5 && money != 10 && money != 20 && money != 50
				&& money != 100 && money != 200 && money != 500);
		return money;
	}
		
	// confirm operation
	public static boolean confirmation() {
		Scanner reader = new Scanner(System.in);
		String confirmation;
		do {
			confirmation = reader.nextLine();
		} while (!confirmation.equals("S") && !confirmation.equals("s") && !confirmation.equals("N")
				&& !confirmation.equals("n"));

		if (confirmation.equals("S") || confirmation.equals("s"))
			return true;
		else
			return false;
	}

	public static void Run() {

		while (true) // operation loop repeat forever
		{
			
			// initialize loop variables
			Zona zona;
			int quantity, inserted = 0;
			VDMMap moneyInserted = new VDMMap();
			
			// display main menu
			mainMenu();

			// wait for user option
			int option;
			if (cartao == null) { // if no card was yet purchased
				do {
					option = readOption();
				} while (option != 1);
			} else { // a card was already bought
				do {
					option = readOption();
				} while (option < 1 || option > 3);
			}

			// parse user option
			switch (option) {

			/**************************************
			 * 		'BUY NEW CARD' Option
			 **************************************/
			
			case 1:
				
				// select card zone
				seletorZona();
				do {
					option = readOption();
				} while (option < 1 || option > 10);
				
				// select quantity
				System.out.print("Quantidade de viagens : ");
				quantity = readQuantity();
				
				// insert money
				zona = zona(option);
				while(inserted < (zona.preco.intValue() * quantity)){
					clearConsole();
					precoMenu(zona, quantity, inserted);
					System.out.print("Inserir quantia (0 para cancelar) : ");
					int money = readMoneyQuantities();
					if (money != 0) {
						if (moneyInserted.containsKey(money)){
							int value = (int) MapUtil.get(moneyInserted, money);
							MapUtil.mapAdd(moneyInserted, new Maplet(money, value + 1));
						} else { MapUtil.mapAdd(moneyInserted, new Maplet(money, 1)); }
						inserted += money;
					} else break;
				}
				
				// check for cancel command
				if(!(inserted < (zona.preco.intValue() * quantity))) {
					// ask for receipt
					System.out.print("Quer recibo? (S|N) : ");
					if (confirmation()) {
						 cartao = new TestMaquinaAndante().testarCompraCartao(zona,quantity,moneyInserted,true);
					} else {
						cartao = new TestMaquinaAndante().testarCompraCartao(zona,quantity,moneyInserted,false);
					}
				} else { new TestMaquinaAndante().testarCancelar(); }
				
				clearConsole();
				break;

			/**************************************
			 * 		'RECHARGE CARD' Option
			 **************************************/

			case 2:
				
				// insert money
				zona = cartao.zona;
				
				// select quantity
				System.out.print("Quantidade de viagens : ");
				quantity = readQuantity();
				
				while(inserted < (zona.preco.intValue() * quantity)){
					clearConsole();
					precoMenu(zona, quantity, inserted);
					System.out.print("Inserir quantia (0 para cancelar) : ");
					int money = readMoneyQuantities();
					if (money != 0) {
						if (moneyInserted.containsKey(money)){
							int value = (int) MapUtil.get(moneyInserted, money);
							MapUtil.mapAdd(moneyInserted, new Maplet(money, value + 1));
						} else { MapUtil.mapAdd(moneyInserted, new Maplet(money, 1)); }
						inserted += money;
					} else break;
				}
				
				// check for cancel command
				if(!(inserted < (zona.preco.intValue() * quantity))) {
					// ask for receipt
					System.out.print("Quer recibo? (S|N) : ");
					if (confirmation()) {
						cartao = new TestMaquinaAndante().testarCarregarCartao(quantity,cartao,moneyInserted,true);
					} else {
						cartao = new TestMaquinaAndante().testarCarregarCartao(quantity,cartao,moneyInserted,false);
					}
				} else { new TestMaquinaAndante().testarCancelar(); }
				
				clearConsole();
				break;

			/**************************************
			 * 	  'CHANGE CARD ZONE' Option
			 **************************************/
			case 3:
				
				// select new card zone
				seletorZona();
				do {
					option = readOption();
				} while (option < 1 || option > 10);
				
				// display confirmation menu
				zona = zona(option);
				
				// ask for confirmation
				if (cartao.quantidade.intValue() == 0) {
					System.out.print("Quer prosseguir? (S|N) : ");
				} else {
					System.out.print("Ainda tem viagens por usar. Se mudar de zona \n "
							+ "elas serão elimindadas. Quer prosseguir? (S|N) : ");
				}
				
				// if user confirmed his intentions
				if (confirmation()) {
					cartao = new TestMaquinaAndante().testarMudarZona(zona,cartao);
				}
				
				clearConsole();
				break;
			
			/**************************************
			* 			'INVALID' Option
			***************************************/
				
			default:
				System.out.println("Something went really bad somewhere!");
			}
		}
	}

	public Main() {}

	public String toString() {
		return "Main{}";
	}

	public static void main(String[] args) {
		Run();
	}
}
