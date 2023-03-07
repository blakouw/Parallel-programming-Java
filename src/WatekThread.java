class WatekThread extends Thread {
    private int id;
    private char character;
    private Obraz obraz;

    public WatekThread(int id, char character, Obraz obraz) {
        this.id = id;
        this.character = character;
        this.obraz = obraz;

    }
    public void run() {
        obraz.calculate_histogram_watekThread(character);
        int histValue = obraz.getHistogramForChar((char)(character-33));
        String znakDoZliczenia = "";
        for(int i = 0; i < histValue; i++) {
            znakDoZliczenia += "=";
        }
        synchronized(obraz.getPrintLock()) {
            System.out.println("Thread id: " + id +" char: " + character +": "+ znakDoZliczenia + "\n");
        }
    }
}