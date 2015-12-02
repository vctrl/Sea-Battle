import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {
    private int x, y;
    private String shoot;
    private static Pattern pattern = Pattern.compile("^[a-j]10|[a-j][0-9]$");
    public static boolean validate(String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
    public void getShoot() {
        System.out.println("Enter coordinates of shoot. Example: d5");
        //todo random example
        Scanner scanner = new Scanner(System.in);
        shoot = scanner.nextLine();
        while (validate(shoot) == false) {
            System.out.println("Please enter correct coordinates. Example: a10");
            shoot = scanner.nextLine();
        }
        if(shoot.length() == 3)
            x = 9;
        else
            x = Character.getNumericValue(shoot.charAt(1)) - 1;
        y = (int) shoot.charAt(0) - 97;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
