public class Main {
    public static void main(String[] args) {
        Search catalogue = SearchCatalogue.getInstance();

        GuestUser guest1 = new GuestUser(catalogue);

        System.out.println(guest1.searchQuestions("Java tutorial"));
        System.out.println(guest1.searchQuestions("[tag]:Java"));
        System.out.println(guest1.searchQuestions("[user]:lbihani9"));

        Account acc1 = new Account("Lokesh", "lbihani9", "abc@gmail.com", "1234");

        Member m1 = new Member(acc1, catalogue);
        System.out.println(m1.searchQuestions("Java tutorial"));

//        Question q1 = m1.createQuestion("Java tutorial", "random..", );
    }
}