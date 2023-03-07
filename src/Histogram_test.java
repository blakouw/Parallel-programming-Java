import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

class Histogram_test {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj wielkosc tablicy: n (rzedy), m (kolumny)");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Obraz obraz_1 = new Obraz(n, m);
        System.out.println("Wersja sekwencyjna:\n");
        obraz_1.calculate_histogram();
        obraz_1.print_histogram();

        System.out.println("extends Thread:\n");
        int num_threads = 94;
        List<WatekThread> watekThr = new ArrayList<>(num_threads);

        for (int i = 0; i < num_threads; i++) {
            watekThr.add(new WatekThread(i, (char) (i + 33), obraz_1));
            watekThr.get(watekThr.size() - 1).start();
        }

        for (WatekThread watek : watekThr) {
            try {
                watek.join();
            } catch (InterruptedException e) {
                System.err.println("Exception in synchronized method");
            }
        }

        System.out.println("implements Runnable: \n");
        System.out.println("Podaj liczbe watkow:");
        int num_threadsRun = scanner.nextInt();
        List<Thread> watekRun = new ArrayList<>(num_threadsRun);
        double section = Math.ceil(94.0/num_threadsRun);

        for (int i = 0; i < num_threadsRun; i++) {
            int start = (int) section * i;//poczatek
            int end = (int) section * (i+1);
            //koniec nie moze byc wiekszy niz przedzial
            if (end > 94) {
                end = 94;
            }
            watekRun.add(new Thread(new WatekRun(obraz_1,start,end)));
            watekRun.get(watekRun.size()-1).start();
        }

        for (Thread thread : watekRun) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Exception in synchronized method");
            }
        }

        if(obraz_1.poprawnosc()) {
            System.out.println("\nTakie same histogramy");
        }
        else {
            System.out.println("\nHistogramy sa rożne");
        }

    }
}


