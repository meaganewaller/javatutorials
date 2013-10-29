class LessonTwoD {
    String text;

    // Constructor
    LessonTwoD(){
        text = "I'm a Simple Program";
    }

    // Accessor method
    String getText(){
        return text;
    }

    public static void main(String[] args) {
        LessonTwoD progInst = new LessonTwoD();
        String retrievedText = progInst.getText();
        System.out.println(retrievedText);
    }
}