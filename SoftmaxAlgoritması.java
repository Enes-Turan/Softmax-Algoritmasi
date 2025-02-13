import java.util.Arrays;

 class SoftmaxRouteSelection {
    public static void main(String[] args) {
        // 3 mahalle için kriter değerleri (nüfus yoğunluğu, ulaşım altyapısı, maliyet, çevresel etki, sosyal fayda)
        double[][] kriterVerileri = {
                {85, 75, 55, 80, 95},
                {65, 85, 70, 90, 80},
                {95, 60, 75, 70, 85}
        };

        // Softmax hesaplaması
        double[] agirlikliSkorlar = hesaplaSoftmax(kriterVerileri);

        int enUygunMahalle = enIyiGuzergah(agirlikliSkorlar);

        String[] mahalleIsimleri = {"Karakaş Mahallesi", "Bademlik Mahallesi", "İstasyon Mahallesi"};
        System.out.println("En iyi toplu taşıma güzergahı: " + mahalleIsimleri[enUygunMahalle]);

        // Maliyet-fayda analizi gerçekleştir
        maliyetFaydaAnalizi(kriterVerileri, enUygunMahalle);
    }

    public static double[] hesaplaSoftmax(double[][] veriler) {
        int mahalleSayisi = veriler.length;
        int kriterSayisi = veriler[0].length;
        double[] skorlar = new double[mahalleSayisi];

        for (int i = 0; i < kriterSayisi; i++) {
            double[] kriterDegerleri = new double[mahalleSayisi];
            for (int j = 0; j < mahalleSayisi; j++) {
                kriterDegerleri[j] = veriler[j][i];
            }

            double[] softmaxDegerleri = softmax(kriterDegerleri);

            for (int j = 0; j < mahalleSayisi; j++) {
                skorlar[j] += softmaxDegerleri[j];
            }
        }
        return skorlar;
    }

    public static double[] softmax(double[] x) {
        double max = Arrays.stream(x).max().orElse(0);
        double sumExp = 0;
        double[] expValues = new double[x.length];

        for (int i = 0; i < x.length; i++) {
            expValues[i] = Math.exp(x[i] - max);
            sumExp += expValues[i];
        }

        double[] softmaxValues = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            softmaxValues[i] = expValues[i] / sumExp;
        }
        return softmaxValues;
    }

    public static int enIyiGuzergah(double[] skorlar) {
        int enIyiIndex = 0;
        double maxSkor = skorlar[0];

        for (int i = 1; i < skorlar.length; i++) {
            if (skorlar[i] > maxSkor) {
                maxSkor = skorlar[i];
                enIyiIndex = i;
            }
        }
        return enIyiIndex;
    }

    public static void maliyetFaydaAnalizi(double[][] veriler, int enUygunMahalle) {
        double maliyet = veriler[enUygunMahalle][2];
        double fayda = veriler[enUygunMahalle][4];
        double analizSonucu = fayda / maliyet;

        System.out.println("Maliyet-Fayda Analizi: ");
        System.out.println("Seçilen mahallede maliyet: " + maliyet);
        System.out.println("Seçilen mahallede sosyal fayda: " + fayda);
        System.out.println("Maliyet-fayda oranı: " + analizSonucu);
    }
}
