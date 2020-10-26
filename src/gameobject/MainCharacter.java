package gameobject;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import util.Animation;
import util.Resource;

public class MainCharacter {

	public static final int LAND_POSY = 80;
	public static final float GRAVITY = 0.4f;

	private static final int NORMAL_RUN = 0;
	private static final int JUMPING = 1;
	private static final int DOWN_RUN = 2;
	private static final int DEATH = 3;

	private float posY;
	private float posX;
	private float speedX;
	private float speedY;
	private Rectangle rectBound;

	public int score = 0;

	private int state = NORMAL_RUN;

	private Animation normalRunAnim;
	private BufferedImage jumping;
	private Animation downRunAnim;
	private BufferedImage deathImage;

	private AudioInputStream jumpSound;
	private AudioInputStream deadSound;
	private AudioInputStream scoreUpSound;
	Clip clip;

	public MainCharacter() throws MalformedURLException {
		posX = 50;
		posY = LAND_POSY;
		rectBound = new Rectangle();
		normalRunAnim = new Animation(90);
		normalRunAnim.addFrame(Resource.getResouceImage("data/main-character1.png"));
		normalRunAnim.addFrame(Resource.getResouceImage("data/main-character2.png"));
		jumping = Resource.getResouceImage("data/main-character3.png");
		downRunAnim = new Animation(90);
		downRunAnim.addFrame(Resource.getResouceImage("data/main-character5.png"));
		downRunAnim.addFrame(Resource.getResouceImage("data/main-character6.png"));
		deathImage = Resource.getResouceImage("data/main-character4.png");

		try {
			// jumpSound = Applet.newAudioClip(new URL("file","","data/jump.wav"));
			jumpSound = AudioSystem.getAudioInputStream(new URL("file", "", "data/jump.wav"));
			// deadSound = Applet.newAudioClip(new URL("file","","data/dead.wav"));
			deadSound = AudioSystem.getAudioInputStream(new URL("file", "", "data/dead.wav"));
			// scoreUpSound = Applet.newAudioClip(new URL("file","","data/scoreup.wav"));
			scoreUpSound = AudioSystem.getAudioInputStream(new URL("file", "", "data/scoreup.wav"));
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println(e);
		}
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void draw(Graphics g) {
		switch (state) {
			case NORMAL_RUN:
				g.drawImage(normalRunAnim.getFrame(), (int) posX, (int) posY, null);
				break;
			case JUMPING:
				g.drawImage(jumping, (int) posX, (int) posY, null);
				break;
			case DOWN_RUN:
				g.drawImage(downRunAnim.getFrame(), (int) posX, (int) (posY + 20), null);
				break;
			case DEATH:
				g.drawImage(deathImage, (int) posX, (int) posY, null);
				break;
		}
		// Rectangle bound = getBound();
		// g.setColor(Color.RED);
		// g.drawRect(bound.x, bound.y, bound.width, bound.height);
	}

	public void update() {
		normalRunAnim.updateFrame();
		downRunAnim.updateFrame();
		if (posY >= LAND_POSY) {
			posY = LAND_POSY;
			if (state != DOWN_RUN) {
				state = NORMAL_RUN;
			}
		} else {
			speedY += GRAVITY;
			posY += speedY;
		}
	}

	public void jump() {
		if (posY >= LAND_POSY) {
			if (jumpSound != null) {
				// try {
				// 	clip.open(jumpSound);
				// } catch (LineUnavailableException e) {
				// 	e.printStackTrace();
				// } catch (IOException e) {
				// 	e.printStackTrace();
				// }
				// clip.start();
				// jumpSound.play();
			}
			speedY = -7.5f;
			posY += speedY;
			state = JUMPING;
		}
	}
	
	public void down(boolean isDown) {
		if(state == JUMPING) {
			return;
		}
		if(isDown) {
			state = DOWN_RUN;
		} else {
			state = NORMAL_RUN;
		}
	}
	
	public Rectangle getBound() {
		rectBound = new Rectangle();
		if(state == DOWN_RUN) {
			rectBound.x = (int) posX + 5;
			rectBound.y = (int) posY + 20;
			rectBound.width = downRunAnim.getFrame().getWidth() - 10;
			rectBound.height = downRunAnim.getFrame().getHeight();
		} else {
			rectBound.x = (int) posX + 5;
			rectBound.y = (int) posY;
			rectBound.width = normalRunAnim.getFrame().getWidth() - 10;
			rectBound.height = normalRunAnim.getFrame().getHeight();
		}
		return rectBound;
	}
	
	public void dead(boolean isDeath) {
		if(isDeath) {
			state = DEATH;
		} else {
			state = NORMAL_RUN;
		}
	}
	
	public void reset() {
		posY = LAND_POSY;
	}
	
	public void playDeadSound() {
		// try {
		// 	clip.open(deadSound);
		// } catch (LineUnavailableException e) {
		// 	e.printStackTrace();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
		// clip.start();
		// deadSound.play();
	}
	
	public void upScore() {
		score += 20;
		if(score % 100 == 0) {
			// try {
			// 	clip.open(scoreUpSound);
			// } catch (LineUnavailableException e) {
			// 	e.printStackTrace();
			// } catch (IOException e) {
			// 	e.printStackTrace();
			// }
			// clip.start();
			// scoreUpSound.play();
		}
	}
	
}
