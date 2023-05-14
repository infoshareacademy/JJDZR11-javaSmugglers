package pl.isa.javasmugglers.model;

public class ExamAnswer {

    private Integer id;
    private String answer_text;
    private boolean is_correct;
    private Integer question_id;

    public ExamAnswer(Integer id, String answer_text, boolean is_correct, Integer question_id) {
        this.id = id;
        this.answer_text = answer_text;
        this.is_correct = is_correct;
        this.question_id = question_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public boolean isIs_correct() {
        return is_correct;
    }

    public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }
}
