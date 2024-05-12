package Users.Setup;

import MeetingSchedule.Organization.State.Town.TimeTableBoard;
import Users.User;

import java.util.Objects;
import java.util.Random;

public class PersonGenerator {
    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 30;
    private static final String[] GENDERS = {"MALE", "FEMALE"};
    private static final String[] MALE_NAMES = {
            "James", "John", "Robert", "Michael", "William", "David", "Richard", "Joseph", "Charles", "Thomas",
            "Daniel", "Matthew", "Anthony", "Donald", "Steven", "Paul", "Andrew", "Joshua", "Kenneth", "Kevin",
            "Brian", "Edward", "Ronald", "Timothy", "Jason", "Jeffrey", "Ryan", "Gary", "Nicholas", "Eric",
            "Stephen", "Jacob", "Larry", "Frank", "Jonathan", "Scott", "Justin", "Raymond", "Brandon", "Gregory",
            "Samuel", "Benjamin", "Patrick", "Jack", "Henry", "Dennis", "Jerry", "Aaron", "Douglas", "Peter",
            "Harold", "Keith", "Johnny", "Roy", "Jordan", "Randy", "Carlos", "Howard", "Billy", "Louis",
            "Terry", "Sean", "Willie", "Jesse", "Ralph", "Philip", "Shawn", "Chris", "Johnny", "Allen",
            "Leonard", "Bradley", "Bryan", "Miguel", "Albert", "Derek", "Adam", "Arthur", "Lawrence", "Wayne",
            "Roy", "Gabriel", "Carlos", "Victor", "Martin", "Harry", "Fred", "Eugene", "Ethan", "Russell",
            "Elijah", "Bobby", "Howard", "Jack", "Billy", "Mason", "Danny", "Earl", "Allen", "Evan"
    };

    private static final String[] FEMALE_NAMES = {
            "Mary", "Patricia", "Jennifer", "Linda", "Elizabeth", "Barbara", "Susan", "Jessica", "Sarah", "Karen",
            "Nancy", "Lisa", "Betty", "Dorothy", "Sandra", "Ashley", "Kimberly", "Donna", "Emily", "Carol",
            "Michelle", "Laura", "Amanda", "Stephanie", "Nicole", "Christine", "Marie", "Janet", "Catherine", "Frances",
            "Ann", "Joyce", "Diane", "Alice", "Julie", "Heather", "Teresa", "Doris", "Gloria", "Evelyn",
            "Jean", "Cheryl", "Mildred", "Katherine", "Joan", "Ashley", "Judith", "Rose", "Janice", "Kelly",
            "Nicole", "Judy", "Christina", "Kathy", "Theresa", "Beverly", "Denise", "Tammy", "Irene", "Jane",
            "Lori", "Rachel", "Marilyn", "Andrea", "Kathryn", "Louise", "Sara", "Anne", "Jacqueline", "Wanda",
            "Bonnie", "Julia", "Ruby", "Lois", "Tina", "Phyllis", "Norma", "Paula", "Diana", "Annie",
            "Lillian", "Emily", "Robin", "Peggy", "Crystal", "Gladys", "Rita", "Dawn", "Connie", "Florence",
            "Tracy", "Edna", "Tiffany", "Carmen", "Rosa", "Cindy", "Grace", "Wendy", "Victoria", "Hazel"
    };


    private static final String[] SURNAMES = {
            "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson",
            "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King",
            "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter",
            "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins",
            "Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", "Morgan", "Bell", "Murphy", "Bailey",
            "Rivera", "Cooper", "Richardson", "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez",
            "James", "Watson", "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross",
            "Henderson", "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington",
            "Butler", "Simmons", "Foster", "Gonzales", "Bryant", "Alexander", "Russell", "Griffin", "Diaz", "Hayes",
            "Hudson", "Murray", "Freeman", "Wells", "Webb", "Simpson", "Stevens", "Tucker", "Porter", "Hunter",
            "Hicks", "Crawford", "Henry", "Boyd", "Mason", "Morales", "Kennedy", "Warren", "Dixon", "Ramos",
            "Reyes", "Burns", "Gordon", "Shaw", "Holmes", "Rice", "Robertson", "Hunt", "Black", "Daniels",
            "Palmer", "Mills", "Nichols", "Grant", "Knight", "Ferguson", "Rose", "Stone", "Hawkins", "Dunn",
            "Perkins", "Hudson", "Spencer", "Gardner", "Stephens", "Payne", "Pierce", "Berry", "Matthews", "Arnold",
            "Wagner", "Willis", "Ray", "Watkins", "Olson", "Carroll", "Duncan", "Snyder", "Hart", "Cunningham",
            "Bradley", "Lane", "Andrews", "Ruiz", "Harper", "Fox", "Riley", "Armstrong", "Carpenter", "Weaver"
    };


    private static final Random random = new Random();

    public static User generateUser(int position, TimeTableBoard workspace) {
        String gender = getRandomElement(GENDERS);
        String name = Objects.equals(gender, "MALE") ? getRandomElement(MALE_NAMES) : getRandomElement(FEMALE_NAMES);
        String surname = getRandomElement(SURNAMES);
        int age = getRandomAge();

        return User.createUser(position, name, surname, age, gender, workspace);
    }

    private static String getRandomElement(String[] array) {
        return array[random.nextInt(array.length)];
    }

    private static int getRandomAge() {
        return random.nextInt(MAX_AGE - MIN_AGE + 1) + MIN_AGE;
    }


}
