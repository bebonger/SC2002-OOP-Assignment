package sc2002.combat.ui;

public interface IUIOutput {
    default void displayMessage(String message) {
        if (message == null || message.isBlank()) {
            return;
        }

        System.out.println(message);
    }
}