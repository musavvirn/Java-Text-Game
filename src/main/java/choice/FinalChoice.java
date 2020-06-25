package choice;

public class FinalChoice extends Choice {
    public FinalChoice(String name) {
        super(name);
    }

    public FinalChoice(String name, String activeText) {
        super(name, activeText);
    }

    @Override
    public void runChoiceSelection() throws Exception {
        this.parentChoice.parentChoice.removeChoice(this.parentChoice);
        this.parentChoice.parentChoice.runChoiceSelection();
    }
}
