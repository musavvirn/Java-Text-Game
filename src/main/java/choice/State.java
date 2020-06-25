package choice;

/* STATE PATTERN
    State represents whether a choice leads to another set of choices,
    or activates a linked choice, or activates a mission etc.
 */

public enum State {

    REGULAR {
        @Override
        public void execute(Choice c) throws Exception {
            c.printEndText();
            c.printChoice();
            c.getUserSelection().runChoiceSelection();
        }
    },

    FINAL {
        @Override
        public void execute(Choice c) throws Exception {
            c.printEndText();
            c.checkFinalChoice();
            c.getUserSelection().runChoiceSelection();
        }
    },

    LINKED {
        @Override
        public void execute(Choice c) throws Exception {
            c.checkLinkedMission();
            c.printEndText();
            c.printChoice();
            c.getUserSelection().runChoiceSelection();
        }
    },

    LINKED_FINAL {
        @Override
        public void execute(Choice c) throws Exception {
            c.checkLinkedMission();
            c.printEndText();
            c.checkFinalChoice();
            c.printChoice();
            c.getUserSelection().runChoiceSelection();
        }
    };

    /* State determines which version of choice execution is performed

     */
    public void execute(Choice choice) throws Exception {}
}
