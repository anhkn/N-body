public class NBody {
    public static void main(String[] args) {
        // recommended implementing order: 0, 1, 2, 6, 3, 4, 5, 5B, 5C, 5A
        // Step 1: Parse command-line arguments.
        double stoppingTime = Double.parseDouble(args[0]);
        double deltaTime = Double.parseDouble(args[1]);
        double GRAVITATIONAL_CONSTANT = 6.67e-11;

        // Step 2. Read the universe from standard input
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();
        double[] px = new double[n];
        double[] py = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];
        double[] mass = new double[n];
        String[] image = new String[n];

        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }

        // Step 3: Initialize standard drawing
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        // Step 4: Play music on standard audio
        // StdAudio.play("2001.wav");
        // Step 5: Simulate the universe (main time loop).
        double[] fx = new double[n];
        double[] fy = new double[n];
        for (double time = 0.0; time < stoppingTime; time += deltaTime) {
            // Step 5A. Calculate net forces
            for (int i = 0; i < n; i++) {
                fx[i] = 0;
                fy[i] = 0;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j)
                        continue;
                    fx[i] += (GRAVITATIONAL_CONSTANT * mass[i] * mass[j] *
                            (px[j] - px[i])) /
                            Math.pow(Math.sqrt((Math.pow(px[j] - px[i], 2) +
                                    Math.pow(py[j] - py[i], 2))), 3);
                    fy[i] += (GRAVITATIONAL_CONSTANT * mass[i] * mass[j] *
                            (py[j] - py[i])) /
                            Math.pow(Math.sqrt((Math.pow(px[j] - px[i], 2) +
                                    Math.pow(py[j] - py[i], 2))), 3);
                }
            }
            // Step 5B. Update velocities and positions
            for (int i = 0; i < n; i++) {
                vx[i] += (fx[i] / mass[i]) * deltaTime;
                vy[i] += (fy[i] / mass[i]) * deltaTime;
                px[i] += vx[i] * deltaTime;
                py[i] += vy[i] * deltaTime;
            }
            // Step 5C. Draw universe to standard drawing
            StdDraw.picture(0, 0, "starfield.jpg");
            for (int i = 0; i < n; i++)
                StdDraw.picture(px[i], py[i], image[i]);
            StdDraw.show();
            StdDraw.pause(20);
        }

        // Step 6. Print universe to standard output
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }
    }
}
