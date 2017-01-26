public class NBody {

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		int numPlanets = planets.length;
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		double time = 0;
		StdAudio.play("audio/2001.mid");
		while (time < T) {
			double[] xForces = new double[numPlanets];
			double[] yForces = new double[numPlanets];
			for (int i = 0; i < numPlanets; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
				planets[i].update(dt, xForces[i], yForces[i]);
				StdDraw.picture(0, 0, "images/starfield.jpg");
				for (int k = 0; k < planets.length; k++) {
					planets[k].draw();
				}
				StdDraw.show(10);
			}
			time += dt;
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
   			planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);	
		}	
	}

	public static double readRadius(String filename) {
		In in = new In(filename);
		int numPlanets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String filename) {
		In in = new In(filename);
		int numPlanets = in.readInt();
		double radius = in.readDouble();
		Planet[] planets = new Planet[numPlanets];
		for (int i = 0; i < numPlanets; i++) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = "images/" + in.readString();
			planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
		}
		return planets;
	}
}
