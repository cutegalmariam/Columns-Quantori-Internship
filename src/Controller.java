import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {

    private final Model model;
    private View view;

    private boolean isPaused = false;
    private Thread pauseThread;

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
            case KeyEvent.VK_2:
                togglePause();
                break;
            case KeyEvent.VK_0:
                if (model.level > 0) {
                    model.decreaseLevel();
                    view.showLevel(model.level);
                }
                model.removedCellsCounter = 0;
                break;
            case KeyEvent.VK_1:
                if (model.level < Model.MaxLevel) {
                    model.increaseLevel();
                    view.showLevel(model.level);
                }
                model.removedCellsCounter = 0;
                break;


        }
    }
    private void togglePause() {
        if (isPaused) {
            model.setPaused(false);
            Columns.scheduleSlideDown(model, model.currentSpeed);
        } else {
            model.setPaused(true);
            Columns.cancelSlideDown();
        }
        isPaused = !isPaused;
    }
}
