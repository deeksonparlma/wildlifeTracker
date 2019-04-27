import org.sql2o.Connection;

import java.sql.Timestamp;
import java.util.Timer;

public class Report {
    private String rangerName;
    private String category;
    private String zone;
    private String name;
    private String health;
    private String age;
    private int id;
    private int categoryId;
    private Timer timer;
    private Timestamp time;

    public Report(String rangername,String category, String zone,String name,String health,String age,int categoryId){
      this.rangerName = rangername;
      this.category = category;
      this.zone = zone;
      this.categoryId = categoryId;
      this.name = name;
      this.health =health;
      this.age = age;
      this.timer = new Timer();
    }

    public Timestamp getTime() {
        return time;
    }

    public Timer getTimer() {
        return timer;
    }

    public String getCategory() {
        return category;
    }

    public String getRangerName() {
        return rangerName;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getHealth() {
        return health;
    }

    public String getZone() {
        return zone;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }
    //functionality//

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sighting (rangername, category, zone, name, health, age, categoryid, time) VALUES (:rangername, :category, :zone, :name , :health , :age , :categoryId ,now())";
            this.id= (int) con.createQuery(sql, true)
                    .addParameter("rangername", this.rangerName)
                    .addParameter("category", this.category)
                    .addParameter("zone", this.zone)
                    .addParameter("name", this.name)
                    .addParameter("health", this.health)
                    .addParameter("age", this.age)
                    .addParameter("categoryId", this.categoryId)
                    .executeUpdate()
                    .getKey();
        }


    }
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM sighting WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
    public static Report find(int id) {
        try(Connection con =   DB.sql2o.open()) {
            String sql = "SELECT * FROM sighting where id = :id;";
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Report.class);
        }
    }
    public void update() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE sighting health = :update  WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
