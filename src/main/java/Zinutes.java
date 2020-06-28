public class Zinutes {
    private int id;
    private String zinute;
    private long data;
    private int userId;

    public Zinutes(int id, String zinute, long data, int userId) {
        this.id = id;
        this.zinute = zinute;
        this.data = data;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZinute() {
        return zinute;
    }

    public void setZinute(String zinute) {
        this.zinute = zinute;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
