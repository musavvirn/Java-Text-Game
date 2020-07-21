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
        this.parent.parent.removeChoice(this.parent);
        this.parent.parent.runChoiceSelection();
    }
}
