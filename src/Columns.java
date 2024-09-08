//import javax.swing.JTabbedPane.ModelListener;
//import columns.ModelListenerDupe;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Columns extends Applet implements ModelListener {

	static final int TimeShift = 250;
	static final int MinTimeShift = 200;

	Model model = new Model();
	View view = new View();
	Controller controller;
	private ScheduledExecutorService timer;

	@Override
	public void init() {
		model.initModel();
		model.addListener(this);
		view.initView(getGraphics());
		model.initMatrixes();
		model.initMembers();
		model.createNewFigure();
		controller = new Controller(model);
		addKeyListener(controller);
		requestFocus();
	}

	@Override
	public void start() {
		view.gr.setColor(Color.black);
		timer = Executors.newSingleThreadScheduledExecutor();
		timer.scheduleAtFixedRate(() -> {
			// If the game is paused, don't update the game state
			if (!controller.isPaused()) {
				model.trySlideDown();  // Only move figures if the game isn't paused
			}
		}, 1, 1, TimeUnit.SECONDS);
	}


	@Override
	public void paint(Graphics g) {
		g.setColor(Color.black);
		view.showLevel(model.level);
		view.showScore(model.Score);
		view.drawField(model.newField);
		view.drawFigure(model.Fig);
		requestFocus();
	}

	@Override
	public void stop() {
		timer.shutdown();
	}

	// ModelListener implementations

	@Override
	public void foundNeighboursAt(int a, int b, int c, int d, int i, int j) {
		view.highlightNeighbours(a, b, c, d, i, j);
	}

	@Override
	public void figureMovedFrom(int oldX, int oldY) {
		view.moveAndDrawFigure(model.Fig, oldX, oldY);
	}

	@Override
	public void figureUpdated(Figure fig) {
		view.drawFigure(fig);
	}

	@Override
	public void fieldUpdated(int[][] newField) {
		view.drawField(newField);
	}

	@Override
	public void scoreHasChanged(int score) {
		view.showScore(score);
	}

	@Override
	public void levelHasChanged(int level) {
		view.showLevel(level);
	}

	@Override
	public void gameOver() {
		// TODO: Implement game over logic
	}
}
