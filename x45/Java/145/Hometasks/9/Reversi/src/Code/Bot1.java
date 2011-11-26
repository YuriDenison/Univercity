package Code;

/**
 * User: Volkman
 * Date: 29.04.2010
 * Time: 5:09:49
 */
public class Bot1 implements IBot {

    public Cell getPos(Logic logic, int player) {
        int maxScore = 0;
        Cell maxScoreCell = null;

        Logic log = Logic.clone(logic);
        for (int i = 0; i < log.getROWS(); i++) {
            for (int j = 0; j < log.getCOLUMNS(); j++) {
                if (log.checkCourse(new Cell(i, j))) {
                    System.out.println("i = " + i + " j = " + j);
                    if((i == 0 && j == 0) || (i == 0 && j == log.getCOLUMNS() - 1) ||
                            (i == log.getROWS() - 1 && j == 0) ||
                            (i == log.getROWS() - 1 && j == log.getCOLUMNS() - 1)){
                        System.out.println("Itog - " + i + " " + j);
                        return new Cell(i, j);
                    }
                    log.setCourse(new Cell(i, j));
                    int curScore = log.getSumPlayer(player);
                    if (curScore > maxScore) {
                        maxScore = curScore;
                        maxScoreCell = new Cell(i, j);
                    }
                    log = Logic.clone(logic);
                }
            }
        }
        if(maxScoreCell == null){
            return new Cell(-1, -1);
        }
        System.out.println("Itog - " + maxScoreCell.x + " " + maxScoreCell.y);
        return maxScoreCell;
    }
}
