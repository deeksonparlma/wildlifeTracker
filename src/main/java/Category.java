import org.sql2o.Connection;

import java.util.List;

public class Category {
    private String category;
    private int id;
    public Category(String type){
        this.category = type;
    }

    public String getType() {
        String category = this.category;
        return category;
    }



    //overrides//

    @Override
    public boolean equals(Object othercategory) {
        if (!(othercategory instanceof Category)) {
            return false;
        } else {
            Category newCategory = (Category) othercategory;
            return this.getType().equals(newCategory.getType()) &&
                    this.getId() == newCategory.getId();
        }
    }


    public static List<Category> all() {
        String sql = "SELECT id,category FROM category;";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Category.class);
        }
    }


    public  List<Report> getReport() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT id,rangername,category,zone,name,health,age,time  FROM sighting where categoryid=:id";
            return con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeAndFetch(Report.class);
        }
    }
    public static Category find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM category where id=:id";
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Category.class);
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO category (category) VALUES (:category)";
            this.id= (int) con.createQuery(sql, true)
                    .addParameter("category", this.category)
                    .executeUpdate()
                    .getKey();
        }

    }
//    public void delete() {
//        try(Connection con = DB.sql2o.open()) {
//            String sql = "DELETE FROM category WHERE id = :id;";
//            con.createQuery(sql)
//                    .addParameter("id", id)
//                    .executeUpdate();
//        }
//    }

    public int getId() {
        return id;
    }

}
