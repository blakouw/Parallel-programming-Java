
public class WatekRun implements Runnable {

    private Obraz obraz;
    private int start;
    private int end;
    public WatekRun(Obraz obraz, int start, int end) {
        this.obraz=obraz;
        this.start=start;
        this.end = end;
    }

    @Override
    public void run(){
        for (int i = 0; i < end - start; i++){
           obraz.calculate_histogram_watekRun(start + i);
           obraz.print_histogram_watekRun(start + i, i);
        }
    }
}

