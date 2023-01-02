package service;

import beans.Member;
import cache.CacheManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemberService {

    public void readInstructionFile() {
        File file = new File("./resources/instructions.txt");
        BufferedReader reader;
        String line = "";
        String instruction = "";
        String name= "";
        String birthday = "";
        String pass = "";
        String mobile = "";
        String fee = "";
        String[] lineArr;
        String key = "";
        String value = "";


        try {
            reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                if (line.contains(";")) {
                    lineArr = line.split(";");
                    instruction = lineArr[0];

                    if (instruction.equals("add")) {
                        name = lineArr[1];
                        birthday = lineArr[2];
                        pass = lineArr[3];
                        mobile = lineArr[4];
                        fee = lineArr[5];
                        addMember(name, birthday, pass, mobile, fee);
                    }

                    if (instruction.equals("save")) {
                        writeToMembersFile();
                    }

                    if (instruction.equals("delete")) {
                        name = lineArr[1];
                        mobile = lineArr[2];
                        deleteMember(name, mobile);
                    }
                }
                else {
                    lineArr = line.split(" ");
                    instruction = lineArr[0];

                    if (instruction.equals("query")) {
                        key = lineArr[1];
                        value= lineArr[2];
                    }

                    if (key.equals("pass") && value.equals("Silver")) {
                        Double totalFee = getTotalMemberShipFees(key, value);
                        writeQuery1ToReportFile(key, value, totalFee);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found" + e);
        } catch (IOException e) {
            System.out.println("Exception while reading file" + e);
        }
    }

    public void deleteMember(String name, String mobile) {
        Member member = new Member(name, "", "", mobile, "");
        if(checkIfMemberExist(member)) {
            ArrayList<Member> memberList = CacheManager.getInstance().getMemberList();

            List<Member> filteredList = memberList.stream()
                    .filter(m -> !m.getName().equals(name) && !m.getMobile().equals(mobile))
                    .collect(Collectors.toList());

            CacheManager.getInstance().setMemberList((ArrayList)filteredList);
        }
        else {
            System.out.println("Member with given name and mobile number doesnt exist");
        }
    }

    public void writeQuery1ToReportFile(String key, String value, Double queryOutput) {
        String queryHeader = "--query " + key + " " + value + "--";
        String queryContent = "Total membership fees = $" + queryOutput;

        StringBuilder sb = new StringBuilder();
        sb.append(queryHeader).append("\n").append(queryContent).append("\n");

        File file = new File("./resources/reportFile.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append(sb);
        } catch (IOException e) {
            System.out.println("Exception while writing to a file" + e);
        }
    }

    public double getTotalMemberShipFees(String key, String value) {
        ArrayList<Member> memberList = CacheManager.getInstance().getMemberList();
        List<Member> filteredMember = memberList.stream()
                .filter(m -> m.getPass().equals(value))
                .collect(Collectors.toList());

        double sum = 0.0;
        for (Member member: filteredMember) {
            sum = sum + Double.parseDouble(member.getFee().substring(1));
        }

        return sum;
    }

    public void addMember(String name, String birthday, String pass, String mobile, String fee) {
        Member member = new Member(name, birthday, pass, mobile, fee);
        if(checkIfMemberExist(member)) {
            updatingMember(member);
        }
        else {
            addingMember(member);
        }
    }

    public void addingMember(Member member) {
        ArrayList<Member> memberList = CacheManager.getInstance().getMemberList();
        memberList.add(member);
    }

    public void updatingMember(Member member) {
        ArrayList<Member> memberList = CacheManager.getInstance().getMemberList();

        for (Member m: memberList) {
            if (m.getName().equals(member.getName()) && m.getMobile().equals(member.getMobile())) {
                m.setBirthday(member.getBirthday());
                m.setPass(member.getPass());
                m.setFee(member.getFee());
            }
        }
    }

    public boolean checkIfMemberExist(Member member) {
        ArrayList<Member> memberList = CacheManager.getInstance().getMemberList();

        Optional<Member> any = memberList.stream()
                .filter(m -> m.getName().equals(member.getName()) && m.getMobile().equals(member.getMobile())).findAny();

        if (any.isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void writeToMembersFile() {
        ArrayList<Member> memberList = CacheManager.getInstance().getMemberList();
        try {
            new ObjectMapper().writeValue(new File("./resources/output.json"), memberList);
        } catch (IOException e) {
            System.out.println("Exception while writing to a file" + e);
        }
    }
}
