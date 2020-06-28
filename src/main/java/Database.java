import org.apache.commons.dbcp.BasicDataSource;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Database {
    private BasicDataSource dataSource;

    //konstruktorius
    public Database(String databaseName) {
        dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:mysql://localhost:3306/" + databaseName + "?useUnicode=yes&characterEncoding=UTF-8");
        dataSource.setValidationQuery("SELECT 1");

    }


    public void printAll() {
        String query = "SELECT * FROM vartotojai";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String vardas = resultSet.getString("vardas");
                String pavarde = resultSet.getString("pavarde");
                int amzius = resultSet.getInt("amzius");
                System.out.println("ID: " + id + ", Vardas: " + vardas
                        + ", Pavarde: " + pavarde + ", amzius: " + amzius);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void iterptiVartotoja(String vardas, String pavarde, int amzius) {
        String query = "INSERT INTO vartotojai (vardas, pavarde, amzius)"
                + " VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vardas);
            statement.setString(2, pavarde);
            statement.setInt(3, amzius);
//            statement.setInt(4, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void pakeistiVarda(int id, String vardas) {
        String query = "UPDATE vartotojai SET vardas=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vardas);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void istrintiVartotoja(int id) {
        String query = "DELETE FROM vartotojai WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int paliestuEiluciuSkaicius = statement.executeUpdate();
            System.out.println("Paliestu eiluciu skacius: " + paliestuEiluciuSkaicius);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //nezinau ar isvis cia mano metodui reikia parametru (turbut net nereik bet kolkas paliekam)
    // ar mano parametras turetu but gauta eilute???

    public JSONArray gautiZinutes(){
        String query = "SELECT * FROM zinutes";
        JSONArray jsonArray = new JSONArray();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                JSONObject json = new JSONObject();
                json.put("id",resultSet.getInt("id"));
                json.put("zinute",resultSet.getString("zinute"));
                json.put("data",resultSet.getLong("data"));
                json.put("userId",resultSet.getInt("user_id"));
                jsonArray.put(json);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }



    public User prisijungti(String email, String password) {
        String query = "SELECT * FROM vartotojai WHERE email=? AND password=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
             ) {

            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setVardas(resultSet.getString("vardas"));
                user.setAmzius(resultSet.getInt("amzius"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public void iterptiBauda(Bauda bauda) {
        String query = "INSERT INTO baudos (bauda, data, user_id) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bauda.getBauda());
            statement.setLong(2, System.currentTimeMillis());
            statement.setInt(3, bauda.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void iterptiZinute(Zinutes zinutes) {
        String query = "INSERT INTO zinutes (zinute, data, user_id) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, zinutes.getZinute());
            statement.setLong(2, System.currentTimeMillis());
            statement.setInt(3, zinutes.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void printZinutes() {
        String query = "SELECT * FROM zinutes";
        ArrayList<String> zinuciuSarasas = new ArrayList<String>();
        ArrayList<Integer> vartotojuSarasas = new ArrayList<Integer>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String zinute = resultSet.getString("zinute");
                int user_id = resultSet.getInt("user_id");
                zinuciuSarasas.add(zinute);
                vartotojuSarasas.add(user_id);


//                System.out.println("ID: " + id + ", Zinute: " + zinute
//                        + ", user_id : " + user_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(zinuciuSarasas);
        System.out.println(vartotojuSarasas);
        System.out.println(zinuciuSarasas.get(1));
    }

//    public ArrayList<String> gautiZinutes(){
//        String query = "SELECT * FROM zinutes";
//
//
//        ArrayList<String> zinute = new ArrayList<String>();
//
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//            while (resultSet.next()) {
//                String zinuteVar = resultSet.getString("zinute");
//
//
//                zinute.add(zinuteVar);
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return zinute;
//    }



}