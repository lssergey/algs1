import java.util.Arrays;

public class Fast {

    private static int binarySearch(double[] a, double key, int end) {
        int lo = 0;
        int hi = end - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    private static void putLines(Point[] points, int start, int end) {
        int firstEqual = start;
        int equal = 1;
        Point org = points[start-1];
        double[] slopes = new double[end];
        // Формируем массив наклонов, который понадобится нам
        // при бинарном поиске повторов.
        for (int i = 0; i < end; i++) {
            slopes[i] = org.slopeTo(points[i]);
        }

        for (int i = start + 1; i < end; i++) {
            Point prev = points[i - 1];
            Point current = points[i];
            boolean equals = org.slopeTo(prev) == org.slopeTo(current);

            if (equals) {
                // Считаем одинаковые наклоны
                equal++;
            }

            // Если наклоны текущий и предыдущей точки не совпадают,
            // или мы уже на последней точке
            if (!equals || i == end -1) {

                // Если обнаружили 3 и более точки с одинаковым наклоном
                // по отношению к org-точке
                if (equal >= 3) {
                    // Проверяем, не было ли уже этой линии до этого
                    double testSlope = org.slopeTo(points[firstEqual]);

                    // Если была, то binarySearch вернёт 0 или положительное число
                    boolean doIt = binarySearch(slopes, testSlope, start - 1) < 0;

                    if (doIt) {
                        // Ура! Мы нашли линию!
                        int lastEqual = firstEqual + equal;
                        Point[] tmp = new Point[equal + 1];

                        // Не забываем добавить точку отсчёта
                        tmp[0] = org;

                        // и все остальные точки с одинаковым наклоном
                        for (int j = 0; j < equal; j++) {
                            tmp[j + 1] = points[firstEqual + j];
                        }

                        // Сортируем, чтобы нарисовать отрезок от «самой маленькой»
                        // точки к «самой большой»
                        Arrays.sort(tmp);

                        // Отрисовываем отрезок
                        tmp[0].drawTo(tmp[equal]);

                        // Выводим на стандартный поток вывода
                        for (int j = 0; j < equal + 1; j++) {
                            StdOut.print(tmp[j]);
                            if (j < equal) {
                                StdOut.print(" -> ");
                            }
                        }
                        StdOut.println();
                    }
                }

                firstEqual = i;
                equal = 1;
            }
        }
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];

        // Загружаем точки в массив и сразу отрисовываем их
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        // -2 потому что в функцию имеет смысл передавать минимум три точки
        for (int i = 1; i < N - 2; i++) {
            // Сортируем обе части: и ту, что до текущей точки, и ту, что после.
            // Сортировка первой части нужна для бинарного поиска при проверке
            // повтора линии. Сортировка второй части необходима для выделения
            // точек с одинаковым наклоном к текущей.
            Arrays.sort(points, 0, i - 1, points[i - 1].SLOPE_ORDER);
            Arrays.sort(points, i, N, points[i - 1].SLOPE_ORDER);
            putLines(points, i, N);
        }

        StdDraw.show(0);
    }
}
