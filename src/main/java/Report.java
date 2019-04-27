public class Report {
    private String rangerName;
    private String category;
    private String zone;
    private String name;
    private String health;
    private String age;
    public Report(String rangername,String category, String zone,String name,String health,String age){
      this.rangerName = rangername;
      this.category = category;
      this.zone = zone;
      this.rangerName = rangername;
      this.name = name;
      this.health =health;
      this.age = age;
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
}
