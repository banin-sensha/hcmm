import cache.CacheManager;
import service.MemberService;

public class Main {
    public static void main(String[] args) {
        CacheManager.getInstance().createMemberCache();

        MemberService memberService = new MemberService();
        memberService.readInstructionFile();
    }
}