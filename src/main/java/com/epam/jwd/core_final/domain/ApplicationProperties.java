package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.util.PropertyReaderUtil;

import static java.lang.Integer.parseInt;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public class ApplicationProperties {
    //todo
    public static final ApplicationProperties APPLICATION_PROPERTIES = new ApplicationProperties();

    private ApplicationProperties() {
    }

    private final String inputRootDir = PropertyReaderUtil.loadProperties("inputRootDir");
    private final String spaceshipsFileName = PropertyReaderUtil.loadProperties("spaceshipsFileName");
    private final Integer fileRefreshRate = parseInt(PropertyReaderUtil.loadProperties("fileRefreshRate"));
    private final String dateTimeFormat = PropertyReaderUtil.loadProperties("dateTimeFormat");
    private final String outputRootDir = PropertyReaderUtil.loadProperties("outputRootDir");
    private final String crewFileName = PropertyReaderUtil.loadProperties("crewFileName");
    private final String missionsFileName = PropertyReaderUtil.loadProperties("missionsFileName");

    public String getInputRootDir() {
        return inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public String getMissionsFileName() {
        return missionsFileName;
    }

    public String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }
}