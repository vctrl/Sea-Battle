public class Game {
    public static void main(String[] args) {
        Field field = new Field();
        Player player = new Player();
        field.setField();
        field.showMap1();
        field.showMap();
        do {
            player.getShoot();
            int shootResult = field.doShoot(player.getX(), player.getY());
            if (shootResult == 0) System.out.println("miss!");
            if (shootResult == 1) System.out.println("you've hit a ship!");
            if (shootResult == 2) System.out.println("you've sank the ship!");
            if (shootResult == 3) System.out.println("you've already shot to this cell");
            // todo method showShootResult
            field.showMap();
        } while (!field.isGameOver());
        System.out.println("Game over.");
        //todo number of shots, final score == shots/20
    }
}
