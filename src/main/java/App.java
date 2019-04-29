import org.apache.log4j.BasicConfigurator;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");

        //port manager//
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);

        //log generator//
        BasicConfigurator.configure();

        //routes//
        String layout = "public/templates/layout.vtl";

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("categories", Category.all());
            model.put("template", "public/templates/category-list.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/form", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "public/templates/category-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/categories", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("categories", Category.all());
            model.put("template", "public/templates/category-list.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/category/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Category category =Category.find(Integer.parseInt(request.params(":id")));
            model.put("category", category);
            model.put("reports", category.getReport());
            model.put("template", "public/templates/category.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/report/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Report report =Report.find(Integer.parseInt(request.params(":id")));
            model.put("report", report);
            model.put("template", "public/templates/report.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //post request//
        post("/categories", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String category = request.queryParams("category");
            Category newCategory = new Category(category);
            newCategory.save();
            model.put("template", "public/templates/proceed.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/reports", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String rangername = request.queryParams("rangername");
            int category = Integer.parseInt(request.queryParams("categoryId"));
            String type = request.queryParams("category");
            String zone = request.queryParams("zone");
            String name = request.queryParams("name");
            String health = request.queryParams("health");
            String age = request.queryParams("age");
            int count = Integer.parseInt(request.queryParams("count"));

            Report newReport = new Report(rangername,type,zone,name,health,age,category,count);
            newReport.save();
            model.put("template", "public/templates/proceed.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/animal/check", (request, response) -> {
            HashMap<String, Object> model = new HashMap<String, Object>();
            Report animal = Report.find(Integer.parseInt(request.queryParams("id")));
            animal.delete();
            animal.sort();
            model.put("reports", animal);
            model.put("template", "public/templates/proceed.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

    }
}
