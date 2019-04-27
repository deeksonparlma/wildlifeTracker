import org.sql2o.Connection;

import java.util.List;

public class Category {
    private String type;
    private int id;
    public Category(String Type){
        this.type = Type;
    }

    public String gettype() {
        return type;
    }

    public int getId() {
        return id;
    }

    //overrides//

    @Override
    public boolean equals(Object othercategory) {
        if (!(othercategory instanceof Category)) {
            return false;
        } else {
            Category newCategory = (Category) othercategory;
            return this.gettype().equals(newCategory.gettype()) &&
                    this.getId() == newCategory.getId();
        }
    }


    public static List<Category> all() {
        String sql = "SELECT * FROM  category";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Category.class);
        }
    }


    public List<Report> getReport() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sighting where categoryid=:id";
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
                    .addParameter("category", this.type)
                    .executeUpdate()
                    .getKey();
        }

    }
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM category WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
