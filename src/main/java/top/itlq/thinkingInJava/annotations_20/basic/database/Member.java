package top.itlq.thinkingInJava.annotations_20.basic.database;

@DBTable
public class Member {
    @SQLString(value = 32,constraints = @Constraints(primaryKey = true))
    private String key;

    @SQLInteger
    private Integer age;

    // 注解元素里必有value
    @SQLString(30)
    private String name;

    @SQLString(100)
    private String aim;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }
}
