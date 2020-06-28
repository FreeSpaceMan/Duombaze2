import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Database db = new Database("kcs12");
//        System.out.println("pries");
        db.printAll();
//        db.printZinutes();
//        System.out.println(db.gautiZinutes());
//        System.out.println(db.gautiZinutes().size());
////        db.iterptiVartotoja("pienius","ramanauskas", 30);
////        db.pakeistiVarda(1, "Mykolas");
//        db.istrintiVartotoja(2);
//        System.out.println("po");
//        db.printAll();

        // Serverio adresas : http://localhost:4567/home
        Spark.port(80);
        Spark.get("/gautiZinute", (request,response) -> {
            return db.gautiZinutes();
        });

        System.out.println(db.gautiZinutes());





        Spark.get("/home", (request, response) -> getTextFromFile("Home.html"));
        Spark.post("/login", (request, response) -> {
            String email = request.queryParams("email"); // situos gaunam is androido
            String password = request.queryParams("slaptazodis");// situos gaunam is androido
            System.out.println("Vardas: " + email + ", slaptazodis: " + password);

            User user = db.prisijungti(email, password); // Userio objektui paduodam duombazes metoda, kuriam paduodam parametrus is android

            if (user != null) {
                JSONObject json = new JSONObject();
                json.put("id", user.getId());
                json.put("vardas", user.getVardas());
                json.put("email", user.getEmail());
                json.put("password", user.getPassword());
                json.put("amzius", user.getAmzius());
                return json.toString();
            } else {
                response.status(400);
                return "Nepavyko prisijungti";
            }


        });

        //Bauda #14 paskaitos kodas
        Spark.post("/bauda", (request, response) -> {

            int baudosSuma = Integer.parseInt(request.queryParams("bauda"));
            int userId = Integer.parseInt(request.queryParams("userId"));
            Bauda bauda = new Bauda(-1, baudosSuma, -1, userId);
            db.iterptiBauda(bauda);
            return "Success";
        });

//        //zemiau mano zinuciu kodas su SPARK
        Spark.post("/zinute", (request, response) -> {
            System.out.println("/zinute");

             String zinutesTekstas = request.queryParams("zinute");
             int userId = Integer.parseInt(request.queryParams("userId"));
             Zinutes zinutes = new Zinutes(-1,zinutesTekstas,-1,userId);
             db.iterptiZinute(zinutes);
//            System.out.println("Zinute: " + zinutesTekstas + ", UserId: " + userId);
             return "Success";

        });





    }


    /**
     * Teksto nuskaitymas iš failo resursų kataloge
     */
    private static String getTextFromFile(String path) {
        try {
            URI fullPath = Main.class.getClassLoader().getResource(path).toURI();
            return Files.readString(Paths.get(fullPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Loading error";
    }


}
