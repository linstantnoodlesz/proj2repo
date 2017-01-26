public class Planet {

	double xxPos,
		yyPos,
		xxVel,
		yyVel,
		mass;
	String imgFileName;
	double G = 6.67e-11;

	/** Constructor for new planet.
		xxPos is the x-position, yyPos is the y-position, xxVel is the velocity of the planet in the x-direction,
		yyVel is the velocity in the y-direction, mass is the mass of the planet, and imgFileName is the name of the
		image file of the planet as a String. **/
	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	// Planet constructor that creates a copy of Planet p
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	//Calculates the distance between this planet and planet p.
	public double calcDistance(Planet p) {
		return Math.sqrt((xxPos - p.xxPos)*(xxPos - p.xxPos) + (yyPos - p.yyPos)*(yyPos - p.yyPos));
	}

	//Calculates the force exerted by planet p by this planet.
	public double calcForceExertedBy(Planet p) {
		double dist = calcDistance(p);
		return G * mass * p.mass / (dist * dist);
	}


	//Separates force exerted by planets into x- and y-components
	public double calcForceExertedByX(Planet p) {
		return calcForceExertedBy(p) * (p.xxPos - xxPos) / calcDistance(p);
	}

	public double calcForceExertedByY(Planet p) {
		return calcForceExertedBy(p) * (p.yyPos - yyPos) / calcDistance(p);
	}


	//Calculates the net force exerted by several planets on this planet, and separates
	// it into its x- and y-components.
	public double calcNetForceExertedByX(Planet[] planets) {
		double netForceX = 0;
		for (int i = 0; i < planets.length; i++) {
			if (!this.equals(planets[i])) {
				netForceX += calcForceExertedByX(planets[i]);
			}
		}
		return netForceX;
	}

	public double calcNetForceExertedByY(Planet[] planets) {
		double netForceY = 0;
		for (int i = 0; i < planets.length; i++) {
			if (!this.equals(planets[i])) {
				netForceY += calcForceExertedByY(planets[i]);
			}
		}
		return netForceY;
	}


	//Updates the planets position and velocity after dt time has passed, 
	//given the net forces exerted on it.
	public void update(double dt, double fX, double fY) {
		double accelerX = fX / mass;
		double accelerY = fY / mass;
		xxVel += dt * accelerX;
		yyVel += dt * accelerY;
		xxPos += dt * xxVel;
		yyPos += dt * yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, imgFileName);
	}
}
