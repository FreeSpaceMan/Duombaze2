public class Bauda {
    private int id;
    private int bauda;
    private long data;
    private int userId;

    public Bauda(int id, int bauda, long data, int userId) {
        this.id = id;
        this.bauda = bauda;
        this.data = data;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBauda() {
        return bauda;
    }

    public void setBauda(int bauda) {
        this.bauda = bauda;
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
