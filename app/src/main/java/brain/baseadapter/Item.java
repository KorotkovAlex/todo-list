package brain.baseadapter;

/**
 * Created by Саня on 13.03.2016.
 */
//
public class Item {
    private String text;
    private boolean done;// видимость переменной паблик, потомучто произошла проблема в файле ItemAdapter в 96 getTaskListItem((Integer) buttonView.getTag()).done = isChecked;
    private long topicId;
    private long id;
    public void setText(String text) {
        this.text = text;
    }
    public void setDone(boolean done) {
        this.done = done;
    }
    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean isDone() {
        return done;
    }
    public String getText() {
        return text;
    }
    public long getTopicId() {
        return topicId;
    }
    public long getId() {
        return id;
    }
}