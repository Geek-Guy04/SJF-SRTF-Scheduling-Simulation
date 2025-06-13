import java.util.Scanner;

public class OSY2 {

    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void main(String[] args) {
        int ch;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println(CYAN + "\n\tSelect Algorithm:-" + RESET);
            System.out.println(GREEN + "1> SJF" + RESET);
            System.out.println(GREEN + "2> SRTF" + RESET);
            System.out.println(RED + "3> Exit" + RESET);

            ch = sc.nextInt();

            switch (ch) {
                case 1:
                    SJF();
                    break;

                case 2:
                    SRTF();
                    break;

                case 3:
                    System.out.println(RED + "Exiting the program. Goodbye!" + RESET);
                    System.exit(0);

                default:
                    System.out.println(RED + "Invalid choice..!" + RESET);
            }
        } while (ch != 3);

        sc.close();
    }

    public static void SRTF() {
        Scanner sc = new Scanner(System.in);
        int[] a = new int[10];
        int[] b = new int[10];
        int[] x = new int[10];
        int[] waiting = new int[10];
        int[] turnaround = new int[10];
        int[] completion = new int[10];
        int i, j, smallest, count = 0, time, n;
        double avg = 0, tt = 0, end;

        System.out.print(CYAN + "\nEnter the number of Processes: " + RESET);
        n = sc.nextInt();
        for (i = 0; i < n; i++) {
            System.out.print(GREEN + "Enter arrival time of process " + (i + 1) + ": " + RESET);
            a[i] = sc.nextInt();
        }
        System.out.print("\n");
        for (i = 0; i < n; i++) {
            System.out.print(GREEN + "Enter burst time of process " + (i + 1) + ": " + RESET);
            b[i] = sc.nextInt();
        }
        for (i = 0; i < n; i++)
            x[i] = b[i];
        b[9] = 9999;
        for (time = 0; count != n; time++) {
            smallest = 9;
            for (i = 0; i < n; i++) {
                if (a[i] <= time && b[i] < b[smallest] && b[i] > 0)
                    smallest = i;
            }
            b[smallest]--;
            if (b[smallest] == 0) {
                count++;
                end = time + 1;
                completion[smallest] = (int) end;
                waiting[smallest] = (int) (end - a[smallest] - x[smallest]);
                turnaround[smallest] = (int) (end - a[smallest]);
            }
        }
        System.out.println(CYAN + "Process\tBurst Time\tArrival Time\tWaiting Time\tTurnaround Time\tCompletion Time" + RESET);
        for (i = 0; i < n; i++) {
            System.out.printf("p%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\n", (i + 1), x[i], a[i], waiting[i], turnaround[i], completion[i]);
            avg += waiting[i];
            tt += turnaround[i];
        }
        System.out.printf("\n" + YELLOW + "Avg WT = %.2f msec" + RESET, avg / n);
        System.out.printf("\n" + YELLOW + "Avg TAT = %.2f msec\n" + RESET, tt / n);
    }

    public static void SJF() {
        int[] bt = new int[20];
        int[] p = new int[20];
        int[] wt = new int[20];
        int[] tat = new int[20];
        int i, j, n, total = 0, totalT = 0, pos, temp;
        float avg_wt, avg_tat;
        Scanner sc = new Scanner(System.in);

        System.out.print(CYAN + "Enter number of processes: " + RESET);
        n = sc.nextInt();

        System.out.println("\n" + GREEN + "Enter Burst Time:" + RESET);
        for (i = 0; i < n; i++) {
            System.out.print(GREEN + "p" + (i + 1) + ": " + RESET);
            bt[i] = sc.nextInt();
            p[i] = i + 1;
        }

        for (i = 0; i < n; i++) {
            pos = i;
            for (j = i + 1; j < n; j++) {
                if (bt[j] < bt[pos])
                    pos = j;
            }

            temp = bt[i];
            bt[i] = bt[pos];
            bt[pos] = temp;

            temp = p[i];
            p[i] = p[pos];
            p[pos] = temp;
        }

        wt[0] = 0;

        for (i = 1; i < n; i++) {
            wt[i] = 0;
            for (j = 0; j < i; j++)
                wt[i] += bt[j];

            total += wt[i];
        }

        avg_wt = (float) total / n;

        System.out.println("\n" + CYAN + "Process\tBurst Time\tWaiting Time\tTurnaround Time" + RESET);
        for (i = 0; i < n; i++) {
            tat[i] = bt[i] + wt[i];
            totalT += tat[i];
            System.out.printf("p%d\t\t%d\t\t%d\t\t%d\n", p[i], bt[i], wt[i], tat[i]);
        }

        avg_tat = (float) totalT / n;
        System.out.printf("\n" + YELLOW + "Average Waiting Time = %.2f msec" + RESET, avg_wt);
        System.out.printf("\n" + YELLOW + "Average Turnaround Time = %.2f msec\n" + RESET, avg_tat);
    }
}
