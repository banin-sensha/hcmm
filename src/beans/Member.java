package beans;

import com.sun.xml.internal.ws.util.StringUtils;

import java.time.LocalDate;
import java.time.Period;

public class Member {
    private String name;

    private String birthday;

    private String pass;

    private String mobile;

    private String fee;

    private int age;

    public Member(String name, String birthday, String pass, String mobile, String fee) {
        this.name = name;
        this.birthday = birthday;
        this.pass = pass;
        this.mobile = mobile;
        this.fee = fee;

        calculateAge();
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

    public void calculateAge() {
        if (!this.birthday.isEmpty() && this.birthday != null) {
            LocalDate dob = LocalDate.parse(this.birthday);
            LocalDate curDate = LocalDate.now();

            if (dob != null && curDate != null) {
                this.age = Period.between(dob, curDate).getYears();
            }
        }
    }

    public int getAge() {
        return age;
    }
}
