package service;

import beans.Member;
import cache.CacheManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class MemberService {

    public void readInstructionFile() {
        File file = new File("./resources/instructions.txt");
        BufferedReader reader;
        String line = "";
        String instruction = "";
        String name= "";
        String birthday = "";
        String pass = "";
        int mobile = 0;
        String fee = "";


        try {
            reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                String[] lineArr = line.split(";");
                instruction = lineArr[0];

                if (instruction.equals("add")) {
                    name = lineArr[1];
                    birthday = lineArr[2];
                    pass = lineArr[3];
                    mobile = Integer.parseInt(lineArr[4]);
                    fee = lineArr[5];
                    addMember(name, birthday, pass, mobile, fee);
                }

                if (instruction.equals("save")) {
                    writeToMembersFile();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found" + e);
        } catch (IOException e) {
            System.out.println("Exception while reading file" + e);
        }
    }

    public void addMember(String name, String birthday, String pass, int mobile, String fee) {
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
            if (m.getName().equals(member.getName()) && m.getMobile() == member.getMobile()) {
                m.setBirthday(member.getBirthday());
                m.setPass(member.getPass());
                m.setFee(member.getFee());
            }
        }
    }

    public boolean checkIfMemberExist(Member member) {
        ArrayList<Member> memberList = CacheManager.getInstance().getMemberList();

        Optional<Member> any = memberList.stream()
                .filter(m -> m.getName().equals(member.getName()) && m.getMobile() == member.getMobile()).findAny();

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
