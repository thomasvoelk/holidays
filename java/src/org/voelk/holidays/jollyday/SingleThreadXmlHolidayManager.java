package org.voelk.holidays.jollyday;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.*;

import org.joda.time.ReadableInterval;

import de.jollyday.CalendarHierarchy;
import de.jollyday.Holiday;
import de.jollyday.HolidayManager;
import de.jollyday.config.Configuration;
import de.jollyday.config.Holidays;
import de.jollyday.parser.HolidayParser;
import de.jollyday.util.XMLUtil;

public class SingleThreadXmlHolidayManager extends HolidayManager {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(SingleThreadXmlHolidayManager.class
            .getName());
    /**
     * The configuration prefix for parser implementations.
     */
    private static final String PARSER_IMPL_PREFIX = "parser.impl.";
    /**
     * prefix of the config files.
     */
    private static final String FILE_PREFIX = "holidays/Holidays";
    /**
     * suffix of the config files.
     */
    private static final String FILE_SUFFIX = ".xml";

    /**
     * Parser cache by XML class name.
     */
    private final Map<String, HolidayParser> parserCache = new HashMap<String, HolidayParser>();
    /**
     * Configuration parsed on initialization.
     */
    protected Configuration configuration;

    @Override
    public Set<Holiday> getHolidays(int year, final String... args) {
        Set<Holiday> holidaySet = new HashSet<Holiday>();
        getHolidays(year, configuration, holidaySet, args);
        return holidaySet;
    }

    /**
     * Calls <code>getHolidays(year, args)</code> for each year within the
     * interval and returns a list of holidays which are then contained in the
     * interval.
     */
    @Override
    public Set<Holiday> getHolidays(ReadableInterval interval, final String... args) {
        if (interval == null) {
            throw new IllegalArgumentException("Interval is NULL.");
        }
        Set<Holiday> holidays = new HashSet<Holiday>();
        for (int year = interval.getStart().getYear(); year <= interval
                .getEnd().getYear(); year++) {
            Set<Holiday> yearHolidays = getHolidays(year, args);
            for (Holiday h : yearHolidays) {
                if (interval.contains(h.getDate().toDateMidnight())) {
                    holidays.add(h);
                }
            }
        }
        return holidays;
    }

    private void getHolidays(int year, final Configuration c,
                             Set<Holiday> holidaySet, final String... args) {
        if (LOG.isLoggable(Level.FINER)) {
            LOG.finer("Adding holidays for " + c.getDescription());
        }
        parseHolidays(year, holidaySet, c.getHolidays());
        if (args != null && args.length > 0) {
            String hierarchy = args[0];
            for (Configuration config : c.getSubConfigurations()) {
                if (hierarchy.equalsIgnoreCase(config.getHierarchy())) {
                    getHolidays(year, config, holidaySet,
                            copyOfRange(args, 1, args.length));
                    break;
                }
            }
        }
    }
    

    /**
     * Copies the specified range from the original array to a new array. This
     * is a replacement for Java 1.6 Arrays.copyOfRange() specialized in String.
     *
     * @param original
     *            the original array to copy range from
     * @param from
     *            the start of the range to copy from the original array
     * @param to
     *            the inclusive end of the range to copy from the original array
     * @return the copied range
     */
    private String[] copyOfRange(final String[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0) {
            throw new IllegalArgumentException(from + " > " + to);
        }
        String[] copy = new String[newLength];
        System.arraycopy(original, from, copy, 0,
                Math.min(original.length - from, newLength));
        return copy;
    }

    private void parseHolidays(int year, Set<Holiday> holidays,
                               final Holidays config) {
        Collection<HolidayParser> parsers = getParsers(config);
        for (HolidayParser p : parsers) {
            p.parse(year, holidays, config);
        }
    }

    private Collection<HolidayParser> getParsers(final Holidays config) {
        Collection<HolidayParser> parsers = new HashSet<HolidayParser>();
        for (Method m : config.getClass().getMethods()) {
            if (isGetter(m) && m.getReturnType() == List.class) {
                try {
                    List<?> l = (List<?>) m.invoke(config);
                    if (l.size() > 0) {
                        String className = l.get(0).getClass().getName();
                        if (!parserCache.containsKey(className)) {
                            String propName = PARSER_IMPL_PREFIX + className;
                            Properties configProps = getProperties();
                            if (configProps.containsKey(
                                    propName)) {
                                HolidayParser hp = (HolidayParser) Class
                                        .forName(
                                                configProps
                                                        .getProperty(propName))
                                        .newInstance();
                                parserCache.put(className, hp);
                            }
                        }
                        if (parserCache.containsKey(className)) {
                            parsers.add(parserCache.get(className));
                        }
                    }
                } catch (Exception e) {
                    throw new IllegalStateException("Cannot create parsers.", e);
                }
            }
        }
        return parsers;
    }

    /**
     * Returns true if the provided <code>Method</code> is a getter method.
     *
     * @param method
     *            The method to check if it is a getter.
     * @return is a getter method
     */
    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get")
                && method.getParameterTypes().length == 0
                && !void.class.equals(method.getReturnType());
    }

    /**
     * Initializes the XMLManager by loading the holidays XML file as resource
     * from the classpath. When the XML file is found it will be unmarshalled
     * with JAXB to some Java classes.
     */
    @Override
    public void init(final String country) {
        String fileName = getConfigurationFileName(country);
        try {
            configuration = XMLUtil.unmarshallConfiguration(getClass()
                    .getClassLoader().getResource(fileName).openStream());
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Cannot instantiate configuration.", e);
        }
        validateConfigurationHierarchy(configuration);
        logHierarchy(configuration, 0);
    }

    protected static void logHierarchy(final Configuration c, int level) {
        if (LOG.isLoggable(Level.FINER)) {
            StringBuilder space = new StringBuilder();
            for (int i = 0; i < level; i++) {
                space.append("-");
            }
            LOG.finer(space + " " + c.getDescription() + "(" + c.getHierarchy()
                    + ").");
            for (Configuration sub : c.getSubConfigurations()) {
                logHierarchy(sub, level + 1);
            }
        }
    }

    public static String getConfigurationFileName(final String country) {
        return FILE_PREFIX + "_" + country + FILE_SUFFIX;
    }

    protected static void validateConfigurationHierarchy(final Configuration c) {
        Map<String, Integer> hierarchyMap = new HashMap<String, Integer>();
        Set<String> multipleHierarchies = new HashSet<String>();
        for (Configuration subConfig : c.getSubConfigurations()) {
            String hierarchy = subConfig.getHierarchy();
            if (!hierarchyMap.containsKey(hierarchy)) {
                hierarchyMap.put(hierarchy, 1);
            } else {
                int count = hierarchyMap.get(hierarchy);
                hierarchyMap.put(hierarchy, ++count);
                multipleHierarchies.add(hierarchy);
            }
        }
        if (multipleHierarchies.size() > 0) {
            StringBuilder msg = new StringBuilder();
            msg.append("Configuration for "
                    + c.getHierarchy()
                    + " contains  multiple SubConfigurations with the same hierarchy id. ");
            for (String hierarchy : multipleHierarchies) {
                msg.append(hierarchy + " "
                        + hierarchyMap.get(hierarchy).toString() + " times ");
            }
            throw new IllegalArgumentException(msg.toString().trim());
        }
        for (Configuration subConfig : c.getSubConfigurations()) {
            validateConfigurationHierarchy(subConfig);
        }
    }

    /**
     * Returns the configurations hierarchy.<br>
     * i.e. Hierarchy 'us' -> Children 'al','ak','ar', ... ,'wv','wy'. Every
     * child might itself have children. The ids be used to call
     * getHolidays()/isHoliday().
     */
    @Override
    public CalendarHierarchy getCalendarHierarchy() {
        return createConfigurationHierarchy(configuration, null);
    }

    private static CalendarHierarchy createConfigurationHierarchy(
            final Configuration c, CalendarHierarchy h) {
        h = new CalendarHierarchy(h, c.getHierarchy());
        for (Configuration sub : c.getSubConfigurations()) {
            CalendarHierarchy subHierarchy = createConfigurationHierarchy(sub,
                    h);
            h.getChildren().put(subHierarchy.getId(), subHierarchy);
        }
        return h;
    }

}
