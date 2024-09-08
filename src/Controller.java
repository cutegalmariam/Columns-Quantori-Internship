import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {

    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        processUserActions(e.getKeyCode());
    }

    private void processUserActions(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                model.tryMoveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                model.tryMoveRight();
                break;
            case KeyEvent.VK_UP:
                model.rotateUp();
                break;
            case KeyEvent.VK_DOWN:
                model.rotateDown();
                break;
            case KeyEvent.VK_SPACE:
                model.dropFigure(model.Fig);
                break;
            case KeyEvent.VK_MINUS:
                if (model.level > 0)
                    model.decreaseLevel();
                model.removedCellsCounter = 0;
                // Assuming a method to update view is called from the controller
                // view.showLevel(model.level);
                break;
            case KeyEvent.VK_PLUS:
                if (model.level < Model.MaxLevel)
                    model.increaseLevel();
                model.removedCellsCounter = 0;
                // Assuming a method to update view is called from the controller
                // view.showLevel(model.level);
                break;
            // Add more cases if needed
        }
    }
}
