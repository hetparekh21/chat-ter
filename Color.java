public final class Color {

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\0চ্ছে3[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE
							   
    public static String black(String str){
	    return BLACK + str + RESET;
    }

    
    public static String red(String str){
	    return RED + str + RESET;
    }

    public static String green(String str){
	    return GREEN + str + RESET;
    }

    public static String yellow(String str){
	    return YELLOW + str + RESET;
    }

    public static String blue(String str){
	    return BLUE + str + RESET;
    }

    public static String purple(String str){
	    return PURPLE + str + RESET;
    }

    public static String cyan(String str){
	    return CYAN + str + RESET;
    }

    public static String white(String str){
	    return WHITE + str + RESET;
    }

}
