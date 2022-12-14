package beans;

public class Member {
    private String name;

    private String birthday;

    private String pass;

    private String mobile;

    private String fee;

    public Member(String name, String birthday, String pass, String mobile, String fee) {
        this.name = name;
        this.birthday = birthday;
        this.pass = pass;
        this.mobile = mobile;
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
