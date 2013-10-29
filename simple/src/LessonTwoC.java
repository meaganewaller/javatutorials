class LessonTwoC {
    static String text = "I'm a simple program";

    // Accessor method
    static String getText(){
        return text;
    }

    public static void main(String[] args) {
        String retrievedText = getText();
        System.out.println(retrievedText);
    }
}