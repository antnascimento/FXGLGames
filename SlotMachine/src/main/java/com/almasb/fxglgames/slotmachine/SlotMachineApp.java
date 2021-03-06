package com.almasb.fxglgames.slotmachine;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxglgames.slotmachine.control.WheelControl;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class SlotMachineApp extends GameApplication {

    private static final int START_MONEY = 500;

    private SlotMachineFactory entityFactory;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Slot Machine");
        settings.setVersion("0.1");
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setIntroEnabled(false);
        settings.setMenuEnabled(false);
        settings.setProfilingEnabled(false);
        settings.setCloseConfirmation(false);
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("money", START_MONEY);
    }

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    @Override
    protected void initGame() {
        entityFactory = new SlotMachineFactory();

        getGameWorld().addEntities(entityFactory.buildWheels());
        getGameWorld().addEntities(entityFactory.buildBackground());
        getGameWorld().addEntity(entityFactory.buildLever());
    }

    @Override
    protected void initUI() {
        Text textMoney = new Text();

        textMoney.layoutBoundsProperty().addListener((observable, oldValue, newBounds) -> {
            textMoney.setTranslateX(getWidth() / 2 - newBounds.getWidth() / 2);
        });

        textMoney.setTranslateY(50);
        textMoney.setFont(Font.font(36));
        textMoney.setFill(Color.WHITE);
        textMoney.textProperty().bind(getGameState().intProperty("money").asString("$%d"));

        getGameScene().addUINode(textMoney);
    }

    private List<Integer> spinValues = new ArrayList<>();

    public boolean isMachineSpinning() {
        return getWheels().stream()
                .filter(WheelControl::isSpinning)
                .count() > 0;
    }

    public void spin() {
        getWheels().forEach(WheelControl::spin);
    }

    public List<WheelControl> getWheels() {
        return getGameWorld()
                .getEntitiesByType(SlotMachineType.WHEEL)
                .stream()
                .map(e -> e.getControlUnsafe(WheelControl.class))
                .collect(Collectors.toList());
    }

    public void onSpinFinished(int value) {
        spinValues.add(value);

        if (spinValues.size() == 5) {
            spinValues.stream()
                    .collect(Collectors.groupingBy(i -> i))
                    .values()
                    .stream()
                    .mapToInt(List::size)
                    .max()
                    .ifPresent(this::giveMoney);

            spinValues.clear();
        }
    }

    public void giveMoney(int highestMatch) {
        int reward;

        if (highestMatch > 1) {
            reward = highestMatch * highestMatch * 50;
        } else {
            reward = -100;
        }

        getGameState().increment("money", reward);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
