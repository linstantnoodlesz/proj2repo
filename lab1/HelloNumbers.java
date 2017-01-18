public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int i, sum;
        while (x < 10) {
            i = 0;
            sum = 0;
            while (i <= x) {
            	sum += i;
            	i += 1;
            }
            System.out.print(sum + " ");
            x += 1;
        }
        System.out.println("");
    }
}