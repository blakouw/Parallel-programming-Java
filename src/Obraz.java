
import  java.util.Random;


class Obraz {
    private Object printLock = new Object();
    private int size_n;
    private int size_m;
    private char[][] tabChar;
    private char[] tabSymb;
    private int[] histogram;
    private int[] histRownolegly1;
    private int[] histRownolegly2;
    public Obraz(int n, int m) {

        this.size_n = n;
        this.size_m = m;
        tabChar = new char[n][m];
        tabSymb = new char[94];
        final Random random = new Random();

        // for general case where symbols could be not just integers
        for(int k=0;k<94;k++) {
            tabSymb[k] = (char)(k+33); // substitute symbols
        }

        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) {
                tabChar[i][j] = tabSymb[random.nextInt(94)];  // ascii 33-127
                //tab[i][j] = (char)(random.nextInt(94)+33);  // ascii 33-127
                System.out.print(tabChar[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");

        histogram = new int[94];
        histRownolegly1 = new int[94];
        histRownolegly2 = new int[94];
        clear_histograms();
    }


    public int getRowSize() { return size_n; }
    public int getColumnSize() { return size_m; }

    public void clear_histograms(){
        for(int i=0;i<94;i++) histogram[i]=0;
        for(int i=0;i<94;i++) histRownolegly1[i]=0;//oczyszczenie histogramow dla runnable thread
        for(int i=0;i<94;i++) histRownolegly2[i]=0;
    }

    public void calculate_histogram(){

        for(int i=0;i<size_n;i++) {
            for(int j=0;j<size_m;j++) {
                for(int k=0;k<94;k++) {
                    if(tabChar[i][j] == tabSymb[k]) histogram[k]++;
                }
            }
        }
    }
    public Object getPrintLock() {//do synchonizacji w watekThread
        return printLock;
    }
    public void calculate_histogram_watekThread(char c) {//analogiczne obliczanie histogramu dla extends Thread

        for(int i=0;i<size_n;i++) {
            for(int j=0;j<size_m;j++) {
                if(tabChar[i][j] == c) histRownolegly1[(int)(c-33)]++;
            }
        }
    }
    public void calculate_histogram_watekRun(int id) {//analogiczne obliczanie histogramu dla implements Runnable
        for (int i = 0; i < size_n; i++) {
            for (int j = 0; j < size_m; j++) {
                if (tabChar[i][j] == (char) (id + 33)) histRownolegly2[id]++;
            }
        }
    }
    public int getHistogramForChar(char c) {//getter dla extends Thread
        return histRownolegly1[c];
    }
    public boolean poprawnosc() { //sprawdzenie poprawnosci histogramow
        for(int k=0;k<94;k++) {
            if(histogram[k] != histRownolegly1[k]) {
                return false;
            }
            else if (histogram[k] != histRownolegly2[k]) {
                return false;
            }
        }
        return true;
    }
    public void print_histogram(){
        for(int i=0;i<94;i++) {
            System.out.print(tabSymb[i]+" "+histogram[i]+"\n");
            //System.out.print((char)(i+33)+" "+histogram[i]+"\n");
        }
    }
    public synchronized void print_histogram_watekRun(int id, int idWatku) {
        System.out.print("Thread id: " + idWatku + " char: " + (char) (id + 33) + ": ");//wyswietlanie "==" (znalezione)
        for (int i = 0; i < histRownolegly2[id]; i++) {
            System.out.print("=");
        }
        System.out.println("\n");
    }
    public int[] getHistRownolegly2() {
        return histRownolegly2;
    }
}