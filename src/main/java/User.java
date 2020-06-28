public class User {
    private int id;
    private String email;
    private String password;
    private String vardas;
    private int amzius;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public int getAmzius() {
        return amzius;
    }

    public void setAmzius(int amzius) {
        this.amzius = amzius;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}