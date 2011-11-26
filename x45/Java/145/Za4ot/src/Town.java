import java.lang.reflect.Field;

/**
 * @author: Yuri Denison
 * @since: 24.03.11
 */
public class Town {
    public static Town MSK = new Town();
    public static Town SPB = new Town();

    /**
     * Town getter by id
     *
     * @param id id of town
     * @return Town object with input id
     *         If there is no such id returns null
     */
    public static Town get(int id) {
        try {
            return (Town) Town.class.getFields()[id].get(new Town());
        } catch (IndexOutOfBoundsException ignored) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Town getter by name
     *
     * @param name Name of town
     * @return Town object with input name
     *         If there is no town with this name returns null
     */
    public static Town get(String name) {
        try {
            for (Field f : Town.class.getFields()) {
                if (f.getName().equals(name)) {
                    return (Town) f.get(new Town());
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(Town.MSK);
        System.out.println(Town.SPB);
        System.out.println(Town.get(0));
        System.out.println(Town.get(1));
        System.out.println(Town.get(2));
        System.out.println(Town.get("SPB"));
        System.out.println(Town.get("AAA"));
    }
}
