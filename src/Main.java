import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	public static void printLista(LinkedList<Processo> listaProcessos, int totalProcessos) {
		Processo x;
		System.out.println("  ID         ArrivalTime        FinishTime          BurstTime                 TurnAround");
		for(int i = 0; i < totalProcessos; i++) {
			x = listaProcessos.get(i);
			System.out.printf("%3d        	%3d         	   %3d         	      %3d        		 %3d\n", 
					x.getIdProcesso(), x.getArrivalTime(), x.getFinishTime(), x.getBurstTime(), x.getTurnAround());
		}
	}
	
	public static void scanTempoManual(LinkedList<Processo> listaProcessos, int totalProcessos, int quantum, Scanner sc) {
		int arrivalTime, burstTime;
		for(int i = 0; i < totalProcessos; i++) {
			System.out.println("Processo " + (i+1) + ":");
			System.out.print("Entre com arrivalTime: ");
			arrivalTime = sc.nextInt();
			System.out.print("Entre com burstTime: ");
			burstTime = sc.nextInt();
			Processo x = new Processo(i+1, arrivalTime, burstTime, burstTime, quantum);
			listaProcessos.add(x);
		}
	}
	
	public static void scanTempoAleatorio(LinkedList<Processo> listaProcessos, int totalProcessos, int quantum) {
		int arrivalTime, burstTime;
		Random rand = new Random();
		for(int i = 0; i < totalProcessos; i++) {
			System.out.println("Processo " + (i+1) + ":");
			arrivalTime = rand.nextInt(101);
			burstTime = rand.nextInt(51);
			burstTime++; // para nao pegar resultado 0!!
			Processo x = new Processo(i+1, arrivalTime, burstTime, burstTime, quantum);
			listaProcessos.add(x);
		}
	}

	public static void main(String[] args) {
		LinkedList<Processo> listaProcesso = new LinkedList<>();
		EscalonadorRR escalonador;
		int n, quantum, flag;
		Scanner sc = new Scanner(System.in);
		System.out.print("Entre com o total de processos: ");
		n = sc.nextInt();
		System.out.print("Entre com o quantum (timeslice): ");
		quantum = sc.nextInt();
		System.out.println("Entrada de Dados:");
		System.out.println("1 - Entrada Manual");
		System.out.println("2 - Entrada Aleatoria");
		flag = sc.nextInt();
		switch(flag) {
		case 1:
			scanTempoManual(listaProcesso, n, quantum, sc);
			break;
		case 2:
			scanTempoAleatorio(listaProcesso, n, quantum);
			break;
		default:
			System.out.println("Opcao invalida!!");
		}
		escalonador = new EscalonadorRR(listaProcesso, n, quantum);
		printLista(listaProcesso, n);
		escalonador.start();
		while(escalonador.isAlive()) 
			;
		listaProcesso = escalonador.getListaProcesso();
		printLista(listaProcesso, n);
		sc.close();
	}

}
